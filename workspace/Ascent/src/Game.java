import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	// screen dimensions and variables
	static int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width - 1000;
	static int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height - 200;
	private JFrame frame;

	// game updates per second
	static final int UPS = 60;
	static int t = 0;

	static int scrollSpeed = 1;
	static double ballXSpeed = -100, ballYSpeed;

	// variables for the thread
	private Thread thread;
	private boolean running;
	KeyBoard k = new KeyBoard();
	Mouse m = new Mouse();
	static MouseMotion mm = new MouseMotion();

	// Game_state Variables
	static int reset = -1;
	static boolean enterPressed, backspacePressed;
	static int GAME_STATE = 0, GAME_MENU = 0, GAME_INSTRUCTION = 1,
			GAME_VIEW_HIGHSCORE = 2, GAME_PLAY = 3, GAME_PAUSE = 4,
			PAUSED_STATE = -1, GAME_WRITE_HIGHSCORE = 5, LOST_STATE = -1;
	/*
	 * static int pausedBar_State = 0, pausedBar_Menu = 0,
	 * pausedBar_Instructions = 1, pausedBar_TimeTrial = 3, pausedBar_Survival =
	 * 4; static int menu_State = 1, menu_Menu = 0, menu_Instructions = 1,
	 * menu_Highscores = 2, menu_Play_Game = 3, menu_Credits = 4;
	 */

	// Brandon Stuff
	// REMEMBER, Whenever you want to add an image, put it in the resources
	// folder and use the "loadBufferedImage method"
	// Images and other Display
	Header header = new Header();
	BufferedImage backgroundImg = null;
	BufferedImage menuBackgroundImg = null;
	BufferedImage logo = null;
	public static boolean jar = true; /*
									 * this variable lets the code know whether
									 * its being run in eclipse or not thus
									 * avoiding image loading errors. Without
									 * this we wouldnt be able to package images
									 * into our jar file
									 */

	// Menu Variables
	double rateAverage = 0;
	// Menu buttons
	private MenuButton playButton = new MenuButton(225, 200, 356, 161,
			"PlayButton.png");
	private MenuButton helpButton = new MenuButton(250, 420, 300, 150,
			"HelpButton.png");
	private MenuButton quitButton = new MenuButton(250, 600, 300, 150,
			"QuitButton.png");
	private MenuButton[] menuButtons = { playButton, helpButton, quitButton };

	// Instruction Screen Variables

	// Highscore Variables
	static String playerName = "";
	Vector<Score> TimeTrialHS;
	Vector<Score> SurvivalHS;
	static int rating = 0;
	public static int score = 0;

	// Game_play Variables
	public static int scroll;
	public static ArrayList<Reflector> reflectors;
	public static Ball ball;

	// used for drawing items to the screen
	public Graphics2D graphics;

	// ----------------------------------------------------------------------------------------
	// menu and gamestate changing methods
	public void updateGameState() {
		// System.out.println(m.holding);
		if (reset != -1 && reset != GAME_PAUSE) {
			GAME_STATE = reset;
			init();
			reset = -1;
		}
		if (reset == GAME_PAUSE) {
			togglePause();
			reset = -1;
		}

	}

	public void togglePause() {
		if (PAUSED_STATE == -1) {
			PAUSED_STATE = GAME_STATE;
			GAME_STATE = GAME_PAUSE;
		} else {
			GAME_STATE = PAUSED_STATE;
			PAUSED_STATE = -1;
			m.clear();
		}
	}

	// ---------------------------------------------------------------------------------------
	// initialize game objects, load media(pics, music, etc)
	public void init() {
		if (GAME_STATE == GAME_MENU) {
			initMenu();
		}
		if (GAME_STATE == GAME_INSTRUCTION) {

		}
		if (GAME_STATE == GAME_VIEW_HIGHSCORE) {

		}
		if (GAME_STATE == GAME_PLAY) {
			initGamePlay();
		}
		if (GAME_STATE != GAME_PAUSE) {

		}
		if (GAME_STATE == GAME_WRITE_HIGHSCORE) {
			initWriteHighscore();
		}
	}

	public void initMenu() {
		/*
		 * Vector<Integer> v = new Vector<Integer>(); try { Scanner s = new
		 * Scanner(new File("RatingsAndPlays.txt")); while (s.hasNextLine()) {
		 * v.add(Integer.parseInt(s.nextLine())); } s.close(); } catch
		 * (FileNotFoundException e) { e.printStackTrace(); } rateAverage = 0;
		 * for(int i = 0; i<v.size();i++){ rateAverage += v.elementAt(i); }
		 * rateAverage/=v.size(); try { menuBackgroundImg =
		 * ImageIO.read(ResourceLoader.load("menuBackground.jpg")); } catch
		 * (IOException e) { e.printStackTrace(); }
		 */
		// logo = loadBufferedImage("Deselect.png");
		// initBackground();
	}

	/*
	 * public BufferedImage loadBufferedImage(String path){ BufferedImage i =
	 * null; if (jar) { try { i = ImageIO.read(ResourceLoader.load(path)); }
	 * catch (IOException e) { e.printStackTrace(); } } else { try { i =
	 * ImageIO.read(new File("images\\Logos\\" + path)); } catch (IOException e)
	 * { e.printStackTrace(); } }
	 * 
	 * return i; }
	 */

	public void initGamePlay() {
		score = 0;
		scroll = 0;
		initReflectors();
		initObstacles();
		initBall();
	}

	public void initReflectors() {
		reflectors = new ArrayList<Reflector>();
		// walls.add(new Reflector(0,HEIGHT-150,WIDTH,HEIGHT-150,1)); //a wall
		// at the bottom of the screen
		// walls.add(new Reflector(200,400,600,400)); no detection
		// walls.add(new Reflector(390,0,535,400)); //testing line
	}

	public void initObstacles() {

	}

	public void initBall() {
		ball = new Ball(WIDTH / 2, HEIGHT - 300, 50, Color.ORANGE);
	}

	public void initBackground() {
		try {
			backgroundImg = ImageIO.read(new File("b.png"));
		} catch (IOException e) {
		}
	}

	public void initWriteHighscore() {

	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// //update game objects
	public void update() {
		updateGameState();
		if (GAME_STATE == GAME_MENU) {
			updateMenu();
		}
		if (GAME_STATE == GAME_INSTRUCTION) {
			updateInstructions();
		}
		if (GAME_STATE == GAME_VIEW_HIGHSCORE) {
			updateHighscoreScreen();
		}
		if (GAME_STATE == GAME_PLAY) {
			updateGamePlay();
		}
		if (GAME_STATE != GAME_PAUSE) {
			k.shortCutKeys();
		} else {
			updatePauseBar();
		}
		if (GAME_STATE == GAME_WRITE_HIGHSCORE) {
			// updateWriteHighscore();
		}
	}

	public void updateMenu() {
		if (m.getIsClicked()) {
			Point click = m.getClick().getPoint();

			if (playButton.contains(click)) {
				reset = GAME_PLAY;
			} else if (helpButton.contains(click)) {
				reset = GAME_INSTRUCTION;
			} else if (quitButton.contains(click)) {
				running = false;
			}
		}

		Point mousePosition = mm.getMouse();

		if (mousePosition != null) {
			for (MenuButton mb : menuButtons) {
				if (mb.contains(mousePosition)) {
					mb.setHoveringOver(true);
				} else {
					mb.setHoveringOver(false);
				}
			}
		}
	}

	public void updateInstructions() {
	}

	public void updateHighscoreScreen() {
		updatePauseBar();
	}

	public void updateGamePlay() {
		t++;
		updateReflectors();
		updateObstacles();
		updateBall();
	}

	public void updateReflectors() {
		Reflector r = m.getReflector();
		if (r != null) {
			reflectors.add(r);
		}
		for (int i = 0; i < reflectors.size(); i++) {
			if (reflectors.get(i).isOutOfBounds()) {
				reflectors.remove(i);
			}
		}
		if (reflectors.size() > 5) {
			reflectors.remove(0);
		}
		// System.out.println(walls.size());
	}

	public void updateObstacles() {

	}

	public void updateBall() {
		updateScroll();
		ball.live();
		for (int i = 0; i < reflectors.size(); i++) {
			collision(reflectors.get(i), ball, i);
		}
		// System.out.println(ball.getVelocityX() + " " + ball.getVelocityY());
	}

	public void updateScroll() {
		scroll += scrollSpeed;
		pan();
	}

	public void pan() {
		if (ball.getY() <= 50 - scroll) {
			scrollSpeed = -((int) ball.getVelocityY()) * 2;
			ballXSpeed = ball.getVelocityX();
			ballYSpeed = ball.getVelocityY();
			ball.setVelocityX(0);
			ball.setVelocityY(0);
		}
		if (ball.getY() > 500 - scroll && ballXSpeed > -100) {
			scrollSpeed = 1;
			ball.setVelocityX(ballXSpeed);
			ball.setVelocityY(ballYSpeed);
			ballXSpeed = -100;
		}
	}

	public void collision(Reflector reflector, Ball ball, int index) {
		// if(b.getEllipse().intersects(r.getLine().getBounds2D())){ //seems
		// unnecessary
		// that was put in there to speed up code. basically why run two nested
		// for loops for objects miles apart.(if speeed ever becomes a problem)
		// - Vanshil
		Line2D[] ballLines = ball.getLines();
		Line2D[] reflectorLines = reflector.get();
		for (int i = 0; i < reflectorLines.length; i++) {
			for (int j = 0; j < 8; j++) {
				if (reflectorLines[i].intersectsLine(ballLines[j])
						&& !(reflectors.get(index).getHitBall())) {
					calculateBounce(reflectorLines[i]);
					wallsHit(index);
				}
			}
		}

	}

	public void calculateBounce(Line2D reflectorLine) {
		// Angles are configured the following way:
		/*
		 * 180 deg ^ 270 deg < > 90 deg v 0/360 deg
		 */
		double totalV = ball.getVelocityT();
		double ballAngle, wallAngle = 0, ballSlope = 0, wallSlope = 0, theta;

		if (ball.getVelocityX() == 0 && ball.getVelocityY() < 0)
			ballAngle = 180;
		else if (ball.getVelocityX() == 0 && ball.getVelocityY() > 0)
			ballAngle = 0;
		else if (ball.getVelocityY() == 0 && ball.getVelocityX() > 0)
			ballAngle = 90;
		else if (ball.getVelocityY() == 0)
			ballAngle = 270;
		else {
			ballSlope = ball.getVelocityX() / ball.getVelocityY();
			if (ball.getVelocityY() < 0) {
				ballAngle = 180 + Math.toDegrees(Math.atan(ballSlope));
				// System.out.println("A");
			} else {
				ballAngle = Math.toDegrees(Math.atan(ballSlope));
				// System.out.println("B");
			}
		}
		// for (int i = 0; i < walls.size(); i++) {
		graphics.setColor(Color.black);
		graphics.draw(reflectorLine);
		double x1 = reflectorLine.getX1(), y1 = reflectorLine.getY1(), x2 = reflectorLine
				.getX2(), y2 = reflectorLine.getY2();
		if (y2 == y1) {
			wallAngle = 90;
		} else if (x2 == x1) {
			wallAngle = 180;
		} else {
			wallSlope = (x2 - x1) / (y2 - y1);
			wallAngle = Math.toDegrees(Math.atan(wallSlope));
		}

		theta = (ballAngle + 2 * (wallAngle - ballAngle));
		theta = Math.toRadians(theta);

		// System.out.println("ball angle: " + ballAngle +
		// " wallAngle: " + wallAngle + " theta: " +
		// Math.toDegrees(theta));
		ball.setVelocityX(Math.sin(theta) * totalV);
		ball.setVelocityY(Math.cos(theta) * totalV);

		// System.out.println("(" + reflectorLine.getX1() + ", " +
		// reflectorLine.getY1() + ")" + "(" + reflectorLine.getX2() + ", " +
		// reflectorLine.getY2() + ")");

		// }
	}

	public void wallsHit(int i) {
		for (int j = 0; j < reflectors.size(); j++) {
			Reflector r = reflectors.get(j);
			if (j == i) {
				r.setHitBall(true);
			} else {
				r.setHitBall(false);
			}
			reflectors.set(j, r);
		}
	}

	public void updatePauseBar() {
		// k.changePausedStates();
		if (enterPressed) {
			reset = GAME_PAUSE;
			enterPressed = false;
		}
		if (backspacePressed) {
			reset = GAME_MENU;
			backspacePressed = false;
		}
	}

	/*
	 * public void updateWriteHighscore(){ if(hs_State == hs_Writing){
	 * k.writePlayerName(); if (enterPressed){ if (LOST_STATE == GAME_RANDOM){
	 * writeTimeInTextFile(); } else if (LOST_STATE == GAME_SURVIVAL){
	 * writeScoreInTextFile(); } hs_State = hs_Rating; enterPressed = false; } }
	 * if(hs_State == hs_Rating){ k.rateGame(); if (enterPressed){
	 * writeRatingInTextFile(); rating = 0; reset = LOST_STATE; hs_State =
	 * hs_Writing; enterPressed = false; } }
	 * 
	 * }
	 */
	public void writeTimeInTextFile() {
		try {
			PrintWriter timeFileWriter = new PrintWriter(new BufferedWriter(
					new FileWriter("TimeHighScore.txt", true)));
			Double time = (double) (t / 6) / 10;
			String text = time.toString();
			timeFileWriter.println(playerName.trim() + "||" + text);
			timeFileWriter.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void writeScoreInTextFile() {
		try {
			PrintWriter scoreFileWriter = new PrintWriter(new BufferedWriter(
					new FileWriter("ScoreHighScore.txt", true)));
			scoreFileWriter.println(playerName.trim() + "||" + score);
			scoreFileWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void writeRatingInTextFile() {
		try {
			PrintWriter scoreFileWriter = new PrintWriter(new BufferedWriter(
					new FileWriter("RatingsAndPlays.txt", true)));
			if (rating > 5) {
				rating = 5;
			}
			scoreFileWriter.println(rating);
			scoreFileWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// draw things to the screen
	public void draw() {
		if (GAME_STATE == GAME_MENU) {
			drawMenu();
		}
		if (GAME_STATE == GAME_INSTRUCTION) {
			drawInstructions();
		}
		if (GAME_STATE == GAME_VIEW_HIGHSCORE) {
			drawHighscoreScreen();
		}
		if (GAME_STATE == GAME_PLAY) {
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			drawGamePlay();
		}
		if (GAME_STATE == GAME_PAUSE) {
			drawPauseBar(true);
		}
		if (GAME_STATE == GAME_WRITE_HIGHSCORE) {
			// drawWriteHighscore();
		}
	}

	public void drawMenu() {
		drawBackground(Color.black);
		playButton.draw(graphics);
		helpButton.draw(graphics);
		quitButton.draw(graphics);
	}

	public void drawInstructions() {
		drawBackground(Color.red);
		header.drawControls(graphics);

	}

	public void drawHighscoreScreen() {

	}

	public void drawTimeHighscore(Vector<Score> v, int x) {
		for (int i = 0; i < v.size(); i++) {
			writeText(Color.cyan, 30, v.elementAt(i).toString(), x,
					(40 * i) + 100);
		}
	}

	public void drawScoreHighscore(Vector<Score> v, int x) {
		for (int i = v.size() - 1; i > 0; i--) {
			writeText(Color.cyan, 30, v.elementAt(i).toString(), x,
					(40 * ((v.size() - i) - 1)) + 100);
		}
	}

	public void drawGamePlay() {
		drawScore();

		drawBackground(Color.gray);
		graphics.translate(0, scroll);
		drawIndicatorLine();
		drawReflectors();
		drawObstacles();
		drawBall();
		// System.out.println(mm.x + ", " + mm.y);
	}

	public void drawIndicatorLine() {
		
		if (m.holding) {
			
			double x2 = mm.x, y2 = mm.y, x1 = m.x1, y1 = m.y1; 
			
			Point2D.Double clickPoint = new Point2D.Double(0, 0);
			clickPoint = new Point2D.Double(m.x1, m.y1);
			Point2D.Double cursorPoint = new Point2D.Double(0,0);
			cursorPoint = new Point2D.Double(mm.x, mm.y);


			Point2D.Double endPoint = cursorPoint;
			double slope = (y2 - y1) / (x2 - x1);
			double recipSlope = -(x2 - x1) / (y2 - y1);
			Line2D.Double tempLine = new Line2D.Double(clickPoint, cursorPoint);
			System.out.println(getLength(tempLine));
			if (getLength(tempLine) > 200) {
				endPoint = getNewPoint(slope, clickPoint, cursorPoint, 200);
			}
			Line2D.Double line = new Line2D.Double(clickPoint, endPoint);

			Point2D.Double topLeftPoint = getNewPoint(recipSlope, clickPoint, endPoint, -20);
			Point2D.Double bottomLeftPoint = getNewPoint(recipSlope, clickPoint, endPoint, 20);
			Point2D.Double topRightPoint = getNewPoint(recipSlope, endPoint, clickPoint, 20);
			Point2D.Double bottomRightPoint = getNewPoint(recipSlope, endPoint, clickPoint, -20);
			Line2D.Double leftLine = new Line2D.Double(bottomLeftPoint, topLeftPoint);
			Line2D.Double topLine = new Line2D.Double(topLeftPoint, topRightPoint);
			Line2D.Double rightLine = new Line2D.Double(topRightPoint, bottomRightPoint);
			Line2D.Double bottomLine = new Line2D.Double(bottomLeftPoint, bottomRightPoint);
			Line2D.Double [] lines = new Line2D.Double[]{leftLine, topLine, rightLine, bottomLine};

//			BufferedImage originalImage = null;
//			originalImage = loadBufferedImage("Reflector.png");
//			System.out.println(getLength(line));
//			if (getLength(line) > 0) {
//			originalImage = originalImage.getSubimage(0, 0,
//					(int) getLength(line), 40);
//			BufferedImage indicatorReflector = originalImage;
//			
//			
//			for (int i = 0; i < originalImage.getWidth(); i++) {
//				for (int j = 0; j < originalImage.getHeight(); j++) {
//					Color highLightedColor = convertIntToColor(originalImage.getRGB(i, j));
//					int darkerRGB = convertColorToInt(highLightedColor.darker());			
//					indicatorReflector.setRGB(i, j, darkerRGB);
//				}
//			}

			double angle = Math.atan(slope) * 180 / Math.PI;
			if (angle < 0) {
				angle += 360;
			}
//
//			double rotateAt = Math.toRadians(angle);
//
//			AffineTransform at = new AffineTransform();
//
//			if (clickPoint.getX() < cursorPoint.getX()) {
//				if (slope > 0) {
//					at.translate(bottomLeftPoint.getX(), bottomLeftPoint.getY());
//				} else {
//					at.translate(topLeftPoint.getX(), topLeftPoint.getY());
//				}
//			} else {
//				if (slope < 0) {
//					at.translate(bottomRightPoint.getX(), bottomRightPoint.getY());
//				} else {
//					at.translate(topRightPoint.getX(), topRightPoint.getY());
//				}
//			}
//			at.rotate(rotateAt);
//			
//			at.translate(-reflectorImage.getWidth()/2, -reflectorImage.getHeight()/2);
//			graphics.drawImage(indicatorReflector, at, null);
			graphics.setColor(Color.black); 
			for (Line2D l : lines) {
				graphics.draw(l);
			}
		}
	}
	
	public static Color convertIntToColor (int rgb)
	{
		return new Color(rgb);
	}

	public static int convertColorToInt (Color color) 
	{
		return color.getRGB();
	}

	private Point2D.Double getNewPoint(double slope, Point2D.Double clickPoint,
			Point2D.Double releasePoint, int maxLength) {
		int length = maxLength;
		double angle = Math.atan(slope) * 180 / Math.PI;
		if (angle < 0) {
			angle += 360;
		}
		double newPointX = (maxLength * Math.cos(Math.toRadians(angle)) + clickPoint
				.getX());
		double newPointY = (maxLength * Math.sin(Math.toRadians(angle)) + clickPoint
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

	private double getLength(Line2D.Double line) {
		double x1 = line.getX1();
		double x2 = line.getX2();
		double y1 = line.getY1();
		double y2 = line.getY2();
		double length = Math
				.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		return length;
	}

	public void drawReflectors() {
		for (int i = 0; i < reflectors.size(); i++) {
			// System.out.println(i);
			graphics.setColor(Color.cyan);
			// System.out.println(i);
			reflectors.get(i).draw(graphics);
		}
	}

	public void drawObstacles() {

	}

	public void drawBall() {
		graphics.setColor(ball.getColor());
		graphics.fill(ball.getEllipse());
		graphics.setColor(Color.black);
		graphics.draw(ball.getPolygon());
		// System.out.println("ran");
	}

	public void drawBackground(Color c) {
		graphics.setColor(c);
		graphics.fillRect(0, 0, WIDTH, HEIGHT);
	}

	public void drawScore() {
		String text = score + "";
		// writeText(Color.orange, 40, text, 0, 30);
		// header.drawScore(graphics, scroll);
	}

	public void drawTime() {
		Double time = (double) (t / 6) / 10;
		String text = time.toString();
		writeText(Color.orange, 40, text, 400, 30);
	}

	public void drawPlayer() {

	}

	public void drawPauseBar(boolean one) {
		/*
		 * int pausedBarX = (WIDTH/2)+100; int y0 = 60, y1 = 120, y2 = 180, y3 =
		 * 240, y4 = 300; int textSize = 40; int add = 10; //
		 * System.out.println(GAME_STATE); if (one){ add = 0;
		 * writeText(Color.orange, textSize+20, "GAME PAUSED", WIDTH/2, y0); }
		 */
		header.drawPause(graphics);
	}

	public void writeText(Color c, int size, String text, int x, int y) {
		graphics.setColor(c);
		graphics.setFont(new Font("Arial", Font.PLAIN, size));
		graphics.drawString(text, x, y);
	}
	
	public BufferedImage loadBufferedImage(String path){
		BufferedImage i = null;
		if (Game.jar){
			try{
				i = ImageIO.read(ResourceLoader.load(path));
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				i = ImageIO.read(new File("images\\" + path));
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return i;
	}

	/*
	 * public void drawWriteHighscore(){ drawBackground(); drawScore();
	 * drawTime(); if (hs_State == hs_Writing){ writeText(Color.red, 50,
	 * "Please Type Your Name(Max 8 Characters):", 100, 100);
	 * writeText(Color.CYAN, 40,
	 * "Please Rate This Game Out of 5(Type the Number)", 100, 300); } else if
	 * (hs_State == hs_Rating){ writeText(Color.CYAN, 50,
	 * "Please Type Your Name(Max 8 Characters):", 100, 100);
	 * writeText(Color.red, 40,
	 * "Please Rate This Game Out of 5(Type the Number)", 100, 300); }
	 * 
	 * writeText(Color.CYAN, 40, playerName, 100, 200);
	 * 
	 * String r = rating + ""; writeText(Color.CYAN, 40, r, 100, 400); }
	 */
	// ---------------------------------------------------------------------------------------
	// main game loop
	public void run() {
		init();
		long startTime = System.nanoTime();
		double ns = 1000000000.0 / UPS;
		double delta = 0;
		int frames = 0;
		int updates = 0;

		long secondTimer = System.nanoTime();
		while (running) {
			long now = System.nanoTime();
			delta += (now - startTime) / ns;
			startTime = now;
			while (delta >= 1) {
				update();
				delta--;
				updates++;
			}
			render();
			frames++;

			if (System.nanoTime() - secondTimer > 1000000000) {
				this.frame.setTitle(updates + " ups  ||  " + frames + " fps");
				secondTimer += 1000000000;
				frames = 0;
				updates = 0;
			}
		}
		stop();

	}

	// ---------------------------------------------------------------------------------------
	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.add(game); // game is a component because it extends Canvas
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		game.start();
	}

	public Game() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		frame = new JFrame();
		this.requestFocus();
		this.setBackground(Color.black);
		this.addKeyListener(k);
		this.addMouseListener(m);
		this.addMouseMotionListener(mm);
	}

	// starts a new thread for the game
	public synchronized void start() {
		thread = new Thread(this, "Game");
		running = true;
		thread.start();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy(); // method from Canvas class

		if (bs == null) {
			createBufferStrategy(3); // creates it only for the first time the
										// loop runs (trip buff)
			return;
		}

		graphics = (Graphics2D) bs.getDrawGraphics();
		draw();
		graphics.dispose();
		bs.show();
	}

	// stops the game thread and quits
	public synchronized void stop() {
		System.exit(0);
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}