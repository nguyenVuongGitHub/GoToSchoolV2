package SPSkill;

import Main.GameState;
import Weapon.BaseSkill;

import java.awt.*;

public class Flash extends BaseSkill {
    public static int TIME_COUNT_DOWN = 0;
    public static final int TIME_REDUCE = 2;
    public Flash(GameState gs) {
        super(gs);
        init();
    }

    @Override
    public void update() {
        if(alive) {
            direction = gs.player.getDirection();
            worldX = gs.player.getWorldX();
            worldY = gs.player.getWorldY();
            solidArea = gs.player.getSolidArea();
            switch (direction) {
                case "nan" -> direction = gs.player.getBeforeDirection();
                case "up-left", "up-right" -> direction = "up";
                case "down-left", "down-right" -> direction = "down";
            }
            gs.CC.checkEntityWithTile(this);
//            System.out.println(direction);
            collisionOn = false;
            if(!collisionOn) {
                switch (direction) {
                    case "up" -> gs.player.setWorldY(gs.player.getWorldY() - speed);
                    case "down" -> gs.player.setWorldY(gs.player.getWorldY() + speed);
                    case "left" -> gs.player.setWorldX(gs.player.getWorldX() - speed);
                    case "right" -> gs.player.setWorldX(gs.player.getWorldX() + speed);
                }
            }
            alive = false;
        }
    }

    @Override
    public void draw(Graphics2D g2) {

    }

    @Override
    public void init() {
        
        typeSkill.typeSupport = SUPPORT_SKILL.Flash;
        alive = false;
        direction = gs.player.getDirection();
        distance = 2;
        speed = (int) (distance * gs.getTile());
        worldX = gs.player.getWorldX();
        worldY = gs.player.getWorldY();
        solidArea = gs.player.getSolidArea();

    }

    @Override
    public void getImage() {

    }
}
