import com.jcraft.jsch.*;


public class Main {

    private static final int BUFFER_SIZE = 4096;
    private static final int SESSION_TIMEOUT = 10000;
    private static final int CHANNEL_TIMEOUT = 5000;
    public static void main(String Args[]){
        String ftpUrl = "ftp://%s:%s@%s/%s;type=i";
        String host = "";
        String user = "";
        String pass = "";
        String filePath = "/home/pi/Downloads/test.txt";
        String savePath = "D:/temp/test.txt";

        Session jschSession = null;

        try {

            JSch jsch = new JSch();

            jsch.setKnownHosts("C:/Users/kylec/.ssh/known_hosts");
            jschSession = jsch.getSession(user, host, 22);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            jschSession.setConfig(config);
            // authenticate using private key
            //jsch.addIdentity("/home/mkyong/.ssh/id_rsa");

            // authenticate using password
            jschSession.setPassword(pass);

            // 10 seconds session timeout
            jschSession.connect(SESSION_TIMEOUT);

            Channel sftp = jschSession.openChannel("sftp");

            // 5 seconds timeout
            sftp.connect(CHANNEL_TIMEOUT);

            ChannelSftp channelSftp = (ChannelSftp) sftp;

            // transfer file from local to remote server
            //channelSftp.put(localFile, remoteFile);


            // download file from remote server to local
             channelSftp.get(filePath, savePath);

            channelSftp.exit();

        } catch (JSchException | SftpException e) {

            e.printStackTrace();

        } finally {
            if (jschSession != null) {
                jschSession.disconnect();
            }
        }

        System.out.println("Done");
    }
}
