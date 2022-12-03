package application.connection.sftpconnection;

import application.DirectoryItem;
import application.connection.EstablishedConnection;
import com.jcraft.jsch.ChannelSftp;

import java.util.Arrays;
import java.util.List;


/**
 * Proxy class for SftpConnection. It caches the user's current directory on the remote SFTP
 * server, returning that when a pwd() function is called. It also keeps the remoteHost and returns
 * the remote host and current directory when the getPrompt() method is called.
 */
public class SftpConnectionProxy extends EstablishedConnection {

    private final SftpConnection sftpConnection;
    private final String remoteHost;
    private String currentDirectory;

    public SftpConnectionProxy(ChannelSftp channelSftp, String remoteHost){
        this.sftpConnection = new SftpConnection(channelSftp);
        this.remoteHost = remoteHost;
        this.currentDirectory = "/";
    }

    @Override
    public String getFile(String filename) {
        return sftpConnection.getFile(filename);
    }

    @Override
    public String getFile(String filename, String remoteHost) {
        return sftpConnection.getFile(filename, remoteHost);
    }

    @Override
    public String putFile(String filename, String remoteHost) {
        return sftpConnection.putFile(filename, remoteHost);
    }
    @Override
    public List<DirectoryItem> listDirectory() {
        return sftpConnection.listDirectory();
    }

    @Override
    public boolean cd(String path) {
        boolean result = sftpConnection.cd(path);
        if (result) {
            currentDirectory = updateCurrentPath(path, currentDirectory);
        }
        return result;
    }

    @Override
    public String getPrompt() {
        return remoteHost + ":" + pwd() + "> ";
    }

    @Override
    public String pwd() {
        return currentDirectory;
    }

    @Override
    public void find(String search) {
        sftpConnection.find(search);
    }

    @Override
    public void disconnect() {
        sftpConnection.disconnect();
    }

    private String updateCurrentPath(String inputPath, String currentDirectory) {
        if (inputPath.equals("..")){
            String[] dirArray = currentDirectory.split("/");
            if (dirArray.length > 1) {
                currentDirectory = String.join("/", Arrays.copyOfRange(dirArray, 0, dirArray.length - 1));
            }
        }
        else if (!inputPath.equals(".")){
            if (inputPath.startsWith("/")){
                currentDirectory = inputPath;
            }
            else {
                if (currentDirectory.endsWith("/")){
                    currentDirectory = currentDirectory + inputPath;
                }
                else {
                    currentDirectory = currentDirectory + "/" + inputPath;
                }
            }
        }
        return currentDirectory;
    }
}
