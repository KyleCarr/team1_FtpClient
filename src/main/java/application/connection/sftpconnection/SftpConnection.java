package application.connection.sftpconnection;

import application.DirectoryItem;
import application.connection.observer.FileObserver;
import application.connection.EstablishedConnection;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import exception.ClientConnectionException;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;


public class SftpConnection extends EstablishedConnection {

    private FileObserver observer = new FileObserver();
    private final ChannelSftp channelSftp;

    public SftpConnection(ChannelSftp channelSftp){
        this.channelSftp = channelSftp;
    }

    @Override
    public String getFile(String filename) {
        try {
            channelSftp.get(filename, System.getProperty("user.home") + FileSystems.getDefault().getSeparator() + "Downloads");
            return "success";
        } catch (SftpException e) {
            if(observer.update() == true){
                getFile(filename);
            }
            else{
                return null;
            }
        }
        return null;
    }

    @Override
    public String getFile(String filename, String remoteHost) {
        try {
             channelSftp.get(filename, remoteHost);
            return "success";
        } catch (SftpException e) {
            if (observer.update() == true) {
                getFile(filename,remoteHost);
            }
            else{
                return null;
            }
        }
        return null;
    }

    @Override
    public String putFile(String filename, String remoteHost) {
        Boolean retry;
        do {
            try {
                channelSftp.put(filename, remoteHost);
                return "success";

            } catch (SftpException e) {
                if (observer.update()){
                    retry = true;
                }
                else {
                    System.err.println("Class of Exception:" + e.getClass());
                    System.err.println(ExceptionUtils.getStackTrace(e));
                    System.out.println("ERROR:" + e.getMessage());
                    return "failure";
                }
            }
        } while (retry);

        // shouldn't reach this point
        return null;
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
    public boolean cd(String path) {
        try {
            channelSftp.cd(path);
        } catch (SftpException e) {
            if (e.getMessage().equals("File not found.")){
                System.out.println("Invalid Directory Path: " + path);
                return false;
            }
            throw new ClientConnectionException(e.getMessage(), e);
        }
        return true;
    }

    @Override
    public String getPrompt() {
        return "> ";
    }

    @Override
    public String pwd() {
        try {
            return channelSftp.pwd();
        } catch (SftpException e) {
            throw new ClientConnectionException(e.getMessage(), e);
        }
    }

    @Override
    public void find(String search) {
        try {
            ChannelExec channelExec = (ChannelExec) channelSftp.getSession().openChannel("exec");
            channelExec.setCommand("find . " + search);

            InputStream in = channelExec.getInputStream();
            InputStream err = channelExec.getExtInputStream();

            channelExec.connect();
            List<String> output = readCommandOutput(in,err,channelExec);
            System.out.println(output.get(0));
            System.out.println(output.get(1));
        } catch (JSchException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void disconnect() {
        channelSftp.disconnect();
    }

    private List<String> readCommandOutput(InputStream in, InputStream err, ChannelExec channelExec){
        try {

            ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
            ByteArrayOutputStream errorBuffer = new ByteArrayOutputStream();
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    outputBuffer.write(tmp, 0, i);
                }
                while (err.available() > 0) {
                    int i = err.read(tmp, 0, 1024);
                    if (i < 0) break;
                    errorBuffer.write(tmp, 0, i);
                }
                if (channelExec.isClosed()) {
                    if ((in.available() > 0) || (err.available() > 0)) continue;
                    System.out.println("exit-status: " + channelExec.getExitStatus());
                    break;
                }
            }
            List<String> output = new ArrayList<>();
            output.add(outputBuffer.toString(StandardCharsets.UTF_8));
            output.add(errorBuffer.toString(StandardCharsets.UTF_8));
            return output;
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }

}
