package application.connection.ftpconnection;

import exception.ClientConnectionException;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static application.connection.ConnectionFactory.CHANNEL_TIMEOUT;

public class FtpConnectionHelper {
    static URLConnection doConnect(String remoteHost, String initialDir, String username, String password, String filename) {
        URL url = buildURL(remoteHost, initialDir, username, password, filename);
        return doConnect(url);
    }

    private static URL buildURL(String remoteHost, String initialDir, String username, String password, String filename) {
        String type = "d";
        if (!initialDir.startsWith("/")){
            initialDir = "/" + initialDir;
        }
        if (!initialDir.endsWith("/")){
            initialDir = initialDir + "/";
        }
        if (StringUtils.isEmpty(filename)){
            filename = "";
        }
        else {
            type = "i";
        }
        String urlString = "ftp://" + username + ":" + password + "@" + remoteHost  + ":21" + initialDir + filename + ";type=" + type;
        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            throw new ClientConnectionException("Invalid URL String: " + urlString + ". Wrapped exception message: " + e.getMessage(), e);
        }
    }

    private static URLConnection doConnect(URL url) {
        try {
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(CHANNEL_TIMEOUT);
            urlConnection.connect();
            return urlConnection;
        } catch (IOException e) {
            throw new ClientConnectionException(e.getMessage(), e);
        }
    }
}
