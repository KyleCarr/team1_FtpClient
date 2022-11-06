package application;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SftpConnectionFactoryTest {

    private final ConnectionFactory connectionFactory = new SftpConnectionFactory();

    @Test
    void connect() {
        String remoteHost = "test.rebex.net";
        String username = "demo";
        String password = "password";
        EstablishedConnection connect = connectionFactory.connect(remoteHost, username, password);
    }
}