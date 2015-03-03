package cryptoIrcServer.Units;

import java.util.ArrayList;

public class AuthList {
    private ArrayList<AuthNode> mAuthList;
    private int                 mMaxCount;
    
    public AuthList(int maxCount){
        mMaxCount = maxCount;
        mAuthList = new ArrayList<>();
    }
    
    private class AuthNode{
        
    }
}
