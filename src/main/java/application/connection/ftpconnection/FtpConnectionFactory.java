package application.connection.ftpconnection;

import application.connection.EstablishedConnection;
import application.connection.ConnectionFactory;
import exception.ClientConnectionException;

import java.net.UnknownHostException;

public class FtpConnectionFactory implements ConnectionFactory {
    @Override
    public EstablishedConnection connect(String remoteHost, String username, String password) {
        try {
            return new FtpConnectionProxy("/", remoteHost, username, password);
        }
        catch(ClientConnectionException e){
            if (e.getCause()!=null && e.getCause() instanceof UnknownHostException){
                System.out.println("Remote host '" + remoteHost + "' was not found");
            }
            else {
                System.out.println(e.getMessage() + "\n" + e);
            }
            return null;
        }
    }
}