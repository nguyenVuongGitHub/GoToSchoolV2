

package Entity;

import Main.GameState;

public class Entity {
	GameState gs;
	
	int speed;
	int damage;
	int hp;
	boolean alive = true;
	
	public Entity(GameState gs) {
		this.gs = gs;
	}
	public void update() {
		
	}
	public void draw() {
		gs.getTile();
	}
}
