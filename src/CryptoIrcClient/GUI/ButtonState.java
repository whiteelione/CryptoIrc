package CryptoIrcClient.GUI;

/**
 *
 * @author Илья
 */
public class ButtonState {
    
    private boolean mPressed;
    private boolean mHovered;
    
    public ButtonState(){
        mPressed = false;
    }
    
    public boolean isPressed(){
        return mPressed;
    }
    
    public boolean isReleased(){
        return !mPressed;
    }
    
    public void press(){
        mPressed = true;
    }
    
    public void release(){
        mPressed = false;
    }
    
    public boolean isHovered(){
        return mHovered;
    }
    
    public void hover(){
        mHovered = true;
    }
    
    public void unHover(){
        mHovered = false;
    }
}
