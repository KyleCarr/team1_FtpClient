package application.connection.sftpconnection;

import application.connection.EstablishedConnection;
import application.connection.ConnectionFactory;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import exception.ClientConnectionException;

import java.util.Map;


public class SftpConnectionFactory implements ConnectionFactory {

    private final Map<String,Session> jschSessionMap;

    SftpConnectionFactory(Map<String,Session> jschSessionMap){
        this.jschSessionMap = jschSessionMap;
    }

    @Override
    public EstablishedConnection connect(String remoteHost, String username, String password) {
        try {
            Session session = jschSessionMap.get(remoteHost);
            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect(CHANNEL_TIMEOUT);
            return new SftpConnection(channelSftp);
        } catch (JSchException e) {
            throw new ClientConnectionException(e);
        }
    }
}
