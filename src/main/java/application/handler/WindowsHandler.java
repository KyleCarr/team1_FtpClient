package application.handler;

import application.DirectoryItem;
import application.connection.ftpconnection.FtpConnectionFactory;
import application.connection.observer.TimeoutObserver;
import application.connection.sftpconnection.SftpConnectionFactoryProxy;
import application.handler.AbstractHandler;

import java.util.ArrayList;
import java.util.List;

public class WindowsHandler extends AbstractHandler {
    private final long TIMEOUT = 300000;

    long startTime = System.currentTimeMillis();
    private TimeoutObserver observer = new TimeoutObserver();
    @Override
    public void handleInput() {
        String choice;
        System.out.println("type sftp or ftp");
        choice = input.nextLine();
        if (choice.equals("sftp")) {
            // alternatively can move this part to main and then pass connection abstractly to the handler
            // change to Windows versions
            System.out.println("enter remotehost");
            this.remoteHost = input.nextLine();
            System.out.println("enter username");
            this.username = input.nextLine();
            System.out.println("enter password");
            this.password = input.nextLine();
            connection = new SftpConnectionFactoryProxy().connect(remoteHost, username, password);
            System.out.println("sftp connection established");
        }
        else {
            System.out.println("enter remotehost");
            this.remoteHost = input.nextLine();
            System.out.println("enter username");
            this.username = input.nextLine();
            System.out.println("enter password");
            this.password = input.nextLine();
            connection = new FtpConnectionFactory().connect(remoteHost, username, password);
            System.out.println("ftp connection established");

        }
        System.out.println("Input commands or press q to exit");
        while ((System.currentTimeMillis() - startTime) < TIMEOUT) {
            choice = input.nextLine();
            List<String> commands = new ArrayList<>(List.of(choice.split(" ")));
            switch(commands.get(0)) {
                case "dir":
                    List<DirectoryItem> files = connection.listDirectory();
                    files.forEach(file->System.out.println(file));

                    break;
                case "get":
                    String file = commands.get(1);
                    String localDirectory = commands.get(2);
                    if(commands.size() == 2){
                        connection.getFile(file);
                    }
                   else{
                        connection.getFile(file, localDirectory);
                   }
                    System.out.println("file has been downloaded");
                    break;
                case "find":
                    connection.find(commands.get(1));
                    break;
                case "q":
                    System.exit(0);
                    break;
                case "cd":
                    connection.cd(commands.get(1));
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