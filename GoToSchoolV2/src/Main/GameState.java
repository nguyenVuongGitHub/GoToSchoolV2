
package Main;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import AttackSkill.*;
import CollisionSystem.SeparatingAxis;
import Entity.*;
import Quadtree.RectangleQ;
import SPSkill.*;
import Scene.Campaign;
import Scene.Loopy;
import Scene.Survival;
import User.UserManager;
import Weapon.LazerBoss;
import Weapon.NormalAttack;
import environment.EnviromentManager;
import src.Main.Sound;
import tile.TileManager;

public class GameState extends JPanel implements Runnable{

	// VARIABLE GLOBAL
	private static final int tile = 64;
	private static final int WINDOW_HEIGHT = 13 * tile; //832 px
	private static final int WINDOW_WIDTH = 23 * tile; //1472 px

	private static final int maxScreenCol = WINDOW_WIDTH/tile;

	private static final int maxScreenRow = WINDOW_HEIGHT/tile;
	//WORLD SETTINGS
	private final int maxWorldCol = 64;
	private final int maxWorldRow = 64;
	public final RectangleQ boundsQuadTree = new RectangleQ(0,0,maxWorldCol*tile,maxWorldRow*tile);

	// VARIABLE SYSTEM
	public final static int FPS = 30;
	Sound sound = new Sound();
	Thread gameThread;
	public KeyHandle keyHandle = new KeyHandle(this);
	public MouseHandle mouseHandle = new MouseHandle();
	public CollisionChecker CC = new CollisionChecker(this);
	public UI ui = new UI(this);
	public State state = State.LOOPY;
	public boolean changeState = false;
	public UserManager user = new UserManager();
    public Campaign campaign = new Campaign(user,this,ui);
	public Survival survival = new Survival(user, this, ui);
	public SeparatingAxis SAT = new SeparatingAxis(this);
	public Loopy loopy = new Loopy(this);
	public TileManager tileM = new TileManager(this);
	public ChangeScene changeScene = new ChangeScene(this);
    public EnviromentManager enviromentManager = new EnviromentManager(this);
    public AttackController aController = new AttackController(this);
	// ENTITY
	public 	Entity player = new Player(this);
	public List<Entity> monsters = new ArrayList<>();
	// WEAPON
	public List<Entity> skillAttacks = new ArrayList<>();
	public List<Entity> skillSupports = new ArrayList<>();
	public int indexSkillSupport1 = 0;
	public int indexSkillSupport2 = 1;

	public Map<String,Integer> Map_chooseSkillSupport = new HashMap<>();
	public Map<String,Integer> Map_chooseSkillAttack = new HashMap<>();

	public List<Entity> skeletonAttacks = new ArrayList<>();
	public List<Entity> bossAttacks = new ArrayList<>();
//	public Entity lazeBoss = null;
	public List<Entity> coins = new ArrayList<>();
	public Color baseColor = new Color(77,138,179,255);
    // OTHER VARIABLE
	public GameState() {
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setBackground(baseColor);
		this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.emptySet());
		this.addKeyListener(keyHandle);
		this.addMouseListener(mouseHandle);
		this.addMouseMotionListener(mouseHandle);
		this.addMouseWheelListener(mouseHandle);
		this.setFocusable(true);
	}
	public void initGame() {
		user.readFileUser();
		user.readAttributeClassesSkill();
		user.readAttributeClassesMonster();
        enviromentManager.init();
		// something here
		loopy.loadMap();
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
//				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
		System.exit(0);
	}
	
	public int exitGame() {
		gameThread = null;
		return 1;
//			System.exit(0);
	}
	public void update() {
		if(state == State.CAMPAIGN) {
			if(campaign.isGameOver()) {
				campaign.setShowDialog(true);
			}
			campaign.update();

		}else if(state == State.SURVIVAL){
			survival.update();
		}else if(state == State.LOOPY) {
			// trường hợp từ CAMPAIGN hoặc SURVIVAL trở về LOOPY thì cần load map lại
			if(changeState) {
				if(changeScene.getNumberDraw() == 2) {
					loopy.loadMap();
					changeState = false;
				}
			}
			loopy.update();
		}
		changeScene.update();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		// Khử răng cưa
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if(state == State.LOOPY) {
			loopy.draw(g2);
		}else if(state == State.CAMPAIGN) {
			campaign.draw(g2);
		}else if(state == State.SURVIVAL) {
			survival.draw(g2);
		}
		ui.draw(g2);
		changeScene.draw(g2);
		g2.dispose();
		
	}
	public void drawBattle(Graphics2D g2) {
		// MAP
		tileM.draw(g2);


		for(Entity monster : monsters) {
			if(monster != null) {
				monster.draw(g2);
			}
		}
		for(Entity skill : skillAttacks) {
			if(skill != null) {
				skill.draw(g2);
			}
		}
		for(Entity bossAttack : bossAttacks) {
			if(bossAttack != null) {
				bossAttack.draw(g2);
			}
		}
		for(Entity skeletonAttack : skeletonAttacks) {
			if(skeletonAttack != null) {
				skeletonAttack.draw(g2);
			}
		}
		// ANOTHER
		coins.forEach(coin -> {
			if(coin != null) {
				coin.draw(g2);
			}
		});
		// ENTITY
		if(player != null) {
			player.draw(g2);
		}
	}

	public void updateBattle() {
		if (monsters.isEmpty()) {
			// nếu trong màn chơi mà tiêu diệt hết quái vật thì
			campaign.setNextLever(true); // set có thể next lever
			campaign.setLoadMapDone(false); // reset load map
			campaign.setShowDialog(true); // hiển thị bảng chọn map
		}

        if(aController != null) {
            aController.update();
        }

		//
		// MONSTER
//		for(Entity monster : monsters) {
//			if(monster != null) {
//				monster.update();
//			}
//		}
		for(int i = 0 ;i < monsters.size(); i ++) {
			Entity monster = monsters.get(i);

			if(monster != null) {
				monster.update();

				if(!monster.getAlive()) {
					monsters.remove(i);
					i--;
				}
			}
		}
		// PLAYER
		if(player != null) {
			player.update();
		}
		// WEAPON
		for(Entity skill : skillAttacks) {
			if(skill != null) {
				skill.update();
			}
		}
		for(Entity e : skillSupports) {
			if(e != null) {
				e.update();
			}
		}

		coins.forEach(coin -> {
			if(coin != null) {
				coin.update();
			}
		});

		for(Entity lazeBoss : bossAttacks) {
			if(lazeBoss != null) {
				lazeBoss.update();
			}
		}

		for(Entity skeletonAttack : skeletonAttacks) {
			if(skeletonAttack != null) {
				skeletonAttack.update();
			}
		}
		// COLLISION
		CC.checkAllEntity(player,monsters,skillAttacks,coins,skeletonAttacks, bossAttacks);
		// AFTER COLLISION
		// Loại bỏ skill đã chết
		skillAttacks.removeIf(attack ->
				attack.getSpriteNum() == 5 &&
			!attack.getAlive()
		);
		skillSupports.removeIf(skill -> !skill.getAlive());
		skeletonAttacks.removeIf(skeletonAttack -> !skeletonAttack.getAlive());
		bossAttacks.removeIf(lazeBoss -> !lazeBoss.getAlive());
		// Loại bỏ quái vật đã chết
//		monsters.removeIf(monster -> !monster.getAlive());
		// remove if coins not alive
		coins.removeIf(coin -> !coin.getAlive());
	}

	public int getTile() {
		return tile;
	}
	public int getWindowHeight() {return WINDOW_HEIGHT;}
	public int getWindowWidth() {return WINDOW_WIDTH;}
	public int getMaxWorldCol() {return maxWorldCol;}
	public int getMaxWorldRow() {return maxWorldRow;}
	public boolean inSightWorld(double x, double y) {
		int width = maxWorldRow*tile;
		int height = maxWorldCol*tile;
		return (x >= 0 || x <= width) && (y >= 0 || y <= height);
	}
	public void setGameExit() {
		gameThread = null;
	}

	public void saveGame() {
			user.saveFile();
//			loopy.setShowDialogExit(false);
	}
	//SOUND
	public void playMusic(int i) {
		sound.setFile(i);
		sound.play();
		sound.loop();
	}
	public void stopMusic() {
		sound.stop();
		sound.stopAll();
	}
	public void playSE(int i) {
		sound.setFile(i);
		sound.play();
	}
	public void playSE_one(int i) {
		sound.setFile(i);
		sound.playSE_one();
	}
	public void closeMusic() {
		sound.close();
	}
}