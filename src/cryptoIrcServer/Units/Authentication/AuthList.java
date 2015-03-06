package cryptoIrcServer.Units.Authentication;

/**
 *
 * @author Илья
 */
import IRCLibrary.SharedClasses.RSA;
import IRCLibrary.SharedClasses.XOR;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class AuthList {
    private final ArrayList<AuthNode> mAuthList;
    private final int                 mMaxCount;
    
    public AuthList(int maxCount){
        mMaxCount = maxCount;
        mAuthList = new ArrayList<>();
    }
    
    public boolean nodeExists(int id){
        for(AuthNode node : mAuthList){
            if(node.getId() == id){
                return true;
            }
        }
        return false;
    }
    
    public boolean idIsFree(int id){
        return !nodeExists(id);
    }
    
    public synchronized int addUser(AuthNode node){
        if(mAuthList.size() == mMaxCount){
            mAuthList.remove(0);
        }
        int freeId = getFreeId();
        node.setId(freeId);
        mAuthList.add(node);
        return freeId;
    }
    
    private int getFreeId(){
        int id = 10000;
        while(nodeExists(id)){
            id++;
        }
        return id;
    }
    
    public AuthNode findById(int id){
        AuthNode authNode = null;
        for(AuthNode node : mAuthList){
            if(node.getId() == id){
                authNode = node;
                break;
            }
        }
        return authNode;
    }
    
    public boolean CheckAuth(int id, byte[] keyXORcrypted, byte[] authWordRSA, PrivateKey keyRSA){
        if(nodeExists(id)){
            try {
                
                AuthNode node = findById(id);
                byte[] keyXOR = RSA.decryptB(keyXORcrypted, keyRSA);
                byte[] wordRsaEncrypted = RSA.decryptB(authWordRSA, keyRSA);
                byte[] wordXorEncrypted = XOR.Encrypt(node.getAuthWord(), keyXOR);
                String rsaResult = new String(wordRsaEncrypted);
                String xorResult = new String(wordXorEncrypted);
                if(rsaResult.equals(xorResult)){
                    return true;
                }
                else{
                    return false;
                }
                
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(AuthList.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchPaddingException ex) {
                Logger.getLogger(AuthList.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeyException ex) {
                Logger.getLogger(AuthList.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(AuthList.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger(AuthList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
}
