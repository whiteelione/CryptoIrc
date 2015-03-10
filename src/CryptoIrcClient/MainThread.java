package CryptoIrcClient;

import CryptoIrcClient.GUI.LoginFrame;

public class MainThread {
    
    private static Client sClient;
    
    public static void main(String[] args){
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
    }
    
    public static void Login(String login, String password){
        sClient = new Client(login, password, 64);
        sClient.StartAuthentication();
    }

}
