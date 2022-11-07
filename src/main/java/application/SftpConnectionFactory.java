package application;

import Config.JschConfig;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;

public class SftpConnectionFactory implements ConnectionFactory {

    private final JschConfig jschConfig = new JschConfig();

    @Override
    public EstablishedConnection connect(String remoteHost, String username, String password) {
        try {
            ChannelSftp channelSftp = jschConfig.setupJsch(remoteHost,username,password);
            channelSftp.connect(CHANNEL_TIMEOUT);
            return new SftpConnection(channelSftp);
        } catch (JSchException e) {
            throw new RuntimeException(e);
        }
    }
}
