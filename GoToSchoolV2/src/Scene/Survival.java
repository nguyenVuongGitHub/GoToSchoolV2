package Scene;

import Entity.Entity;
import Main.GameState;
import Main.State;

import java.awt.*;

public class Survival {
    GameState gs;
    boolean showDialog = true;
    public Survival(GameState gs) {
        this.gs = gs;
    }

    public void loadMap() {
        gs.tileM.loadMap("/maps/survival.txt", 1);
    }
    private boolean checkChange(Entity player, int x, int y) {
        return gs.CC.checkEntityEvent(player,x,y);
    }
    public void update() {

        gs.player.update();

        // va chạm với ô cần di chuyển đến CAMPAIGN
        if(checkChange(gs.player,56,24) && gs.keyHandle.isEnterPress()){
            gs.state = State.CAMPAIGN;
            gs.changeState = true;

        }
    }

    public void draw(Graphics2D g2) {
        // MAP
        gs.tileM.draw(g2);
        // ENTITY
        if(gs.player != null) {
            gs.player.draw(g2);
        }

    }
}
