import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {
	// gamestates
	private int gameState;
	private final int GAME_TITLE = 0;
	private final int GAME_MENU = 1;
	private final int GAME_START = 2;
	private final int GAME_CONTROL = 3;
	private final int GAME_QUIT = 4;
	private final int GAME_WIN = 5;
	private final int GAME_LOSE = 6;
	private final int GAME_PAUSE = 7;
	private final int GAME_RATING = 8;
	// menuStates
	private Menu menu;
	private int menuState;
	private final int MENU_PLAY = 0;
	private final int MENU_CONTROL = 1;
	private final int MENU_QUIT = 2;
	private final int MENU_RATEONE = 3;
	private final int MENU_RATETWO = 4;
	private final int MENU_RATETHREE = 5;
	private boolean[] keyPressed = new boolean[8];
	private final int KEY_LEFT = 0, KEY_RIGHT = 1, KEY_UP = 2, KEY_DOWN = 3,
			KEY_Q = 4, KEY_ENTER = 5, KEY_BACKSPACE = 6, KEY_P = 7;
	private KeyBoard keyboard = new KeyBoard();
	// Buffered Images
	private BufferedImage Title;
	private BufferedImage Menu;
	private BufferedImage Control;
	private BufferedImage spriteSheet;
	// in-Game screen variables
	private Header header = new Header();
	private int Score;
	private int Lives;
	private int level;
	private int playNum = 100;
	private int rate = 0;
	private double FinalRate = 0;
	private int rateEntries = 0;
	// used for drawing items to the screen
	private Graphics2D graphics;

	static Ball ball;
	// screen dimensions and variables
	static final int WIDTH = 800;
	static final int HEIGHT = 600;
	private JFrame frame;

	// game updates per second
	static final int UPS = 60;

	// variables for the thread
	private Thread thread;
	private boolean running;

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
		this.setBackground(Color.black);
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		frame = new JFrame();
		this.setFocusable(true);
		this.requestFocus();
		addKeyListener(this);
	}

	// starts a new thread for the game
	public synchronized void start() {
		thread = new Thread(this, "Game");
		running = true;
		thread.start();
	}

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
		System.exit(0);
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

	// initialize game objects, load media(pics, music, etc)
	public void init() {
		gameState=GAME_START;
		initInGame();
	}

	public void initInGame() {
		initializeGame();
	}

	public void initializeGame() {
		Score = 0;
		Lives = 3;
		level = 1;
	}

	public void loadImages() {
		try {
			Menu = ImageIO.read(new File("Ascent Menu.png"));
			Control = ImageIO.read(new File("Ascemt Controls.png"));
		} catch (IOException e) {
		}
	}

	// update game objects
	public void update() {
		if (gameState == GAME_TITLE) {
			updateTitle();
		}
		if (gameState == GAME_MENU || gameState == GAME_CONTROL) {
			updateMenu();
		}
		if (gameState == GAME_START) {
			updateGame();
		}
	}

	public void updateGame() {
		updateGameState();
	}

	public void updateTitle() {
		if (keyPressed[KEY_ENTER]) {
			gameState = GAME_MENU;
			keyPressed[KEY_ENTER] = false;
		}
	}

	public void updateMenu() {
		updateMenuGameState();
		updateMenuState();
	}

	public void updateMenuGameState() {
		if (menuState == MENU_PLAY && keyPressed[KEY_ENTER]) {
			gameState = GAME_START;
			playNum++;
		}
		if (menuState == MENU_CONTROL && keyPressed[KEY_ENTER])
			gameState = GAME_CONTROL;
		if (menuState == MENU_QUIT && keyPressed[KEY_ENTER]) {
			gameState = GAME_QUIT;
			System.exit(0);
		}
		if (keyPressed[KEY_Q])
			gameState = GAME_QUIT;
		if (gameState == GAME_CONTROL && keyPressed[KEY_BACKSPACE])
			gameState = GAME_MENU;
	}

	public void updateMenuState() {
		if (menuState == MENU_PLAY && keyPressed[KEY_DOWN]) {
			menuState = MENU_CONTROL;
			keyPressed[KEY_DOWN] = false;
		} else if (menuState == MENU_PLAY && keyPressed[KEY_UP]) {
			menuState = MENU_QUIT;
			keyPressed[KEY_UP] = false;
		} else if (menuState == MENU_CONTROL && keyPressed[KEY_DOWN]) {
			menuState = MENU_QUIT;
			keyPressed[KEY_DOWN] = false;
		} else if (menuState == MENU_CONTROL && keyPressed[KEY_UP]) {
			menuState = MENU_PLAY;
			keyPressed[KEY_UP] = false;
		} else if (menuState == MENU_QUIT && keyPressed[KEY_DOWN]) {
			menuState = MENU_PLAY;
			keyPressed[KEY_DOWN] = false;
		} else if (menuState == MENU_QUIT && keyPressed[KEY_UP]) {
			menuState = MENU_CONTROL;
			keyPressed[KEY_UP] = false;
		} else if ((menuState == MENU_QUIT || menuState == MENU_CONTROL || menuState == MENU_PLAY)
				&& keyPressed[KEY_LEFT])
			menuState = MENU_RATEONE;
		else if ((menuState == MENU_RATEONE || menuState == MENU_RATETWO || menuState == MENU_RATETHREE)
				&& keyPressed[KEY_RIGHT])
			menuState = MENU_PLAY;
		else if (menuState == MENU_RATEONE && keyPressed[KEY_DOWN]) {
			menuState = MENU_RATETWO;
			keyPressed[KEY_DOWN] = false;
		} else if (menuState == MENU_RATETWO && keyPressed[KEY_DOWN]) {
			menuState = MENU_RATETHREE;
			keyPressed[KEY_DOWN] = false;
		} else if (menuState == MENU_RATETHREE && keyPressed[KEY_UP]) {
			menuState = MENU_RATETWO;
			keyPressed[KEY_UP] = false;
		} else if (menuState == MENU_RATETWO && keyPressed[KEY_UP]) {
			menuState = MENU_RATEONE;
			keyPressed[KEY_UP] = false;
		}
	}

	public void updateGameState() {
		if (keyPressed[KEY_Q])
			gameState = GAME_QUIT;
		if (Lives <= 0)
			gameState = GAME_LOSE;
		if (keyPressed[KEY_P])
			gameState = GAME_PAUSE;
	}

	// draw things to the screen
	public void draw() {
		if (gameState == GAME_MENU) {
			drawGameMenu();
		} 
		else if (gameState == GAME_CONTROL) {
			graphics.drawImage(Control, 0, 0, 980, 735, null);
		} 
		else if (gameState == GAME_START) {
			drawIconBar();
		} 
		else if (gameState == GAME_LOSE) {
			drawLose();
			if (keyPressed[KEY_BACKSPACE])
				gameState = GAME_MENU;
			initializeGame();
			initInGame();
			keysFalse();
		} 
		else if (gameState == GAME_WIN) {
			drawWin();
			if (keyPressed[KEY_BACKSPACE])
				gameState = GAME_MENU;
			initializeGame();
			initInGame();
			keysFalse();
		} 
		else if (gameState == GAME_PAUSE) {
			header.drawPause(graphics);
			if (keyPressed[KEY_ENTER])
				gameState = GAME_START;
			if (keyPressed[KEY_BACKSPACE]) {
				gameState = GAME_MENU;
				initializeGame();
				initInGame();
				keysFalse();
			}
		}
	}

	public void drawGameMenu() {
	}

	public void drawIconBar() {
		Rectangle2D iconBackground = new Rectangle2D.Double(0, 2, 170, 731);
		Rectangle2D iconBorder = new Rectangle2D.Double(0, 0, 173, 735);
		Rectangle2D iconBackground2 = new Rectangle2D.Double(540, 2, 270, 731);
		Rectangle2D iconBorder2 = new Rectangle2D.Double(537, 0, 270, 735);
		graphics.setColor(Color.gray);
		graphics.fill(iconBorder);
		graphics.fill(iconBorder2);
		graphics.setColor(Color.darkGray);
		graphics.fill(iconBackground);
		graphics.fill(iconBackground2);
		header.drawLevel(graphics, level);
		header.drawLives(graphics, Lives);
		header.drawScore(graphics, Score);
	}

	public void drawWin() {
		header.drawWin(graphics);
	}

	public void drawLose() {
		header.drawLose(graphics);
	}

	// ///////////Keyboard Accessing Methods/////////////
	public void keysFalse() {
		keyPressed[KEY_UP] = false;
		keyPressed[KEY_DOWN] = false;
		keyPressed[KEY_LEFT] = false;
		keyPressed[KEY_RIGHT] = false;
	}

	public void keyPressed(KeyEvent e) {
		keyboard.keyPressed(keyPressed, KEY_LEFT, KEY_RIGHT, KEY_UP, KEY_DOWN,
				KEY_Q, KEY_ENTER, KEY_BACKSPACE, KEY_P, e);
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	// stops the game thread and quits
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}