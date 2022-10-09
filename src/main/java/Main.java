import Config.JschConfig;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Main {

    private static final int BUFFER_SIZE = 4096;

    public static void main(String Args[]){
        String ftpUrl = "ftp://%s:%s@%s/%s;type=i";
        String host = "";
        String user = "";
        String pass = "";
        String filePath = "";
        String savePath = "";

        ftpUrl = String.format(ftpUrl, user, pass, host, filePath);
        System.out.println("URL: " + ftpUrl);

        try {
            URL url = new URL(ftpUrl);
            URLConnection conn = url.openConnection();

            InputStream inputStream = conn.getInputStream();

            FileOutputStream outputStream = new FileOutputStream(savePath);

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

            System.out.println("File downloaded");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
