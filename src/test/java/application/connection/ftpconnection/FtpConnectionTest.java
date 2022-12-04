package application.connection.ftpconnection;

import application.DirectoryItem;
import application.connection.EstablishedConnection;
import application.connection.ftpconnection.FtpConnectionFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FtpConnectionTest {

    private final String remoteHost = "ftp.dlptest.com";
    private final String username = "dlpuser";
    private final String password = "rNrKYTX9g7z3RgJRmxWuGHbeu";

    private final EstablishedConnection connection = new FtpConnectionFactory().connect(remoteHost,username,password);

    @Test
    void listDirectory() {
        List<DirectoryItem> files = connection.listDirectory();
        assertTrue(files.size()>0);
        files.forEach(item->System.out.println(item.getName()));
    }

    @Test
    void getFile() {
        String file = connection.getFile("Thanks.pdf");
    }

    @Test
    void cd_pwd() {
        assertEquals("/",connection.pwd());
        connection.cd("frep");
        assertEquals("/frep",connection.pwd());
        connection.cd("Arch");
        assertEquals("/frep/Arch",connection.pwd());
    }

    @Test
    void cd_dotdot_goesUpOneDirectory() {
        connection.cd("/frep/Arch");
        assertEquals("/frep/Arch",connection.pwd());
        connection.cd("..");
        assertEquals("/frep",connection.pwd());
    }
}