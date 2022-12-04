package application.connection.ftpconnection;

import application.DirectoryItem;
import application.connection.EstablishedConnection;
import application.connection.observer.FileObserver;
import exception.ClientConnectionException;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.*;
import java.net.URLConnection;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;


public class FtpConnection extends EstablishedConnection {
    private final URLConnection urlConnection;
    private final FileObserver observer = new FileObserver();

    public FtpConnection(URLConnection urlConnection) {
        this.urlConnection = urlConnection;
    }

    @Override
    public String getFile(String filename) {
        String localDirectory = System.getProperty("user.home") + FileSystems.getDefault().getSeparator() + "Downloads";
        return getFile(filename, localDirectory);
    }

    @Override
    public String getFile(String filename, String localDirectory) {
        try (InputStream inputStream = urlConnection.getInputStream();
             ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
             FileOutputStream fileOutputStream = new FileOutputStream(localDirectory + FileSystems.getDefault().getSeparator() + filename)) {
            int blockSize = 16384;
            int bytesRead = 0;
            byte[] buffer = new byte[blockSize];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteOutputStream.write(buffer, 0, bytesRead);
            }
            byteOutputStream.writeTo(fileOutputStream);
        } catch (FileNotFoundException e) {
            return String.format("Error: File '%s' not found", filename);
        } catch (IOException e) {
            System.err.println("Class of Exception:" + e.getClass());
            System.err.println(ExceptionUtils.getStackTrace(e));
            if (observer.update()) {
                getFile(filename, localDirectory);
            } else {
                throw new ClientConnectionException(e.getMessage(), e);
            }
        }
        return String.format("Downloaded '%s' to '%s'", filename, localDirectory);
    }

    @Override
    public String putFile(String filename, String remoteHost) {
        Boolean retry;
        do {
            try (OutputStream outputStream = urlConnection.getOutputStream()) {

                FileInputStream fileInputStream = new FileInputStream(filename);
                int blockSize = 16384;
                int bytesRead = 0;
                byte[] buffer = new byte[blockSize];
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                return "File was uploaded successfully";

            } catch (IOException e) {
                if (observer.update()) {
                    retry = true;
                } else {
                    System.err.println("Class of Exception:" + e.getClass());
                    System.err.println(ExceptionUtils.getStackTrace(e));
                    System.out.println("ERROR:" + e.getMessage());
                    return "File was not uploaded successfully";
                }
            }
        } while (retry);

        // shouldn't reach this point
        return null;
    }

    @Override
    public List<DirectoryItem> listDirectory() {
        List<DirectoryItem> directoryItemList = new ArrayList<>();

        try (InputStream inputStream = urlConnection.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            for (String line; (line = reader.readLine()) != null; ) {
                DirectoryItem directoryItem = new DirectoryItem(line);
                directoryItemList.add(directoryItem);
            }
        } catch (IOException e) {
            System.err.println("Class of Exception:" + e.getClass());
            System.err.println(ExceptionUtils.getStackTrace(e));
            throw new ClientConnectionException(e.getMessage(), e);
        }

        return directoryItemList;
    }

    @Override
    public boolean cd(String inputPath) {
        return true;
    }

    @Override
    public String pwd() {
        return null;
    }

    @Override
    public void find(String search) {
        //logic handled in FtpConnectionProxy
    }

    @Override
    public void disconnect() {
        //logic handled in FtpConnectionProxy
    }

    @Override
    public String getPrompt() {
        return "> ";
    }
}
