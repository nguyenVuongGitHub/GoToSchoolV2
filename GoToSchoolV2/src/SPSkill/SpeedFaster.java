package SPSkill;

import Main.GameState;
import Weapon.BaseSkill;

import java.awt.*;

public class SpeedFaster extends BaseSkill {
    public static int TIME_COUNT_DOWN = 0;
    public static final int TIME_REDUCE = 30;
    public int TIME_DELAY = 3;
    public int COUNT_TIME_DELAY = 0;
    int newSpeed;
    int normalSpeed;
    public SpeedFaster(GameState gs) {
        super(gs);
        init();
    }

    @Override
    public void update() {
        if(alive) {
            COUNT_TIME_DELAY++;
            if(COUNT_TIME_DELAY <= TIME_DELAY) {
                COUNT_TIME_DELAY = 0;
                gs.player.setSpeed(newSpeed);
                if(newSpeed > normalSpeed) {
                    newSpeed -= 5;
                }else {
                    newSpeed = normalSpeed;
                    gs.player.setSpeed(newSpeed);
                    alive = false;
                }
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {

    }

    @Override
    public void init() {
        typeSkill.typeSupport = SUPPORT_SKILL.SpeedFaster;
        speed = gs.player.getSpeed() * 4;
        newSpeed = speed;
        normalSpeed = gs.player.getSpeed();
        alive = false;
    }

    @Override
    public void getImage() {

    }
}
