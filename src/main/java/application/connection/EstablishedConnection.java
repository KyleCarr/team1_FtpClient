package application.connection;

import application.DirectoryItem;

import java.util.List;

public abstract class EstablishedConnection {
    public abstract boolean isConnected();
    public abstract String getFile(String filename);
    public abstract List<DirectoryItem> listDirectory();
    //public void download(ChannelSftp channelSftp, String filePath, String savePath) throws SftpException;
    public abstract void disconnect();
}
