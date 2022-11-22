package application;

import application.connection.ftpconnection.FtpConnectionFactory;
import application.connection.sftpconnection.SftpConnectionFactory;
import application.connection.sftpconnection.SftpConnectionFactoryProxy;

import java.util.ArrayList;
import java.util.List;

public class WindowsHandler extends AbstractHandler{
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
            connection = new FtpConnectionFactory().connect(remoteHost, username, password);
            System.out.println("ftp connection established");

        }
        System.out.println("Input commands or press q to exit");
        while (true) {
            choice = input.nextLine();
            List<String> commands = new ArrayList<>(List.of(choice.split(" ")));
            switch(commands.get(0)) {
                case "dir":
                    List<DirectoryItem> files = connection.listDirectory();
                    files.forEach(file->System.out.println(file));
                    break;
                case "get":
                    String file = commands.get(1);
                    String remoteHost = commands.get(2);
                    if(commands.size() == 2){
                        connection.getFile(file);
                    }
                   else{
                        connection.getFile(file, remoteHost);
                   }
                    System.out.println("file has been downloaded");
                    break;
                case "find":
                    connection.find(commands.get(1));
                    break;
                case "q":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }
}
