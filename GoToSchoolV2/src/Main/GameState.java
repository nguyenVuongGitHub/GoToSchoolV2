
package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GameState extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;
	
	// VARIABLE GLOBAL
	private static final int tile = 32;
	private static final int WINDOW_HEIGHT = 720;
	private static final int WINDOW_WIDTH = 1080;
	
	
	// VARIABLE SYSTEM
	private final static int FPS = 60;
	Thread gameThread;
	KeyHandle keyHandle = new KeyHandle();
	MouseHandle mouseHandle = new MouseHandle();
	// ENTITY
	
	// OTHER VARIABLE
	

	public GameState() {
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setBackground(Color.BLACK);
		this.addKeyListener(keyHandle);
		this.addMouseListener(mouseHandle);
		this.addMouseWheelListener(mouseHandle);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
	}
	public void initGame() {
		
	}
	public void runGame() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	@Override
	public void run() {
		
		// 1000000000 (nano time) is a one second
		double drawTime = 1000000000/ FPS; // 1/60 second ~ 0.0166666666666667 seconds

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
	
	private void exitGame() {
		// TODO Auto-generated method stub
		if(gameThread == null) {
			System.exit(0);
		}
	}
	public void update() {
		if(keyHandle.isEscPress()) {
			gameThread = null;
		}
		if(mouseHandle.mouseLeftPress) {
			System.out.println("left");
		}
		if(mouseHandle.mouseRightPress) {
			System.out.println("right");
		}
		
		if(mouseHandle.mouseWhellUp) {
			System.out.println("up");
		}
		if(mouseHandle.mouseWhellDown) {
			System.out.println("down");
		}
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		g2.dispose();
		
	}
 	public static int getTile() {
		return tile;
	}
}
