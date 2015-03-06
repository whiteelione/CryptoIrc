package cryptoIrcServer.Units;

/**
 *
 * @author Илья
 */
import java.util.ArrayList;

public class UserList {
    
    private final ArrayList<User> mList;
    
    public UserList(){
        mList = new ArrayList<>();
    }
    
    public User findById(int id){
        for(User u : mList){
            if(u.getSessionId() == id){
                return u;
            }
        }
        return null;
    }
    
    public boolean userExists(int id){
        for(User u : mList){
            if(u.getSessionId() == id){
                return true;
            }
        }
        return false;
    }
    
    public boolean idIsFree(int id){
        return !userExists(id);
    }
    
    public synchronized int addUser(User user){
        int freeId = getFreeId();
        user.setSessionId(freeId);
        mList.add(user);
        return freeId;
    }
    
    private int getFreeId(){
        int id = 10000;
        while(userExists(id)){
            id++;
        }
        return id;
    }
}
