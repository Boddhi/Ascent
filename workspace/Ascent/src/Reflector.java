import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Reflector {
	public double x1, y1, x2, y2;
	private Line2D.Double line = null;
	private final Point2D.Double clickPoint;
	private final Point2D.Double releasePoint;
	private final double slope;
	private boolean hitBall;

	public Reflector(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		// System.out.println(x1);
		this.clickPoint = new Point2D.Double(x1, y1);
		this.releasePoint = new Point2D.Double(x2, y2);
		this.slope = (y2 - y1) / (x2 - x1);

		line = new Line2D.Double(clickPoint, releasePoint);
		if (getLength(line) > 200) {
			line = new Line2D.Double(clickPoint, getNewPoint(slope, clickPoint,
					releasePoint));
		}
	}
	
	public Reflector(double x1, double y1, double x2, double y2,int override) { //ignores length restraints, used for debugging purposes
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		// System.out.println(x1);
		this.clickPoint = new Point2D.Double(x1, y1);
		this.releasePoint = new Point2D.Double(x2, y2);
		this.slope = (y2 - y1) / (x2 - x1);
		line = new Line2D.Double(clickPoint, releasePoint);
	}

	public static Point2D.Double getNewPoint(double slope,
			Point2D.Double clickPoint, Point2D.Double releasePoint) {
		int maxLength = 5;
		double angle = Math.atan(slope) * 180 / Math.PI;
		if (angle < 0) {
			angle += 360;
		}
		double newPointX = (200 * Math.cos(Math.toRadians(angle)) + clickPoint
				.getX());
		double newPointY = (200 * Math.sin(Math.toRadians(angle)) + clickPoint
				.getY());
		if (releasePoint.getX() < clickPoint.getX()) {
			double xDifference = newPointX - clickPoint.getX();
			newPointX = clickPoint.getX() - xDifference;
			double yDifference = newPointY - clickPoint.getY();
			newPointY = clickPoint.getY() - yDifference;
		}
		Point2D.Double newPoint = new Point2D.Double(newPointX, newPointY);
		return newPoint;
	}

	public double getLength(Line2D.Double line) {
		double x1 = line.getX1();
		double x2 = line.getX2();
		double y1 = line.getY1();
		double y2 = line.getY2();
		double length = Math
				.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		return length;
	}

	public boolean isOutOfBounds() {
		if (y1 > Game.HEIGHT - Game.scroll && y2 > Game.HEIGHT - Game.scroll) {
			return true;
		}
		return false;
	}

	public Line2D getLine() {
		return line;
	}
	
	public void draw(Graphics2D graphics) {
		graphics.draw(getLine());
	}

	public double getX1() {
		return x1;
	}
	public void setX1(int x1) {
		this.x1 = x1;
	}
	
	public double getY1() {
		return y1;
	}
	public void setY1(int y1) {
		this.y1 = y1;
	}

	public double getX2() {
		return x2;
	}
	public void setX2(int x2) {
		this.x2 = x2;
	}

	public double getY2() {
		return y2;
	}
	public void setY2(int y2) {
		this.y2 = y2;
	}
	
	public void setHitBall(boolean hitBall){
		this.hitBall = hitBall;
	}
	public boolean getHitBall(){
		return hitBall;
	}
}
