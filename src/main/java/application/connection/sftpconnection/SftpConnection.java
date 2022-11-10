package application.connection.sftpconnection;

import application.DirectoryItem;
import application.connection.EstablishedConnection;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import exception.ClientConnectionException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import static java.lang.Math.min;

public class SftpConnection extends EstablishedConnection {

    private final ChannelSftp channelSftp;

    public SftpConnection(ChannelSftp channelSftp){
        this.channelSftp = channelSftp;
    }

    @Override
    public boolean isConnected() {
        return channelSftp.isConnected();
    }

    @Override
    public String getFile(String filename) {
        try {
            InputStream inputStream = channelSftp.get(filename);
            String fileContents = new String(inputStream.readAllBytes());
            Files.writeString(FileSystems.getDefault().getPath(System.getProperty("user.dir"),filename), fileContents, StandardCharsets.UTF_8);
            return fileContents.substring(0, min(fileContents.length(), 1024));
        } catch (SftpException|IOException e) {
            throw new ClientConnectionException(e.getMessage(),e);
        }
    }

    @Override
    public List<DirectoryItem> listDirectory() {
        try {
            Vector vector = channelSftp.ls(".");
            List<DirectoryItem> directoryItemList = new ArrayList<>();
            for (Iterator iterator = vector.iterator();iterator.hasNext();){
                ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry) iterator.next();
                directoryItemList.add(new DirectoryItem(lsEntry));
            }
            return directoryItemList;
        } catch (SftpException e) {
            throw new ClientConnectionException(e.getMessage(),e);
        }
    }



    @Override
    public void disconnect() {
        channelSftp.disconnect();
    }


}
