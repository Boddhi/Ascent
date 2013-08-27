import java.awt.event.KeyEvent;


public class KeyBoard {
	public void keyPressed(boolean [] keyPressed,int KEY_LEFT, int KEY_RIGHT, int KEY_UP, int KEY_DOWN, int KEY_Q, int KEY_ENTER, int KEY_BACKSPACE, int KEY_P, KeyEvent e) {
        keyPressed[KEY_Q]=false;
        keyPressed[KEY_P]=false;
		keyPressed[KEY_ENTER]=false;
		keyPressed[KEY_BACKSPACE]=false;
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			leftTrue(keyPressed, KEY_LEFT, KEY_RIGHT, KEY_UP, KEY_DOWN);
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
        	rightTrue(keyPressed, KEY_LEFT, KEY_RIGHT, KEY_UP, KEY_DOWN);
        }
        else if(e.getKeyCode() == KeyEvent.VK_UP) {
        	upTrue(keyPressed, KEY_LEFT, KEY_RIGHT, KEY_UP, KEY_DOWN);
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
        	downTrue(keyPressed, KEY_LEFT, KEY_RIGHT, KEY_UP, KEY_DOWN);
        }
        else if(e.getKeyCode() == KeyEvent.VK_Q)
            keyPressed[KEY_Q] = true;
        else if(e.getKeyCode() == KeyEvent.VK_ENTER)
        	keyPressed[KEY_ENTER] = true;
        else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
        	keyPressed[KEY_BACKSPACE] = true;
        else if(e.getKeyCode() == KeyEvent.VK_P)
        	keyPressed[KEY_P] = true;
    }
    public void leftTrue(boolean [] keyPressed,int KEY_LEFT, int KEY_RIGHT, int KEY_UP, int KEY_DOWN){
        keyPressed[KEY_LEFT] = true;//key left is pressed
        keyPressed[KEY_RIGHT] = false;
        keyPressed[KEY_UP] = false;
        keyPressed[KEY_DOWN] = false;
    }
    public void rightTrue(boolean [] keyPressed,int KEY_LEFT, int KEY_RIGHT, int KEY_UP, int KEY_DOWN){
    	keyPressed[KEY_RIGHT] = true;//key right is pressed
     	keyPressed[KEY_LEFT] = false;
     	keyPressed[KEY_UP] = false;
     	keyPressed[KEY_DOWN] = false;
    }
    public void upTrue(boolean [] keyPressed,int KEY_LEFT, int KEY_RIGHT, int KEY_UP, int KEY_DOWN){
    	keyPressed[KEY_UP] = true; //key up is pressed
     	keyPressed[KEY_RIGHT] = false;
     	keyPressed[KEY_LEFT] = false;
     	keyPressed[KEY_DOWN] = false;
    }
    public void downTrue(boolean [] keyPressed,int KEY_LEFT, int KEY_RIGHT, int KEY_UP, int KEY_DOWN){
    	keyPressed[KEY_DOWN] = true; //key down is pressed
    	keyPressed[KEY_UP] = false;
    	keyPressed[KEY_RIGHT] = false;
    	keyPressed[KEY_LEFT] = false;
    }
    public void keyReleased(KeyEvent e) {
    	
    }
     
    public void keyTyped(KeyEvent e) {
    	
    }
    
}
