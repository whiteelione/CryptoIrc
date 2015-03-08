package IRCLibrary.SharedClasses;

import java.io.Serializable;
import java.util.Arrays;

public class Message implements Serializable {
    
    public enum MessageType{
        authGetRsaKey,
        authTryLogin,
        authSuccess,
        authFail,
        error
    }
    
    private int         mSessionId;
    private byte[]      mSessionPass;
    private MessageType mMessageType;
    private byte[]      mMessage;
    private byte[]      lParam;
    private byte[]      rParam;
    
    private Message(){}
    
    public Message(int sessionId, byte[] sessionPass, MessageType messageType, byte[] message, byte[] lParam, byte[] rParam){
        mSessionId = sessionId;
        mSessionPass = sessionPass;
        mMessageType = messageType;
        mMessage = message;
        this.lParam = lParam;
        this.rParam = rParam;
    }

    public MessageType getMessageType() {
        return mMessageType;
    }

    public byte[] getMessage() {
        return mMessage;
    }

    public byte[] getlParam() {
        return lParam;
    }

    public byte[] getrParam() {
        return rParam;
    }

    public int getSessionId() {
        return mSessionId;
    }

    public void setSessionId(int sessionId) {
        mSessionId = sessionId;
    }
    
    public void PRINT(){
        System.out.println("\n MESSAGE PRINT");
        System.out.println(mSessionId);
        System.out.println(new String(mSessionPass));
        System.out.println(mMessageType);
        System.out.println(new String(mMessage));
        System.out.println(new String(lParam));
        System.out.println(new String(rParam));
    }
}