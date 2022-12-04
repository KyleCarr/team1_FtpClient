package application.handler;

import application.connection.observer.TimeoutObserver;

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
            choice = input.nextLine();
            List<String> commands = new ArrayList<>(List.of(choice.split(" ")));
            switch (commands.get(0)) {
                case "dir":
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

}
