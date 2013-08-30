
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyBoard implements KeyListener{
	boolean keyPressedUp = false, keyPressedDown = false;
	KeyEvent g;// this gobal KeyEvent lets me run the key listener methods in the game class.
	public void keyPressed(KeyEvent e) {
		g = e;// whenever a key is pressed the global KeyEvent is updated
		if(g.getKeyCode() == KeyEvent.VK_UP){
			keyPressedUp = true;
		}
		if(g.getKeyCode() == KeyEvent.VK_DOWN){
			keyPressedDown = true;
		}
		if (g.getKeyCode() == KeyEvent.VK_BACK_SPACE&&(Game.GAME_STATE == Game.GAME_PAUSE||Game.GAME_STATE == Game.GAME_MENU)){
			Game.backspacePressed = true;
		}
		if (g.getKeyCode() == KeyEvent.VK_ENTER&&(Game.GAME_STATE == Game.GAME_PAUSE||Game.GAME_STATE == Game.GAME_MENU)){
			Game.enterPressed = true;
		}
		if (Game.GAME_STATE == Game.GAME_PLAY||Game.GAME_STATE == Game.GAME_PAUSE){
			if(g.getKeyChar() == 'p'){
				Game.reset = Game.GAME_PAUSE;
			}	
		}
	}
	
		public void shortCutKeys(){//shortcuts that were initially implemented for testing, very useful for the user
			if (g!=null){
				if(g.getKeyCode() == KeyEvent.VK_0){
					Game.reset = 0;
				}
				if(g.getKeyCode() == KeyEvent.VK_1){
					Game.reset = 1;
				}
				if(g.getKeyCode() == KeyEvent.VK_2){
					Game.reset = 2;
				}
				if(g.getKeyCode() == KeyEvent.VK_3){
					Game.reset = 3;
				}
			}	
			g = null;
		}		
		
		public void writePlayerName(){//inputs the name of the player

		}
			private boolean isLetter(char c){
			if ((c>64&&c<91)||(c>96&&c<123)||(c>47&&c<58)){
				return true;
			}
			else {
				return false;
			}
		}
		
		public void rateGame(){
		
		}
		
	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {
		
		
	}
		
}
//:3