package application.connection.sftpconnection;

import application.DirectoryItem;
import application.connection.EstablishedConnection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SftpConnectionTest {

    private final String remoteHost = "test.rebex.net";
    private final String username = "demo";
    private final String password = "password";

    private EstablishedConnection connection = new SftpConnectionFactoryProxy().connect(remoteHost, username, password);

    @Test
    void listDirectory() {
        List<DirectoryItem> files = connection.listDirectory();
        assertTrue(files.size() > 0);
        files.forEach(item->System.out.println(item.getName()));
    }

    @Test
    void getFile() throws IOException {
        String downloadsFile = System.getProperty("user.home") + FileSystems.getDefault().getSeparator() +
                "Downloads" + FileSystems.getDefault().getSeparator() + "readme.txt";
        Path path = Paths.get(downloadsFile);
        path.toFile().delete();

        try (InputStream inputStream = this.getClass().getResourceAsStream("/expected_readme.txt")){
            String expected = new String(inputStream.readAllBytes());
            String result = connection.getFile("readme.txt");
            assertEquals("success",result);
            assertTrue(path.toFile().exists());
            String actual = Files.readString(path);
            assertEquals(expected, actual);
        }
    }

    @Test
    void pwd() {
        connection.pwd();
    }
}