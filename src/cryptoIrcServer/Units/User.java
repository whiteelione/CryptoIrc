package cryptoIrcServer.Units;

/**
 *
 * @author Илья
 */
public class User {
    
    private int     mSessionId;
    private byte[]  mSessionPass;
    private String  mLogin;
    private String  mPassword;
    private byte[]  mXorKey;
    
    private User(){
    }

    public User(int sessionId, byte[] sessionPass, String login, String password, byte[] xorKey) {
        this.mSessionId = sessionId;
        this.mSessionPass = sessionPass;
        this.mLogin = login;
        this.mPassword = password;
        this.mXorKey = xorKey;
    }

    public int getSessionId() {
        return mSessionId;
    }

    public void setSessionId(int sessionId) {
        this.mSessionId = sessionId;
    }
}
