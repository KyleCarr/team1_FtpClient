package application;

import com.jcraft.jsch.*;

import java.util.Scanner;


public class Main {

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
                    WindowsSftpConnection connection = new WindowsSftpConnection();
                    ChannelSftp channelSftp = connection.connect();
                    connection.listDirectory(channelSftp,channelSftp.pwd());

                case 2:
                    System.exit(0);
            }
        }
    }
}
