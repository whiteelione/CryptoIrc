package CryptoIrcClient;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainThread {
    
    private static Client client;
    
    public static void main(String[] args){
        client = new Client("LOGIN", "PASSWORD", 64);
        client.StartAuthentication();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MainThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
