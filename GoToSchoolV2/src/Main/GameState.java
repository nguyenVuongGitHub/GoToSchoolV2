
package Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import Entity.*;
import Quadtree.*;
import Quadtree.RectangleQ;
import Weapon.Bullet;

public class GameState extends JPanel implements Runnable{

	// VARIABLE GLOBAL
	private static final int tile = 32;
	private static final int WINDOW_HEIGHT = 1080;
	private static final int WINDOW_WIDTH = 1280;
	public QuadTree quadTree = new QuadTree(10,new RectangleQ(0,0,WINDOW_WIDTH,WINDOW_HEIGHT), this);
	public PointQ[] found = null;

	// VARIABLE SYSTEM
	private final static int FPS = 60;
	Thread gameThread;
	public KeyHandle keyHandle = new KeyHandle();
	public MouseHandle mouseHandle = new MouseHandle();
	public CollisionChecker CC = new CollisionChecker(this);

	// ENTITY
	public Entity player = new Player(this);
	public List<Entity> bullets = new ArrayList<>();
	public List<Entity> monsters = new ArrayList<Entity>();
	int numberMonster = 30;

//	int n = 1500;
//	public Entity players[] = new Player[n];

	// OTHER VARIABLE
	int timmingShoot = 0;

	public GameState() {
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setBackground(Color.BLACK);
		this.addKeyListener(keyHandle);
		this.addMouseListener(mouseHandle);
		this.addMouseMotionListener(mouseHandle);
		this.addMouseWheelListener(mouseHandle);
		this.setFocusable(true);
	}
	public void initGame() {
		// something here
		for(int i = 0; i < numberMonster; i++) {
			Entity monster = new Monster(this);
			monsters.add(monster);
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

		// RE - GAME
		if(monsters.size() <= 0) {
			for(int i = 0; i < numberMonster; i++) {
				Entity monster = new Monster(this);
				monsters.add(monster);
			}
		}
		// SHOOTING
		timmingShoot++;
		if(timmingShoot >= 5 && mouseHandle.isMouseLeftPress()) {
			Entity bullet = new Bullet(this);
			bullets.add(bullet);
			timmingShoot = 0;
		}

		// MONSTER
		for(Entity monster : monsters) {
			if(monster != null) {
				monster.update();
			}
		}
		// PLAYER
		if(player != null) {
			player.update();
		}

		// WEAPON
		for(Entity bullet : bullets) {
			if(bullet != null) {
				bullet.update();
				if(checkOutOfScreen(bullet.getBounds().x,bullet.getBounds().y)){
					bullet.setAlive(false);
				}
			}
		}

		// COLLISION
		CC.checkBulletWithMonster(bullets,monsters);

		// AFTER COLLISION
        // Loại bỏ viên đạn đã chết
        bullets.removeIf(bullet -> !bullet.getAlive());

        // Loại bỏ quái vật đã chết
		monsters.removeIf(monsters -> monsters.getHP()<=0);
        monsters.removeIf(monster -> !monster.getAlive());

	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		// MAP

		// ENTITY
		if(player != null) {
			player.draw(g2);
		}

		for(Entity monster : monsters) {
			if(monster != null) {
				monster.draw(g2);
			}
		}
		for(Entity bullet : bullets) {
			if(bullet != null) {
				bullet.draw(g2);
			}
		}
		// ANOTHER

		g2.dispose();
		
	}
 	public int getTile() {
		return tile;
	}
	public int getWindowHeight() {return WINDOW_HEIGHT;}
	public int getWindowWidth() {return WINDOW_WIDTH;}
	public boolean checkOutOfScreen(int x, int y) {
		if(x < -5 || x > WINDOW_WIDTH) {
			return true;
		}
		if(y < -5 || y > WINDOW_HEIGHT) {
			return true;
		}
		return false;
	}
}