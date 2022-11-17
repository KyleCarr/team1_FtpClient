package application;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class WindowsHandler extends AbstractHandler{
    @Override
    public void handleInput() throws IOException {
        String choice;
        System.out.println("type sftp or ftp");
        choice = input.nextLine();
        if (choice.equals("sftp")) {
            // alternatively can move this part to main and then pass connection abstractly to the handler
            // change to windows versions
            connection = new SftpConnectionFactory().connect(remoteHost, username, password);
            System.out.println("sftp connection established");
        }
        else {
            connection = new FtpConnectionFactory().connect(remoteHost, username, password);
            System.out.println("ftp connection established");

        }
        System.out.println("Input commands or press q to exit");
        while (true) {
            choice = input.nextLine();

            switch(choice) {
                case "dir":
                    List<DirectoryItem> files = connection.listDirectory();
                    files.forEach(file->System.out.println(file));
                    break;

                case "get":
                    Path path = Paths.get("src/test/resources/readme.txt");

                    String actual = connection.getFile("readme.txt");
                    Files.writeString(path, actual, StandardCharsets.UTF_8);
                    break;

                case "q":
                    System.exit(0);

                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }
}
