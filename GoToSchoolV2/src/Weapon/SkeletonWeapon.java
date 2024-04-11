package Weapon;

import Entity.TYPE;
import Main.GameState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Objects;

public class SkeletonWeapon extends BaseSkill{


    public SkeletonWeapon(GameState gs) {
        super(gs);
        init();
    }

    public void getImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapon/ske_weapon.png")));
            currentImage = up1;
        }catch(Exception ignored) {

        }
    }
    @Override
    public void init() {
        type = TYPE.WEAPON;
        distance = 50;
        damage = 5;
        speed = 10;
        solidArea = new Rectangle(0,0,gs.getTile()/2,gs.getTile()/2);
        getImage();
        setPolygonVertices();
    }

    @Override
    public void update() {
        if(alive) {
            if(distance <= 0) {
                alive = false;
            }
            worldX += Math.cos(angleTarget) * speed;
            worldY += Math.sin(angleTarget) * speed;
            distance--;
            clearVertices();
            setPolygonVertices();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if(alive) {
            screenX = (int) (worldX - gs.player.getWorldX() + gs.player.getScreenX());
            screenY = (int) (worldY - gs.player.getWorldY() + gs.player.getScreenY());
            g2.drawImage(currentImage, screenX, screenY, gs.getTile()/2, gs.getTile()/2, null);

        }
    }

}
