package application.connection.observer;

import java.util.Scanner;

public class FileObserver extends Observer {


    @Override
    public boolean update() {
        Scanner input = new Scanner(System.in);
        System.out.println("The file transfer has failed. Would you like to retry?(Yes/No)");
        while (true) {
            switch (input.nextLine().toLowerCase()) {
                case "yes":
                    return true;
                case "no":
                    return false;
                default:
                    System.out.println("Invalid input. Please respond with yes or no");
            }
        }
    }

}

