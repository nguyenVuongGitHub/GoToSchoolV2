package Scene;
import Entity.*;

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
        ui.drawSubWindow(x,y,width,height,g2);

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
                g2.drawString("MÀN : " + i,x-gs.getTile()*2 + 30,y);
            }else{
                g2.setColor(Color.white);
                g2.drawString("MÀN : " + i,x-gs.getTile()*2 + 30,y);
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
                for(int i = 0; i < 1; i ++) {
                    Entity monster = new Boss(gs);
                    monster.setWorldX(gs.getTile()*30);
                    monster.setWorldY(gs.getTile()*45);
                    gs.monsters.add(monster);
                }
//                for(int i = 0; i < 5; i++) {
//                    Entity monster = new Skeleton(gs);
//                    monster.setWorldX(gs.getTile()*39);
//                    monster.setWorldY(gs.getTile()*50);
//                    gs.monsters.add(monster);
//                }
//                for(int i = 0; i < 10; i++) {
//                    Entity monster = new Slime(gs);
//                    monster.setWorldX(gs.getTile()*28);
//                    monster.setWorldY(gs.getTile()*27);
//                    gs.monsters.add(monster);
//                }
//                for(int i = 0; i < 10; i++) {
//                    Entity monster = new Slime(gs);
//                    monster.setWorldX(gs.getTile()*35);
//                    monster.setWorldY(gs.getTile()*20);
//                    gs.monsters.add(monster);
//                }
//                for(int i = 0; i < 5; i++) {
//                    Entity monster = new Skeleton(gs);
//                    monster.setWorldX(gs.getTile()*24);
//                    monster.setWorldY(gs.getTile()*49);
//                    gs.monsters.add(monster);
//                }
//                for(int i = 0; i < 5; i++) {
//                    Entity monster = new Slime(gs);
//                    monster.setWorldX(gs.getTile()*13);
//                    monster.setWorldY(gs.getTile()*42);
//                    gs.monsters.add(monster);
//                }
//                for(int i = 0; i < 10; i++) {
//                    Entity monster = new Slime(gs);
//                    monster.setWorldX(gs.getTile()*10);
//                    monster.setWorldY(gs.getTile()*26);
//                    gs.monsters.add(monster);
//                }
//                for(int i = 0; i < 5; i++) {
//                    Entity monster = new Skeleton(gs);
//                    monster.setWorldX(gs.getTile()*52);
//                    monster.setWorldY(gs.getTile()*9);
//                    gs.monsters.add(monster);
//                }
//                for(int i = 0; i < 5; i++) {
//                    Entity monster = new Slime(gs);
//                    monster.setWorldX(gs.getTile()*50);
//                    monster.setWorldY(gs.getTile()*31);
//                    gs.monsters.add(monster);
//                }
                break;
            case 2:
                gs.tileM.loadMap("/maps/map2_1.txt",1);
                gs.tileM.loadMap("/maps/map2_2.txt",2);
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*20);
                    monster.setWorldY(gs.getTile()*2);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 10; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*32);
                    monster.setWorldY(gs.getTile()*8);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 10; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*12);
                    monster.setWorldY(gs.getTile()*23);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*8);
                    monster.setWorldY(gs.getTile()*36);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*28);
                    monster.setWorldY(gs.getTile()*29);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 10; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*13);
                    monster.setWorldY(gs.getTile()*58);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 1; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*43);
                    monster.setWorldY(gs.getTile()*24);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 4; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*3);
                    monster.setWorldY(gs.getTile()*61);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*57);
                    monster.setWorldY(gs.getTile()*44);
                    gs.monsters.add(monster);
                }
                // setup MONSTER
                break;
            case 3:
                gs.tileM.loadMap("/maps/map3_1.txt",1);
                gs.tileM.loadMap("/maps/map3_2.txt",2);
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
//            gs.changeState = true;
            gs.changeScene.setAlive(true);
            gs.keyHandle.resetAllData();
            gs.player.setWorldX(29* gs.getTile());
            gs.player.setWorldY(28* gs.getTile());
        }
        if(gs.keyHandle.isAccessLoadMap()) {
            gs.changeScene.setAlive(true);
            loadMap(choose);
            gs.keyHandle.setAccessLoadMap(false);
            showDialog = false;
            if(choose == 1) {
                gs.player.setWorldX(10*gs.getTile());
                gs.player.setWorldY(52*gs.getTile());
            }else if(choose == 2) {
                gs.player.setWorldX(4*gs.getTile());
                gs.player.setWorldY(5*gs.getTile());
            }else if(choose == 3) {
                gs.player.setWorldX(3*gs.getTile());
                gs.player.setWorldY(60*gs.getTile());
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
    public boolean isGameOver() {
        return isGameOver;
    }
}
