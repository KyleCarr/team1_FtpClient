package application;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public interface SftpConnection {
    int CHANNEL_TIMEOUT = 5000;

    ChannelSftp connect() throws JSchException, SftpException;
    void listDirectory(ChannelSftp channelSftp, String path) throws SftpException;
    void download(ChannelSftp channelSftp, String filePath, String savePath) throws SftpException;
}
