package application.connection.ftpconnection;

import application.connection.EstablishedConnection;
import application.connection.ConnectionFactory;

public class FtpConnectionFactory implements ConnectionFactory {
    @Override
    public EstablishedConnection connect(String remoteHost, String username, String password) {
        return new FtpConnectionProxy("/",remoteHost, username, password);
    }
}