package application.connection.sftpconnection;

import config.JschConfig;
import application.connection.ConnectionFactory;
import application.connection.EstablishedConnection;
import application.connection.UserInfoImpl;
import com.jcraft.jsch.*;
import exception.ClientConnectionException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SftpConnectionFactoryProxy implements ConnectionFactory {
    private final JschConfig jschConfig = JschConfig.getInstance();
    private static final int SESSION_TIMEOUT = 10000;
    private final SftpConnectionFactory sftpConnectionFactory;
    private final Map<String,Session> jschSessionMap = new HashMap<>();

    public SftpConnectionFactoryProxy(){
        sftpConnectionFactory = new SftpConnectionFactory(jschSessionMap);
    }

    @Override
    public EstablishedConnection connect(String remoteHost, String username, String password) throws ClientConnectionException {
        Session jschSession = null;
        try {
            jschSession = jschConfig.getJsch().getSession(username, remoteHost,22);
            jschSession.setPassword(password);
            jschSession.connect(SESSION_TIMEOUT);
            jschSessionMap.put(remoteHost,jschSession);
            return sftpConnectionFactory.connect(remoteHost, username, password);
        }
        catch(JSchException e){
            if (jschSession != null && e.getMessage().contains("UnknownHostKey")){
                allowUserToAddHost(jschSession, jschConfig.getJsch(), e);
                return connect(remoteHost,username,password);
            }
            throw new ClientConnectionException(e);
        }
    }

    private static void allowUserToAddHost(Session jschSession, JSch jsch, JSchException jSchException) {
        HostKey hostKey = jschSession.getHostKey();
        String fingerPrint = hostKey.getFingerPrint(jsch);

        // The following website demonstrated how to add the host key
        // https://dentrassi.de/2015/07/13/programmatically-adding-a-host-key-with-jsch/
        try {
            byte [] key = Base64.getDecoder().decode(hostKey.getKey());
            HostKey newHostKey = new HostKey(hostKey.getHost(), key);
            HostKeyRepository hostKeyRepository = jsch.getHostKeyRepository();
            UserInfo userInfo = new UserInfoImpl();
            boolean knownHostFileAlreadyExists = JschConfig.getInstance().getKnownHostsFile().exists();
            String message = String.format(
                    "Do you wish to add host '%s' with fingerprint:\n\n    %s\n\n to your known hosts file? (y/n)",
                    hostKey.getHost(), fingerPrint);
            boolean addToKnownHosts = userInfo.promptYesNo(message);

            if (addToKnownHosts) {
                hostKeyRepository.add(newHostKey, userInfo);
                jsch.setHostKeyRepository(hostKeyRepository);
                if (!knownHostFileAlreadyExists){
                    try {
                        Path knownHostsPath = JschConfig.getInstance().getKnownHostsFile().toPath();
                        Set<PosixFilePermission> perms = Files.readAttributes(knownHostsPath, PosixFileAttributes.class).permissions();
                        perms.clear();
                        perms.add(PosixFilePermission.OWNER_WRITE);
                        perms.add(PosixFilePermission.OWNER_READ);
                        Files.setPosixFilePermissions(knownHostsPath, perms);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            else {
                throw new ClientConnectionException(jSchException);
            }
        } catch (JSchException e) {
            throw new RuntimeException(e);
        }
    }
}
