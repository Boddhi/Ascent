import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.Toolkit;
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
	static int WIDTH =  Toolkit.getDefaultToolkit().getScreenSize().width-1000;
	static int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height-200;
	private JFrame frame;

	// game updates per second
	static final int UPS = 60;
	static int t = 0;

	// variables for the thread
	private Thread thread;
	private boolean running;
	KeyBoard k = new KeyBoard();
	Mouse m = new Mouse();
	static MouseMotion mm = new MouseMotion();

	//Game_state Variables
	static int reset = -1;
	static boolean enterPressed, backspacePressed;
	static  int GAME_STATE = 0, GAME_MENU = 0, GAME_INSTRUCTION = 1,GAME_VIEW_HIGHSCORE = 2, GAME_PLAY = 3, GAME_PAUSE = 4, PAUSED_STATE = -1, GAME_WRITE_HIGHSCORE = 5, LOST_STATE = -1;
	/*static  int pausedBar_State = 0, pausedBar_Menu = 0, pausedBar_Instructions = 1, pausedBar_TimeTrial = 3, pausedBar_Survival = 4; 
	static  int menu_State = 1, menu_Menu = 0, menu_Instructions = 1, menu_Highscores = 2, menu_Play_Game = 3, menu_Credits = 4; */

	//Brandon Stuff
	//REMEMBER, Whenever you want to add an image, put it in the resources folder and use the "loadBufferedImage method"
	//Images and other Display
	Header header = new Header();
	BufferedImage backgroundImg = null;
	BufferedImage menuBackgroundImg = null;
	BufferedImage logo = null;
	public static boolean jar = false; // this variable lets the code know whether its being run in eclipse or not thus avoiding image loading errors. Without this we wouldnt be able to package images into our jar file

	//Menu Variables	
	double rateAverage = 0;
	//Menu buttons
	private MenuButton playButton = new MenuButton (225, 200, 356, 161, "PlayButton.png");
	private MenuButton helpButton = new MenuButton (250, 420, 300, 150, "HelpButton.png");
	private MenuButton quitButton = new MenuButton (250, 600, 300, 150, "QuitButton.png");
	private MenuButton [] menuButtons = {playButton, helpButton, quitButton}; 

	//Instruction Screen Variables

	//Highscore Variables
	static String playerName = "";
	Vector<Score> TimeTrialHS;
	Vector<Score> SurvivalHS;
	static int rating = 0;
	public static int score = 0;

	//Game_play Variables
	public static int scroll;
	public static ArrayList<Reflector> reflectors;
	public static ArrayList<Reflector> obstacles;
	public static Ball ball;

	// used for drawing items to the screen
	public Graphics2D graphics;

	//----------------------------------------------------------------------------------------
	// menu and gamestate changing methods
	public void updateGameState(){
	//	System.out.println(m.holding);
		if (reset != -1 && reset != GAME_PAUSE){
			GAME_STATE = reset;
			init();
			reset = -1;
		}
		if (reset == GAME_PAUSE){
			togglePause();
			reset = -1;
		}

	}
	public void togglePause(){
		if (PAUSED_STATE == -1){
			PAUSED_STATE = GAME_STATE;
			GAME_STATE = GAME_PAUSE;
		}
		else {
			GAME_STATE = PAUSED_STATE;
			PAUSED_STATE = -1;
			m.clear();
			}
	}

	// ---------------------------------------------------------------------------------------
	// initialize game objects, load media(pics, music, etc)
	public void init(){	
		if (GAME_STATE == GAME_MENU){
			initMenu();
		}
		if (GAME_STATE == GAME_INSTRUCTION){

		}
		if (GAME_STATE == GAME_VIEW_HIGHSCORE){

		}
		if (GAME_STATE == GAME_PLAY){
			initGamePlay();
		}
		if (GAME_STATE != GAME_PAUSE){

		}
		if (GAME_STATE == GAME_WRITE_HIGHSCORE){
			initWriteHighscore();
		}
	}

		public void initMenu(){
		/*	Vector<Integer> v = new Vector<Integer>(); 
			try {
		            Scanner s = new Scanner(new File("RatingsAndPlays.txt"));
		            while (s.hasNextLine()) {
		                v.add(Integer.parseInt(s.nextLine()));
		            }
		            s.close();
		        } catch (FileNotFoundException e) {
		            e.printStackTrace();
		        }
			rateAverage = 0;
			for(int i = 0; i<v.size();i++){
				rateAverage += v.elementAt(i);
			}
			rateAverage/=v.size();
			try {
				menuBackgroundImg = ImageIO.read(ResourceLoader.load("menuBackground.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			logo = loadBufferedImage("Deselect.png");
			initBackground();
		}
		public BufferedImage loadBufferedImage(String path){
			BufferedImage i = null;
			if (jar){
				try{
					i = ImageIO.read(ResourceLoader.load(path));
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				try {
					i = ImageIO.read(new File("images\\Logos\\" + path));
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}

			return i;
		}
		public void initGamePlay() {
			score = 0;
			scroll = 0;
			initWalls();
			initObstacles();
			initBall();
		}
			public void initWalls(){
				reflectors = new ArrayList<Reflector>();
				reflectors.add(new Reflector(0,HEIGHT-150,WIDTH,HEIGHT-150,1)); //a wall at the bottom of the screen			
				//walls.add(new Reflector(200,400,600,400)); no detection
				//walls.add(new Reflector(390,0,535,400)); //testing line
			}
			public void initObstacles(){

				obstacles = new ArrayList<Reflector>();
			}
			public void initBall(){
				ball = new Ball(WIDTH/2, HEIGHT-300, 50, Color.ORANGE);
			}

		public void initBackground(){
			try {
				//backgroundImg = ImageIO.read(ResourceLoader.load("b.png"));
				backgroundImg  = ImageIO.read(new File("b.png"));
			} catch (IOException e) {
			}
		}
		public void initWriteHighscore(){

		}
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// //update game objects
	public void update() {
		updateGameState();
		if (GAME_STATE == GAME_MENU){
			updateMenu();
		}
		if (GAME_STATE == GAME_INSTRUCTION){
			updateInstructions();
		}
		if (GAME_STATE == GAME_VIEW_HIGHSCORE){
			updateHighscoreScreen();
		}
		if (GAME_STATE == GAME_PLAY){
			updateGamePlay();
		}
		if (GAME_STATE != GAME_PAUSE){	
			k.shortCutKeys();
		}
		else {
			updatePauseBar();
		}
		if (GAME_STATE == GAME_WRITE_HIGHSCORE){
			//updateWriteHighscore();
		}
	}

		public void updateMenu(){
			if (m.getIsClicked()) {
				Point click = m.getClick().getPoint();

				if (playButton.contains(click)) {
					reset = GAME_PLAY;
				} 
				else if (helpButton.contains(click)) {
					reset = GAME_INSTRUCTION;
				}
				else if (quitButton.contains(click)) {
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

		public void updateInstructions(){
		}

		public void updateHighscoreScreen(){
			updatePauseBar();
		}

		public void updateGamePlay(){
			t++;
			updateReflectors();
			updateObstacles();
			updateBall();
		}
			public void updateReflectors(){	
				scroll++;
				Reflector r = m.getReflector();
				if (r!=null){
					reflectors.add(r);
				}
				for (int i = 0; i<reflectors.size(); i++){
					if (reflectors.get(i).isOutOfBounds()){
						reflectors.remove(i);
					}
				}
				if (reflectors.size()>5){
					reflectors.remove(0);
				}
				//System.out.println(walls.size());
			}
			public void updateObstacles(){
				
				if (t%60 == 0){
					obstacles.add(generateNewObstacle());
				}
				
				for (int i = 0; i<obstacles.size(); i++){
					if (obstacles.get(i).isOutOfBounds()){
						obstacles.remove(i);
					}
				}
			}
				public Reflector generateNewObstacle(){
					Random R = new Random();
					Reflector r = new Reflector(R.nextInt(800)-scroll, R.nextInt(800)-scroll, R.nextInt(800)-scroll, R.nextInt(800)-scroll);
					if (scroll == 799){
						System.out.println(scroll);
					}
					return r;
				}
			public void updateBall(){
				ball.live();
				//System.out.println(ball.getVelocityX() + " " + ball.getVelocityY());
				calculateBounce(reflectors);
				calculateBounce(obstacles);
			}
			
			public void calculateBounce(ArrayList<Reflector> obstacles2){
				//Angles are configured the following way:
				/*
				 *             180 deg
				 *              ^
				 *    270 deg <   > 90 deg
				 *              v
				 *             0/360 deg
				 * 
				 * 
				 */
				double totalV = ball.getVelocityT();
				double ballAngle, wallAngle=0, ballSlope=0, wallSlope=0, theta;
				if (ball.getVelocityX() == 0 && ball.getVelocityY() < 0) ballAngle = 180;
				else if(ball.getVelocityX() == 0 && ball.getVelocityY() > 0) ballAngle = 0;
				else if (ball.getVelocityY() == 0 && ball.getVelocityX() > 0) ballAngle = 90;
				else if (ball.getVelocityY() == 0) ballAngle = 270;
				else {
					ballSlope = ball.getVelocityX() / ball.getVelocityY();
					if(ball.getVelocityY() < 0) { 
						ballAngle = 180 + Math.toDegrees(Math.atan(ballSlope));
	//					System.out.println("A");
					}
					else {
						ballAngle = Math.toDegrees(Math.atan(ballSlope));
	//					System.out.println("B");
					}
				}
				for (int i = 0; i<obstacles2.size(); i++){
				    if(obstacles2.get(i).getY2() == obstacles2.get(i).getY1()) wallAngle = 90; 
				    else if (obstacles2.get(i).getX2() ==  obstacles2.get(i).getX1())wallAngle = 180;
				    else {
				    	wallSlope = (((double)(obstacles2.get(i).getX2() - obstacles2.get(i).getX1()) / (double)(obstacles2.get(i).getY2() - obstacles2.get(i).getY1())));
				    	wallAngle =  Math.toDegrees(Math.atan(wallSlope));
				    }
			    	theta = (ballAngle + 2*(wallAngle-ballAngle));
			    	theta = Math.toRadians(theta);
			 	    if(collision(obstacles2.get(i),ball) && !(obstacles2.get(i).getHitBall())){
	//		 	    	System.out.println("ball angle: " + ballAngle + " wallAngle: " + wallAngle + " theta: " + Math.toDegrees(theta));
				    	ball.setVelocityX(Math.sin(theta)*totalV);
				    	ball.setVelocityY(Math.cos(theta)*totalV);
				    	wallsHit(obstacles2, i);
				    }	
				}
			}
			public void wallsHit(ArrayList<Reflector> obstacles2, int i){
				for (int j = 0; j<obstacles2.size(); j++){
					Reflector r = obstacles2.get(j);
					if (j == i){
						r.setHitBall(true);
					}
					else {
						r.setHitBall(false);
					}
					obstacles2.set(j, r);
				}
			}
			
			public boolean collision(Reflector r, Ball b){
				//if(b.getEllipse().intersects(r.getLine().getBounds2D())){ //seems unnecessary
					Line2D[] l = b.getLines(); 
					for (int i = 0; i < 8; i++){
						if(r.getLine().intersectsLine(l[i])){
							return true;
						}
					}
				//}
				return false;
			}
		public void updatePauseBar(){
			//k.changePausedStates();
			if (enterPressed){
				reset = GAME_PAUSE;
				enterPressed = false;
			}if (backspacePressed){
				reset = GAME_MENU;
				backspacePressed = false;
			}
		}

	/*	public void updateWriteHighscore(){
			if(hs_State == hs_Writing){
				k.writePlayerName();
				if (enterPressed){
					if (LOST_STATE == GAME_RANDOM){
						writeTimeInTextFile();
					}
					else if (LOST_STATE == GAME_SURVIVAL){
						writeScoreInTextFile();
					}
					hs_State = hs_Rating;
					enterPressed = false;
				}
			}
			if(hs_State == hs_Rating){
				k.rateGame();
				if (enterPressed){		
					writeRatingInTextFile();
					rating = 0;
					reset = LOST_STATE;
					hs_State = hs_Writing;
					enterPressed = false;
				}
			}
			
		}*/
			public void writeTimeInTextFile(){
				try {
					PrintWriter timeFileWriter = new PrintWriter(new BufferedWriter(new FileWriter("TimeHighScore.txt", true)));
					Double time = (double)(t/6)/10;
					String text = time.toString();
					timeFileWriter.println(playerName.trim() + "||" + text);
					timeFileWriter.close();	
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			public void writeScoreInTextFile(){
				try {
					PrintWriter scoreFileWriter = new PrintWriter(new BufferedWriter(new FileWriter("ScoreHighScore.txt", true)));
					scoreFileWriter.println(playerName.trim() + "||" + score);
					scoreFileWriter.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e){
					e.printStackTrace();
				}

			}
			public void writeRatingInTextFile(){
				try {
					PrintWriter scoreFileWriter = new PrintWriter(new BufferedWriter(new FileWriter("RatingsAndPlays.txt", true)));
					if (rating>5){
						rating = 5;
					}
					scoreFileWriter.println(rating);
					scoreFileWriter.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e){
					e.printStackTrace();
				}					
			}
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// draw things to the screen
	public void draw() {
		if (GAME_STATE == GAME_MENU){
			drawMenu();
		}
		if (GAME_STATE == GAME_INSTRUCTION){
			drawInstructions();
		}
		if (GAME_STATE == GAME_VIEW_HIGHSCORE){
			drawHighscoreScreen();
		}
		if (GAME_STATE == GAME_PLAY){
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			drawGamePlay();
		}
		if (GAME_STATE == GAME_PAUSE){
			drawPauseBar(true);
		}
		if (GAME_STATE == GAME_WRITE_HIGHSCORE){
			//drawWriteHighscore();
		}
	}

		public void drawMenu(){
			drawBackground(Color.black);
			playButton.draw(graphics);
			helpButton.draw(graphics);
			quitButton.draw(graphics);
		}

		public void drawInstructions(){
			drawBackground(Color.red);
			header.drawControls(graphics);

		}

		public void drawHighscoreScreen(){

		}
			public void drawTimeHighscore(Vector<Score> v, int x){
				for(int i = 0; i<v.size(); i++){
					writeText(Color.cyan, 30, v.elementAt(i).toString(), x, (40*i)+100);
				}
			}
			public void drawScoreHighscore(Vector<Score> v, int x){
				for(int i = v.size()-1; i>0; i--){
					writeText(Color.cyan, 30, v.elementAt(i).toString(), x, (40*((v.size()-i)-1))+100);
				}
			}

		public void drawGamePlay(){	

			drawBackground(Color.gray);
			graphics.translate(0, scroll);
			if (m.holding){
				graphics.setColor(Color.black);
				graphics.drawLine((int)m.x1, (int)m.y1, (int)mm.x, (int)mm.y);
			}
			drawReflectors();
			drawObstacles();
			drawBall();
			//System.out.println(mm.x + ", " + mm.y);
		}
			public void drawReflectors(){
				for(int i = 0; i<reflectors.size(); i++){
				//	System.out.println(i);
					graphics.setColor(Color.cyan);
				//	System.out.println(i);
					float thickness = 15;
					Stroke oldStroke = graphics.getStroke();
					graphics.setStroke(new BasicStroke(thickness));
					reflectors.get(i).draw(graphics);
					graphics.setStroke(oldStroke);
				}
			}
			public void drawObstacles(){
				for(int i = 0; i<obstacles.size(); i++){
				//	System.out.println(i);
					graphics.setColor(Color.green);
				//	System.out.println(i);
					float thickness = 15;
					Stroke oldStroke = graphics.getStroke();
					graphics.setStroke(new BasicStroke(thickness));
					obstacles.get(i).draw(graphics);
					graphics.setStroke(oldStroke);
				}
			}
			public void drawBall(){
				graphics.setColor(ball.getColor());
				graphics.fill(ball.getEllipse());
				//System.out.println("ran");
			}

			public void drawBackground(Color c) {
				graphics.setColor(c);
				graphics.fillRect(0, 0, WIDTH, HEIGHT);
			}
			public void drawScore(){
				String text = score + "";
				writeText(Color.orange, 40, text, 0, 30);
			}
			public void drawTime(){
				Double time = (double)(t/6)/10;
				String text = time.toString();
				writeText(Color.orange, 40, text, 400, 30);
			}
			public void drawPlayer() {

			}

		public void drawPauseBar(boolean one){
			/*int pausedBarX = (WIDTH/2)+100;
			int y0 = 60, y1 = 120, y2 = 180, y3 = 240, y4 = 300;
			int textSize = 40;
			int add = 10;
		//	System.out.println(GAME_STATE);
			if (one){
				add = 0;
				writeText(Color.orange, textSize+20, "GAME PAUSED", WIDTH/2, y0);
			}*/
			header.drawPause(graphics);
		}
		public void writeText(Color c, int size, String text, int x, int y){
			graphics.setColor(c);
			graphics.setFont(new Font("Arial", Font.PLAIN, size));
			graphics.drawString(text, x, y);
		}

		/*public void drawWriteHighscore(){	 
			drawBackground();
			drawScore();
			drawTime();
			if (hs_State == hs_Writing){
				writeText(Color.red, 50, "Please Type Your Name(Max 8 Characters):", 100, 100);
				writeText(Color.CYAN, 40, "Please Rate This Game Out of 5(Type the Number)", 100, 300);
			}
			else if (hs_State == hs_Rating){
				writeText(Color.CYAN, 50, "Please Type Your Name(Max 8 Characters):", 100, 100);
				writeText(Color.red, 40, "Please Rate This Game Out of 5(Type the Number)", 100, 300);
			}
			
			writeText(Color.CYAN, 40, playerName, 100, 200);
		
			String r = rating + "";
			writeText(Color.CYAN, 40, r, 100, 400);
		}*/
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
//:3
