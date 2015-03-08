package CryptoIrcClient;

import CryptoIrcClient.socketThreads.MessageSocketThread;
import IRCLibrary.SharedClasses.Message;
import IRCLibrary.SharedClasses.Message.MessageType;
import IRCLibrary.SharedClasses.RSA;
import IRCLibrary.SharedClasses.XOR;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Client {
    
    private final byte[] NULLMESSAGE = new byte[] { 0 };
    
    private String mLogin;
    private String mPassword;
    private byte[] mClientKey;
    private int    mKeySize;
    
    private byte[] mLoginCrypted;
    private byte[] mPasswordCrypted;
    
    private byte[] mAuthWord;
    
    private boolean mLogged;
    
    public Client(String login, String password, int keySize){
        mLogin = login;
        mPassword = password;
        mKeySize = keySize;
        mClientKey = new byte[mKeySize];
        mAuthWord = new byte[mKeySize / 2];
        GenKey();
    }
    
    private void GenKey(){
        Random random = new Random();
        random.nextBytes(mClientKey);
        random.nextBytes(mAuthWord);
        mLoginCrypted = XOR.Encrypt(mLogin.getBytes(), mClientKey);
        mPasswordCrypted = XOR.Encrypt(mPassword.getBytes(), mClientKey);
    }
    
    public void ProcessMessage(Object obj){
        Message msg = (Message)obj;
        switch(msg.getMessageType()){
            case authGetRsaKey:
                Authentication(msg);
                break;
            case authSuccess:
                AuthSuccess(msg);
                break;
            case authFail:
                AuthFail(msg);
                break;
            default:
                break;
        }
    }
    
    public void StartAuthentication(){
        Message msg = new Message(
                0, 
                NULLMESSAGE, 
                MessageType.authGetRsaKey, 
                XOR.Encrypt(mAuthWord, mClientKey), 
                mLoginCrypted, 
                mPasswordCrypted);
        ClientLogger.StartAuthLog(mLogin, mPassword);
        SendMessage(msg);
    }
    
    private void Authentication(Message msg){
        try {
            byte[] rsaPublicKey = msg.getlParam();
            byte[] clientKey = RSA.encrypt(mClientKey, RSA.byteArrayToPublicKey(rsaPublicKey));
            byte[] authWord = RSA.encrypt(mAuthWord, RSA.byteArrayToPublicKey(rsaPublicKey));
            Message auth = new Message(
                    msg.getSessionId(), 
                    NULLMESSAGE, 
                    MessageType.authTryLogin, 
                    NULLMESSAGE, 
                    clientKey, 
                    authWord);
            ClientLogger.AuthLog(msg.getSessionId());
            SendMessage(auth);
            
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void AuthSuccess(Message msg){
        ClientLogger.AuthSuccess(msg.getSessionId());
    }
    
    private void AuthFail(Message msg){
        ClientLogger.AuthFail();        
    }
    
    private void SendMessage(Message msg){
        try {
            Thread sender = new Thread(new MessageSocketThread(msg, InetAddress.getByName("127.0.0.1"), 4000, this));
            sender.setDaemon(true);
            sender.start();
        } catch (UnknownHostException ex) {
            System.out.println("Sending message interrupted!!!\n Wrong IP-address.");
        }
    }
}
