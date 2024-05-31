package Scene;


import Main.GameState;
import Main.State;
import SPSkill.Flicker;
import  Entity.Entity;
import SPSkill.Restore;
import SPSkill.Sprint;

import java.awt.*;

public class Loopy {
    GameState gs;
    boolean showDialogExit = false;

    boolean showDialogChooseSkillsSupport = false;


    boolean showDialogChooseSkillsAttack = false;


    boolean showDialogUpgradeSkill = false;

    int chooseDialogExit = 1;

    int chooseSkill = 1;
    int skillAttackHave = 0;
    int skillSuportHave = 0;

    boolean[] selectedAttack = new boolean[4];
    boolean[] selectedSupport = new boolean[3];
    boolean[] selectedUpgrade = new boolean[4];
    boolean[] selectedExitOrSave = new boolean[2];
    public boolean[] getSelectedSupport() {
        return selectedSupport;
    }

    public void setSelectedSupport(boolean value, int index) {
        this.selectedSupport[index] = value;
    }
    public boolean[] getSelectedAttack() {
        return selectedAttack;
    }

    public void setSelectedAttack(boolean value, int index) {
        this.selectedAttack[index] = value;
    }
    public boolean[] getSelectedUpgrade() {
        return selectedUpgrade;
    }

    public void setSelectedUpgrade(boolean value, int index) {
        this.selectedUpgrade[index] = value;
    }

    public Loopy(GameState gs) {
        this.gs = gs;
    }

    public void loadMap() {
        gs.stopMusic();
        gs.playMusic(2);
        gs.tileM.loadMap("/maps/loopy_1.txt",1);
        gs.tileM.loadMap("/maps/loopy_2.txt",2);
    }
    private boolean checkChange(Entity player, int x, int y) {
        return gs.CC.checkEntityEvent(player,x,y);
    }

    public void update() {
        // nếu ở loopy sẽ có background tương tự như màu biển
        gs.setBackground(gs.baseColor);
        gs.player.update();

        gs.ui.setDrawExitGame(showDialogExit);
        gs.ui.setDrawChooseSkillsSupport(showDialogChooseSkillsSupport);
        gs.ui.setDrawChooseSkillsAttack(showDialogChooseSkillsAttack);
        gs.ui.setDrawUpgradeSkill(showDialogUpgradeSkill);


        if(gs.keyHandle.isResetSkillSupport()) {
            gs.Map_chooseSkillSupport.clear();
            gs.skillSupports.clear();
            skillSuportHave = 0;
            gs.keyHandle.setResetSkillSupport(false);
        }

        if(gs.keyHandle.isResetSkillAttack()) {
            gs.Map_chooseSkillAttack.clear();
            gs.skillAttacks.clear();
            skillAttackHave = 0;
            gs.keyHandle.setResetSkillAttack(false);
        }
        if(gs.keyHandle.isAccessSaveGame()) {
            gs.saveGame();
            gs.ui.setPlayerSay(true);
        }
        if(gs.ui.isShowSubSaveGame()) {

            if(gs.keyHandle.isSaveGame().equals("yes")){
                gs.saveGame();
                gs.closeMusic();
                gs.exitGame();
            }else if(gs.keyHandle.isSaveGame().equals("no")) {
                gs.closeMusic();
                gs.exitGame();
            }
        }


        // va chạm với ô cần di chuyển đến CAMPAIGN
        if(checkChange(gs.player,30,28)
                || checkChange(gs.player,31,28)
                || checkChange(gs.player,32,28)
                || checkChange(gs.player,30,29)
                || checkChange(gs.player,32,29)
                || checkChange(gs.player,30,30)
                || checkChange(gs.player,31,30)
                || checkChange(gs.player,32,30)) {
            if(gs.keyHandle.isEnterPress()) {
                gs.state = State.CAMPAIGN;
                gs.changeState = true;
                gs.ui.setDrawNotice(false);
            }else {
                gs.ui.setDrawNotice(true);
            }
        }
        // survival mode
        else if(checkChange(gs.player,21,44)) {
            if(gs.keyHandle.isEnterPress()) {
                gs.player.setHP(100);
                gs.state = State.SURVIVAL;
                gs.changeState = true;
                gs.ui.setDrawNotice(false);
                gs.changeScene.setAlive(true);
            }else {
                gs.ui.setDrawNotice(true);
            }
        }
        else if(checkChange(gs.player,15,30)
                ||checkChange(gs.player,16,30)
                ||checkChange(gs.player,15,31)
                ||checkChange(gs.player,16,31)) {
            if(gs.keyHandle.isEnterPress()) {
                showDialogChooseSkillsSupport = true;
            }else {
                gs.ui.setDrawNotice(!gs.ui.isDrawChooseSkillsSupport());
            }
        }
        else if(checkChange(gs.player,19,30)
                ||checkChange(gs.player,20,30)
                ||checkChange(gs.player,19,31)
                ||checkChange(gs.player,20,31)) {
            if(gs.keyHandle.isEnterPress()) {
                showDialogChooseSkillsAttack = true;
            }else {
                gs.ui.setDrawNotice(!gs.ui.isDrawChooseSkillsAttack());
            }
        }
        else if(checkChange(gs.player,23,30)
                ||checkChange(gs.player,24,30)
                ||checkChange(gs.player,23,31)
                ||checkChange(gs.player,24,31)) {
            if(gs.keyHandle.isEnterPress()) {
                showDialogUpgradeSkill = true;
            }else {
                gs.ui.setDrawNotice(!gs.ui.isDrawUpgradeSkill());
            }
        }
        else {
            gs.ui.setDrawNotice(false);
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
    public boolean isShowDialogExit() {
        return showDialogExit;
    }

    public void setShowDialogExit(boolean showDialogExit) {
        this.showDialogExit = showDialogExit;
    }
    public int getChooseDialogExit() {
        return chooseDialogExit;
    }

    public void setChooseDialogExit(int chooseDialogExit) {
        this.chooseDialogExit = chooseDialogExit;
    }
    public int getChooseSkill() {
        return chooseSkill;
    }

    public void setChooseSkill(int chooseSkill) {
        this.chooseSkill = chooseSkill;
    }
    public boolean isShowDialogChooseSkillsSupport() {
        return showDialogChooseSkillsSupport;
    }

    public void setShowDialogChooseSkillsSupport(boolean showDialogChooseSkills) {
        this.showDialogChooseSkillsSupport = showDialogChooseSkills;
    }
    public int getSkillSuportHave() {
        return skillSuportHave;
    }

    public void setSkillSuportHave(int skillHave) {
        this.skillSuportHave = skillHave;
    }
    public int getSkillAttackHave() {
        return skillAttackHave;
    }

    public void setSkillAttackHave(int skillHave) {
        this.skillAttackHave = skillHave;
    }
    public boolean isShowDialogChooseSkillsAttack() {
        return showDialogChooseSkillsAttack;
    }

    public void setShowDialogChooseSkillsAttack(boolean showDialogChooseSkillsAttack) {
        this.showDialogChooseSkillsAttack = showDialogChooseSkillsAttack;
    }
    public boolean isShowDialogUpgradeSkill() {
        return showDialogUpgradeSkill;
    }

    public void setShowDialogUpgradeSkill(boolean showDialogUpgradeSkill) {
        this.showDialogUpgradeSkill = showDialogUpgradeSkill;
    }

    public boolean[] getSelectedExitOrSave() {
        return selectedExitOrSave;
    }

    public void setSelectedExitOrSave(boolean value, int index) {
        this.selectedExitOrSave[index] = value;
    }
}
