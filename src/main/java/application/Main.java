package application;

import com.jcraft.jsch.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String Args[]) throws IOException {
        Scanner input = new Scanner(System.in);
        while (true) {
            int choice = -99;
            System.out.println("Select an option\n" +
                    "1: Copy file from remote host\n" +
                    "2: Exit program");
            choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1:
                    AbstractHandler handler;

                    if (System.getProperty("os.name").contains("Windows")) {
                        handler = new WindowsHandler();
                    }
                    else {
                        handler = new UnixHandler();
                    }

                    handler.handleInput();

                case 2:
                    System.exit(0);
            }
        }
    }
}
