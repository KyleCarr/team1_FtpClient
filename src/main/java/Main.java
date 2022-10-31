import application.RetrieveFromHost;
import com.jcraft.jsch.*;

import java.util.Scanner;


public class Main {

    private static final int BUFFER_SIZE = 4096;
    private static final int SESSION_TIMEOUT = 10000;
    private static final int CHANNEL_TIMEOUT = 5000;

    public static void main(String Args[]) throws JSchException, SftpException {
        Scanner input = new Scanner(System.in);
        while (true) {
            int choice = -99;
            System.out.println("Select an option\n" +
                    "1: Copy file from remote host\n" +
                    "2: Exit program");
            choice = input.nextInt();
            switch (choice) {
                case 1:
                    new RetrieveFromHost().connect();
                case 2:
                    System.exit(0);
            }
            break;
        }
    }
}
