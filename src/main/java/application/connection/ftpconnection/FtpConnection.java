package application.connection.ftpconnection;

import application.DirectoryItem;
import application.connection.EstablishedConnection;
import application.connection.observer.FileObserver;
import exception.ClientConnectionException;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;


public class FtpConnection extends EstablishedConnection {
    private final URLConnection urlConnection;
    private FileObserver observer = new FileObserver();
    public FtpConnection(URLConnection urlConnection) {
        this.urlConnection = urlConnection;
    }

    @Override
    public String getFile(String filename) {
        String localDirectory = System.getProperty("user.home") + FileSystems.getDefault().getSeparator() + "Downloads";
        return getFile(filename,localDirectory);
    }

    @Override
    public String getFile(String filename, String localDirectory) {
        try (InputStream inputStream = urlConnection.getInputStream();
             ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
             FileOutputStream fileOutputStream = new FileOutputStream(localDirectory + FileSystems.getDefault().getSeparator() + filename)) {
            int blockSize = 16384;
            int bytesRead = 0;
            byte[] buffer = new byte[blockSize];
            while((bytesRead = inputStream.read(buffer)) != -1){
                byteOutputStream.write(buffer,0, bytesRead);
            }
            byteOutputStream.writeTo(fileOutputStream);
        } catch (FileNotFoundException e) {
            return String.format("Error: File '%s' not found", filename);
        } catch (IOException e) {
            System.err.println("Class of Exception:"+e.getClass());
            System.err.println(ExceptionUtils.getStackTrace(e));
            if(observer.update() == true){
                getFile(filename,localDirectory);
            }
            else {
                throw new ClientConnectionException(e.getMessage(), e);
            }
        }
        return String.format("Downloaded '%s' to '%s'", filename, localDirectory);
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
            System.err.println("Class of Exception:"+e.getClass());
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
        List<DirectoryItem> activeDirectory;
        String directory;

            activeDirectory =  listDirectory();

            for(int i = 0; i < activeDirectory.size()-1; i++){
                directory =activeDirectory.get(i).getName();
                if(directory.equals(search)){
                    System.out.println( "TODO: pwd goes here"+ directory);
                    return;
                }
                System.out.println(activeDirectory.get(i).getName());
                if(cd(directory) == true){
                    find(search);
                }
            }
            System.out.println( search + " not found");

    }

    @Override
    public void disconnect() {

    }

    @Override
    public String getPrompt() {
        return "> ";
    }
}
