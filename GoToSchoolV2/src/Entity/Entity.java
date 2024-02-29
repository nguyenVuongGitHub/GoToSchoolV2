

package Entity;

import Main.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;

;
public abstract class Entity {
	protected GameState gs;
	// CHARACTER ATTIBUTES
	protected TYPE type;
	protected int speed;
	protected int baseDamage;
	protected int hp;
	protected boolean alive = true;
	protected double worldX, worldY;
	protected boolean collision;
	protected double angleTarget;

	// liên quan đến hình ảnh
	BufferedImage image1;
	protected Rectangle solidArea = new Rectangle(0,0,0,0);

	// biến đếm
	protected int countCanMove = 0;
	protected int timingMove = 20;

	// các phương thức
	public Entity(GameState gs) {
		this.gs = gs;
	}
	public abstract void update();
	public abstract void draw(Graphics2D g2);

	public abstract void init();
	public abstract Rectangle getBounds();

	public void setCollision(boolean collision) {this.collision = collision;}
	public boolean getAlive() {return alive;}
	public void setAlive(boolean alive) {this.alive = alive;}
	public void setHP(int hp) {this.hp = hp;}

	public int getDame() {
		return baseDamage;
	}

	public int getHP() {
		return hp;
	}
}
