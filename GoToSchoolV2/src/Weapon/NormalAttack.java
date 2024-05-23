package Weapon;

import AttackSkill.ATTACK_SKILL;
import CollisionSystem.PointX;
import Entity.TYPE;
import Main.GameState;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
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
            BufferedImage largeImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapon/fireBall.png")));
            int x = 0;
            int y = 0;
            int scale = 2;
            UtilityTool uTool = new UtilityTool();
            up1 = largeImage.getSubimage(x,y,gs.getTile(),gs.getTile());
            up1 = uTool.scaleImage(up1,scale);
            x+=64;
            up2 = largeImage.getSubimage(x,y,gs.getTile(),gs.getTile());
            up2 = uTool.scaleImage(up2,scale);
            x+=64;
            up3 = largeImage.getSubimage(x,y,gs.getTile(),gs.getTile());
            up3 = uTool.scaleImage(up3,scale);
            x+=64;
            up4 = largeImage.getSubimage(x,y,gs.getTile(),gs.getTile());
            up4 = uTool.scaleImage(up4,scale);
            x+=64;
            up5 = largeImage.getSubimage(x,y,gs.getTile(),gs.getTile());
            up5 = uTool.scaleImage(up5,scale);
            x+=64;
            down1 = largeImage.getSubimage(x,y,gs.getTile(),gs.getTile());
            down1 = uTool.scaleImage(down1,scale);
            x+=64;
            down2 = largeImage.getSubimage(x,y,gs.getTile(),gs.getTile());
            down2 = uTool.scaleImage(down2,scale);
            x+=64;
            down3 = largeImage.getSubimage(x,y,gs.getTile(),gs.getTile());
            down3 = uTool.scaleImage(down3,scale);
            x+=64;
            down4 = largeImage.getSubimage(x,y,gs.getTile(),gs.getTile());
            down4 = uTool.scaleImage(down4,scale);
            x+=64;
            down5 = largeImage.getSubimage(x,y,gs.getTile(),gs.getTile());
            down5 = uTool.scaleImage(down5,scale);
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
        speed = 25;
        worldX = gs.player.getWorldX();
        worldY = gs.player.getWorldY();
        solidArea = new Rectangle(32,32,gs.getTile(),gs.getTile());
        angleTarget = anglePlayerAndMouse();
        getImage();
        setPolygonVertices();
    }

    @Override
    public void update() {
        if(alive) {
            collisionOn = false;
            gs.CC.checkEntityWithTile(this);

            if(!collisionOn && alive) {
                if(distance <= 0) {
                    alive = false;
                    spriteNum = 1;
                }
                worldX += Math.cos(angleTarget) * speed;
                worldY += Math.sin(angleTarget) * speed;
                distance--;
            }else {
                alive = false;
                spriteNum = 1;
            }
            clearVertices();
            setPolygonVertices();
        }
        spriteCounter++;
        if(spriteCounter > 1) {
            spriteCounter = 0;
            if(spriteNum == 1) {
                spriteNum = 2;
            }
            else if(spriteNum == 2) {
                spriteNum = 3;
            }
            else if(spriteNum == 3) {
                spriteNum = 4;
            }
            else if(spriteNum == 4) {
                spriteNum = 5;
            }
            else if(spriteNum == 5) {
                spriteNum = 1;
            }
        }

    }

    @Override
    public void draw(Graphics2D g2) {
        if(alive) {
            if (spriteNum == 1) {
                currentImage = up1;
            } else if (spriteNum == 2) {
                currentImage = up2;
            } else if (spriteNum == 3) {
                currentImage = up3;
            } else if (spriteNum == 4) {
                currentImage = up4;
            }else if (spriteNum == 5) {
                currentImage = up5;
            }
        }else {
            if (spriteNum == 1) {
                currentImage = down1;
            } else if (spriteNum == 2) {
                currentImage = down2;
            } else if (spriteNum == 3) {
                currentImage = down3;
            } else if (spriteNum == 4) {
                currentImage = down4;
            }else if (spriteNum == 5) {
                currentImage = down5;
            }
        }
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
    }
}
