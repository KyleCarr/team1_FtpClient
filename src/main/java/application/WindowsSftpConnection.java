package application;

import Config.JschConfig;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import exception.ClientConnectionException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.Vector;

public class WindowsSftpConnection {
//
//    Scanner input = new Scanner(System.in);
//    JschConfig jschConfig = new JschConfig();
//
//    @Override
//    public EstablishedConnection connect(String remoteHost, String username, String password) {
//
////        System.out.println("Enter the remote host url");
////        String remoteHost = input.nextLine();
////        System.out.println("Enter the remote host username");
////        String username = input.nextLine();
////        System.out.println("Enter the remote host password");
////        String password = input.nextLine();
//        EstablishedConnection establishedConnection;
//
//        try {
//            ChannelSftp channelSftp = jschConfig.setupJsch(remoteHost,username,password);
//            channelSftp.connect(CHANNEL_TIMEOUT);
//            establishedConnection = new SftpConnection(channelSftp);
//        } catch (JSchException e) {
//            getNonSSLConnection(remoteHost, username, password);
//
//        }
//        return establishedConnection;
//    }
//
//    private void getNonSSLConnection(String remoteHost, String username, String password) {
//        String urlString = "ftp://" + username + ":" + password + "@" + remoteHost + "/;type=d";
//        URL url = null;
//        try {
//            url = new URL(urlString);
//        } catch (MalformedURLException e) {
//            throw new ClientConnectionException("Invalid URL String: " + urlString + ". Wrappred exception message: " + e.getMessage(),e);
//        }
//        URLConnection urlConnection = null;
//        try {
//            urlConnection = url.openConnection();
//            urlConnection.setConnectTimeout(CHANNEL_TIMEOUT);
//            urlConnection.connect();
//        } catch (IOException e) {
//            throw new ClientConnectionException(e.getMessage(), e);
//        }
//        int readTimeout = urlConnection.getReadTimeout();
//    }
//
//    @Override
//    public void listDirectory() throws SftpException {
//        Vector filelist = channelSftp.ls(path); //TODO: fix hardcoded variable. currently for testing functionality
//        for(int i=0; i<filelist.size();i++){
//            ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) filelist.get(i);
//            if(!(entry.getFilename().equals(".") || entry.getFilename().equals(".."))){
//                System.out.println( entry.getFilename());
//            }
//        }
//    }
//
//    @Override
//    public void download(ChannelSftp channelSftp, String filePath, String savePath) throws SftpException {
//        channelSftp.get(filePath, savePath);
//
//    }
}
