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

    int chooseDialogExit = 1;

    int chooseSkill = 1;


    int skillAttackHave = 0;
    int skillSuportHave = 0;
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

        gs.ui.setDrawExitGame(showDialogExit);
        gs.ui.setDrawChooseSkillsSupport(showDialogChooseSkillsSupport);
        gs.ui.setDrawChooseSkillsAttack(showDialogChooseSkillsAttack);
        if(gs.keyHandle.isAddSkillSupport()) {

            Entity newSupport = null;
            if(gs.keyHandle.getYourAddSkillSupport().equals("flash")) {
                newSupport = new Flicker(gs);
            } else if (gs.keyHandle.getYourAddSkillSupport().equals("speed")) {
                newSupport = new Sprint(gs);
            } else if (gs.keyHandle.getYourAddSkillSupport().equals("healing")) {
                newSupport = new Restore(gs);
            }

            gs.skillSupports.add(newSupport);
            gs.keyHandle.setAddSkillSupport(false);
        }

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
        if(gs.keyHandle.isAccessExitGame()) {
            gs.exitGame();
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
        }else if(checkChange(gs.player,21,44)) {
            if(gs.keyHandle.isEnterPress()) {
                gs.ui.setDrawNotice(false);
//                showDialogExit = true;
                showDialogChooseSkillsAttack = true;
//                showDialogChooseSkillsSupport = true;
            }else {
//                gs.ui.setDrawNotice(!gs.ui.isDrawChooseSkillsSupport());
                gs.ui.setDrawNotice(!gs.ui.isDrawChooseSkillsAttack());
//                gs.ui.setDrawNotice(!gs.ui.isDrawExitGame());
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
}
