package AttackSkill;

import CollisionSystem.PointX;
import Entity.TYPE;
import Main.GameState;
import Main.UtilityTool;
import Weapon.BaseSkill;
import baseAttributeSkills.BaseMultiArrow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class MultiArrow extends BaseSkill {
    public static int TIME_COUNT_DOWN_ATTACK = 1;
    public static int TIME_REDUCE = 1;
    BufferedImage[] images;
    public MultiArrow(GameState gs) {
        super(gs);
        init();
    }
    @Override
    public void update() {
        if(alive) {
            clearVertices();
            setPolygonVertices();
            PointX center = new PointX(vertices.get(3).getX() + (double) getBounds().width /2,vertices.get(3).getY());
            double newAngle = Math.toRadians((int)Math.toDegrees(angleTarget) + 90);
            getVertices().set(0, getVertices().get(0).clockwiseTransform(center, newAngle));
            getVertices().set(1, getVertices().get(1).clockwiseTransform(center, newAngle));
            getVertices().set(2, getVertices().get(2).clockwiseTransform(center, newAngle));
            getVertices().set(3, getVertices().get(3).clockwiseTransform(center, newAngle));
            collisionOn = false;
            gs.CC.checkEntityWithTile(this);

            if(!collisionOn && alive) {
                if(distance <= 0) {
                    alive = false;
                }
                worldX += Math.cos(angleTarget) * speed;
                worldY += Math.sin(angleTarget) * speed;
                distance--;
            }else {
                alive = false;
            }
            spriteCounter++;
            if(spriteCounter > 3) {
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
                    spriteNum = 1;
                }
            }
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
        }
    }

    @Override
    public void init() {
        type = TYPE.WEAPON;
        typeSkill.typeAttack = ATTACK_SKILL.MULTI_ARROW;
        worldX = gs.player.getWorldX();
        worldY = gs.player.getWorldY();
        solidArea = new Rectangle(48,0,gs.getTile()/5,gs.getTile());
        angleTarget = anglePlayerAndMouse();
        alive = true;
        damage = BaseMultiArrow.damage[BaseMultiArrow.LEVER];
        speed = BaseMultiArrow.speed[BaseMultiArrow.LEVER];
        distance = BaseMultiArrow.distance[BaseMultiArrow.LEVER];
        TIME_REDUCE = BaseMultiArrow.timeReduce[BaseMultiArrow.LEVER];
        getImage();
        setPolygonVertices();
    }

    @Override
    public void getImage() {
        try {
            images = new BufferedImage[9];
            UtilityTool uTool = new UtilityTool();
            BufferedImage largeImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapon/ice.png")));
            int x = 0, y = 0;
            int scale = 2;
            for(int i = 0; i < images.length; i++) {
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
