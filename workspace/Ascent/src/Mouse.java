import java.awt.event.*;
public class Mouse implements MouseListener{
	
	public static double x, y, x1, y1, x2, y2;
	
	public boolean holding = false;
	public boolean rReady = false;
	private MouseEvent click = null;
	private boolean isClicked = false;
	
	public Reflector getReflector(){
		if (rReady){
			Reflector reflector = new Reflector(x1, y1, x2, y2);
			rReady = false;		
			return reflector;
		}
		return null;
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		click = e;
		isClicked = true;
	}
		public MouseEvent getClick() {
		isClicked = false;
		return click;
	}
		public boolean getIsClicked() {
		return isClicked;
	}
		
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	public void clear(){
		x1 = MouseMotion.x;
		y1 = MouseMotion.y;
		x2 = MouseMotion.x;
		y2 = MouseMotion.y;
		holding = false;
		rReady = false;
	}

	public void mousePressed(MouseEvent e) {
		if(Game.GAME_STATE != Game.GAME_PLAY || holding) return;
		x1 = e.getX();
		y1 = e.getY()-Game.scroll;
		holding = true;
	}
	
	public void mouseReleased(MouseEvent e) {
		if(!holding) return;
		x2 = e.getX();
		y2 = e.getY()-Game.scroll;
		holding = false;
		rReady = true;
	}
	
}
