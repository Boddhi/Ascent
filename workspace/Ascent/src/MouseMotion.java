import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;


public class MouseMotion implements MouseMotionListener{
	public static int x, y;
	private Point mouse = null;
	private boolean mouseMoved=false;
	
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY()-Game.t;
		//System.out.println(x + ", " + y);
	}
	
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		mouse = e.getPoint();
		//System.out.println(x + ", " + y);
	}
	public Point getMouse() {
		return mouse;
	}

}

