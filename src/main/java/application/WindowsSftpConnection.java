package application;

import Config.JschConfig;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import java.util.Scanner;
import java.util.Vector;

public class WindowsSftpConnection implements SftpConnection {

    Scanner input = new Scanner(System.in);
    JschConfig jschConfig = new JschConfig();

    @Override
    public ChannelSftp connect() throws JSchException, SftpException {

        System.out.println("Enter the remote host url");
        String remoteHost = input.nextLine();
        System.out.println("Enter the remote host username");
        String username = input.nextLine();
        System.out.println("Enter the remote host password");
        String password = input.nextLine();

        Channel sftp = jschConfig.setupJsch(remoteHost,username,password);
        sftp.connect(CHANNEL_TIMEOUT);
        return  (ChannelSftp) sftp;
    }

    @Override
    public void listDirectory(ChannelSftp channelSftp, String path) throws SftpException {
        Vector filelist = channelSftp.ls(path); //TODO: fix hardcoded variable. currently for testing functionality
        for(int i=0; i<filelist.size();i++){
            ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) filelist.get(i);
            if(!(entry.getFilename().equals(".") || entry.getFilename().equals(".."))){
                System.out.println( entry.getFilename());
            }
        }
    }

    @Override
    public void download(ChannelSftp channelSftp, String filePath, String savePath) throws SftpException {
        channelSftp.get(filePath, savePath);

    }
}
