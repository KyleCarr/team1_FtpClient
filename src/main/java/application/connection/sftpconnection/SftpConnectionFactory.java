package application.connection.sftpconnection;

import application.connection.EstablishedConnection;
import application.connection.ConnectionFactory;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import config.JschConfig;

public class SftpConnectionFactory implements ConnectionFactory {

    private final JschConfig jschConfig =  JschConfig.getInstance();

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
