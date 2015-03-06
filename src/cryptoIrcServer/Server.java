package cryptoIrcServer;

/**
 *
 * @author Илья
 */
import cryptoIrcServer.Utility.ServerSocketThread;
import cryptoIrcServer.Utility.ServerConsole;
import IRCLibrary.SharedClasses.Message;
import IRCLibrary.SharedClasses.Message.MessageType;
import IRCLibrary.SharedClasses.RSA;
import IRCLibrary.SharedClasses.XOR;
import cryptoIrcServer.Units.Authentication.AuthList;
import cryptoIrcServer.Units.Authentication.AuthNode;
import cryptoIrcServer.Units.User;
import cryptoIrcServer.Units.UserList;
import cryptoIrcServer.Utility.ServerLogger;
import java.io.IOException;
import java.net.ServerSocket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Server{
   
    private final byte[] EMPTY_BYTE_ARRAY = new byte[] { };
    private final int    SESSION_KEY_LENGTH = 32;
    
    private ServerSocket    mServerSocket;
    private final int       mPort;
    private final int       mMaxClientCount;    
    
    private final UserList  mUserList;
    private final AuthList  mAuthList;
    
    private ServerConsole   mConsole;
    
    private final RSA       mRsa;
    
    private final Random    random;
    
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
        random = new Random();
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
            case authGetRsaKey:
                answer = StartAuthentication(msg);
                ServerLogger.StartAuthLog(answer.getSessionId());
                break;
            case authTryLogin:
                answer = ClientLogin(msg);
                break;
            default:
                answer = new Message(0, EMPTY_BYTE_ARRAY, MessageType.error, EMPTY_BYTE_ARRAY, EMPTY_BYTE_ARRAY, EMPTY_BYTE_ARRAY);
                break;
        }
        
        return answer;
    }
    
    private Message ClientLogin(Message msg){
        boolean loginSuccess = false;
        byte[] sessionKey = null;
        int sessionId = 0;
        
        if(mAuthList.CheckAuth(msg.getSessionId(), msg.getlParam(), msg.getrParam(), mRsa.getPrivateKey())){
            try {
                AuthNode node = mAuthList.findById(msg.getSessionId());
                byte[] xorKey = RSA.decryptB(msg.getlParam(), mRsa.getPrivateKey());
                String loginDeCrypted = new String(XOR.Encrypt(node.getLogin(), xorKey));
                String passwordDeCrypted =  new String(XOR.Encrypt(node.getPassword(), xorKey));
                
                if(userExists(loginDeCrypted, passwordDeCrypted)){
                    sessionKey = new byte[SESSION_KEY_LENGTH];
                    random.nextBytes(sessionKey);
                    User user = new User(0, sessionKey, loginDeCrypted, passwordDeCrypted, xorKey);
                    sessionId = mUserList.addUser(user);
                    loginSuccess = true;
                    sessionKey = XOR.Encrypt(sessionKey, xorKey);
                    ServerLogger.EndAuth(sessionId, loginSuccess, loginDeCrypted, passwordDeCrypted);
                }
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchPaddingException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeyException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(loginSuccess){
            return new Message(
                    sessionId, 
                    sessionKey, 
                    MessageType.authSuccess, 
                    EMPTY_BYTE_ARRAY, EMPTY_BYTE_ARRAY, EMPTY_BYTE_ARRAY);
        }else{
            ServerLogger.EndAuth(msg.getSessionId(), false, "", "");
            return new Message(
                    0, 
                    EMPTY_BYTE_ARRAY, 
                    MessageType.authFail, 
                    EMPTY_BYTE_ARRAY, EMPTY_BYTE_ARRAY, EMPTY_BYTE_ARRAY);
        }
    }
    
    private boolean userExists(String login, String password){
        //TODO
        //проверка существования комбинации
        //соответствующих логина-пароля
        return true;
    }
    
    private Message StartAuthentication(Message msg){
        byte[] publicKey = mRsa.getPublicKeyB();
        
        AuthNode node = new AuthNode(0, msg.getMessage(), msg.getlParam(), msg.getrParam());
        int authId = mAuthList.addUser(node);
        
        return new Message(authId, EMPTY_BYTE_ARRAY, MessageType.authGetRsaKey, "RSAkey".getBytes(), publicKey, EMPTY_BYTE_ARRAY);
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
