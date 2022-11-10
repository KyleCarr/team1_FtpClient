package application;

import application.connection.EstablishedConnection;
import application.connection.ftpconnection.FtpConnectionFactory;
import application.connection.sftpconnection.SftpConnectionFactory;
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
                    String remoteHost = "";
                    String username = "";
                    String password = "";
                    EstablishedConnection connection = new SftpConnectionFactory().connect(remoteHost,username,password);

                    connection.listDirectory();

                case 2:
                    System.exit(0);
            }
        }
    }
}
