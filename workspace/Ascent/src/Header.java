import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;


public class Header {
		public void drawLose(Graphics2D graphics){
	    }
		public void drawWin(Graphics2D graphics){
	    }
		public void drawPause(Graphics2D graphics){
	    }
	    public void drawScore(Graphics2D graphics, int Score){
	    	String text = "Score:";
	    	graphics.setColor(Color.white);
	    	graphics.setFont(new Font("Tekton Pro", Font.PLAIN, 30));
	    	graphics.drawString(text,550  , 40); 
	    	graphics.drawString(String.valueOf(Score),640,40);
	    }
	    public void drawLives(Graphics2D graphics, int Lives) {
	    	String text = "Lives:";
	    	graphics.setColor(Color.white);
	    	graphics.setFont(new Font("Tekton Pro", Font.PLAIN, 30));
	    	graphics.drawString(text,670  , 40); 
	    	graphics.drawString(String.valueOf(Lives),750,40);
	    }
	    public void drawLevel(Graphics2D graphics, int level) {
	    	String text = "Level:";
	    	graphics.setColor(Color.white);
	    	graphics.setFont(new Font("Tekton Pro", Font.PLAIN, 30));
	    	graphics.drawString(text,30  , 40); 
	    	graphics.drawString(String.valueOf(level),110,40);
	    }
}
