package config;

import com.jcraft.jsch.*;

import java.io.*;

public final class JschConfig {

    private static JschConfig INSTANCE;
    private final JSch jsch;
    private final String knownHostsFile;

    private JschConfig() throws JSchException {
        jsch = new JSch();
        String userHomeDir = System.getProperty("user.home"); //move to SftpConnection
        knownHostsFile = userHomeDir.replace('\\', '/') + "/.ssh/known_hosts";
        jsch.setKnownHosts(knownHostsFile);//move to SftpConnection
    }

    public static synchronized JschConfig getInstance() {
        if(INSTANCE == null){
            try {
                INSTANCE = new JschConfig();
            } catch (JSchException e) {
                throw new RuntimeException(e.getMessage(),e);
            }
        }
        return INSTANCE;
    }
    public JSch getJsch(){
        return jsch;
    }

    public File getKnownHostsFile(){
        return new File(knownHostsFile);
    }

//    public ChannelSftp setupJsch(String remoteHost, String username, String password) throws JSchException {
//
//        Session jschSession = jsch.getSession(username, remoteHost,22);
//        jschSession.setPassword(password);
//
//        //debug(jschSession);
//
//        jschSession.connect(SESSION_TIMEOUT);
//        return (ChannelSftp) jschSession.openChannel("sftp");
//    }
//
//    public Session debug(Session jschSession){
//        java.util.Properties config = new java.util.Properties();
//        config.put("StrictHostKeyChecking", "no");
//        jschSession.setConfig(config);
//        return jschSession;
//    }
}

