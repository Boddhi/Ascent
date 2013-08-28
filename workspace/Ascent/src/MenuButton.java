import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.color.*;

import javax.imageio.ImageIO;


public class MenuButton {
	private final int x;
	private final int y;
	private final int width;
	private final int height;
	private BufferedImage originalImage = null;
	private BufferedImage darkenedImage = null;
	private boolean hoveringOver = false;
	private boolean pressed = false;

	
	
	public MenuButton(int x, int y, int width, int height, String imageName) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		try {
			this.originalImage = ImageIO.read(new File(imageName));
			this.darkenedImage = ImageIO.read(new File(imageName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < originalImage.getWidth(); i++) {
			for (int j = 0; j < originalImage.getHeight(); j++) {
				Color highLightedColor = convertIntToColor(originalImage.getRGB(i, j));
				int darkenedRGB = convertColorToInt(highLightedColor.darker());			
				this.darkenedImage.setRGB(i, j, darkenedRGB);
			}
		}	
	}
	
	public void draw(Graphics2D graphics) {
		if (hoveringOver) {
			graphics.drawImage(darkenedImage,x, y, width, height, null);
		}
		else if (!hoveringOver) {
			graphics.drawImage(originalImage,x, y, width, height, null);
		}
	}
	
	public boolean contains (Point mousePosition) {
		int mouseY = (int) mousePosition.getY();
		int mouseX = (int) mousePosition.getX();
		boolean contained = false;
		if (mouseY < y + height && mouseY > y && mouseX < x + width && mouseX > x) 
			{
				contained = true;
			}
		return contained;
	}

	public static Color convertIntToColor (int rgb)
	{
		return new Color(rgb);
	}
		
	public static int convertColorToInt (Color color) 
	{
		return color.getRGB();
	}
	
	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}

	public boolean isHoveringOver() {
		return hoveringOver;
	}
	
	public void setHoveringOver(boolean hoveringOver) {
		this.hoveringOver = hoveringOver;
	}
}
