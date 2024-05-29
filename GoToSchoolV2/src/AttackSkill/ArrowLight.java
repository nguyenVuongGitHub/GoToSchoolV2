package AttackSkill;

import CollisionSystem.PointX;
import Entity.TYPE;
import Main.GameState;
import Main.UtilityTool;
import Weapon.BaseSkill;
import baseAttributeSkills.BaseArrowLight;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.Objects;

/*
* kỹ năng bắn ra mũi tên về phía trước
* */

public class ArrowLight extends BaseSkill {
    public static int TIME_COUNT_DOWN_ATTACK = 0;
    public static int TIME_REDUCE = 0;
    BufferedImage[] images;
    public ArrowLight(GameState gs) {
        super(gs);
        init();
    }
    @Override
    public void update() {
        if(alive) {
            // cập nhật lại mới các đỉnh
            clearVertices();
            setPolygonVertices();
            // tạo điểm trung tâm của các đỉnh
            PointX center = new PointX(vertices.get(3).getX() + (double) getBounds().width /2,vertices.get(3).getY());

            // thực hiện xoay đỉnh theo góc được chỉ định
            double newAngle = Math.toRadians((int)Math.toDegrees(angleTarget) + 90);
            getVertices().set(0, getVertices().get(0).clockwiseTransform(center, newAngle));
            getVertices().set(1, getVertices().get(1).clockwiseTransform(center, newAngle));
            getVertices().set(2, getVertices().get(2).clockwiseTransform(center, newAngle));
            getVertices().set(3, getVertices().get(3).clockwiseTransform(center, newAngle));

            // gán biến va chạm với tile set là false
            collisionOn = false;

            // kiểm tra có va chạm với tile set không trong đây sẽ trả về giá trị cho collisionOn
            // true là có va chạm với tile set
            // false là không có va chạm
            gs.CC.checkEntityWithTile(this);

            // nếu như không có va chạm và thực thể còn sống
            if(!collisionOn && alive) {
                if(distance <= 0) {
                    alive = false;
                }
                // thực hiện di chuyển
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
            //  vị trí vẽ tại màn hình
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
//            drawVertices(g2);
//            drawCircle(g2);
        }
    }

    @Override
    public void init() {
        type = TYPE.WEAPON;
        typeSkill.typeAttack = ATTACK_SKILL.ARROW_LIGHT;
        worldY = gs.player.getWorldY();
        worldX = gs.player.getWorldX();
        solidArea = new Rectangle(48,0,gs.getTile()/5,gs.getTile());
        angleTarget = anglePlayerAndMouse();
        alive = true;
        speed = BaseArrowLight.speed[BaseArrowLight.LEVER];
        distance = BaseArrowLight.distance[BaseArrowLight.LEVER];
        damage = BaseArrowLight.damage[BaseArrowLight.LEVER];
        TIME_REDUCE = BaseArrowLight.timeReduce[BaseArrowLight.LEVER];
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
