package application.connection.sftpconnection;

import application.DirectoryItem;
import application.connection.EstablishedConnection;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import exception.ClientConnectionException;

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
            channelSftp.get(filename, "C:/Users/kylec/Downloads/");
            return "success";
        } catch (SftpException e) {
            throw new ClientConnectionException(e.getMessage(),e);
        }
    }

    @Override
    public String getFile(String filename, String remoteHost) {
        try {
             channelSftp.get(filename, remoteHost);
            return "success";
        } catch (SftpException e) {
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
    public boolean cd(List<String> pathList) {

        return false;
    }

    @Override
    public String pwd() {
        return null;
    }

    @Override
    public void disconnect() {
        channelSftp.disconnect();
    }


}
