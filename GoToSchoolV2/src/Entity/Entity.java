

package Entity;

import Main.GameState;
import CollisionSystem.*;
import Weapon.TypeSkill;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.image.BufferedImage;

;
public abstract class Entity {
	protected GameState gs;
	// CHARACTER ATTIBUTES
	protected TYPE type;
	protected TypeSkill typeSkill = new TypeSkill();
	protected int speed;
	protected int damage;
	protected int hp;
	protected boolean alive = true;
	protected double worldX, worldY;
	protected List<PointX> vertices = new ArrayList<>();
	protected double radius = 80;
	protected boolean collision = false;
	protected boolean collisionOn = false;

	public double getAngleTarget() {
		return angleTarget;
	}

	public void setAngleTarget(double angleTarget) {
		this.angleTarget = angleTarget;
	}

	protected double angleTarget;
	protected boolean canTouch = true;
	protected int timeCanTouch = 10;

// liên quan đến hình ảnh

	protected int screenX;
	protected int screenY;
	int scale = 1;
	protected BufferedImage up1 = null, up2 = null, up3 = null, up4 = null, up5 = null, up6 = null,
			down1 = null, down2 = null, down3 = null, down4 = null, down5 = null, down6 = null,
			left1 = null, left2 = null, left3 = null, left4 = null, left5 = null, left6 = null,
			right1 = null, right2 = null, right3 = null, right4 = null, right5 = null, right6 = null;
	protected  BufferedImage currentImage = null;

	protected String direction = "right";

	protected int spriteCounter = 0;
	protected int spriteNum = 1;

	public int getSpriteNum() {
		return spriteNum;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	//
	protected Rectangle solidArea = new Rectangle(0,0,64,64);

	// các phương thức
	public void generateCoin(){}
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
	public abstract void getImage();

	public Rectangle getSolidArea() {
		return solidArea;
	}

	public Rectangle getBounds() {
		return new Rectangle((int)worldX + solidArea.x,(int) worldY + solidArea.y, solidArea.width, solidArea.height);
	}

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
		vertices.add(new PointX(worldX + getSolidArea().x, worldY + getSolidArea().y));
		vertices.add(new PointX(worldX + getSolidArea().width + getSolidArea().x, worldY + getSolidArea().y));
		vertices.add(new PointX(worldX + getSolidArea().width + getSolidArea().x, worldY + getSolidArea().height + getSolidArea().y));
		vertices.add(new PointX(worldX + getSolidArea().x, worldY + getSolidArea().height + getSolidArea().y));
	}
	protected void clearVertices()
	{
		vertices.clear();
	}
	public String getDirection() {
		return direction;
	}

	public boolean getCollision() {
		return collision;
	}
	public int getTimeCanTouch() {
		return timeCanTouch;
	}

	public void setTimeCanTouch(int timeCanTouch) {
		this.timeCanTouch = timeCanTouch;
	}
	public boolean isCanTouch() {
		return canTouch;
	}

	public void setCanTouch(boolean canTouch) {
		this.canTouch = canTouch;
	}

	public TYPE getType() {
		return type;
	}
	public void drawVertices(Graphics2D g2) {
		g2.drawLine((int) (screenX + vertices.get(0).getX() - worldX),
				(int) (screenY + vertices.get(0).getY() - worldY),
				(int) (screenX + vertices.get(1).getX() - worldX),
				(int) (screenY + vertices.get(1).getY() - worldY));
		g2.drawLine((int) (screenX + vertices.get(1).getX() - worldX),
				(int) (screenY + vertices.get(1).getY() - worldY),
				(int) (screenX + vertices.get(2).getX() - worldX),
				(int) (screenY + vertices.get(2).getY() - worldY));
		g2.drawLine((int) (screenX + vertices.get(2).getX() - worldX),
				(int) (screenY + vertices.get(2).getY() - worldY),
				(int) (screenX + vertices.get(3).getX() - worldX),
				(int) (screenY + vertices.get(3).getY() - worldY));
		g2.drawLine((int) (screenX + vertices.get(3).getX() - worldX),
				(int) (screenY + vertices.get(3).getY() - worldY),
				(int) (screenX + vertices.get(0).getX() - worldX),
				(int) (screenY + vertices.get(0).getY() - worldY));
	}
	public void drawCircle(Graphics2D g2) {
		g2.drawOval(screenX,screenY, (int) radius, (int) radius);
	}

	public String getBeforeDirection() {
        return null;
    }

	public TypeSkill getTypeSkill() {
		return typeSkill;
	}

	public void setTypeSkill(TypeSkill typeSkill) {
		this.typeSkill = typeSkill;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
}
