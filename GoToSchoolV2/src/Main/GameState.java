
package Main;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import AttackSkill.ArrowLight;
import AttackSkill.CircleFire;
import AttackSkill.MoonLight;
import AttackSkill.MultiArrow;
import CollisionSystem.SeparatingAxis;
import Entity.*;
import Quadtree.RectangleQ;
import SPSkill.Healing;
import SPSkill.SpeedFaster;
import Scene.Campaign;
import Scene.Loopy;
import User.UserManager;
import SPSkill.Flash;
import Weapon.LazerBoss;
import Weapon.NormalAttack;
import SPSkill.SUPPORT_SKILL;
import tile.TileManager;


public class GameState extends JPanel implements Runnable{

	// VARIABLE GLOBAL
	private static final int tile = 64;
	private static final int WINDOW_HEIGHT = 15 * tile;
	private static final int WINDOW_WIDTH = 25 * tile;

	private static final int maxScreenCol = WINDOW_WIDTH/tile;

	private static final int maxScreenRow = WINDOW_HEIGHT/tile;
	//WORLD SETTINGS
	private final int maxWorldCol = 64;
	private final int maxWorldRow = 64;
	public final RectangleQ boundsQuadTree = new RectangleQ(0,0,maxWorldCol*tile,maxWorldRow*tile);

	// VARIABLE SYSTEM
	private final static int FPS = 30;
	Thread gameThread;
	public KeyHandle keyHandle = new KeyHandle(this);
	public MouseHandle mouseHandle = new MouseHandle();
	public CollisionChecker CC = new CollisionChecker(this);
	public UI ui = new UI(this);
	public State state = State.LOOPY;
	public boolean changeState = false;
	public UserManager user = new UserManager();
    public Campaign campaign = new Campaign(user,this,ui);
	public SeparatingAxis SAT = new SeparatingAxis(this);
	public Loopy loopy = new Loopy(this);
	public TileManager tileM = new TileManager(this);

	// ENTITY
	public 	Entity player = new Player(this);
	public List<Entity> monsters = new ArrayList<>();
	// WEAPON
	public List<Entity> skillAttacks = new ArrayList<>();
	public List<Entity> skillSupports = new ArrayList<>();
	public int indexSkillSupport1 = 0;
	public int indexSkillSupport2 = 1;
	public Map<String,Integer> Map_chooseSkill = new HashMap<>();
	public List<Entity> skeletonAttacks = new ArrayList<>();
	public Entity lazeBoss = new LazerBoss(this);
	public List<Entity> coins = new ArrayList<>();

    // OTHER VARIABLE

	public GameState() {
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setBackground(new Color(77,138,179,255));
		this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.emptySet());
		this.addKeyListener(keyHandle);
		this.addMouseListener(mouseHandle);
		this.addMouseMotionListener(mouseHandle);
		this.addMouseWheelListener(mouseHandle);
		this.setFocusable(true);
	}
	public void initGame() {
		user.readFile("/user/infUser.txt");
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
				NormalAttack.TIME_COUNT_DOWN_ATTACK--;
				if(NormalAttack.TIME_COUNT_DOWN_ATTACK <= 0) {
					NormalAttack.TIME_COUNT_DOWN_ATTACK = -1;
				}
				MoonLight.TIME_COUNT_DOWN_ATTACK--;
				if(MoonLight.TIME_COUNT_DOWN_ATTACK <= 0) {
					MoonLight.TIME_COUNT_DOWN_ATTACK = -1;
				}
				ArrowLight.TIME_COUNT_DOWN_ATTACK--;
				if(ArrowLight.TIME_COUNT_DOWN_ATTACK <= 0) {
					ArrowLight.TIME_COUNT_DOWN_ATTACK = -1;
				}
				MultiArrow.TIME_COUNT_DOWN_ATTACK--;
				if(MultiArrow.TIME_COUNT_DOWN_ATTACK <= 0) {
					MultiArrow.TIME_COUNT_DOWN_ATTACK = -1;
				}
				CircleFire.TIME_COUNT_DOWN_ATTACK--;
				if(CircleFire.TIME_COUNT_DOWN_ATTACK <= 0) {
					CircleFire.TIME_COUNT_DOWN_ATTACK = -1;
				}
				Flash.TIME_COUNT_DOWN--;
				if(Flash.TIME_COUNT_DOWN <= 0) {
					Flash.TIME_COUNT_DOWN = -1;
				}
				Healing.TIME_COUNT_DOWN--;
				if(Healing.TIME_COUNT_DOWN <= 0) {
					Healing.TIME_COUNT_DOWN = -1;
				}
				SpeedFaster.TIME_COUNT_DOWN--;
				if(SpeedFaster.TIME_COUNT_DOWN <= 0) {
					SpeedFaster.TIME_COUNT_DOWN = -1;
				}
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

		}else if(state == State.LOOPY) {
			// trường hợp từ CAMPAIGN hoặc SURVIVAL trở về LOOPY thì cần load map lại
			if(changeState) {
				loopy.loadMap();
				changeState = false;
			}
			loopy.update();
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		if(state == State.LOOPY) {
			loopy.draw(g2);
		}else if(state == State.CAMPAIGN) {
			campaign.draw(g2);
		}else if(state == State.SURVIVAL) {

		}
		ui.draw(g2);

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
		if(lazeBoss != null) {
			lazeBoss.draw(g2);
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

		// ATTACK
		if(keyHandle.isSpacePress() && NormalAttack.TIME_COUNT_DOWN_ATTACK <= 0) {
			Entity normalAttack = new NormalAttack(this);
			skillAttacks.add(normalAttack);
			NormalAttack.TIME_COUNT_DOWN_ATTACK = NormalAttack.TIME_REDUCE;
		}
//		if (keyHandle.isSkill1Press() && MoonLight.TIME_COUNT_DOWN_ATTACK <= 0) {
//			Entity moonLight = new MoonLight(this);
//			skillAttacks.add(moonLight);
//			MoonLight.TIME_COUNT_DOWN_ATTACK = MoonLight.TIME_REDUCE;
//		}
		if (keyHandle.isSkill1Press() && CircleFire.TIME_COUNT_DOWN_ATTACK <= 0) {
			Entity moonLight = new CircleFire(this);
			skillAttacks.add(moonLight);
			CircleFire.TIME_COUNT_DOWN_ATTACK = CircleFire.TIME_REDUCE;
		}
		if (keyHandle.isSkill2Press() && ArrowLight.TIME_COUNT_DOWN_ATTACK <= 0) {
			Entity arrowLight = new ArrowLight(this);
			skillAttacks.add(arrowLight);
			ArrowLight.TIME_COUNT_DOWN_ATTACK = ArrowLight.TIME_REDUCE;
		}
		if (keyHandle.isSkill3Press() && MultiArrow.TIME_COUNT_DOWN_ATTACK <= 0) {
			Entity multiArrow1 = new MultiArrow(this);
			Entity multiArrow2 = new MultiArrow(this);
			Entity multiArrow3 = new MultiArrow(this);
			multiArrow2.setAngleTarget(Math.toRadians((int)Math.toDegrees(multiArrow1.getAngleTarget()) - 10));
			multiArrow3.setAngleTarget(Math.toRadians((int)Math.toDegrees(multiArrow1.getAngleTarget()) + 10));
			skillAttacks.add(multiArrow1);
			skillAttacks.add(multiArrow2);
			skillAttacks.add(multiArrow3);
			MultiArrow.TIME_COUNT_DOWN_ATTACK = MultiArrow.TIME_REDUCE;
		}
		if(keyHandle.isSupportSkill1() && loopy.getSkillHave() >= 1) {
			Entity e = skillSupports.get(indexSkillSupport1);
			if(e.getTypeSkill().typeSupport == SUPPORT_SKILL.Flash) {
				if(Flash.TIME_COUNT_DOWN <= 0) {
					e.setAlive(true);
					Flash.TIME_COUNT_DOWN = Flash.TIME_REDUCE;
				}
			}else if(e.getTypeSkill().typeSupport == SUPPORT_SKILL.SpeedFaster) {
				if(SpeedFaster.TIME_COUNT_DOWN <= 0) {
					e.setAlive(true);
					SpeedFaster.TIME_COUNT_DOWN = SpeedFaster.TIME_REDUCE;
				}
			}else if(e.getTypeSkill().typeSupport == SUPPORT_SKILL.Healing) {
				if(Healing.TIME_COUNT_DOWN <= 0) {
					e.setAlive(true);
					Healing.TIME_COUNT_DOWN = Healing.TIME_REDUCE;
				}
			}
			skillSupports.set(indexSkillSupport1,e);
		}
		if(keyHandle.isSupportSkill2() && loopy.getSkillHave() == 2) {
			Entity e = skillSupports.get(indexSkillSupport2);
			if(e.getTypeSkill().typeSupport == SUPPORT_SKILL.Flash) {
				if(Flash.TIME_COUNT_DOWN <= 0) {
					e.setAlive(true);
					Flash.TIME_COUNT_DOWN = Flash.TIME_REDUCE;
				}
			}else if(e.getTypeSkill().typeSupport == SUPPORT_SKILL.SpeedFaster) {
				if(SpeedFaster.TIME_COUNT_DOWN <= 0) {
					e.setAlive(true);
					SpeedFaster.TIME_COUNT_DOWN = SpeedFaster.TIME_REDUCE;
				}
			}else if(e.getTypeSkill().typeSupport == SUPPORT_SKILL.Healing) {
				if(Healing.TIME_COUNT_DOWN <= 0) {
					e.setAlive(true);
					Healing.TIME_COUNT_DOWN = Healing.TIME_REDUCE;
				}
			}
			skillSupports.set(indexSkillSupport2,e);
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

		if(lazeBoss != null) {
			lazeBoss.update();
		}

		for(Entity skeletonAttack : skeletonAttacks) {
			if(skeletonAttack != null) {
				skeletonAttack.update();
			}
		}
		// COLLISION
		CC.checkAllEntity(player,monsters,skillAttacks,coins,skeletonAttacks, lazeBoss);
		// AFTER COLLISION
		// Loại bỏ skill đã chết
		skillAttacks.removeIf( normalAttack -> !normalAttack.getAlive());
		skeletonAttacks.removeIf(skeletonAttack -> !skeletonAttack.getAlive());
		if(lazeBoss != null) {
			if(!lazeBoss.getAlive()) {
				lazeBoss = null;
			}
		}

		// Loại bỏ quái vật đã chết
		monsters.removeIf(monster -> !monster.getAlive());
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
		if(gameThread != null) {
			user.saveFile("GotoSchoolV2/res/user/infUser.txt");
		}
	}
}