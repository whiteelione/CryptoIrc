package CryptoIrcClient.GUI;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 *
 * @author Илья
 */
public class Button{
    protected final JLabel mLabel;
    
    protected boolean mPressed;
    protected boolean mHovered;
    
    protected final Icon mIdleIcon;
    protected final Icon mHoverIcon;
    protected final Icon mPressedIcon;

    public Button(JLabel label, Icon idleIcon, Icon hoverIcon, Icon pressedIcon) {
        this.mLabel = label;
        this.mIdleIcon = idleIcon;
        this.mHoverIcon = hoverIcon;
        this.mPressedIcon = pressedIcon;
        mPressed = false;
        mHovered = false;
        mLabel.setIcon(mIdleIcon);
    }
    
    public boolean isPressed(){
        return mPressed;
    }
    
    public boolean isReleased(){
        return !mPressed;
    }
    
    public boolean isHovered(){
        return mHovered;
    }
    
    public void press(){
        mPressed = true;
        mLabel.setIcon(mPressedIcon);
    }
    
    public void release(){
        mPressed = false;
        mLabel.setIcon(mIdleIcon);
    }
    
    public void hover(){
        mHovered = true;
        if(isReleased()){
            mLabel.setIcon(mHoverIcon);
        }
    }
    
    public void unHover(){
        mHovered = false;
        if(isReleased()){
            mLabel.setIcon(mIdleIcon);
        }
    }
}
