package environment;

import Main.GameState;

import java.awt.*;

public class EnviromentManager {
    GameState gs;
    Lighting lighting;

    public EnviromentManager(GameState gs) {
        this.gs = gs;
    }
    public void init() {
        lighting = new Lighting(gs,gs.getTile()*8);
    }
    public void draw(Graphics2D g2, String state) {
        if(state.equals("dark"))
            lighting.draw(g2);
    }
}
