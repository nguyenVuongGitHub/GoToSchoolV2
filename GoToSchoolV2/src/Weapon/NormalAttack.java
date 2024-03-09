package Weapon;

import Entity.TYPE;
import Main.GameState;

import java.awt.*;

public class NormalAttack extends BaseSkill{
    public static int TIME_COUNT_DOWN_ATTACK = 1;
    public static final int TIME_ATTACK = 1;
    public NormalAttack(GameState gs) {
        super(gs);
        init();
    }

    @Override
    public void init() {
        type = TYPE.WEAPON;
        distance = 100;
        damage = 5;
        speed = 10;
        worldX = gs.player.getWorldX() + (double) gs.getTile() /2;
        worldY = gs.player.getWorldY() + (double) gs.getTile() /2;
        solidArea = new Rectangle((int) worldX,(int) worldY,gs.getTile() /2,gs.getTile() /2);
        direction = gs.player.getDirection();
    }

    @Override
    public Rectangle getBounds() {
        return solidArea;
    }

    @Override
    public void update() {
        if(distance <= 0) {
            alive = false;
            distance = 100;
        }
        if(alive) {
            switch (direction) {
                case "up" :
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
            distance--;
        }
        solidArea.x = (int) worldX;
        solidArea.y = (int) worldY;
    }

    @Override
    public void draw(Graphics2D g2) {
        if(alive) {
            screenX = (int) (worldX - gs.player.getWorldX() + gs.player.getScreenX());
            screenY = (int) (worldY - gs.player.getWorldY() + gs.player.getScreenY());
            g2.drawRect(screenX,screenY,gs.getTile() /2,gs.getTile() /2);
            g2.setColor(Color.magenta);
            g2.fillRect(screenX,screenY,gs.getTile() /2,gs.getTile() /2);
        }
    }
}
