package application.handler;

import application.connection.observer.TimeoutObserver;

import java.util.ArrayList;
import java.util.List;

public class UnixHandler extends AbstractHandler {

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
                case "ls":
                    getDirectories();
                    break;
                case "get":
                    get(commands);
                    break;
                case "put":
                    put(commands);
                    break;
                case "help":
                    help();
                    System.out.println("ls: Prints directory onto console");
                    System.out.println("pwd: Prints current remote directory onto console");
                    break;
                case "clear":
                    clear();
                    break;
                case "find":
                    connection.find(commands.get(1));
                    break;
                case "q":
                    connection.disconnect();
                    break;
                case "cd":
                    connection.cd(commands.get(1));
                    break;
                case "pwd":
                    System.out.println(connection.pwd());
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
