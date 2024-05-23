package AttackSkill;

import CollisionSystem.PointX;
import Entity.TYPE;
import Main.GameState;
import Weapon.BaseSkill;
import baseAttributeSkills.BaseMultiArrow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Objects;

public class MultiArrow extends BaseSkill {
    public static int TIME_COUNT_DOWN_ATTACK = 1;
    public static int TIME_REDUCE = 1;
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

        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if(alive) {
            screenX = (int) (worldX - gs.player.getWorldX() + gs.player.getScreenX());
            screenY = (int) (worldY - gs.player.getWorldY() + gs.player.getScreenY());
//            g2.drawImage(currentImage, screenX + gs.getTile()/2 , screenY + gs.getTile()/2, gs.getTile(), gs.getTile(), null);
            drawVertices(g2);
            PointX center = new PointX(vertices.get(3).getX() + (double) getBounds().width /2,vertices.get(3).getY());
//            drawCircle(g2);
        }
    }

    @Override
    public void init() {
        type = TYPE.WEAPON;
        typeSkill.typeAttack = ATTACK_SKILL.MULTI_ARROW;
        worldX = gs.player.getWorldX();
        worldY = gs.player.getWorldY();
        solidArea = new Rectangle(0,0,gs.getTile()/5,gs.getTile());
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
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapon/nomalAttack.png")));
            currentImage = up1;
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
