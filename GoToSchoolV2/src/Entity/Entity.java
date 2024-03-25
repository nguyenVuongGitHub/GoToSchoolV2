

package Entity;

import Main.GameState;
import CollisionSystem.*;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.image.BufferedImage;

;
public abstract class Entity {
	protected GameState gs;
	// CHARACTER ATTIBUTES
	protected TYPE type;
	protected int speed;
	protected int damage;
	protected int hp;
	protected boolean alive = true;
	protected double worldX, worldY;
	protected List<PointX> vertices = new ArrayList<>();
	protected double radius;
	protected boolean collision = false;
	protected boolean collisionOn = false;
	protected double angleTarget;

	// liên quan đến hình ảnh

	protected int screenX;
	protected int screenY;

	protected BufferedImage up1, up2, up3, up4, up5, up6,
			down1, down2, down3, down4, down5, down6,
			left1, left2, left3, left4, left5, left6,
			right1, right2, right3, right4, right5, right6;

	protected String direction = "right";

	protected int spriteCounter = 0;
	protected int spriteNum = 1;

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	//
	protected Rectangle solidArea = new Rectangle(0,0,0,0);

	// các phương thức

	public int getScreenX() {return screenX;}
	public int getScreenY() {return screenY;}
	public int getWorldX() {return (int) worldX;}
	public int getWorldY() {return (int) worldY;}
	public void setScreenX(int x) {this.screenX = x;}
	public void setScreenY(int y) {this.screenY = y;}

	public void setWorldX(double amount) { worldX = amount;}
	public void setWorldY(double amount) {worldY = amount;}
	public List<PointX> getVertices() { return vertices;}
	public double getRadius() {	return radius;}
	public Entity(GameState gs) {
		this.gs = gs;
	}
	public abstract void update();
	public abstract void draw(Graphics2D g2);

	public abstract void init();

	public Rectangle getSolidArea() {
		return solidArea;
	}

	public abstract Rectangle getBounds();

	public void setCollision(boolean collision) {this.collision = collision;}
	public void setCollisionOn(boolean collisionOn) {this.collisionOn = collisionOn;}
	public boolean getAlive() {return alive;}
	public void setAlive(boolean alive) {this.alive = alive;}
	public void setHP(int hp) {this.hp = hp;}
	public int getDamage() {
		return damage;
	}

	public int getHP() {
		return hp;
	}
	protected void setPolygonVertices()
	{
		vertices.add(new PointX(worldX, worldY));
		vertices.add(new PointX(worldX + gs.getTile(), worldY));
		vertices.add(new PointX(worldX + gs.getTile(), worldY + gs.getTile()));
		vertices.add(new PointX(worldX, worldY + gs.getTile()));
	}
	protected void clearVertices()
	{
		vertices.clear();
	}
	public String getDirection() {
		return direction;
	}

}
