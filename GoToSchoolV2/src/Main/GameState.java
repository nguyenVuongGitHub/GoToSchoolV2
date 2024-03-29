
package Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import Entity.*;
import Quadtree.*;
import Quadtree.RectangleQ;
import Scene.Campaign;
import User.UserManager;
import Weapon.NormalAttack;
import tile.TileManager;

public class GameState extends JPanel implements Runnable{

	// VARIABLE GLOBAL
	private static final int tile = 64;
	private static final int WINDOW_HEIGHT = 1080;
	private static final int WINDOW_WIDTH = 1280;

	private static final int maxScreenCol = WINDOW_WIDTH/tile;

	private static final int maxScreenRow = WINDOW_HEIGHT/tile;

	public QuadTree quadTree = new QuadTree(4,new RectangleQ(0,0,WINDOW_WIDTH*tile,WINDOW_HEIGHT*tile), this);
	public List<PointQ> found = new ArrayList<>();

	// VARIABLE SYSTEM
	private final static int FPS = 60;
	Thread gameThread;
	public KeyHandle keyHandle = new KeyHandle(this);
	public MouseHandle mouseHandle = new MouseHandle();
	public CollisionChecker CC = new CollisionChecker(this);
	public UI ui = new UI(this);
	public State state = State.CAMPAIGN;
	public UserManager user = new UserManager();
    public Campaign campaign = new Campaign(user,this,ui);
	public TileManager tileM = new TileManager(this);

	// ENTITY
	public 	Entity player = new Player(this);
	public List<Entity> monsters = new ArrayList<Entity>();
	// WEAPON
	public List<Entity> skillAttacks = new ArrayList<Entity>();
	int numberMonster = 5;

//WORLD SETTINGS
	private final int maxWorldCol = 32;
	private final int maxWorldRow = 32;
	private final int worldWidth = tile * maxWorldCol;
	private final int worldHeight = tile * maxWorldRow;


//	int n = 1500;
//	public Entity players[] = new Player[n];

	// OTHER VARIABLE

	public GameState() {
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setBackground(Color.DARK_GRAY);
		this.addKeyListener(keyHandle);
		this.addMouseListener(mouseHandle);
		this.addMouseMotionListener(mouseHandle);
		this.addMouseWheelListener(mouseHandle);
		this.setFocusable(true);
	}
	public void initGame() {
		user.readFile("/user/infUser.txt");
		// something here

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
			// mỗi giây
			if(timer >= 1000000000) {
				NormalAttack.TIME_COUNT_DOWN_ATTACK--;
//				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
			
		}
		exitGame();
	}
	
	public void exitGame() {
		if(gameThread == null) {
			user.saveFile("/user/infUser.txt");
			System.exit(0);
		}
	}
	public void update() {
		if(state == State.CAMPAIGN) {
			campaign.update();

		}else if(state == State.SURVIVAL){

		}else if(state == State.LOOPY) {
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		if(state == State.LOOPY) {

		}else if(state == State.CAMPAIGN) {
			campaign.draw(g2);
		}else if(state == State.SURVIVAL) {

		}
		g2.dispose();
		
	}

	public void updateBattle() {
		// ATTACK
		if(keyHandle.isSpacePress() && NormalAttack.TIME_COUNT_DOWN_ATTACK <= 0) {
			Entity normalAttack = new NormalAttack(this);
			skillAttacks.add(normalAttack);
			NormalAttack.TIME_COUNT_DOWN_ATTACK = NormalAttack.TIME_ATTACK;
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
		for(Entity normalAttack : skillAttacks) {
			if(normalAttack != null) {
				normalAttack.update();
			}
		}

		// COLLISION
		CC.checkSkillWithMonster(skillAttacks,monsters);
		CC.checkPlayerAndMonsters(player,monsters);



		// AFTER COLLISION
		// Loại bỏ skill đã chết
		skillAttacks.removeIf( normalAttack -> !normalAttack.getAlive());

		// Loại bỏ quái vật đã chết
		monsters.removeIf(monster -> monster.getHP()<=0);
		monsters.removeIf(monster -> !monster.getAlive());
	}

	public int getTile() {
		return tile;
	}
	public int getWindowHeight() {return WINDOW_HEIGHT;}
	public int getWindowWidth() {return WINDOW_WIDTH;}
	public int getMaxScreenCol() {return maxScreenCol;}
	public int getMaxScreenRow() {return maxScreenRow;}
	public int getMaxWorldCol() {return maxWorldCol;}
	public int getMaxWorldRow() {return maxWorldRow;}
	public int getWorldWidth() {return worldWidth;}
	public int getWorldHeight() {return worldHeight;}
}