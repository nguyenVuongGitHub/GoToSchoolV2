package SPSkill;

import Main.GameState;
import Weapon.BaseSkill;

import java.awt.*;

public class Healing extends BaseSkill {
    public static int TIME_COUNT_DOWN = 0;
    public static final int TIME_REDUCE = 60;
    public static int NUMBER_HEALING = 3;
    public int TIME_DELAY = 10;
    public int COUNT_TIME_DELAY = 0;
    public Healing(GameState gs) {
        super(gs);
        init();
    }

    @Override
    public void update() {
        if(alive) {
            COUNT_TIME_DELAY++;
            if(COUNT_TIME_DELAY == TIME_DELAY) {
                COUNT_TIME_DELAY = 0;
                NUMBER_HEALING--;
                if(NUMBER_HEALING >= 0) {
                    double currentHP = gs.player.getHP();
                    currentHP += damage;
                    gs.player.setHP((int) Math.min(currentHP, 100));
                }else {
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
        typeSkill.typeSupport = SUPPORT_SKILL.Healing;
        damage = 10;
        alive = false;
    }

    @Override
    public void getImage() {

    }
}
