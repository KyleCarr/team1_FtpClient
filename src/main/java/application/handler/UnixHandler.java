package application.handler;

import application.DirectoryItem;
import application.connection.ftpconnection.FtpConnectionFactory;
import application.connection.observer.TimeoutObserver;
import application.connection.sftpconnection.SftpConnectionFactoryProxy;

import java.util.ArrayList;
import java.util.List;

public class UnixHandler extends AbstractHandler {
    private final long TIMEOUT = 300000;

    long startTime = System.currentTimeMillis();
    private TimeoutObserver observer = new TimeoutObserver();
    @Override
    public void handleInput() {
        String choice;
        System.out.println("type sftp or ftp");
        choice = input.nextLine();
        if (choice.equals("sftp")) {
            this.remoteHost = "test.rebex.net";
            this.username = "demo";
            this.password = "password";
//            System.out.println("enter remotehost");
//            this.remoteHost = input.nextLine();
//            System.out.println("enter username");
//            this.username = input.nextLine();
//            System.out.println("enter password");
//            this.password = input.nextLine();
            connection = new SftpConnectionFactoryProxy().connect(remoteHost, username, password);
            System.out.println("sftp connection established");
        }
        else {
            this.remoteHost = "ftp.dlptest.com";
            this.username = "dlpuser";
            this.password = "rNrKYTX9g7z3RgJRmxWuGHbeu";

            connection = new FtpConnectionFactory().connect(remoteHost, username, password);
            if (connection == null){
                System.exit(1);
            }
            System.out.println("ftp connection established");

        }
        System.out.println("Input commands or press q to exit");
       while ((System.currentTimeMillis() - startTime) < TIMEOUT) {
            System.out.println();
            System.out.print(connection.getPrompt());
            choice = input.nextLine();
            List<String> commands = new ArrayList<>(List.of(choice.split(" ")));
            switch(commands.get(0)) {
                case "ls":
                    List<DirectoryItem> files = connection.listDirectory();
                    files.forEach(file->System.out.println(file));
                    break;
                case "get":
                    String file = commands.get(1);
                    String message;
                    if(commands.size() == 2){
                        message = connection.getFile(file);
                    }
                    else{
                        String localDirectory = commands.get(2);
                        connection.getFile(file, localDirectory);
                    }
                    break;
                case "put":
                    file = commands.get(1);
                    String localDirectory = commands.get(2);

                    message = connection.putFile(file, localDirectory);

                    System.out.println(message);
                    break;
                case "help":
                    System.out.println("Welcome to FTP/SFTP terminal project! Here are the available commands:");
                    System.out.println("ls: Prints directory onto console");
                    System.out.println("get <filepath> <destination> OR get <filepath>: Downloads file to chosen directory. " +
                            "If no destination is provided, file will be sent to Downloads folder");
                    System.out.println("put <filepath> <destination>: Uploads file to destination directory");
                    System.out.println("clear: Clears console of all output");
                    System.out.println("find <filename>: Searches remote directory for the given filename and outputs all searched paths on console");
                    System.out.println("cd <path>: Enters given directory");
                    System.out.println("pwd: Prints current remote directory onto console");
                    System.out.println("q: Quits program");

                    break;
                case "clear":
                    for (int i = 0; i < 100; i++) {
                        System.out.println();
                    }
                    break;
                case "find":
                    connection.find(commands.get(1));
                    break;
                case "q":
                    connection.disconnect();
                    System.exit(0);
                    break;
                case "cd":
                    connection.cd(commands.get(1));
                    break;
                case "pwd":
                    String currentDirectory = connection.pwd();
                    System.out.println(currentDirectory);
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
           startTime = System.currentTimeMillis();
        }
        observer.update();
    }
}
