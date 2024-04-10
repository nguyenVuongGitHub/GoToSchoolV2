package Scene;


import Main.GameState;
import Main.State;
import Main.UI;
import User.UserManager;
import  Entity.Entity;
import java.awt.*;

public class Loopy {
    GameState gs;
    boolean showDialog = true;
    public Loopy(GameState gs) {
        this.gs = gs;
    }

    public void loadMap() {
        gs.tileM.loadMap("/maps/loopy_1.txt",1);
        gs.tileM.loadMap("/maps/loopy_2.txt",2);
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
//        gs.tileM.drawHelper(g2,1);
        // ENTITY
        if(gs.player != null) {
            gs.player.draw(g2);
        }
//        gs.tileM.drawHelper(g2,2);
    }
}
