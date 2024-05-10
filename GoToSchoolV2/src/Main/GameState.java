
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
import tile.TileManager;

public class GameState extends JPanel implements Runnable{

	// VARIABLE GLOBAL
	private static final int tile = 64;
	private static final int WINDOW_HEIGHT = 13 * tile; //832
	private static final int WINDOW_WIDTH = 23 * tile; //1427

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
	public State state = State.SURVIVAL;
	public boolean changeState = false;
	public UserManager user = new UserManager();
    public Campaign campaign = new Campaign(user,this,ui);
	public Survival survival = new Survival(user, this, ui);
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

	public Map<String,Integer> Map_chooseSkillSupport = new HashMap<>();
	public Map<String,Integer> Map_chooseSkillAttack = new HashMap<>();

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
		user.readFile();
		user.readAttributeClasses();
		// something here
		survival.loadMap();
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
//				System.out.println(ArrowLight.TIME_REDUCE);
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
				Flicker.TIME_COUNT_DOWN--;
				if(Flicker.TIME_COUNT_DOWN <= 0) {
					Flicker.TIME_COUNT_DOWN = -1;
				}
				Restore.TIME_COUNT_DOWN--;
				if(Restore.TIME_COUNT_DOWN <= 0) {
					Restore.TIME_COUNT_DOWN = -1;
				}
				Sprint.TIME_COUNT_DOWN--;
				if(Sprint.TIME_COUNT_DOWN <= 0) {
					Sprint.TIME_COUNT_DOWN = -1;
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
			survival.update();
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
			survival.draw(g2);
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
		if(keyHandle.isSkill1Press() && loopy.getSkillAttackHave() >= 1) {
			for(Map.Entry<String,Integer> entry : Map_chooseSkillAttack.entrySet()) {
				if(entry.getValue() == 1) {
					Entity e;
					if(entry.getKey().equals("ArrowLight")) {
						if(ArrowLight.TIME_COUNT_DOWN_ATTACK <= 0) {
							e = new ArrowLight(this);
							ArrowLight.TIME_COUNT_DOWN_ATTACK = ArrowLight.TIME_REDUCE;
							skillAttacks.add(e);
						}
					}else if(entry.getKey().equals("MultiArrowLight")) {
						if(MultiArrow.TIME_COUNT_DOWN_ATTACK <= 0) {
							e = new MultiArrow(this);
							MultiArrow.TIME_COUNT_DOWN_ATTACK = MultiArrow.TIME_REDUCE;
							skillAttacks.add(e);
						}
					}else if(entry.getKey().equals("MoonLight")) {
						if(MoonLight.TIME_COUNT_DOWN_ATTACK <= 0) {
							e = new MoonLight(this);
							MoonLight.TIME_COUNT_DOWN_ATTACK = MoonLight.TIME_REDUCE;
							skillAttacks.add(e);
						}
					}else if(entry.getKey().equals("CircleFire")) {
						if(CircleFire.TIME_COUNT_DOWN_ATTACK <= 0) {
							e = new CircleFire(this);
							CircleFire.TIME_COUNT_DOWN_ATTACK = CircleFire.TIME_REDUCE;
							skillAttacks.add(e);
						}
					}
				}
			}
		}
		if(keyHandle.isSkill2Press() && loopy.getSkillAttackHave() == 2) {
			for(Map.Entry<String,Integer> entry : Map_chooseSkillAttack.entrySet()) {
				if(entry.getValue() == 2) {
					Entity e;
					if(entry.getKey().equals("ArrowLight")) {
						if(ArrowLight.TIME_COUNT_DOWN_ATTACK <= 0) {
							e = new ArrowLight(this);
							ArrowLight.TIME_COUNT_DOWN_ATTACK = ArrowLight.TIME_REDUCE;
							skillAttacks.add(e);
						}
					}else if(entry.getKey().equals("MultiArrowLight")) {
						if(MultiArrow.TIME_COUNT_DOWN_ATTACK <= 0) {
							e = new MultiArrow(this);
							MultiArrow.TIME_COUNT_DOWN_ATTACK = MultiArrow.TIME_REDUCE;
							skillAttacks.add(e);
						}
					}else if(entry.getKey().equals("MoonLight")) {
						if(MoonLight.TIME_COUNT_DOWN_ATTACK <= 0) {
							e = new MoonLight(this);
							MoonLight.TIME_COUNT_DOWN_ATTACK = MoonLight.TIME_REDUCE;
							skillAttacks.add(e);
						}
					}else if(entry.getKey().equals("CircleFire")) {
						if(CircleFire.TIME_COUNT_DOWN_ATTACK <= 0) {
							e = new CircleFire(this);
							CircleFire.TIME_COUNT_DOWN_ATTACK = CircleFire.TIME_REDUCE;
							skillAttacks.add(e);
						}
					}
				}
			}
		}

		if(keyHandle.isSupportSkill1() && loopy.getSkillSuportHave() >= 1) {
			Entity e = skillSupports.get(indexSkillSupport1);
			if(e.getTypeSkill().typeSupport == SUPPORT_SKILL.Flicker) {
				if(Flicker.TIME_COUNT_DOWN <= 0) {
					e.setAlive(true);
					Flicker.TIME_COUNT_DOWN = Flicker.TIME_REDUCE;
				}
			}else if(e.getTypeSkill().typeSupport == SUPPORT_SKILL.Sprint) {
				if(Sprint.TIME_COUNT_DOWN <= 0) {
					e.setAlive(true);
					Sprint.TIME_COUNT_DOWN = Sprint.TIME_REDUCE;
				}
			}else if(e.getTypeSkill().typeSupport == SUPPORT_SKILL.Restore) {
				if(Restore.TIME_COUNT_DOWN <= 0) {
					e.setAlive(true);
					Restore.TIME_COUNT_DOWN = Restore.TIME_REDUCE;
				}
			}
			skillSupports.set(indexSkillSupport1,e);
		}
		if(keyHandle.isSupportSkill2() && loopy.getSkillSuportHave() == 2) {
			Entity e = skillSupports.get(indexSkillSupport2);
			if(e.getTypeSkill().typeSupport == SUPPORT_SKILL.Flicker) {
				if(Flicker.TIME_COUNT_DOWN <= 0) {
					e.setAlive(true);
					Flicker.TIME_COUNT_DOWN = Flicker.TIME_REDUCE;
				}
			}else if(e.getTypeSkill().typeSupport == SUPPORT_SKILL.Sprint) {
				if(Sprint.TIME_COUNT_DOWN <= 0) {
					e.setAlive(true);
					Sprint.TIME_COUNT_DOWN = Sprint.TIME_REDUCE;
				}
			}else if(e.getTypeSkill().typeSupport == SUPPORT_SKILL.Restore) {
				if(Restore.TIME_COUNT_DOWN <= 0) {
					e.setAlive(true);
					Restore.TIME_COUNT_DOWN = Restore.TIME_REDUCE;
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
		skillAttacks.removeIf(attack ->
				attack.getSpriteNum() == 5 &&
			!attack.getAlive()
		);
		skeletonAttacks.removeIf(skeletonAttack -> !skeletonAttack.getAlive());
		if(lazeBoss != null) {
			if (!lazeBoss.getAlive()) {
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
			user.saveFile();
		}
	}
}