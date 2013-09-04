import java.awt.Color;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class Ball {
	private int x, y;
	private int size;
	private double velX = 0, velY = -5, velT; //should un-hardcode this soon
	private Color color;

	public Ball(int x, int y, int size, Color color){
		setX(x);
		setY(y);
		setSize(size);
		setColor(color);
		setVelocityT();
	}

	public void live(){
		x+=velX;
		y+=velY;
	}

	public int getRadius(){
		return this.size;
	}

	public void live(double mod){
		x+=velX*mod;
		y+=velY*mod;
	}

	public Ellipse2D getEllipse(){
		Ellipse2D.Double e = new Ellipse2D.Double(x, y, size, size);
		return e;
	}
	public Polygon getPolygon(){

		//int x1 = (int)(x+velX), y1 = (int)(y+velY);'
		int x1 = x, y1 = y;
		int[] xPoints = {(int)x1, (int)(x1+size*0.3), (int)(x1+size*0.7),(int)(x1+size), (int)(x1+size), (int)(x1+size*0.7), (int)(x1+size*0.3), (int)x1};
		int[] yPoints = {(int)(y1+size*0.3), (int)y1, (int)y1, (int)(y1+size*0.3), (int)(y1+size*0.7),(int)(y1+size),(int)(y1+size),(int)(y1+size*0.7)};

		Polygon poly = new Polygon(xPoints, yPoints, 8);
		return poly;
	}
	public Line2D[] getLines(){
		int x1 = (int)(x-velX), y1 = (int)(y-velY);
		int[] xPoints = {(int)x1, (int)(x1+size*0.3), (int)(x1+size*0.7),(int)(x1+size), (int)(x1+size), (int)(x1+size*0.7), (int)(x1+size*0.3), (int)x1};
		int[] yPoints = {(int)(y1+size*0.3), (int)y1, (int)y1, (int)(y1+size*0.3), (int)(y1+size*0.7),(int)(y1+size),(int)(y1+size),(int)(y1+size*0.7)};
		Line2D[] lines = new Line2D[8];
		lines[0] = new Line2D.Double(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
		lines[1] = new Line2D.Double(xPoints[1], yPoints[1], xPoints[2], yPoints[2]);
		lines[2] = new Line2D.Double(xPoints[2], yPoints[2], xPoints[3], yPoints[3]);
		lines[3] = new Line2D.Double(xPoints[3], yPoints[3], xPoints[4], yPoints[4]);
		lines[4] = new Line2D.Double(xPoints[4], yPoints[4], xPoints[5], yPoints[5]);
		lines[5] = new Line2D.Double(xPoints[5], yPoints[5], xPoints[6], yPoints[6]);
		lines[6] = new Line2D.Double(xPoints[6], yPoints[6], xPoints[7], yPoints[7]);
		lines[7] = new Line2D.Double(xPoints[7], yPoints[7], xPoints[0], yPoints[0]);
		return lines;
	}	

	public void setX(int x){
		this.x=x;
	}
	public int getX(){
		return this.x;
	}

	public void setY(int y){
		this.y=y;
	}
	public int getY(){
		return this.y;
	}

	public void setSize(int size){
		this.size=size;
	}
	public int getSize(){
		return this.size;
	}

	public void setColor(Color color){
		this.color=color;
	}
	public Color getColor(){
		return this.color;
	}

	public void setVelocityX(double d){
		this.velX = d;
	}
	public double getVelocityX(){
		return this.velX;
	}

	public void setVelocityY(double d){
		this.velY=d;
	}
	public double getVelocityY(){
		return this.velY;
	}

	public void setVelocityT(){
		this.velT = Math.sqrt(this.velX*this.velX + this.velY * this.velY);
	}

	public double getVelocityT(){
		return this.velT;
	}
}