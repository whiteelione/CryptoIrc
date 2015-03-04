package cryptoIrcServer.Units;

/**
 *
 * @author Илья
 */
public class AuthNode {
    int     mId;
    byte[]  mAuthWord;
    byte[]  mLogin;
    byte[]  mPasswrod;
    
    private AuthNode(){}
    
    public AuthNode(int id, byte[] key, byte[] login, byte[] passwrod) {
        this.mId = id;
        this.mAuthWord = key;
        this.mLogin = login;
        this.mPasswrod = passwrod;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public byte[] getAuthWord() {
        return mAuthWord;
    }

    public byte[] getLogin() {
        return mLogin;
    }

    public byte[] getPasswrod() {
        return mPasswrod;
    }
}
