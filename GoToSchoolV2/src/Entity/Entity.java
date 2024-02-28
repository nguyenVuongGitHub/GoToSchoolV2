

package Entity;

import Main.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
	GameState gs;

	// CHARACTER ATTIBUTES
	int type;
	int speed;
	int damage;
	int hp;
	boolean alive = true;
	int worldX, worldY;
	boolean collision;


	// liên quan đến hình ảnh
	BufferedImage image1;

	// các phương thức
	public Entity(GameState gs) {
		this.gs = gs;
	}
	public abstract void update();
	public abstract void draw(Graphics2D g2);

	public abstract void init();

}
