import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;


public class MouseMotion implements MouseMotionListener{
	public static int x, y;
	private Point mouse = null;
	
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY()-Game.scroll;
		//System.out.println(x + ", " + y);
	}
	
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY()-Game.scroll;
		mouse = e.getPoint();
		//System.out.println(x + ", " + y);
	}
	
	public Point getMouse() {
		return mouse;
	}

}
