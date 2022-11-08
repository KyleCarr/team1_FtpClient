package application;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SftpConnectionTest {

    private final String remoteHost = "test.rebex.net";
    private final String username = "demo";
    private final String password = "password";

    private EstablishedConnection ftpConnection = new SftpConnectionFactory().connect(remoteHost, username, password);

    @Test
    void listDirectory() {
        List<DirectoryItem> files = ftpConnection.listDirectory();
        assertTrue(files.size() > 0);
        files.forEach(file->System.out.println(file));
    }

    @Test
    void getFile() throws IOException {
        Path path = Paths.get("src/test/resources/readme.txt");
        path.toFile().delete();

        try (InputStream inputStream = this.getClass().getResourceAsStream("/expected_readme.txt")){
            String expected = new String(inputStream.readAllBytes());
            String actual = ftpConnection.getFile("readme.txt");
            assertEquals(expected,actual);
            Files.writeString(path, actual, StandardCharsets.UTF_8);
        }
    }
}