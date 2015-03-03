package cryptoIrcServer;

import IRCLibrary.SharedClasses.Message;
import IRCLibrary.SharedClasses.RSA;
import cryptoIrcServer.Units.UserList;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server{
   
    private ServerSocket       mServerSocket;
    private final int          mPort;
    private final int          mMaxClientCount;    
    
    private final UserList     mUserList;
    
    private ServerConsole mConsole;
    
    public Server(int port, int maxClients){
        mConsole = new ServerConsole(this);
        mConsole.println("Создаётся класс сервера.");
        try {
            mServerSocket = new ServerSocket(port, maxClients);
        } catch (IOException ex) {
            mConsole.println("Socket not created.");
        }
        mPort = port;
        mMaxClientCount = maxClients;
        mUserList = new UserList();
    }
    
    public void Start(){
        mConsole.println("Начинается прослушка порта.");
        new Thread(new ServerSocketThread(this)).start();
        mConsole.Start();
    }
    
    public Object ProcessMessage(Object obj){
        mConsole.println("Recieved...");
        Message msg = (Message)obj;
        msg.PrintConsole();
        
        Message answer;
        
        switch(msg.getMessageType()){
            case authentication:
                answer = new Message(Message.MessageType.authentication, null, null, null);
                break;
            default:
                answer = new Message(Message.MessageType.authentication, null, null, null);
                break;
        }
        
        return answer;
    }
    
    
    private void StartAuthentication(){
        RSA rsa = new RSA();
        rsa.generateKey();
        byte[] publicKey = rsa.getPublicKeyB();
        
        Message msg = new Message(Message.MessageType.authentication, "RSAkey".getBytes(), publicKey, null);
    }
    
    public void Stop(){
        try {
            mServerSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ServerSocket getServerSocket() {
        return mServerSocket;
    }
}
