package application.handler;

import application.DirectoryItem;
import application.connection.EstablishedConnection;
import application.connection.ftpconnection.FtpConnectionFactory;
import application.connection.sftpconnection.SftpConnectionFactoryProxy;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public abstract class AbstractHandler {
    Scanner input = new Scanner(System.in);
    EstablishedConnection connection;

    static final long TIMEOUT = 300000;
    boolean connected = false;
    String connectionType;
    String remoteHost;
    String username;
    String password;

    public abstract void handleInput() throws IOException;

    private void establishConnection() {
        System.out.println("type sftp or ftp");
        this.connectionType = input.nextLine();
        System.out.println("enter remotehost");
        this.remoteHost = input.nextLine();
        System.out.println("enter username");
        this.username = input.nextLine();
        System.out.println("enter password");
        this.password = input.nextLine();
    }

    protected void connect() {
        try {
            establishConnection();
            if (this.connectionType.equalsIgnoreCase("sftp")) {
                connection = new SftpConnectionFactoryProxy().connect(remoteHost, username, password);
                System.out.println("sftp connection established");
                this.connected = true;

            } else if (this.connectionType.equalsIgnoreCase("ftp".toLowerCase())) {
                connection = new FtpConnectionFactory().connect(remoteHost, username, password);
                System.out.println("ftp connection established");
                this.connected = true;

            } else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            System.out.println("Error: entered information is invalid");
            connect();
        }
    }

    protected void help() {
        System.out.println("Welcome to FTP/SFTP terminal project! Here are the available commands:");
        System.out.println("dir: Prints directory onto console");
        System.out.println("get <filepath> <destination> OR get <filepath>: Downloads file to chosen directory. " +
                "If no destination is provided, file will be sent to Downloads folder");
        System.out.println("put <filepath> <destination>: Uploads file to destination directory");
        System.out.println("clear: Clears console of all output");
        System.out.println("find <filename>: Searches remote directory for the given filename and outputs all searched paths on console");
        System.out.println("cd <path>: Enters given directory");
        System.out.println("pwd: Prints current remote directory onto console");
        System.out.println("q: Quits program");
    }

    protected void clear() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    protected void get(List<String> commands) {
        String file = commands.get(1);
        if (commands.size() == 2) {
            System.out.println(connection.getFile(file));
        } else {
            String localDirectory = commands.get(2);
            System.out.println(connection.getFile(file, localDirectory));
        }
    }

    protected void put(List<String> commands) {
        String file = commands.get(1);
        String localDirectory = commands.get(2);
        System.out.println(connection.putFile(file, localDirectory));
    }

    protected void getDirectories() {
        List<DirectoryItem> files = connection.listDirectory();
        files.forEach(System.out::println);
    }
}
