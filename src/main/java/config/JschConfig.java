package config;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;

import java.io.File;

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
        if (INSTANCE == null) {
            try {
                INSTANCE = new JschConfig();
            } catch (JSchException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return INSTANCE;
    }

    public JSch getJsch() {
        return jsch;
    }

    public File getKnownHostsFile() {
        return new File(knownHostsFile);
    }
}

