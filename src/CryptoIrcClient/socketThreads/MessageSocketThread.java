package CryptoIrcClient.socketThreads;

import CryptoIrcClient.Client;
import IRCLibrary.SharedClasses.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageSocketThread implements Runnable{
    
    protected final Object      mMessage;
    protected final InetAddress mIpAddress;
    protected final int         mPort;
    protected final Client      mClient;
    
    public MessageSocketThread(Object message, InetAddress ipAddress, int port, Client client){
        mMessage = message;
        mIpAddress = ipAddress;
        mPort = port;
        mClient = client;
    }
    
    @Override
    public void run() {        
        try {
            Socket socket = new Socket(mIpAddress, mPort);
            
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            output.writeObject(mMessage);
            
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            Object answer = input.readObject(); 
            
            output.flush();            
            output.close();                       
            input.close();            
            socket.close();
            
            mClient.ProcessMessage(answer);
            
        } catch (IOException ex) {
            Logger.getLogger(MessageSocketThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MessageSocketThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
