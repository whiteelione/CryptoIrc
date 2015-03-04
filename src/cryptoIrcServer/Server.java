package cryptoIrcServer;

/**
 *
 * @author Илья
 */
import IRCLibrary.SharedClasses.Message;
import IRCLibrary.SharedClasses.RSA;
import cryptoIrcServer.Units.AuthList;
import cryptoIrcServer.Units.AuthNode;
import cryptoIrcServer.Units.UserList;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server{
   
    private final byte[] NULLMESSAGE = new byte[] { 0 };
    
    private ServerSocket    mServerSocket;
    private final int       mPort;
    private final int       mMaxClientCount;    
    
    private final UserList  mUserList;
    private final AuthList  mAuthList;
    
    private ServerConsole   mConsole;
    
    private RSA             mRsa;
    
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
        mAuthList = new AuthList(100);
        mRsa = new RSA();
        mRsa.generateKey();
    }
    
    public void Start(){
        mConsole.println("Начинается прослушка порта.");
        new Thread(new ServerSocketThread(this)).start();
        mConsole.Start();
    }
    
    public Object ProcessMessage(Object obj){
        Message msg = (Message)obj;
        
        Message answer;
        
        switch(msg.getMessageType()){
            case authGetPublicKey:
                answer = StartAuthentication(msg);
                break;
            case authLogin:
                answer = ClientLogin(msg);
                break;
            default:
                answer = new Message(0, Message.MessageType.error, NULLMESSAGE, NULLMESSAGE, NULLMESSAGE);
                break;
        }
        
        return answer;
    }
    
    private Message ClientLogin(Message msg){
        if(mAuthList.CheckAuth(msg.getSessionId(), msg.getlParam(), msg.getrParam(), mRsa.getPrivateKey())){
            AuthNode node = mAuthList.findById(msg.getSessionId());
            byte[] loginCrypted = node.getLogin();
            byte[] passwordCrypted = node.getPasswrod();
            byte[] xorKey = msg.getlParam();
            
            return new Message(
                msg.getSessionId(),
                Message.MessageType.authLogin,
                "Login success.".getBytes(),
                NULLMESSAGE,
                NULLMESSAGE);
        }
        return new Message(
                msg.getSessionId(),
                Message.MessageType.authLogin,
                "Login failed.".getBytes(),
                NULLMESSAGE,
                NULLMESSAGE);
    }
    
    private Message StartAuthentication(Message msg){
        byte[] publicKey = mRsa.getPublicKeyB();
        
        AuthNode node = new AuthNode(0, msg.getMessage(), msg.getlParam(), msg.getrParam());
        int authId = mAuthList.addUser(node);
        
        return new Message(authId, Message.MessageType.authGetPublicKey, "RSAkey".getBytes(), publicKey, NULLMESSAGE);
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
