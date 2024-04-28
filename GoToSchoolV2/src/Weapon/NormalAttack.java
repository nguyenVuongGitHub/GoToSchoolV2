package Weapon;

import AttackSkill.ATTACK_SKILL;
import Entity.TYPE;
import Main.GameState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Objects;

public class NormalAttack extends BaseSkill{
    public static int TIME_COUNT_DOWN_ATTACK = 1;
    public static final int TIME_REDUCE = 1;

    public NormalAttack(GameState gs) {
        super(gs);
        init();
    }
    public void getImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapon/nomalAttack.png")));
            currentImage = up1;
        }catch(Exception ignored) {

        }
    }
    @Override
    public void init() {
        type = TYPE.WEAPON;
        typeSkill.typeAttack = ATTACK_SKILL.NORMAL;
        distance = 50;
        damage = 5;
        speed = 20;
        worldX = gs.player.getWorldX();
        worldY = gs.player.getWorldY();
        solidArea = new Rectangle(0,0,gs.getTile(),gs.getTile());
        angleTarget = anglePlayerAndMouse();
        getImage();
        setPolygonVertices();
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
        clearVertices();
        setPolygonVertices();
    }

    @Override
    public void draw(Graphics2D g2) {
        if(alive) {
            screenX = (int) (worldX - gs.player.getWorldX() + gs.player.getScreenX());
            screenY = (int) (worldY - gs.player.getWorldY() + gs.player.getScreenY());
            g2.drawImage(currentImage, screenX + gs.getTile()/4 , screenY + gs.getTile()/4, gs.getTile()/2, gs.getTile()/2, null);
        }
    }
}
