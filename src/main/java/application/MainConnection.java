package application;

import application.connection.ConnectionFactory;
import application.connection.ftpconnection.FtpConnectionFactory;

public class MainConnection {
    public static void main(String... args){
        ConnectionFactory connectionFactory = new FtpConnectionFactory();
        //connectionFactory.connect()
    }
}
