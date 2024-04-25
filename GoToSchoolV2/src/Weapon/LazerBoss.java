package Weapon;

import CollisionSystem.PointX;
import Entity.TYPE;
import Main.GameState;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Random;

public class LazerBoss extends BaseSkill{
    BufferedImage largeImage[];
    int scale = 6;
    int centerX = 52*scale;
    int centerY = 33*scale;
    PointX center;
    public LazerBoss(GameState gs) {
        super(gs);
        init();
        getImage();
    }

    @Override
    public void getImage() {
        try {
            BufferedImage largeImage_temp = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapon/laser.png")));
            UtilityTool uTool =  new UtilityTool();
            int col = largeImage_temp.getWidth() / 300;

            largeImage = new BufferedImage[col];
            int y;
            for(int x = 0; x < col; x ++) {
                y = 0;
                largeImage[x] = largeImage_temp.getSubimage(x*300,y,300,100);
                largeImage[x] = uTool.scaleImage(largeImage[x],scale);
            }
            currentImage = largeImage[0];
        }catch(Exception ignored) {

        }
    }
    @Override
    public void init() {
        type = TYPE.WEAPON;
        distance = 50;
        damage = 20;
        speed = 10;
        solidArea = new Rectangle(24*scale,24*scale,275*scale,20*scale);
        getImage();
        setPolygonVertices();
    }

    @Override
    public void update() {
        if(alive) {
            clearVertices();
            setPolygonVertices();
            // point need transform
            center = new PointX(vertices.getFirst().getX() + 28 * scale ,vertices.getFirst().getY() + (8 * scale));
            double newAngle;
            if (direction.equals("left")) {
                newAngle = angleTarget + Math.toRadians(180);
            }
            else {
                newAngle = angleTarget;
            }
            getVertices().set(0, getVertices().get(0).clockwiseTransform(center, newAngle));
            getVertices().set(1, getVertices().get(1).clockwiseTransform(center, newAngle));
            getVertices().set(2, getVertices().get(2).clockwiseTransform(center, newAngle));
            getVertices().set(3, getVertices().get(3).clockwiseTransform(center, newAngle));
            spriteCounter++;
            if(spriteCounter > 4) {
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
                    spriteNum = 6;
                }
                else if(spriteNum == 6) {
                    spriteNum = 7;
                }
                else if(spriteNum == 7) {
                    spriteNum = 8;
                }
                else if(spriteNum == 8) {
                    spriteNum = 9;
                }
                else if(spriteNum == 9) {
                    spriteNum = 10;
                }
                else if(spriteNum == 10) {
                    spriteNum = 11;
                }
                else if(spriteNum == 11) {
                    spriteNum = 12;
                }
                else if(spriteNum == 12) {
                    spriteNum = 13;
                }
                else if(spriteNum == 13) {
                    spriteNum = 14;
                }else if(spriteNum == 14) {
                    alive = false;
                    spriteNum = 1;
                }
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if(alive) {
            currentImage = largeImage[spriteNum-1];
            screenX = (int) (worldX - gs.player.getWorldX() + gs.player.getScreenX());
            screenY = (int) (worldY - gs.player.getWorldY() + gs.player.getScreenY());
            int currentScreenX = screenX;
            int currentScreenY = screenY;
            AffineTransform oldTransform = g2.getTransform();
            AffineTransform at = new AffineTransform();
            if (direction.equals("left")) {
                at.scale(-1,1);
                currentScreenX = -currentScreenX - currentImage.getWidth()/3;
                at.translate(currentScreenX,currentScreenY);
                at.rotate(-angleTarget,centerX,centerY);
            } else {
                at.translate(currentScreenX,currentScreenY);
                at.rotate(angleTarget,centerX,centerY);
            }
            drawVertices(g2);
            g2.drawImage(currentImage,at,null);
            g2.setTransform(oldTransform);
        }
    }
}
