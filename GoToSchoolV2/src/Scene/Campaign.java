package Scene;
import Entity.Entity;
import Entity.Monster;
import Entity.Slime;

import Main.GameState;
import Main.State;
import Main.UI;
import User.UserManager;

import java.awt.*;

public class Campaign {
    GameState gs;
    UserManager um;
    Graphics2D g2;
    UI ui;
    boolean loadMapDone = false;
    boolean isGameOver = false;
    int choose = 1;
    boolean showDialog = true;
    short thisLever = 0;
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
        y += gs.getTile();
        g2.drawString("ESC - EXIT",x+gs.getTile(),y);
        g2.drawString("STATE 1",x+gs.getTile()*8,y);
        y += gs.getTile();
        x += gs.getTile()*6;
        for(int i = 1; i <= um.getMaxNumberLevers(); i++) {
            if(choose == i) {
                g2.drawString("-> ", x-gs.getTile()*2,y);
            }
            if(i > um.getNumberLeversUnlocked()) {
                g2.setColor(Color.red);
                g2.drawString("MÀN : " + String.valueOf(i),x-gs.getTile()*2 + 30,y);
            }else{
                g2.setColor(Color.white);
                g2.drawString("MÀN : " + String.valueOf(i),x-gs.getTile()*2 + 30,y);
            }
            y += gs.getTile();
        }
    }

    public void loadMap(int index) {
        resetAllData();
        switch (index) {
            case 1:
                gs.tileM.loadMap("/maps/map1_1.txt",1);
                gs.tileM.loadMap("/maps/map1_2.txt",2);
                // setup MONSTER
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*39+2);
                    monster.setWorldY(gs.getTile()*50);
                    gs.monsters.add(monster);
                }
//                for(int i = 0; i < 10; i++) {
//                    Entity monster = new Slime(gs);
//                    monster.setWorldX(gs.getTile()*28+i);
//                    monster.setWorldY(gs.getTile()*27);
//                    gs.monsters.add(monster);
//                }
//                for(int i = 0; i < 10; i++) {
//                    Entity monster = new Slime(gs);
//                    monster.setWorldX(gs.getTile()*35+i);
//                    monster.setWorldY(gs.getTile()*20);
//                    gs.monsters.add(monster);
//                }
//                for(int i = 0; i < 5; i++) {
//                    Entity monster = new Slime(gs);
//                    monster.setWorldX(gs.getTile()*24+i);
//                    monster.setWorldY(gs.getTile()*49);
//                    gs.monsters.add(monster);
//                }
//                for(int i = 0; i < 5; i++) {
//                    Entity monster = new Slime(gs);
//                    monster.setWorldX(gs.getTile()*13+i);
//                    monster.setWorldY(gs.getTile()*42);
//                    gs.monsters.add(monster);
//                }
//                for(int i = 0; i < 10; i++) {
//                    Entity monster = new Slime(gs);
//                    monster.setWorldX(gs.getTile()*10+i);
//                    monster.setWorldY(gs.getTile()*26);
//                    gs.monsters.add(monster);
//                }
//                for(int i = 0; i < 5; i++) {
//                    Entity monster = new Slime(gs);
//                    monster.setWorldX(gs.getTile()*52+i);
//                    monster.setWorldY(gs.getTile()*9);
//                    gs.monsters.add(monster);
//                }
//                for(int i = 0; i < 5; i++) {
//                    Entity monster = new Slime(gs);
//                    monster.setWorldX(gs.getTile()*50+i);
//                    monster.setWorldY(gs.getTile()*31);
//                    gs.monsters.add(monster);
//                }
                break;
            case 2:
                gs.tileM.loadMap("/maps/map_2.txt",1);
                // setup MONSTER
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*6+i);
                    monster.setWorldY(gs.getTile()*33);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 3; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*13+i);
                    monster.setWorldY(gs.getTile()*41);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 3; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*29+i);
                    monster.setWorldY(gs.getTile()*58);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 1; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*52+i);
                    monster.setWorldY(gs.getTile()*41);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 2; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*47+i);
                    monster.setWorldY(gs.getTile()*30);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 3; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*57+i);
                    monster.setWorldY(gs.getTile()*28);
                    gs.monsters.add(monster);
                }

                for(int i = 0; i < 6; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*47+i);
                    monster.setWorldY(gs.getTile()*11);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 1; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*37+i);
                    monster.setWorldY(gs.getTile()*15);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*24+i);
                    monster.setWorldY(gs.getTile()*5);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*20+i);
                    monster.setWorldY(gs.getTile()*9);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*16+i);
                    monster.setWorldY(gs.getTile()*13);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 1; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()+i);
                    monster.setWorldY(gs.getTile()*47);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 10; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*6+i);
                    monster.setWorldY(gs.getTile()*5);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 10; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*10);
                    monster.setWorldY(gs.getTile()*60);
                    gs.monsters.add(monster);
                }
                break;
            case 3:
//                gs.tileM.loadMap("/maps/map_test.txt");
                // setup MONSTER
                break;
        }
        loadMapDone = true;
        thisLever = (short) index;
    }

    public void update() {

        if(!gs.player.getAlive()) {
            isGameOver = true;
        }
        if(gs.keyHandle.isExitMap()) {
            showDialog = true;
            gs.keyHandle.setExitMap(false);
            gs.keyHandle.setTimeExitMap(0);
        }
        if(gs.monsters.isEmpty()) {
            // update lever
            if(um.getNumberLeversUnlocked() == thisLever) {
                short newLever = (short) (um.getNumberLeversUnlocked() + 1);
                um.setNumberLeversUnlocked(newLever);
            }
        }
        if(gs.keyHandle.isAccessReturnLoopy()) {
            gs.state = State.LOOPY;
            gs.changeState = true;
            gs.keyHandle.resetAllData();
            gs.player.setWorldX(56* gs.getTile());
            gs.player.setWorldY(24* gs.getTile());
        }
        if(gs.keyHandle.isAccessLoadMap()) {
            loadMap(choose);
            gs.keyHandle.setAccessLoadMap(false);
            showDialog = false;
            if(choose == 1) {
                gs.player.setWorldX(10*gs.getTile());
                gs.player.setWorldY(52*gs.getTile());
            }else if(choose == 2) {
                gs.player.setWorldX(24*gs.getTile());
                gs.player.setWorldY(30*gs.getTile());
            }
        }

         if(loadMapDone) {
            gs.updateBattle();
        }

    }

    public void resetAllData() {
        isGameOver = false;
        gs.monsters.clear();
        gs.player.setAlive(true);
        gs.player.setHP(100);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        if(showDialog) {
            dialogChooseMap(g2);
        }else{
           gs.drawBattle(g2);
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
    public boolean getLoadMapDone() {return loadMapDone;}
    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
}
