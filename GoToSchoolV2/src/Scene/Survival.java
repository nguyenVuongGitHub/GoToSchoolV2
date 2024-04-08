package Scene;

import Entity.Entity;
import Main.GameState;
import Main.State;
import Main.UI;
import User.UserManager;

import java.awt.*;

public class Survival {
    GameState gs;
    UserManager um;
    Graphics2D g2;
    UI ui;
    boolean showDialog = true;
    public Survival(UserManager um, GameState gs, UI ui) {
        this.gs = gs;
        this.um = um;
        this.ui = ui;
    }

    public void loadMap() {
        gs.tileM.loadMap("/maps/survival.txt", 1);
    }
    private boolean checkChange(Entity player, int x, int y) {
        return gs.CC.checkEntityEvent(player,x,y);
    }
    public void update() {

        gs.updateBattle();

    }

    public void draw(Graphics2D g2) {
        // MAP
        gs.tileM.draw(g2);
        // ENTITY
        if(gs.player != null) {
            gs.player.draw(g2);
        }

        for(Entity monster : gs.monsters) {
            if(monster != null) {
                monster.draw(g2);
            }
        }
        for(Entity skill : gs.skillAttacks) {
            if(skill != null) {
                skill.draw(g2);
            }
        }

    }
}
