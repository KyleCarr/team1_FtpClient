package application;

import application.connection.ConnectionFactory;
import application.connection.EstablishedConnection;
import application.connection.ftpconnection.FtpConnectionFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FtpConnectionFactoryTest {

    private final ConnectionFactory connectionFactory = new FtpConnectionFactory();

    @Test
    void connect() {
        String remoteHost = "ftp.dlptest.com";
        String username = "dlpuser";
        String password = "rNrKYTX9g7z3RgJRmxWuGHbeu";

        EstablishedConnection connect = connectionFactory.connect(remoteHost, username, password);
        assertTrue(connect.isConnected());
        connect.disconnect();

    }
}