package AttackSkill;

import CollisionSystem.PointX;
import Entity.TYPE;
import Main.GameState;
import Weapon.BaseSkill;
import baseAttribute.BaseCircleFire;
import baseAttribute.BaseMoonLight;

import java.awt.*;

public class CircleFire extends BaseSkill {
    public static int TIME_COUNT_DOWN_ATTACK = 0;
    public static int TIME_REDUCE = 1;
    public static int NUMBER_BURNING = 3;
    public int TIME_DELAY = 60;
    public int COUNT_TIME_DELAY = 0;
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
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if(alive) {
            screenX = (int) (worldX - gs.player.getWorldX() + gs.player.getScreenX());
            screenY = (int) (worldY - gs.player.getWorldY() + gs.player.getScreenY());
//            g2.drawImage(currentImage, screenX + gs.getTile()/2 , screenY + gs.getTile()/2, gs.getTile(), gs.getTile(), null);
            drawCircle(g2);
        }

    }

    @Override
    public void init() {
        type = TYPE.WEAPON;
        typeSkill.typeAttack = ATTACK_SKILL.CIRCLE_FIRE;
        alive = true;
        damage = BaseCircleFire.damage[BaseCircleFire.LEVER];
        radius = BaseCircleFire.radius[BaseCircleFire.LEVER];
        TIME_REDUCE = BaseCircleFire.timeReduce[BaseCircleFire.LEVER];
        NUMBER_BURNING = BaseCircleFire.numberBurning[BaseCircleFire.LEVER];
        worldX = PointX.getCenterPointFromList(gs.player.getVertices()).getX() - radius/2;
        worldY = PointX.getCenterPointFromList(gs.player.getVertices()).getY() - radius/2;
        solidArea = new Rectangle(0,0, (int) radius, (int) radius);
        getImage();
        setPolygonVertices();
    }

    @Override
    public void getImage() {

    }
}
