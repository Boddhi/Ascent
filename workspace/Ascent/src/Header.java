import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


public class Header {
	public void	drawMenu(){
		//DRAW THE TITLE AND MAYBE EVEN THE LOGO also depending on where the mouse is we should change the buttons
		
	}
	
	public void drawLose(Graphics2D graphics){
		Rectangle2D textBackground=new Rectangle2D.Double(219,232,292, 146);
		Rectangle2D textBackground2=new Rectangle2D.Double(222,235,286, 140);
    	String text = "You Lose!";
    	String text2 = "Press Back to Return";
    	String text3 = "to Menu";
    	graphics.setColor(Color.gray);
		graphics.fill(textBackground);
    	graphics.setColor(Color.darkGray);
		graphics.fill(textBackground2);
    	graphics.setColor(Color.red);
    	Font arial2= new Font("Tekton Pro", Font.PLAIN, 25);
    	Font arial= new Font("Tekton Pro", Font.PLAIN, 50);
    	graphics.setFont(arial);
    	graphics.drawString(text, 283  , 285);
    	graphics.setFont(arial2);
    	graphics.setColor(Color.orange);
    	graphics.drawString(text2, 270  , 310);
    	graphics.drawString(text3, 340, 335);
    }
	public void drawWin(Graphics2D graphics){
		Rectangle2D textBackground=new Rectangle2D.Double(219,232,292, 146);
		Rectangle2D textBackground2=new Rectangle2D.Double(222,235,286, 140);
    	String text = "You Win!";
    	String text2 = "Press Back to Return";
    	String text3 = "to Menu";
    	graphics.setColor(Color.gray);
		graphics.fill(textBackground);
    	graphics.setColor(Color.darkGray);
		graphics.fill(textBackground2);
    	graphics.setColor(Color.green);
    	Font arial2= new Font("Tekton Pro", Font.PLAIN, 25);
    	Font arial= new Font("Tekton Pro", Font.PLAIN, 50);
    	graphics.setFont(arial);
    	graphics.drawString(text, 283  , 285);
    	graphics.setFont(arial2);
    	graphics.setColor(Color.orange);
    	graphics.drawString(text2, 270  , 310);
    	graphics.drawString(text3, 340, 335);
    }
	
	public void drawScore(Graphics2D graphics, int Score){
	    	String text = "Score: " + Game.score;
	    	graphics.setColor(Color.white);
	    	graphics.setFont(new Font("Tekton Pro", Font.PLAIN, 30));
	    	graphics.drawString(text,30  , 120); 
	    	graphics.drawString(String.valueOf(Score),120,120);
	    }
	public void drawLives(Graphics2D graphics, int Lives) {
 	String text = "Lives:";
 	graphics.setColor(Color.white);
 	graphics.setFont(new Font("Tekton Pro", Font.PLAIN, 30));
 	graphics.drawString(text,30  , 80); 
 	graphics.drawString(String.valueOf(Lives),110,80);
 }
	public void drawLevel(Graphics2D graphics, int level) {
 	String text = "Level:";
 	graphics.setColor(Color.white);
 	graphics.setFont(new Font("Tekton Pro", Font.PLAIN, 30));
 	graphics.drawString(text,30  , 40); 
 	graphics.drawString(String.valueOf(level),110,40);
 }
	
	public void drawPause(Graphics2D graphics){
		// this is taking too long the first time we press pause idk\
		Rectangle2D textBackground=new Rectangle2D.Double(219,232,292, 146);
		Rectangle2D textBackground2=new Rectangle2D.Double(222,235,286, 140);
    	String text = "Paused!";
    	String text2 = "Press          to Resume";
    	String text3 = "Press                    to";
    	String text4 = "Return to Menu";
    	String text5 = " Enter";
    	String text6 = " BackSpace";
    	graphics.setColor(Color.gray);
		graphics.fill(textBackground);
    	graphics.setColor(Color.darkGray);
		graphics.fill(textBackground2);
    	graphics.setColor(Color.orange);
    	Font arial2= new Font("Tekton Pro", Font.PLAIN, 25);
    	Font arial= new Font("Tekton Pro", Font.PLAIN, 50);
    	graphics.setFont(arial);
    	graphics.setColor(Color.white);
    	graphics.drawString(text, 285  , 285);
    	graphics.setColor(Color.orange);
    	graphics.setFont(arial2);
    	graphics.drawString(text2, 250  , 310);
    	graphics.drawString(text3, 260, 335);
    	graphics.drawString(text4, 290, 360);
    	graphics.setColor(Color.red);
    	graphics.drawString(text5, 315, 310);
    	graphics.drawString(text6, 325, 335);
    }
   
    
    public void drawControls(Graphics2D graphics){
    	String text = "Q: Quit Game";
    	String text2= "P: Pause Game";
    	String text3= "Controls:";
    	String text4= "Use Mouse Keys";
    	String text5= "To Draw Reflectors";
    	graphics.setColor(Color.white);
    	graphics.setFont(new Font("Tekton Pro", Font.PLAIN, 30));
    	graphics.drawString(text,555  , 40); 
    	graphics.drawString(text2,555  , 80); 
    	graphics.drawString(text3,555  , 150); 
    	graphics.setFont(new Font("Tekton Pro", Font.PLAIN, 28));
    	graphics.drawString(text4,555  , 190); 
    	graphics.drawString(text5,555  , 225); 
    }
    public void drawLogo(BufferedImage logo, Graphics2D graphics){
    	graphics.drawImage(logo,30,250,200, 200,null);
    }
}
