import java.awt.event.*;
public class Mouse implements MouseListener{
	
	public static int x, y, x1, y1, x2, y2;
	
	public boolean holding;
	public boolean rReady = false;
	
	public Reflector get(){
		if (rReady){
			Reflector t = new Reflector(x1, y1, x2, y2);
			rReady = false;
			//System.out.println("ran" + Game.walls.size());
			
			return t;
		}
		return null;
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY()-Game.t;
		holding = true;
	}
	
	public void mouseReleased(MouseEvent e) {
		x2 = e.getX();
		y2 = e.getY()-Game.t;
		holding = false;
		rReady = true;
	}
	
}
