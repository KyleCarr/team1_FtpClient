package application.connection.ftpconnection;

import application.DirectoryItem;
import application.connection.EstablishedConnection;

import java.io.IOException;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

import static application.connection.ftpconnection.FtpConnectionHelper.doConnect;

/**
 * Proxy class for FtpConnection class. As the UrlConnection class is stateless, this class persists
 * the remote connection information as well as the current directory to provide a stateful view to the
 * FTP Server the user is connected to.
 *
 * It contains the implementation of the functions listed below. The remaining function calls are passed
 * on to the FTPConnection class.
 *
 *      cd(), pwd(), getPrompt()
 */

public class FtpConnectionProxy extends EstablishedConnection {
    private final String remoteHost;
    private final String username;
    private final String password;
    private String currentDirectory;

    public FtpConnectionProxy(String directory, String remoteHost, String username, String password) {
        this.remoteHost = remoteHost;
        currentDirectory = directory;
        this.username = username;
        this.password = password;
        doConnect(remoteHost, currentDirectory, username, password, null);
    }

    @Override
    public String getFile(String filename) {
        FtpConnection ftpConnection = new FtpConnection(doConnect(remoteHost, currentDirectory, username, password, filename));
        return ftpConnection.getFile(filename);
    }

    @Override
    public String getFile(String filename, String localDirectory) {
        FtpConnection ftpConnection = new FtpConnection(doConnect(remoteHost, currentDirectory, username, password, filename));
        return ftpConnection.getFile(filename, localDirectory);
    }

    @Override
    public List<DirectoryItem> listDirectory() {
        FtpConnection ftpConnection = new FtpConnection(doConnect(remoteHost, currentDirectory, username, password, null));
        return ftpConnection.listDirectory();
    }

    @Override
    public String putFile(String filename, String localDirectory) {
        FtpConnection ftpConnection = new FtpConnection(doConnect(remoteHost, currentDirectory, username, password, localDirectory));
        return ftpConnection.putFile(filename, localDirectory);
    }

    @Override
    public boolean cd(String inputPath) {
        String path = inputPath;
        if (path.equals("..")) {
            String[] dirArray = currentDirectory.split("/");
            if (dirArray.length > 1) {
                path = String.join("/", Arrays.copyOfRange(dirArray, 0, dirArray.length - 1)) + "/";
            }
        } else {
            if (!path.endsWith("/")) {
                path += "/";
            }
            if (!path.startsWith("/")) {
                if (currentDirectory.endsWith("/")) {
                    path = currentDirectory + path;
                } else {
                    path = currentDirectory + "/" + path;
                }
            }
        }
        // connect to verify the directory exists
        URLConnection urlConnection = doConnect(remoteHost, path, username, password, null);
        try {
            urlConnection.getInputStream().read();
            currentDirectory = path;
        } catch (IOException e) {
            if (e.getMessage().contains(inputPath + ":550")){
                System.out.println("Invalid Directory Path: " + inputPath);
                return false;
            }
            else {
                System.out.println("IOException: " + e.getMessage() + "\n    " + e);
                return false;
            }
        }

        return true;
    }

    @Override
    public String pwd() {
        return currentDirectory.length() > 1 ? currentDirectory.substring(0, currentDirectory.length() - 1) : currentDirectory;
    }

    @Override
    public void find(String search) {
        List<DirectoryItem> activeDirectory;
        String directory;
        activeDirectory =  listDirectory();

        for(int i = 0; i < activeDirectory.size()-1; i++){
            directory =activeDirectory.get(i).getName();
            if(directory.equals(search)){
                System.out.println( "File was found at" + pwd() + "/" + directory);
                return;
            }
            if(cd(directory) == true){
                find(search);
            }
        }

    }

    @Override
    public void disconnect() {

    }

    @Override
    public String getPrompt() {
        return remoteHost + ":" + pwd() + "> ";
    }
}
