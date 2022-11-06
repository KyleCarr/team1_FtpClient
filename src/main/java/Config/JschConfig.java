package Config;

import com.jcraft.jsch.*;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Predicate;

public final class JschConfig {

    private static JschConfig INSTANCE;
    private final String userHomeDir = System.getProperty("user.home"); //move to SftpConnection
    private static final int SESSION_TIMEOUT = 10000;

    public static JschConfig getInstance(){
        if(INSTANCE == null){
            INSTANCE = new JschConfig();
        }
        return INSTANCE;
    }
    public ChannelSftp setupJsch(String remoteHost, String username, String password) throws JSchException {
        JSch jsch = new JSch();
        jsch.setKnownHosts(userHomeDir.replace('\\','/') + "/.ssh/known_hosts");//move to SftpConnection

        Session jschSession = jsch.getSession(username, remoteHost,22);
        jschSession.setPassword(password);

        debug(jschSession);

        //  jsch.getHostKeyRepository().add(jschSession.getHostKeyRepository().,null);
        jschSession.connect(SESSION_TIMEOUT);
        return (ChannelSftp) jschSession.openChannel("sftp");
    }

    public Session debug(Session jschSession){
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        jschSession.setConfig(config);
        return jschSession;
    }
}

