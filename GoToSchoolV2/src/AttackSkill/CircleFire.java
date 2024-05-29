package AttackSkill;

import CollisionSystem.PointX;
import Entity.TYPE;
import Main.GameState;
import Main.UtilityTool;
import Weapon.BaseSkill;
import baseAttributeSkills.BaseCircleFire;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class CircleFire extends BaseSkill {
    public static int TIME_COUNT_DOWN_ATTACK = 0;
    public static int TIME_REDUCE = 1;
    public static int NUMBER_BURNING = 3;
    public int TIME_DELAY = 60;
    public int COUNT_TIME_DELAY = 0;
    BufferedImage[] images;
    int scale = 9;
    public CircleFire(GameState gs) {
        super(gs);
        init();
    }

    @Override
    public void update() {
        if(alive) {
            COUNT_TIME_DELAY++;
            if(COUNT_TIME_DELAY == TIME_DELAY) {
                COUNT_TIME_DELAY = 0;
                NUMBER_BURNING--;
                if(NUMBER_BURNING <= 0) {
                    alive = false;
                }
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
            g2.drawImage(currentImage,screenX - getSolidArea().width/2 + 32, screenY - getSolidArea().height/2 + 32, null);
//            drawCircle(g2);
        }

    }

    @Override
    public void init() {
        type = TYPE.WEAPON;
        typeSkill.typeAttack = ATTACK_SKILL.CIRCLE_FIRE;
        alive = true;
        damage = BaseCircleFire.damage[BaseCircleFire.LEVER];
        radius = BaseCircleFire.radius[BaseCircleFire.LEVER];
        TIME_REDUCE = 1;
        NUMBER_BURNING = BaseCircleFire.numberBurning[BaseCircleFire.LEVER];
        worldX = PointX.getCenterPointFromList(gs.player.getVertices()).getX() - radius/2;
        worldY = PointX.getCenterPointFromList(gs.player.getVertices()).getY() - radius/2;
        solidArea = new Rectangle(0,0, (int) radius, (int) radius);
        getImage();
        setPolygonVertices();
    }

    @Override
    public void getImage() {
        try {
            images = new BufferedImage[4];
            UtilityTool uTool = new UtilityTool();
            BufferedImage largeImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapon/fire.png")));
            int x = 0, y = 0;
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
