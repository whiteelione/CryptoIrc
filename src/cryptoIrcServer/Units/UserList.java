package cryptoIrcServer.Units;

import java.util.ArrayList;

public class UserList {
    
    private ArrayList<User> mList;
    
    public UserList(){
        mList = new ArrayList<>();
    }
    
    public User findById(int id){
        for(User u : mList){
            if(u.getId() == id){
                return u;
            }
        }
        return null;
    }
    
    public boolean userExists(int id){
        for(User u : mList){
            if(u.getId() == id){
                return true;
            }
        }
        return false;
    }
    
    public boolean idIsFree(int id){
        if(userExists(id)){
            return false;
        }else{
            return true;
        }
    }
    
    public synchronized int AddUser(User user){
        int freeId = getFreeId();
        user.setId(freeId);
        mList.add(user);
        return freeId;
    }
    
    private int getFreeId(){
        int id = 10000;
        while(!idIsFree(id)){
            id++;
        }
        return id;
    }
}
