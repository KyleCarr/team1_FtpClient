package application;

import application.connection.EstablishedConnection;
import application.connection.ftpconnection.FtpConnectionFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FtpConnectionTest {

    private final String remoteHost = "ftp.dlptest.com";
    private final String username = "dlpuser";
    private final String password = "rNrKYTX9g7z3RgJRmxWuGHbeu";

    private EstablishedConnection ftpConnection = new FtpConnectionFactory().connect(remoteHost,username,password);

    @Test
    void listDirectory() {
        List<DirectoryItem> files = ftpConnection.listDirectory();
        assertTrue(files.size()>0);
    }
}