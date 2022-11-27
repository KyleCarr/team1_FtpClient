package application;

import application.connection.ftpconnection.FtpConnectionFactory;
import application.connection.sftpconnection.SftpConnectionFactoryProxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UnixHandler extends AbstractHandler{
    @Override
    public void handleInput() {
        String choice;
        System.out.println("type sftp or ftp");
        choice = input.nextLine();
        if (choice.equals("sftp")) {
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
                case "ls":
                    List<DirectoryItem> files = connection.listDirectory();
                    files.forEach(file->System.out.println(file));
                    break;
                case "get":
                    String file = commands.get(1);
                    // move this into else?
                    String localDirectory = commands.get(2);
                    if(commands.size() == 2){
                        connection.getFile(file);
                    }
                    else{
                        connection.getFile(file, localDirectory);
                    }
                    System.out.println("file has been downloaded");
                    break;
                case "put":
                    file =commands.get(1);
                    localDirectory = commands.get(2);
                    System.out.println(localDirectory);

                    //if(commands.size() == 2){
                    //    connection.putFile(file);
                    //}
                    //else{
                        connection.putFile(file, localDirectory);
                    //}
                    System.out.println("file has been uploaded");
                    break;
                case "help":
                    System.out.println("Commands: get put ls find");
                    break;
                case "clear":
                    for (int i = 0; i < 25; i++) {
                        System.out.println();
                    }
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
                    // put clear help
            }
        }
    }
}
