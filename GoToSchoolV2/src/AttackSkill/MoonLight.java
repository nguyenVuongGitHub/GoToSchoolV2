package AttackSkill;

import Entity.TYPE;
import Main.GameState;
import Main.UtilityTool;
import Weapon.BaseSkill;
import baseAttributeSkills.BaseMoonLight;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class MoonLight extends BaseSkill {
    public static int TIME_COUNT_DOWN_ATTACK = 1;
    public static int TIME_REDUCE = 1;
    BufferedImage[] images;

    public MoonLight(GameState gs) {
        super(gs);
        init();
    }

    @Override
    public void update() {
        if(alive) {
            if(!gs.inSightWorld(worldX,worldY)) {
                alive = false;
            }
            worldX += Math.cos(angleTarget) * speed;
            worldY += Math.sin(angleTarget) * speed;
            spriteCounter++;
            if(spriteCounter > 3) {
                spriteCounter = 0;
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 3;
                } else if (spriteNum == 3) {
                    spriteNum = 4;
                } else if (spriteNum == 4) {
                    spriteNum = 5;
                } else if (spriteNum == 5) {
                    spriteNum = 1;
                }
            }
            clearVertices();
            setPolygonVertices();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if(alive) {
            currentImage = images[spriteNum-1];
            screenX = (int) (worldX - gs.player.getWorldX() + gs.player.getScreenX());
            screenY = (int) (worldY - gs.player.getWorldY() + gs.player.getScreenY());
            int currentScreenX = screenX;
            int currentScreenY = screenY;
            AffineTransform oldTransform = g2.getTransform();
            AffineTransform at = new AffineTransform();
            at.translate(currentScreenX,currentScreenY);
            at.rotate(angleTarget, (double) currentImage.getWidth(null) /2, (double) currentImage.getHeight(null) /2);
            g2.drawImage(currentImage, at,null);
            g2.setTransform(oldTransform);
//            drawCircle(g2);
//            drawVertices(g2);
        }
    }

    @Override
    public void init() {
        type = TYPE.WEAPON;
        typeSkill.typeAttack = ATTACK_SKILL.MOON_LIGHT;
        worldX = gs.player.getWorldX();
        worldY = gs.player.getWorldY();
        solidArea = new Rectangle(32,32,gs.getTile(),gs.getTile());
        angleTarget = anglePlayerAndMouse();
        getImage();
        setPolygonVertices();
        damage = BaseMoonLight.damage[BaseMoonLight.LEVER];
        speed = BaseMoonLight.speed[BaseMoonLight.LEVER];
        TIME_REDUCE = BaseMoonLight.timeReduce[BaseMoonLight.LEVER];
    }

    @Override
    public void getImage() {
        try {
            images = new BufferedImage[5];
            UtilityTool uTool = new UtilityTool();
            BufferedImage largeImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapon/moon.png")));
            int scale = 2;
            int x = 0, y = 0;
            for (int i = 0; i < images.length; i++) {
                images[i] = largeImage.getSubimage(x,y,64,64);
                images[i] = uTool.scaleImage(images[i],scale);
                x+=64;
            }

            currentImage = images[0];
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
