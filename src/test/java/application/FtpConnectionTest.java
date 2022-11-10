package application;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FtpConnectionTest {

    private final String remoteHost = "ftp.dlptest.com";
    private final String username = "dlpuser";
    private final String password = "rNrKYTX9g7z3RgJRmxWuGHbeu";

    private EstablishedConnection connection = new FtpConnectionFactory().connect(remoteHost,username,password);

    @Test
    void listDirectory() {
        List<DirectoryItem> files = connection.listDirectory();
        assertTrue(files.size()>0);
        files.forEach(item->System.out.println(item.getName()));
    }
}