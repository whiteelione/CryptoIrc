package cryptoIrcServer.Utility;

/**
 *
 * @author Илья
 */
import IRCLibrary.SharedClasses.Message;
import cryptoIrcServer.Server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSocketThread implements Runnable{
    
    private Socket mSocket;
    private final Server mServer;
    
    public ServerSocketThread(Server server){
        mServer = server;
    }

    @Override
    public void run() {        
        try {
            mSocket = mServer.getServerSocket().accept();
            new Thread(new ServerSocketThread(mServer)).start();
            
            ObjectInputStream input = new ObjectInputStream(mSocket.getInputStream());
            Object obj = input.readObject();   
            
            ProcessMessage(obj);
                     
            input.close();
            mSocket.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ServerSocketThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerSocketThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void ProcessMessage(Object obj){
        Object answer = mServer.ProcessMessage(obj);
            
        ObjectOutputStream output;
        try {
            output = new ObjectOutputStream(mSocket.getOutputStream());
            output.writeObject(answer);
            output.flush();
            output.close();
        } catch (IOException ex) {
            System.out.println("IOException at socket output stream.");
        }
    }
}
