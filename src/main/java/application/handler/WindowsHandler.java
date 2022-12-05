package application.handler;

import application.connection.observer.TimeoutObserver;

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

public class WindowsHandler extends AbstractHandler {
    long startTime = System.currentTimeMillis();
    private final TimeoutObserver observer = new TimeoutObserver();

    @Override
    public void handleInput() {
        String choice;
        connect();

        System.out.println("Input commands or press q to exit");
        while ((System.currentTimeMillis() - startTime) < TIMEOUT) {
            System.out.println();
            System.out.print(connection.getPrompt());
            choice = input.nextLine();
            List<String> commands = new ArrayList<>(List.of(choice.split(" ")));
            switch (commands.get(0)) {
                case "dir":
                    getDirectories();
                    break;
                case "get":
                    if (commands.size() == 3) {
                        commands.set(2, lowercaseFileName(commands.get(2)));
                    }
                    get(commands);
                    break;
                case "put":
                    commands.set(2, lowercaseFileName(commands.get(2)));
                    put(commands);
                    break;
                case "help":
                    help();
                    System.out.println("dir: Prints directory onto console");
                    System.out.println("cd: Prints current remote directory onto console");
                    break;
                case "clear":
                    clear();
                    break;
                case "find":
                    connection.find(commands.get(1));
                    break;
                case "q":
                    System.exit(0);
                    break;
                case "cd":
                    if (commands.size() == 1) {
                        System.out.println(connection.pwd());
                    } else {
                        connection.cd(commands.get(1));
                    }
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
            startTime = System.currentTimeMillis();
        }
        observer.update();

    }

    private String lowercaseFileName(String filepath) {
        int index = filepath.lastIndexOf(FileSystems.getDefault().getSeparator());
        if (index != -1) {
            String first = filepath.substring(0, index);
            String second = filepath.substring(index);
            filepath = first + second.toLowerCase();
        }
        else {
            filepath = filepath.toLowerCase();
        }
        return filepath;
    }

}
