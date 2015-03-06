package cryptoIrcServer.Units.Authentication;

/**
 *
 * @author Илья
 */
public class AuthNode {
    int     mId;
    byte[]  mAuthWord;
    byte[]  mLogin;
    byte[]  mPassword;
    
    private AuthNode(){}
    
    public AuthNode(int id, byte[] key, byte[] login, byte[] password) {
        this.mId = id;
        this.mAuthWord = key;
        this.mLogin = login;
        this.mPassword = password;
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

    public byte[] getPassword() {
        return mPassword;
    }
}
