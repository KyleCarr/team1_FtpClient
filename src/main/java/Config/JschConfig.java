package Config;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

import java.io.IOException;

public class JschConfig {

    private final String userHomeDir = System.getProperty("user.home");
    private static final int SESSION_TIMEOUT = 10000;
    public ChannelSftp setupJsch(String remoteHost, String username, String password) throws JSchException {
        JSch jsch = new JSch();
        jsch.setKnownHosts(userHomeDir.replace('\\','/') + "/.ssh/known_hosts");

        Session jschSession = jsch.getSession(username, remoteHost,22);
        jschSession = debug(jschSession);
        jschSession.setPassword(password);
        jschSession.connect(SESSION_TIMEOUT);
        return (ChannelSftp) jschSession.openChannel("sftp");
    }

//    public SSHClient setupSshj() throws IOException {
//        SSHClient client = new SSHClient();
//        client.addHostKeyVerifier(new PromiscuousVerifier());
//        client.connect(remoteHost);
//        client.authPassword(username, password);
//        return client;
//    }

    public Session debug(Session jschSession){
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        jschSession.setConfig(config);
        return jschSession;
    }
}
