import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;


public class Reflector { 
	public int x1, y1, x2, y2;
	private Line2D.Double line = null;
	private Point2D.Double clickPoint;
	private Point2D.Double releasePoint;
	private double slope;
	
	public Reflector(int x1, int y1, int x2, int y2, String madebygabe){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.clickPoint = new Point2D.Double(x1, y1);
		this.releasePoint = new Point2D.Double(x2, y2);
		this.slope = (y2 - y1)/(x2 - x1);
		line = new Line2D.Double(clickPoint, releasePoint);
		if (getLength(line) > 200) {
			line = new Line2D.Double(clickPoint, getNewPoint(slope, clickPoint));
		}
	}

	
	public Line2D getLineGabe(){
		return line;
	}
	
	public Point2D.Double getNewPoint(double slope, Point2D.Double point) {
		double maxLength = 200;
		double angle = Math.atan(slope)*180/Math.PI;
		double newPointX = maxLength*Math.cos(angle) + point.getX();
		double newPointY = maxLength*Math.sin(angle) + point.getY();
		Point2D.Double newPoint = new Point2D.Double(newPointX, newPointY);
		return newPoint;
	}
	
	public double getLength(Line2D.Double line) {
		double x1 = line.getX1();
		double x2 = line.getX2();
		double y1 = line.getY1();
		double y2 = line.getY2();
		double length = Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
		return length;
	}
	
	public Reflector(int x1, int y1, int x2, int y2){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public boolean isOutOfBounds(){
		if (y1>Game.HEIGHT-Game.scroll&&y2>Game.HEIGHT-Game.scroll){
			return true;
		}
		return false;
	}

	
	public Line2D getLine(){
		Line2D.Double l = new Line2D.Double(x1, y1, x2, y2);
		return l;
	}
	public void draw(Graphics2D graphics  ){
		graphics.drawLine(x1, y1, x2, y2);
	}
	
	public int getX1() {
		return x1;
	}
	public void setX1(int x1) {
		this.x1 = x1;
	}
	
	public int getY1() {
		return y1;
	}
	public void setY1(int y1) {
		this.y1 = y1;
	}
	
	public int getX2() {
		return x2;
	}
	public void setX2(int x2) {
		this.x2 = x2;
	}
	
	public int getY2() {
		return y2;
	}
	public void setY2(int y2) {
		this.y2 = y2;
	}	

}
