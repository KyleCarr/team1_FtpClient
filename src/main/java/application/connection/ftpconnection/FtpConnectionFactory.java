package application.connection.ftpconnection;

import application.connection.ConnectionFactory;
import application.connection.EstablishedConnection;
import exception.ClientConnectionException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FtpConnectionFactory implements ConnectionFactory {
    @Override
    public EstablishedConnection connect(String remoteHost, String username, String password) {
        URL url = buildURL(remoteHost, username, password);
        try {
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(CHANNEL_TIMEOUT);
            urlConnection.connect();
            return new FtpConnection(urlConnection);
        } catch (IOException e) {
            throw new ClientConnectionException(e.getMessage(), e);
        }
    }

    private URL buildURL(String remoteHost, String username, String password) {
        String urlString = "ftp://" + username + ":" + password + "@" + remoteHost + "/;type=d";
        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            throw new ClientConnectionException("Invalid URL String: " + urlString + ". Wrapped exception message: " + e.getMessage(),e);
        }
    }
}