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
        distance = 50;
        damage = 5;
        speed = 20;
        worldX = gs.player.getWorldX() + (double) gs.getTile() /2;
        worldY = gs.player.getWorldY() + (double) gs.getTile() /2;
        solidArea = new Rectangle(0,0,gs.getTile() /2,gs.getTile() /2);
        angleTarget = anglePlayerAndMouse();
        // Chuyển góc thành độ
        double angleDegrees = Math.toDegrees(angleTarget);

        // phán đoán hướng di chuyển
        if (angleDegrees >= -45 && angleDegrees < 45) {
            direction = "right";
        } else if (angleDegrees >= 45 && angleDegrees < 135) {
            direction = "down";
        } else if (angleDegrees >= -135 && angleDegrees < -45) {
            direction = "up";
        } else {
            direction = "left";
        }

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)worldX + solidArea.x,(int)worldY + solidArea.y,solidArea.width,solidArea.height);
    }

    @Override
    public void update() {
        collisionOn = false;
        gs.CC.checkEntityWithTile(this);

        if(!collisionOn && alive) {
            if(distance <= 0) {
                alive = false;
                distance = 100;
            }
            worldX += Math.cos(angleTarget) * speed;
            worldY += Math.sin(angleTarget) * speed;
            distance--;
        }else {
            alive = false;
            distance = 100;
        }
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
