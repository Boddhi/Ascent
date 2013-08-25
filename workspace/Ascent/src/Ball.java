import java.awt.Color;

public class Ball {
	private int x;
	private int y;
	private int width;
	private int height;
	private Color color;
	private int velX;
	private int velY;
	private int flipVelX;
	private int flipVelY;
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
	public void setVelocityX(int velX){
		this.velX=velX;
	}
	public int getVelocityX(){
		return this.velX;
	}
	public void setVelocityY(int velY){
		this.velY=velY;
	}
	public int getVelocityY(){
		return this.velY;
	}
	public int flipVelocityX(){
		this.flipVelX=velX*-1;
		return this.flipVelX;
	}
	public int flipVelocityY(){
		this.velY = velY*-1;
		return this.velY;
	}
	public Ball(int x, int y, int width, int height, Color color){
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setColor(color);
	}
}
