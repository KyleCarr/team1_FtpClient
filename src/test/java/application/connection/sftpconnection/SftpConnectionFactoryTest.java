package application.connection.sftpconnection;

import application.connection.ConnectionFactory;
import application.connection.EstablishedConnection;
import application.connection.sftpconnection.SftpConnectionFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SftpConnectionFactoryTest {

    private final ConnectionFactory connectionFactory = new SftpConnectionFactoryProxy();

    @Test
    void connect() {
        String remoteHost = "test.rebex.net";
        String username = "demo";
        String password = "password";
        EstablishedConnection connect = connectionFactory.connect(remoteHost, username, password);
        assertNotNull(connect.listDirectory());
    }
}