package Scene;
import Entity.*;

import Main.GameState;
import Main.State;
import Main.UI;
import User.UserManager;
import baseAttributeMonsters.BaseBoss;
import baseAttributeMonsters.BaseSkeleton;
import baseAttributeMonsters.BaseSlime;

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
    boolean isResetData = true;
    boolean nextLever = false;

    short currentStateCampaign = 1;
    int step;



    public Campaign(UserManager um, GameState gs, UI ui) {
        this.gs = gs;
        this.um = um;
        this.ui = ui;
    }
    private void dialogChooseMap(Graphics2D g2) {
        // position
        int x = gs.getWindowWidth()/2 - (gs.getWindowWidth() - (gs.getTile()*15))/2;
        int y = gs.getTile();
        int width = gs.getWindowWidth() - (gs.getTile()*15);
        int height = gs.getTile()*10;
        ui.drawSubWindow(x,y,width,height,g2);
        g2.setFont(ui.getMaruMonica().deriveFont(Font.BOLD,30F));
        y += gs.getTile();
        g2.drawString("ESC - EXIT",x+gs.getTile(),y);
        g2.drawString("STATE " + currentStateCampaign,x+gs.getTile()*5 + 30,y);
        y += gs.getTile();
        x += gs.getTile()*4;

        step = ((currentStateCampaign-1)*um.getMaxNumberLevers());
        for(int j = 1; j <= um.getMaxNumberLevers(); j++) {
            if(choose == j) {
                if((j+step) <= um.getNumberLeversUnlocked()) {
                    g2.drawString("-> ", x-gs.getTile(),y);
                }
            }
            if((j+step)> um.getNumberLeversUnlocked()) {
                g2.setColor(Color.red);
                g2.drawString("MAP : " + (j+step),x-gs.getTile() + 30,y);
            }else{
                g2.setColor(Color.white);
                g2.drawString("MAP : " + (j+step),x-gs.getTile() + 30,y);
            }
            y += gs.getTile();
        }
    }

    public void loadMap(int index) {
        switch (index) {
            case 1:
                gs.tileM.loadMap("/maps/map1_1.txt",1);
                gs.tileM.loadMap("/maps/map1_2.txt",2);
//                for(int i = 0; i < 1; i ++) {
//                    Entity monster = new Boss(gs);
//                    monster.setWorldX(gs.getTile()*30);
//                    monster.setWorldY(gs.getTile()*45);
//                    gs.monsters.add(monster);
//
//                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*39);
                    monster.setWorldY(gs.getTile()*50);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 10; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*28);
                    monster.setWorldY(gs.getTile()*27);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 10; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*35);
                    monster.setWorldY(gs.getTile()*20);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*24);
                    monster.setWorldY(gs.getTile()*49);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*13);
                    monster.setWorldY(gs.getTile()*42);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 10; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*10);
                    monster.setWorldY(gs.getTile()*26);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*52);
                    monster.setWorldY(gs.getTile()*9);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*50);
                    monster.setWorldY(gs.getTile()*31);
                    gs.monsters.add(monster);
                }
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
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*50);
                    monster.setWorldY(gs.getTile()*57);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*52);
                    monster.setWorldY(gs.getTile()*45);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 2; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*53);
                    monster.setWorldY(gs.getTile()*20);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 1; i++) {
                    Entity monster = new Boss(gs);
                    monster.setWorldX(gs.getTile()*45);
                    monster.setWorldY(gs.getTile()*10);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*35);
                    monster.setWorldY(gs.getTile()*13);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 10; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*25);
                    monster.setWorldY(gs.getTile()*9);
                    gs.monsters.add(monster);
                }
                // setup MONSTER
                break;
            case 4:
                gs.tileM.loadMap("/maps/map4_1.txt",1);
                gs.tileM.loadMap("/maps/map4_2.txt",2);
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*17);
                    monster.setWorldY(gs.getTile()*45);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*46);
                    monster.setWorldY(gs.getTile()*47);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*9);
                    monster.setWorldY(gs.getTile()*23);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*46);
                    monster.setWorldY(gs.getTile()*24);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*41);
                    monster.setWorldY(gs.getTile()*8);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*34);
                    monster.setWorldY(gs.getTile()*13);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*25);
                    monster.setWorldY(gs.getTile()*4);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*39);
                    monster.setWorldY(gs.getTile()*12);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*14);
                    monster.setWorldY(gs.getTile()*32);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*39);
                    monster.setWorldY(gs.getTile()*29);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*11);
                    monster.setWorldY(gs.getTile()*16);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*51);
                    monster.setWorldY(gs.getTile()*51);
                    gs.monsters.add(monster);
                }
                break;
            case 5:
                gs.tileM.loadMap("/maps/map5_1.txt",1);
                gs.tileM.loadMap("/maps/map5_2.txt",2);
                for(int i = 0; i < 10; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*9);
                    monster.setWorldY(gs.getTile()*20);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 10; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*35);
                    monster.setWorldY(gs.getTile()*30);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 10; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*32);
                    monster.setWorldY(gs.getTile()*32);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 1; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*20);
                    monster.setWorldY(gs.getTile()*2);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 1; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*39);
                    monster.setWorldY(gs.getTile()*11);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*55);
                    monster.setWorldY(gs.getTile()*3);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*55);
                    monster.setWorldY(gs.getTile()*14);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*32);
                    monster.setWorldY(gs.getTile()*54);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 5; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*55);
                    monster.setWorldY(gs.getTile()*44);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 3; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*7);
                    monster.setWorldY(gs.getTile()*40);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 2; i++) {
                    Entity monster = new Skeleton(gs);
                    monster.setWorldX(gs.getTile()*19);
                    monster.setWorldY(gs.getTile()*56);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 2; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*17);
                    monster.setWorldY(gs.getTile()*40);
                    gs.monsters.add(monster);
                }
                for(int i = 0; i < 3; i++) {
                    Entity monster = new Slime(gs);
                    monster.setWorldX(gs.getTile()*11);
                    monster.setWorldY(gs.getTile()*55);
                    gs.monsters.add(monster);
                }
                break;
        }
        loadMapDone = true;
        thisLever = (short) index;
    }

    public void update() {
        if(showDialog) {
            gs.setBackground(gs.baseColor);
            // nếu cho phép next lever thì mới được cập nhật lever mới
            // nextLever được gọi trong updateBattle
            if(nextLever) {
                if(um.getNumberLeversUnlocked() == thisLever) {
                    short newLever = (short) (um.getNumberLeversUnlocked() + 1);
                    um.setNumberLeversUnlocked(newLever);
                }
                nextLever = false;
            }
            if(gs.keyHandle.isAccessReturnLoopy()) {
                if(gs.changeScene.getNumberDraw() == 2) {
                    gs.state = State.LOOPY;
                    gs.keyHandle.resetAllData();
                    gs.player.setWorldX(29* gs.getTile());
                    gs.player.setWorldY(28* gs.getTile());
                    gs.keyHandle.setAccessReturnLoopy(false);
                }
            }
            if(gs.keyHandle.isAccessLoadMap()) {

                // 50 is max lever of monster
                if(gs.user.getExperience() / 100 < 50) {
                    BaseBoss.LEVER = (int) gs.user.getExperience() / 100;
                    BaseSkeleton.LEVER = (int) gs.user.getExperience() / 100;
                    BaseSlime.LEVER = (int) gs.user.getExperience() / 100;
                }else {
                    BaseBoss.LEVER = BaseBoss.MAX_LEVER-1;
                    BaseSkeleton.LEVER = BaseSkeleton.MAX_LEVER-1;
                    BaseSlime.LEVER = BaseSlime.MAX_LEVER-1;
                }

                // nếu như trong lúc draw == 2 -> hoạt ảnh ra ( có nghĩa là từ màn hình đen -> sáng dần )
                // thì mới load map
                if(gs.changeScene.getNumberDraw() == 2) {
                    loadMap(choose);
                    gs.keyHandle.setAccessLoadMap(false);
                    showDialog = false;
                    if(choose+step == 1) {
                        gs.player.setWorldX(10*gs.getTile());
                        gs.player.setWorldY(52*gs.getTile());
                    }else if(choose+step == 2) {
                        gs.player.setWorldX(4*gs.getTile());
                        gs.player.setWorldY(5*gs.getTile());
                    }else if(choose+step == 3) {
                        gs.player.setWorldX(3*gs.getTile());
                        gs.player.setWorldY(60*gs.getTile());
                    }else if(choose+step == 4) {
                        gs.player.setWorldX(33*gs.getTile());
                        gs.player.setWorldY(45*gs.getTile());
                    }else if(choose+step == 5) {
                        gs.player.setWorldX(2*gs.getTile());
                        gs.player.setWorldY(2*gs.getTile());
                    }
                }
            }
        }else {
            if(gs.keyHandle.isExitMap()) {
                showDialog = true;
                gs.keyHandle.setExitMap(false);
            }
            if(!gs.player.getAlive()) {
                isGameOver = true;
            }
            if(loadMapDone) {
                if(choose+step == 5) {
                    gs.setBackground(Color.black);
                }
                gs.updateBattle();
            }
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
            // nếu đang ở dialog chọn màn chơi sẽ xóa sạch mọi dữ liệu từ map.
            if(isResetData) {
                resetAllData();
                isResetData = false;
            }
            dialogChooseMap(g2);
        }else{
            // gán lại giá trị của reset data
            if(!isResetData) {
                isResetData = true;
            }
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
    public boolean isNextLever() {
        return nextLever;
    }

    public void setNextLever(boolean nextLever) {
        this.nextLever = nextLever;
    }
    public boolean isLoadMapDone() {
        return loadMapDone;
    }

    public void setLoadMapDone(boolean loadMapDone) {
        this.loadMapDone = loadMapDone;
    }
    public short getCurrentStateCampaign() {
        return currentStateCampaign;
    }

    public void setCurrentStateCampaign(short currentStateCampaign) {
        this.currentStateCampaign = currentStateCampaign;
    }
    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
