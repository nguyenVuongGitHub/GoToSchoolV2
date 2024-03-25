package Scene;
import Entity.Entity;
import Entity.Monster;
import Main.GameState;
import Main.State;
import Main.UI;
import User.UserManager;
import tile.TileManager;

import java.awt.*;

public class Campaign {
    GameState gs;
    UserManager um;
    Graphics2D g2;
    UI ui;
    int choose = 1;
    boolean showDialog = true;
    public Campaign(UserManager um, GameState gs, UI ui) {
        this.gs = gs;
        this.um = um;
        this.ui = ui;
    }
    private void dialogChooseMap(Graphics2D g2) {
        // position
        int x = gs.getWindowWidth()/2 - (gs.getWindowWidth() - (gs.getTile()*15))/2;
        int y = gs.getTile()*3;
        int width = gs.getWindowWidth() - (gs.getTile()*15);
        int height = gs.getTile()*10;
        drawSubWindow(x,y,width,height);
        g2.setFont(ui.getMaruMonica().deriveFont(Font.BOLD,30F));
        x += gs.getTile();
        y += gs.getTile();

        for(int i = 1; i <= um.getMaxNumberLevers(); i++) {
            if(choose == i) {
                g2.drawString("->", x-30,y);
            }
            g2.drawString("MÀN : " + String.valueOf(i),x+10,y);
            y += gs.getTile()*2;
        }
    }

    public void loadMap(int index) {
        switch (index) {
            case 1:
                gs.tileM.loadMap("/maps/map_test.txt");

                    Entity monster = new Monster(gs);
                    monster.setWorldX(gs.getTile()*5);
                    monster.setWorldY(gs.getTile()*10);
                    gs.monsters.add(monster);

                // setup MONSTER
                break;
            case 2:
                gs.tileM.loadMap("/maps/map_test.txt");
                // setup MONSTER
                break;
            case 3:
                gs.tileM.loadMap("/maps/map_test.txt");
                // setup MONSTER
                break;
        }
    }
    public void update() {
        if (gs.monsters.isEmpty()) {
            showDialog = true;
        }
        gs.updateBattle();
    }
    public void draw(Graphics2D g2) {
        this.g2 = g2;
        if(showDialog) {
            dialogChooseMap(g2);
        }else{
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
            // ANOTHER
        }
    }
    private void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0,0,0,210);
        g2.setColor(c);

        g2.fillRoundRect(x, y, width, height, 35, 35);
        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }

    public void setShowDialog(boolean b) {
        this.showDialog = b;
    }

    public boolean isShowDialog() {
        return showDialog;
    }

    public void setChoose(int choose) {
        this.choose = choose;
    }
    public int getChoose() {
        return choose;
    }
}
