package application.connection;

import application.DirectoryItem;

import java.util.List;

public abstract class EstablishedConnection {
    public abstract String getFile(String filename);

    public abstract String getFile(String filename, String remoteHost);

    public abstract String putFile(String filename, String remoteHost);

    public abstract List<DirectoryItem> listDirectory();

    public abstract boolean cd(String path);

    public abstract String pwd();

    public abstract void find(String search);

    public abstract void disconnect();

    public abstract String getPrompt();
}
