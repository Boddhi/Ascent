import java.awt.Color;


public class Obstacles {
	private int x;
	private int y;
	private int width;
	private int height;
	private Color color;
	public Obstacles(int x, int y, int width, int height, Color color){
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
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
	public void setWidth(int width){
		this.width=width;
	}
	public int getWidth(){
		return this.width;
	}
	public void setHeight(int height){
		this.height=height;
	}
	public int getHeight(){
		return this.height;
	}
	public void setColor(Color color){
		this.color=color;
	}
	public Color getColor(){
		return this.color;
	}
}
