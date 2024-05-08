package AttackSkill;

import Entity.TYPE;
import Main.GameState;
import Weapon.BaseSkill;
import baseAttribute.BaseMoonLight;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Objects;

public class MoonLight extends BaseSkill {
    public static int TIME_COUNT_DOWN_ATTACK = 1;
    public static int TIME_REDUCE = 1;

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
            clearVertices();
            setPolygonVertices();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if(alive) {
            screenX = (int) (worldX - gs.player.getWorldX() + gs.player.getScreenX());
            screenY = (int) (worldY - gs.player.getWorldY() + gs.player.getScreenY());
            g2.drawImage(currentImage, screenX + gs.getTile()/2 , screenY + gs.getTile()/2, gs.getTile(), gs.getTile(), null);
//            drawVertices(g2);
            drawCircle(g2);
        }
    }

    @Override
    public void init() {
        type = TYPE.WEAPON;
        typeSkill.typeAttack = ATTACK_SKILL.MOON_LIGHT;
        worldX = gs.player.getWorldX();
        worldY = gs.player.getWorldY();
        solidArea = new Rectangle(0,0,gs.getTile(),gs.getTile());
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
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapon/nomalAttack.png")));
            currentImage = up1;
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
