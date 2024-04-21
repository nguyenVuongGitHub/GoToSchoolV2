package Weapon;

import Main.GameState;

import java.awt.*;

public class Flash extends BaseSkill{
    public static int TIME_COUNT_DOWN = 0;
    public static final int TIME_ATTACK = 30;
    public Flash(GameState gs) {
        super(gs);
        init();
    }

    @Override
    public void update() {
        switch (direction) {
            case "nan" -> direction = gs.player.getBeforeDirection();
            case "up-left", "up-right" -> direction = "up";
            case "down-left", "down-right" -> direction = "down";
        }

        gs.CC.checkEntityWithTile(this);
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

    @Override
    public void draw(Graphics2D g2) {

    }

    @Override
    public void init() {
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
