package application;

import com.jcraft.jsch.*;

import java.util.Scanner;


public class Main {

    public static void main(String Args[]) throws JSchException, SftpException {
        Scanner input = new Scanner(System.in);
//        while (true) {
//            int choice = -99;
//            System.out.println("Select an option\n" +
//                    "1: Copy file from remote host\n" +
//                    "2: Exit program");
//            choice = input.nextInt();
//            switch (choice) {
//                case 1:
//                    String remoteHost = "ftp.dlptest.com";
//                    String username = "dlpuser";
//                    String password = "rNrKYTX9g7z3RgJRmxWuGHbeu";
//                    WindowsSftpConnection connection = new WindowsSftpConnection();
//                    ChannelSftp channelSftp = connection.connect(remoteHost,username,password);
//                    connection.listDirectory(channelSftp,channelSftp.pwd());
//
//                case 2:
//                    System.exit(0);
//            }
//        }
    }
}
