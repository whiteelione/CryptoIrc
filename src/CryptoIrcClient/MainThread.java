package CryptoIrcClient;

import CryptoIrcClient.GUI.LoginFrame;

public class MainThread {
    
    private static Client sClient;
    private static String sLogin;
    private static String sPassword;
    
    public static void main(String[] args){
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
    }
    
    public static void Login(){
        sClient = new Client(sLogin, sPassword, 64);
        sClient.StartAuthentication();
    }

    public static void setLogin(String login) {
        MainThread.sLogin = login;
    }

    public static void setPassword(String password) {
        MainThread.sPassword = password;
    }

}
