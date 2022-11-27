package application.connection;

import application.DirectoryItem;

import java.util.List;

public abstract class EstablishedConnection {
    public abstract boolean isConnected();
    public abstract String getFile(String filename);

    public abstract String getFile(String filename, String remoteHost);

    //public abstract String putFile(String filename);
// put src/test/resources/expected_readme.txt src/main/java/test.txt

    public abstract String putFile(String filename, String remoteHost);

    public abstract List<DirectoryItem> listDirectory();
    public abstract boolean cd(List<String> pathList );
    public abstract String pwd();
    public abstract void find(String search);
    //public void download(ChannelSftp channelSftp, String filePath, String savePath) throws SftpException;
    public abstract void disconnect();
}
