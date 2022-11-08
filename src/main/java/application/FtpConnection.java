package application;

import exception.ClientConnectionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class FtpConnection extends EstablishedConnection {

    private final URLConnection urlConnection;

    public FtpConnection(URLConnection urlConnection){
        this.urlConnection = urlConnection;
    }

    // https://stackoverflow.com/questions/41103538/how-to-check-if-urlconnection-is-already-in-connected-state
    public boolean isConnected(){
        try {
            urlConnection.setDoOutput(urlConnection.getDoOutput());
            return false;
        } catch (IllegalStateException e) {
            return true;
        }
    }

    @Override
    public String getFile(String filename) {
        return null;
    }

    @Override
    public List<DirectoryItem> listDirectory() {
        List<DirectoryItem> directoryItemList = new ArrayList<>();

        try(InputStream inputStream = urlConnection.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            for (String line; (line = reader.readLine()) != null;) {
                DirectoryItem directoryItem = new DirectoryItem(line);
                directoryItemList.add(directoryItem);
                System.out.println(line);
                System.out.println(directoryItem);
            }
        } catch (IOException e) {
            throw new ClientConnectionException(e.getMessage(),e);
        }


        return directoryItemList;
    }

    @Override
    public void disconnect() {
        System.out.println("No discconect available for URLConnection");
    }
}