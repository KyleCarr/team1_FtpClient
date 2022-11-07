package application;

import exception.ClientConnectionException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FtpConnectionFactory implements ConnectionFactory {
    @Override
    public EstablishedConnection connect(String remoteHost, String username, String password) {
        String urlString = "ftp://" + username + ":" + password + "@" + remoteHost + "/;type=d";
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            throw new ClientConnectionException("Invalid URL String: " + urlString + ". Wrappred exception message: " + e.getMessage(),e);
        }
        URLConnection urlConnection = null;
        try {
            urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(CHANNEL_TIMEOUT);
            urlConnection.connect();
        } catch (IOException e) {
            throw new ClientConnectionException(e.getMessage(), e);
        }
        return new FtpConnection(urlConnection);
    }
}