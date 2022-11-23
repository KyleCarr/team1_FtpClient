package application.connection;

import com.jcraft.jsch.UserInfo;

import javax.swing.*;

public class UserInfoImpl implements UserInfo {
    @Override
    public String getPassphrase() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean promptPassword(String s) {
        return false;
    }

    @Override
    public boolean promptPassphrase(String s) {
        return false;
    }

    @Override
    public boolean promptYesNo(String s) {
        JFrame jFrame = new JFrame();
        jFrame.setAlwaysOnTop(true);
        int reply = JOptionPane.showConfirmDialog(jFrame, s, "",  JOptionPane.YES_NO_OPTION);
        return (reply == JOptionPane.YES_OPTION);
    }

    @Override
    public void showMessage(String s) {
    }

    public static void main(String... args){
        new UserInfoImpl().promptYesNo("test?");
    }
}
