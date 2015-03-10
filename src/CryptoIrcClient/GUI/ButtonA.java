package CryptoIrcClient.GUI;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 *
 * @author Илья
 */
public class ButtonA extends Button{
    
    private boolean mActive;
    private Icon    mDisabledIcon;

    public ButtonA(JLabel label, Icon idleIcon, Icon hoverIcon, Icon pressedIcon, Icon disabledIcon, boolean active) {
        super(label, idleIcon, hoverIcon, pressedIcon);
        mDisabledIcon = disabledIcon;
        mActive = active;
        if(!mActive){
            mLabel.setIcon(mDisabledIcon);
        }
    }
    
    public boolean isActive(){
        return mActive;
    }
    
    public void enable(){
        mActive = true;
        if(mHovered){
            mLabel.setIcon(mHoverIcon);
        }
        else{
            mLabel.setIcon(mIdleIcon);
        }
    }
    
    public void disable(){
        mActive = false;
        mLabel.setIcon(mDisabledIcon);
    }
    
    @Override
    public void press(){
        if(mActive){
            mPressed = true;
            mLabel.setIcon(mPressedIcon);
        }
    }
    
    @Override
    public void release(){
        if(mActive){
            mPressed = false;
            mLabel.setIcon(mIdleIcon);
        }
    }
    
    @Override
    public void hover(){
        mHovered = true;
        if(mActive){
            if(isReleased()){
                mLabel.setIcon(mHoverIcon);
            }
        }
    }
    
    @Override
    public void unHover(){
        mHovered = false;
        if(mActive){
            if(isReleased()){
                mLabel.setIcon(mIdleIcon);
            }
        }
    }
}
