
package Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import Entity.*;
import Quadtree.*;
import Quadtree.RectangleQ;

public class GameState extends JPanel implements Runnable{

	// VARIABLE GLOBAL
	private static final int tile = 32;
	private static final int WINDOW_HEIGHT = 1080;
	private static final int WINDOW_WIDTH = 1280;

	public QuadTree quadTree = new QuadTree(10,new RectangleQ(0,0,WINDOW_WIDTH,WINDOW_HEIGHT), this);
	
	// VARIABLE SYSTEM
	private final static int FPS = 60;
	Thread gameThread;
	KeyHandle keyHandle = new KeyHandle();
	MouseHandle mouseHandle = new MouseHandle();
	CollisionChecker CC = new CollisionChecker(this);

	// ENTITY
//	Entity player = new Player(this);
	int n = 1500;
	public Entity players[] = new Player[n];
	public PointQ[] found = null;
	// OTHER VARIABLE
	

	public GameState() {
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setBackground(Color.BLACK);
		this.addKeyListener(keyHandle);
		this.addMouseListener(mouseHandle);
		this.addMouseWheelListener(mouseHandle);
		this.setFocusable(true);
	}
	public void initGame() {
//		player.init();
		for(int i = 0; i < n; i ++) {
			players[i] = new Player(this);
		}
		for(int i = 0; i < n; i ++) {
			players[i].init();
		}
	}
	public void runGame() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	@Override
	public void run() {
		
		// 1000000000 (nano time) is a one second
		double drawTime = (double) 1000000000 / FPS; // 1/60 second ~ 0.0166666666666667 seconds

		long startTime = System.nanoTime();
		double deltaTime = 0;
		long currentTime = 0;
		
		long timer = 0;
		int drawCount = 0;

		while(gameThread != null) {
			
			// setup FPS 
			currentTime = System.nanoTime();
			deltaTime += (currentTime - startTime) / drawTime;
			timer += (currentTime - startTime);
			startTime = currentTime;
			
			
			if(deltaTime >= 1) {
				update();
				repaint();	
				
				deltaTime--;
				drawCount++;
			}
			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
			
		}
		exitGame();
	}
	
	public void exitGame() {
		if(gameThread == null) {
			System.exit(0);
		}
	}
	public void update() {
		if(keyHandle.isEscPress()) {
			gameThread = null;
		}
		for(int i = 0; i < n; i ++) {
			players[i].update();
		}
		CC.checkEntity(players);

	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		for(int i = 0; i < n; i ++) {
			players[i].draw(g2);
		}

		g2.dispose();
		
	}
 	public int getTile() {
		return tile;
	}
	public int getWindowHeight() {return WINDOW_HEIGHT;}
	public int getWindowWidth() {return WINDOW_WIDTH;}
}