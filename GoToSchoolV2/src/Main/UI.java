package Main;

import AttackSkill.ArrowLight;
import AttackSkill.CircleFire;
import AttackSkill.MoonLight;
import AttackSkill.MultiArrow;
import Entity.Entity;
import SPSkill.Flicker;
import SPSkill.Restore;
import SPSkill.SUPPORT_SKILL;
import SPSkill.Sprint;
import baseAttributeSkills.BaseArrowLight;
import baseAttributeSkills.BaseCircleFire;
import baseAttributeSkills.BaseMoonLight;
import baseAttributeSkills.BaseMultiArrow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class UI {
    GameState gs;
    Graphics2D g2;
    Font maruMonica, purisaB;
    BufferedImage HP;
    boolean isDrawNotice = false;
    boolean isDrawExitGame = false;
    boolean isPlayerSay = false;
    boolean isDrawChooseSkillsSupport = false;

    boolean isDrawUpgradeSkill = false;

    boolean isDrawChooseSkillsAttack = false;

    public UI(GameState gs) {
        this.gs = gs;
        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
            HP = ImageIO.read(getClass().getResourceAsStream("/user/hp.png"));

        } catch (FontFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2) {
        this.g2 = g2;

        if(gs.state == State.CAMPAIGN && !gs.campaign.isShowDialog()) {
            drawCampaign();
        }else if(gs.state == State.LOOPY) {
            drawLoopy();
        }
        else if(gs.state == State.SURVIVAL) {

        }
    }

    //====================================================================================
    private void playerSayAny(String words) {
        int x = 2 * gs.getTile() + 6*gs.getTile();
        int y = 2 * gs.getTile();
        int w = 12 * gs.getTile();
        int h = 3 * gs.getTile();
        drawSubWindow(x,y,w,h,g2);
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,30F));
        x += gs.getTile();
        y += gs.getTile();
        g2.drawString(words,x,y);
    }

    public void drawSubWindow(int x, int y, int width, int height, Graphics2D g2, int stroke, Color background, Color border,
                              int roundx,int roundy, int withx, int heighty,int arcw, int arch) {
//        Color c = new Color(0,0,0,210);
        g2.setColor(background);
        g2.fillRoundRect(x, y, width, height, arcw, arch);
//        c = new Color(255,255,255);
        g2.setColor(border);
        g2.setStroke(new BasicStroke(stroke));
        g2.drawRoundRect(x+roundx, y+roundy, width-withx, height-heighty, arcw, arch);
    }
    public void drawSubWindow(int x, int y, int width, int height, Graphics2D g2) {
        Color c = new Color(0,0,0,210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }

    //====================================================================================
    private void drawLoopy() {
        if(gs.keyHandle.isTabPress()) {
            drawInformationPlayer();
        }
        if(isDrawNotice) {
            drawNotice();
        }
        if(isDrawExitGame) {
            drawDialogExitGame();
        }
        if(isDrawChooseSkillsSupport) {
            drawChooseSkillSupport();
        }
        if(isDrawUpgradeSkill) {
            drawUpgradeSkill();
        }
        if(isDrawChooseSkillsAttack) {
            drawChooseSkillAttack();
        }
        if(isPlayerSay) {
            playerSayAny("SAVE SUCCESSFUL");
        }
    }
    private void drawSubTitleSkillSupport(String nameSkill) {
        int x = 9 * gs.getTile() + gs.getTile()/2;
        int y = gs.getTile()/2;
        int w = 8 * gs.getTile();
        int h = 4 * gs.getTile();
        drawSubWindow(x,y,w,h,g2);
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,30F));
        int xString = x + gs.getTile()/2;
        int yString = y + gs.getTile();
        g2.drawString(nameSkill,xString,yString);
        yString += gs.getTile();
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,23F));
        if(nameSkill.equals("Flicker")) {
            g2.drawString("Teleports a certain distance in the target direction.",xString,yString);
            yString += gs.getTile();
            g2.drawString(Flicker.TIME_REDUCE + "s Cooldown.",xString,yString);
        }else if(nameSkill.equals("Restore")) {
            g2.drawString("Recovers 30 Heath to player.",xString,yString);
            yString += gs.getTile();
            g2.drawString(Restore.TIME_REDUCE + "s Cooldown.",xString,yString);
        }else if(nameSkill.equals("Sprint")) {
            g2.drawString("Increases player speed.",xString,yString);
            yString += gs.getTile();
            g2.drawString(Sprint.TIME_REDUCE + "s Cooldown.",xString,yString);
        }
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,30F));
    }
    private void drawSubTitleSkillUpgrade(String nameSkill) {
        int x = 10 * gs.getTile() + gs.getTile()/2;
        int y = gs.getTile()/2 + 5*gs.getTile();
        int w = 12 * gs.getTile();
        int h = 5 * gs.getTile();
        drawSubWindow(x,y,w,h,g2);
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,30F));
        int xString = x + gs.getTile()/2;
        int yString = y + gs.getTile();
        xString = x + gs.getTile()/2;
        yString = y + gs.getTile();
        g2.drawString(nameSkill,xString,yString);
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,23F));
        if(nameSkill.equals("ArrowLight")) {
            xString = 15 * gs.getTile();
            g2.drawString("lever " + (BaseArrowLight.LEVER+1),xString,yString);
            xString = x + gs.getTile()/2;
            yString += gs.getTile();
            g2.drawString("Shoot a single arrow in the direction pointed by the mouse cursor, dealing " + BaseArrowLight.damage[BaseArrowLight.LEVER+1] + " damage. ",xString,yString);
            yString += gs.getTile();
            g2.drawString("Upon impact, the arrow disappears.",xString,yString);
            yString += gs.getTile();
            g2.drawString(BaseArrowLight.timeReduce[BaseArrowLight.LEVER+1] + "s Cooldown.",xString,yString);
        }else if(nameSkill.equals("MultiArrowLight")) {
            xString = 15 * gs.getTile();
            g2.drawString("lever " + (BaseMultiArrow.LEVER+1),xString,yString);
            xString = x + gs.getTile()/2;
            yString += gs.getTile();
            g2.drawString("Shoot 3 arrows in the direction pointed by the mouse cursor, each dealing " + BaseMultiArrow.damage[BaseMultiArrow.LEVER+1] +" damage.",xString,yString);
            yString += gs.getTile();
            g2.drawString("Upon impact, the arrows vanish.",xString,yString);
            yString += gs.getTile();
            g2.drawString(BaseMultiArrow.timeReduce[BaseMultiArrow.LEVER+1] + "s Cooldown.",xString,yString);
        }else if(nameSkill.equals("MoonLight")) {
            xString = 15 * gs.getTile();
            g2.drawString("lever " + (BaseMoonLight.LEVER+1),xString,yString);
            xString = x + gs.getTile()/2;
            yString += gs.getTile();
            g2.drawString("Shoot a beam of light in the direction pointed by the mouse cursor, dealing " + BaseMoonLight.damage[BaseMoonLight.LEVER+1]+" damage",xString,yString);
            yString += gs.getTile();
            g2.drawString("and passing through objects until it exits the map.",xString,yString);
            yString += gs.getTile();
            g2.drawString(BaseMoonLight.timeReduce[BaseMoonLight.LEVER+1] + "s Cooldown.",xString,yString);
        }else if(nameSkill.equals("CircleFire")) {
            xString = 15 * gs.getTile();
            g2.drawString("lever " + (BaseCircleFire.LEVER+1),xString,yString);
            xString = x + gs.getTile()/2;
            yString += gs.getTile();
            g2.drawString("Create a zone with a width of " + BaseCircleFire.radius[BaseCircleFire.LEVER+1] + ", igniting enemies for " + BaseCircleFire.damage[BaseCircleFire.LEVER+1] + " damage each time they ",xString,yString);
            yString += gs.getTile();
            g2.drawString("enter the area.",xString,yString);
            yString += gs.getTile();
            g2.drawString(BaseCircleFire.timeReduce[BaseCircleFire.LEVER+1] + "s Cooldown.",xString,yString);
        }
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,30F));
    }
    private void drawSubTitleSkillAttack(String nameSkill) {
        int x = 10 * gs.getTile() + gs.getTile()/2;
        int y = gs.getTile()/2;
        int w = 12 * gs.getTile();
        int h = 5 * gs.getTile();
        drawSubWindow(x,y,w,h,g2);
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,30F));
        int xString = x + gs.getTile()/2;
        int yString = y + gs.getTile();
        g2.drawString(nameSkill,xString,yString);
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,23F));
        if(nameSkill.equals("ArrowLight")) {
            xString = 15 * gs.getTile();
            if(BaseArrowLight.LEVER+1 == BaseArrowLight.MAX_LEVER) {
                g2.drawString("lever MAX",xString,yString);
            }else {
                g2.drawString("lever " + BaseArrowLight.LEVER,xString,yString);
            }
            xString = x + gs.getTile()/2;
            yString += gs.getTile();
            g2.drawString("Shoot a single arrow in the direction pointed by the mouse cursor, dealing " + BaseArrowLight.damage[BaseArrowLight.LEVER] + " damage. ",xString,yString);
            yString += gs.getTile();
            g2.drawString("Upon impact, the arrow disappears.",xString,yString);
            yString += gs.getTile();
            g2.drawString(BaseArrowLight.timeReduce[BaseArrowLight.LEVER] + "s Cooldown.",xString,yString);
        }else if(nameSkill.equals("MultiArrowLight")) {
            xString = 15 * gs.getTile();
            if(BaseMultiArrow.LEVER+1 == BaseMultiArrow.MAX_LEVER) {
                g2.drawString("lever MAX",xString,yString);
            }else {
                g2.drawString("lever " + BaseMultiArrow.LEVER,xString,yString);
            }
            xString = x + gs.getTile()/2;
            yString += gs.getTile();
            g2.drawString("Shoot 3 arrows in the direction pointed by the mouse cursor, each dealing " + BaseMultiArrow.damage[BaseMultiArrow.LEVER] +" damage.",xString,yString);
            yString += gs.getTile();
            g2.drawString("Upon impact, the arrows vanish.",xString,yString);
            yString += gs.getTile();
            g2.drawString(BaseMultiArrow.timeReduce[BaseMultiArrow.LEVER] + "s Cooldown.",xString,yString);
        }else if(nameSkill.equals("MoonLight")) {
            xString = 15 * gs.getTile();
            if(BaseMoonLight.LEVER+1 == BaseMoonLight.MAX_LEVER) {
                g2.drawString("lever MAX",xString,yString);
            }else {
                g2.drawString("lever " + BaseMoonLight.LEVER,xString,yString);
            }
            xString = x + gs.getTile()/2;
            yString += gs.getTile();
            g2.drawString("Shoot a beam of light in the direction pointed by the mouse cursor, dealing " + BaseMoonLight.damage[BaseMoonLight.LEVER]+" damage",xString,yString);
            yString += gs.getTile();
            g2.drawString("and passing through objects until it exits the map.",xString,yString);
            yString += gs.getTile();
            g2.drawString(BaseMoonLight.timeReduce[BaseMoonLight.LEVER] + "s Cooldown.",xString,yString);
        }else if(nameSkill.equals("CircleFire")) {
            xString = 15 * gs.getTile();
            if(BaseCircleFire.LEVER+1 == BaseCircleFire.MAX_LEVER) {
                g2.drawString("lever MAX",xString,yString);
            }else {
                g2.drawString("lever " + BaseCircleFire.LEVER,xString,yString);
            }
            xString = x + gs.getTile()/2;
            yString += gs.getTile();
            g2.drawString("Create a zone with a width of " + BaseCircleFire.radius[BaseCircleFire.LEVER] + ", igniting enemies for " + BaseCircleFire.damage[BaseCircleFire.LEVER] + " damage each time they ",xString,yString);
            yString += gs.getTile();
            g2.drawString("enter the area.",xString,yString);
            yString += gs.getTile();
            g2.drawString(BaseCircleFire.timeReduce[BaseCircleFire.LEVER] + "s Cooldown.",xString,yString);
        }
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,30F));
    }
    private void drawChooseSkillAttack() {
        int x = 2 * gs.getTile();
        int y = gs.getTile()/2;
        int w = 8 * gs.getTile();
        int h = 10 * gs.getTile();
        drawSubWindow(x,y,w,h,g2);
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,30F));
        int xString = x+gs.getTile();
        int yString = y+gs.getTile();
        xString = 5 * gs.getTile();
        g2.drawString("SKILLS ATTACK",xString,yString);
        yString += gs.getTile();
        xString = 3 * gs.getTile();
        if(gs.loopy.getSkillAttackHave() <= 0) {
            if(gs.loopy.getChooseSkill() == 1) {
                g2.drawString("Arrow Light",xString,yString);
                xString = 8 * gs.getTile();
                g2.drawString("<--",xString,yString);
                drawSubTitleSkillAttack("ArrowLight");
                xString = 3 * gs.getTile();
                yString += gs.getTile();
                g2.drawString("Multi Arrow Light",xString,yString);
                yString += gs.getTile();
                g2.drawString("Moon Light",xString,yString);
                yString += gs.getTile();
                g2.drawString("Circle Fire",xString,yString);
            }
            else if(gs.loopy.getChooseSkill() == 2) {
                g2.drawString("Arrow Light",xString,yString);
                yString += gs.getTile();
                g2.drawString("Multi Arrow Light",xString,yString);
                xString = 8 * gs.getTile();
                g2.drawString("<--",xString,yString);
                drawSubTitleSkillAttack("MultiArrowLight");
                xString = 3 * gs.getTile();
                yString += gs.getTile();
                g2.drawString("Moon Light",xString,yString);
                yString += gs.getTile();
                g2.drawString("Circle Fire",xString,yString);
            }
            else if(gs.loopy.getChooseSkill() == 3) {
                g2.drawString("Arrow Light",xString,yString);
                yString += gs.getTile();
                g2.drawString("Multi Arrow Light",xString,yString);
                yString += gs.getTile();
                g2.drawString("Moon Light",xString,yString);
                xString = 8 * gs.getTile();
                g2.drawString("<--",xString,yString);
                drawSubTitleSkillAttack("MoonLight");
                xString = 3 * gs.getTile();
                yString += gs.getTile();
                g2.drawString("Circle Fire",xString,yString);
            }else if(gs.loopy.getChooseSkill() == 4) {
                g2.drawString("Arrow Light",xString,yString);
                yString += gs.getTile();
                g2.drawString("Multi Arrow Light",xString,yString);
                yString += gs.getTile();
                g2.drawString("Moon Light",xString,yString);
                yString += gs.getTile();
                g2.drawString("Circle Fire",xString,yString);
                xString = 8 * gs.getTile();
                g2.drawString("<--",xString,yString);
                drawSubTitleSkillAttack("CircleFire");
            }
        }else {
            if(gs.loopy.getChooseSkill() == 1) {
                xString = 3 * gs.getTile();
                g2.drawString("Arrow Light",xString,yString);
                if(gs.Map_chooseSkillAttack.containsKey("ArrowLight")) {
                    xString = 7 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillAttack.get("ArrowLight").toString(),xString,yString);
                }
                xString = 8 * gs.getTile();
                g2.drawString("<--",xString,yString);
                drawSubTitleSkillAttack("ArrowLight");
                yString += gs.getTile();
                xString = 3 * gs.getTile();
                g2.drawString("Multi Arrow Light",xString,yString);
                if(gs.Map_chooseSkillAttack.containsKey("MultiArrowLight")) {
                    xString = 7 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillAttack.get("MultiArrowLight").toString(),xString,yString);
                }
                yString += gs.getTile();
                xString = 3 * gs.getTile();
                g2.drawString("Moon Light",xString,yString);
                if(gs.Map_chooseSkillAttack.containsKey("MoonLight")) {
                    xString = 7 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillAttack.get("MoonLight").toString(),xString,yString);
                }
                yString += gs.getTile();
                xString = 3 * gs.getTile();
                g2.drawString("Circle Fire",xString,yString);
                if(gs.Map_chooseSkillAttack.containsKey("CircleFire")) {
                    xString = 7 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillAttack.get("CircleFire").toString(),xString,yString);
                }
            }
            else if(gs.loopy.getChooseSkill() == 2) {
                xString = 3 * gs.getTile();
                g2.drawString("Arrow Light",xString,yString);
                if(gs.Map_chooseSkillAttack.containsKey("ArrowLight")) {
                    xString = 7 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillAttack.get("ArrowLight").toString(),xString,yString);
                }
                yString += gs.getTile();
                xString = 3 * gs.getTile();
                g2.drawString("Multi Arrow Light",xString,yString);
                if(gs.Map_chooseSkillAttack.containsKey("MultiArrowLight")) {
                    xString = 7 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillAttack.get("MultiArrowLight").toString(),xString,yString);
                }
                xString = 8 * gs.getTile();
                g2.drawString("<--",xString,yString);
                drawSubTitleSkillAttack("MultiArrowLight");
                yString += gs.getTile();
                xString = 3 * gs.getTile();
                g2.drawString("Moon Light",xString,yString);
                if(gs.Map_chooseSkillAttack.containsKey("MoonLight")) {
                    xString = 7 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillAttack.get("MoonLight").toString(),xString,yString);
                }
                yString += gs.getTile();
                xString = 3 * gs.getTile();
                g2.drawString("Circle Fire",xString,yString);
                if(gs.Map_chooseSkillAttack.containsKey("CircleFire")) {
                    xString = 7 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillAttack.get("CircleFire").toString(),xString,yString);
                }
            }
            else if(gs.loopy.getChooseSkill() == 3) {
                xString = 3 * gs.getTile();
                g2.drawString("Arrow Light",xString,yString);
                if(gs.Map_chooseSkillAttack.containsKey("ArrowLight")) {
                    xString = 7 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillAttack.get("ArrowLight").toString(),xString,yString);
                }
                yString += gs.getTile();
                xString = 3 * gs.getTile();
                g2.drawString("Multi Arrow Light",xString,yString);
                if(gs.Map_chooseSkillAttack.containsKey("MultiArrowLight")) {
                    xString = 7 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillAttack.get("MultiArrowLight").toString(),xString,yString);
                }

                yString += gs.getTile();
                xString = 3 * gs.getTile();
                g2.drawString("Moon Light",xString,yString);
                if(gs.Map_chooseSkillAttack.containsKey("MoonLight")) {
                    xString = 7 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillAttack.get("MoonLight").toString(),xString,yString);
                }
                xString = 8 * gs.getTile();
                g2.drawString("<--",xString,yString);
                drawSubTitleSkillAttack("MoonLight");
                yString += gs.getTile();
                xString = 3 * gs.getTile();
                g2.drawString("Circle Fire",xString,yString);
                if(gs.Map_chooseSkillAttack.containsKey("CircleFire")) {
                    xString = 7 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillAttack.get("CircleFire").toString(),xString,yString);
                }
            }else if(gs.loopy.getChooseSkill() == 4) {
                xString = 3 * gs.getTile();
                g2.drawString("Arrow Light",xString,yString);
                if(gs.Map_chooseSkillAttack.containsKey("ArrowLight")) {
                    xString = 7 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillAttack.get("ArrowLight").toString(),xString,yString);
                }
                yString += gs.getTile();
                xString = 3 * gs.getTile();
                g2.drawString("Multi Arrow Light",xString,yString);
                if(gs.Map_chooseSkillAttack.containsKey("MultiArrowLight")) {
                    xString = 7 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillAttack.get("MultiArrowLight").toString(),xString,yString);
                }

                yString += gs.getTile();
                xString = 3 * gs.getTile();
                g2.drawString("Moon Light",xString,yString);
                if(gs.Map_chooseSkillAttack.containsKey("MoonLight")) {
                    xString = 7 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillAttack.get("MoonLight").toString(),xString,yString);
                }
                yString += gs.getTile();
                xString = 3 * gs.getTile();
                g2.drawString("Circle Fire",xString,yString);
                if(gs.Map_chooseSkillAttack.containsKey("CircleFire")) {
                    xString = 7 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillAttack.get("CircleFire").toString(),xString,yString);
                }
                xString = 8 * gs.getTile();
                g2.drawString("<--",xString,yString);
                drawSubTitleSkillAttack("CircleFire");
            }
        }
        xString = 3 * gs.getTile();
        yString += gs.getTile();
        g2.drawString("***********************",xString,yString);
        yString += gs.getTile();
        g2.drawString("SPACE to choose.",xString,yString);
        yString += gs.getTile();
        g2.drawString("Q to reset.",xString,yString);
        yString += gs.getTile();
        g2.drawString("ESC to exit.",xString,yString);
    }

    public void drawInformationPlayer() {
        int x = 2 * gs.getTile();
        int y = gs.getTile()/2;
        int w = 8 * gs.getTile();
        int h = 12 * gs.getTile();
        drawSubWindow(x,y,w,h,g2);
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,30F));
        int xString = x+gs.getTile();
        int yString = y+gs.getTile();
        g2.drawString("Coins : " + gs.user.getCoin(),xString,yString);
        yString += gs.getTile();
        g2.drawString("Lever unlocked : " + gs.user.getNumberLeversUnlocked(),xString,yString);
        yString += gs.getTile();
        g2.drawString("Your skill Attack: ",xString,yString);
        for(Map.Entry<String,Integer> entry : gs.Map_chooseSkillAttack.entrySet()) {
            if(entry.getValue() == 1) {
                yString = 5 * gs.getTile();
                g2.drawString("skill " + (entry.getValue()) + " : " + entry.getKey() ,xString,yString);
            }else {
                yString = 6 * gs.getTile();
                g2.drawString("skill " + (entry.getValue()) + " : " + entry.getKey() ,xString,yString);
            }
        }
        yString = 7* gs.getTile();
        g2.drawString("Your skill Support: ",xString,yString);
        for(Map.Entry<String,Integer> entry : gs.Map_chooseSkillSupport.entrySet()) {
            if(entry.getValue() == 1) {
                yString = 8 * gs.getTile();
                g2.drawString("skill " + (entry.getValue()) + " : " + entry.getKey() ,xString,yString);
            }else {
                yString = 9 * gs.getTile();
                g2.drawString("skill " + (entry.getValue()) + " : " + entry.getKey() ,xString,yString);
            }
        }
    }
    private void drawNotice() {
        int x = gs.player.getScreenX() + gs.getTile();
        int y = gs.player.getScreenY() - gs.getTile()/2;
        int w = gs.getTile() /2;
        int h = gs.getTile() /2;
        drawSubWindow(x,y,w,h,g2,3,new Color(0,0,0,200),new Color(255,255,255),0,0,0,0,2,2);
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,20F));
        x += gs.getTile()/4 - 5;
        y += gs.getTile()/4 + 10;
        g2.drawString("E",x,y);
    }
    private void drawChooseSkillSupport() {
        int x = 2 * gs.getTile();
        int y = gs.getTile()/2;
        int w = 7 * gs.getTile();
        int h = 9 * gs.getTile();
        drawSubWindow(x,y,w,h,g2);
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,30F));
        int xString = x+gs.getTile();
        int yString = y+gs.getTile();
        xString = 4 * gs.getTile();
        g2.drawString("SKILLS SUPPORT",xString,yString);
        yString += gs.getTile();
        xString = 3 * gs.getTile();
        if(gs.loopy.getSkillSuportHave() <= 0) {
            if(gs.loopy.getChooseSkill() == 1) {
                g2.drawString("Flicker",xString,yString);
                xString = 6 * gs.getTile();
                g2.drawString("<--",xString,yString);
                drawSubTitleSkillSupport("Flicker");
                yString += gs.getTile();
                xString = 3 * gs.getTile();
                g2.drawString("Sprint",xString,yString);
                yString += gs.getTile();
                g2.drawString("Restore",xString,yString);
            }
            else if(gs.loopy.getChooseSkill() == 2) {
                g2.drawString("Flicker",xString,yString);
                yString += gs.getTile();
                g2.drawString("Sprint",xString,yString);
                xString = 6 * gs.getTile();
                g2.drawString("<--",xString,yString);
                drawSubTitleSkillSupport("Sprint");
                xString = 3 * gs.getTile();
                yString += gs.getTile();
                g2.drawString("Restore",xString,yString);
            }
            else if(gs.loopy.getChooseSkill() == 3) {
                g2.drawString("Flicker",xString,yString);
                yString += gs.getTile();
                g2.drawString("Sprint",xString,yString);
                yString += gs.getTile();
                xString = 6 * gs.getTile();
                g2.drawString("<--",xString,yString);
                drawSubTitleSkillSupport("Restore");
                xString = 3 * gs.getTile();
                g2.drawString("Restore",xString,yString);
            }
        }else {
            if(gs.loopy.getChooseSkill() == 1) {
                xString = 3 * gs.getTile();
                g2.drawString("Flicker",xString,yString);
                if(gs.Map_chooseSkillSupport.containsKey("Flicker")) {
                    xString = 5 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillSupport.get("Flicker").toString(),xString,yString);
                }
                xString = 6 * gs.getTile();
                g2.drawString("<--",xString,yString);
                drawSubTitleSkillSupport("Flicker");
                yString += gs.getTile();
                xString = 3 * gs.getTile();
                g2.drawString("Sprint",xString,yString);
                if(gs.Map_chooseSkillSupport.containsKey("Sprint")) {
                    xString = 5 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillSupport.get("Sprint").toString(),xString,yString);
                }
                yString += gs.getTile();
                xString = 3 * gs.getTile();
                g2.drawString("Restore",xString,yString);
                if(gs.Map_chooseSkillSupport.containsKey("Restore")) {
                    xString = 5 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillSupport.get("Restore").toString(),xString,yString);
                }
            }
            else if(gs.loopy.getChooseSkill() == 2) {
                xString = 3 * gs.getTile();
                g2.drawString("Flicker",xString,yString);
                if(gs.Map_chooseSkillSupport.containsKey("Flicker")) {
                    xString = 5 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillSupport.get("Flicker").toString(),xString,yString);
                }
                xString = 3 * gs.getTile();
                yString += gs.getTile();
                g2.drawString("Sprint",xString,yString);
                xString = 6 * gs.getTile();
                g2.drawString("<--",xString,yString);
                drawSubTitleSkillSupport("Sprint");
                if(gs.Map_chooseSkillSupport.containsKey("Sprint")) {
                    xString = 5 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillSupport.get("Sprint").toString(),xString,yString);
                }
                yString += gs.getTile();
                xString = 3 * gs.getTile();
                g2.drawString("Restore",xString,yString);
                if(gs.Map_chooseSkillSupport.containsKey("Restore")) {
                    xString = 5 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillSupport.get("Restore").toString(),xString,yString);
                }

            }
            else if(gs.loopy.getChooseSkill() == 3) {
                xString = 3 * gs.getTile();
                g2.drawString("Flicker",xString,yString);
                if(gs.Map_chooseSkillSupport.containsKey("Flicker")) {
                    xString = 5 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillSupport.get("Flicker").toString(),xString,yString);
                }
                xString = 3 * gs.getTile();
                yString += gs.getTile();
                g2.drawString("Sprint",xString,yString);
                if(gs.Map_chooseSkillSupport.containsKey("Sprint")) {
                    xString = 5 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillSupport.get("Sprint").toString(),xString,yString);
                }
                yString += gs.getTile();
                xString = 3 * gs.getTile();
                g2.drawString("Restore",xString,yString);
                xString = 6 * gs.getTile();
                g2.drawString("<--",xString,yString);
                drawSubTitleSkillSupport("Restore");
                if(gs.Map_chooseSkillSupport.containsKey("Restore")) {
                    xString = 5 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkillSupport.get("Restore").toString(),xString,yString);
                }
            }
        }
        xString = 3 * gs.getTile();
        yString += gs.getTile();
        g2.drawString("***********************",xString,yString);
        yString += gs.getTile();
        g2.drawString("SPACE to choose.",xString,yString);
        yString += gs.getTile();
        g2.drawString("Q to reset.",xString,yString);
        yString += gs.getTile();
        g2.drawString("ESC to exit.",xString,yString);
    }
    private void drawUpgradeSkill() {
        int x = 2 * gs.getTile();
        int y = gs.getTile()/2;
        int w = 8 * gs.getTile();
        int h = 12 * gs.getTile();
        drawSubWindow(x,y,w,h,g2);
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,30F));
        int xString = x+gs.getTile();
        int yString = y+gs.getTile();
        xString = 3 * gs.getTile();
        g2.drawString("UPGRADE SKILLS",xString,yString);
        xString = 7 * gs.getTile();
        g2.drawString("Coin " + gs.user.getCoin(),xString,yString);
        yString += gs.getTile();
        xString = 3 * gs.getTile();

        if(gs.loopy.getChooseSkill() == 1) {
            g2.drawString("Arrow Light",xString,yString);
            xString = 8 * gs.getTile();
            g2.drawString("<--",xString,yString);
            drawSubTitleSkillAttack("ArrowLight");
            if(BaseArrowLight.LEVER+1 < BaseArrowLight.MAX_LEVER) {
                drawSubTitleSkillUpgrade("ArrowLight");
            }

            xString = 3 * gs.getTile();
            yString += gs.getTile();
            g2.drawString("Multi Arrow Light",xString,yString);
            yString += gs.getTile();
            g2.drawString("Moon Light",xString,yString);
            yString += gs.getTile();
            g2.drawString("Circle Fire",xString,yString);
        }
        else if(gs.loopy.getChooseSkill() == 2) {
            g2.drawString("Arrow Light",xString,yString);
            yString += gs.getTile();
            g2.drawString("Multi Arrow Light",xString,yString);
            xString = 8 * gs.getTile();
            g2.drawString("<--",xString,yString);
            drawSubTitleSkillAttack("MultiArrowLight");
            if(BaseMultiArrow.LEVER+1 < BaseMultiArrow.MAX_LEVER)
                drawSubTitleSkillUpgrade("MultiArrowLight");
            xString = 3 * gs.getTile();
            yString += gs.getTile();
            g2.drawString("Moon Light",xString,yString);
            yString += gs.getTile();
            g2.drawString("Circle Fire",xString,yString);
        }
        else if(gs.loopy.getChooseSkill() == 3) {
            g2.drawString("Arrow Light",xString,yString);
            yString += gs.getTile();
            g2.drawString("Multi Arrow Light",xString,yString);
            yString += gs.getTile();
            g2.drawString("Moon Light",xString,yString);
            xString = 8 * gs.getTile();
            g2.drawString("<--",xString,yString);
            drawSubTitleSkillAttack("MoonLight");
            if(BaseMoonLight.LEVER+1 < BaseMoonLight.MAX_LEVER)
                drawSubTitleSkillUpgrade("MoonLight");
            xString = 3 * gs.getTile();
            yString += gs.getTile();
            g2.drawString("Circle Fire",xString,yString);
        }else if(gs.loopy.getChooseSkill() == 4) {
            g2.drawString("Arrow Light",xString,yString);
            yString += gs.getTile();
            g2.drawString("Multi Arrow Light",xString,yString);
            yString += gs.getTile();
            g2.drawString("Moon Light",xString,yString);
            yString += gs.getTile();
            g2.drawString("Circle Fire",xString,yString);
            xString = 8 * gs.getTile();
            g2.drawString("<--",xString,yString);
            drawSubTitleSkillAttack("CircleFire");
            if(BaseCircleFire.LEVER+1 < BaseCircleFire.MAX_LEVER)
                drawSubTitleSkillUpgrade("CircleFire");
        }
        xString = 3 * gs.getTile();
        yString += gs.getTile();
        g2.drawString("***********************",xString,yString);
        yString += gs.getTile();
        g2.drawString("Coin need update : " + gs.user.getCoinNeedUpgrade(), xString, yString);
        yString += gs.getTile();
        g2.drawString("SPACE to choose to update.",xString,yString);
        yString += gs.getTile();
        g2.drawString("Q to reset.",xString,yString);
        yString += gs.getTile();
        g2.drawString("ESC to exit.",xString,yString);
    }
    private void drawDialogExitGame() {
        int x = gs.getWindowWidth()/2 - 3*gs.getTile();
        int y = gs.getWindowHeight()/2 - ( 3 * gs.getTile())/2;
        int w = 6 * gs.getTile();
        int h = 3 * gs.getTile();
        drawSubWindow(x,y,w,h,g2);
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,30F));
        y += gs.getTile();
        x += gs.getTile()*2;

        if(gs.loopy.getChooseDialogExit() == 1) {
            g2.setColor(Color.RED);
            g2.drawString("SAVE GAME",x,y);
            g2.setColor(Color.white);
            y+=gs.getTile();
            g2.drawString(" EXIT GAME",x,y);
        }
        else if(gs.loopy.getChooseDialogExit() == 2) {
            g2.setColor(Color.white);
            g2.drawString("SAVE GAME",x,y);
            y+=gs.getTile();
            g2.setColor(Color.RED);
            g2.drawString(" EXIT GAME",x,y);
        }

    }
    //====================================================================================
    private void drawCampaign() {
        if(gs.campaign.getChoose() == 5) {
            gs.enviromentManager.draw(g2,"dark");
        }
        String numberOfEnemy = "Enemy : ";
        numberOfEnemy += String.valueOf(gs.monsters.size());
        int x = getXforCenterdText(numberOfEnemy);
        int y = gs.getTile() ;
        g2.setFont(maruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,45F));
        g2.setColor(Color.white);
        g2.drawString(numberOfEnemy,x,y);
        drawHpPlayer();
        drawFrameHpPlayer();
        if(gs.keyHandle.isTabPress()) {
            gs.ui.drawInformationPlayer();
        }
        drawCooldownSkill();
    }
    private void drawCooldownSkill() {
        int x,y,w,h;
        int xString, yString;
        y = 12 * gs.getTile();
        yString = y + gs.getTile()/2;
        w = gs.getTile();
        h = gs.getTile();
        // skill 1
        x = 9 * gs.getTile();
        xString = x + gs.getTile()/4;

        drawSubWindow(x,y,w,h,g2);
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,20F));
        if(gs.loopy.getSkillAttackHave() > 1) {
            for (Map.Entry<String,Integer> entry : gs.Map_chooseSkillAttack.entrySet()) {
                if(entry.getValue() == 1) {
                    switch (entry.getKey()) {
                        case "ArrowLight" -> {
                            if (ArrowLight.TIME_COUNT_DOWN_ATTACK <= 0) {
                                g2.drawString("Done", xString, yString);
                            } else {
                                g2.drawString(String.valueOf(ArrowLight.TIME_COUNT_DOWN_ATTACK), xString, yString);
                            }
                        }
                        case "MultiArrowLight" -> {
                            if (MultiArrow.TIME_COUNT_DOWN_ATTACK <= 0) {
                                g2.drawString("Done", xString, yString);
                            } else {
                                g2.drawString(String.valueOf(MultiArrow.TIME_COUNT_DOWN_ATTACK), xString, yString);
                            }
                        }
                        case "MoonLight" -> {
                            if (MoonLight.TIME_COUNT_DOWN_ATTACK <= 0) {
                                g2.drawString("Done", xString, yString);
                            } else {
                                g2.drawString(String.valueOf(MoonLight.TIME_COUNT_DOWN_ATTACK), xString, yString);
                            }
                        }
                        case "CircleFire" -> {
                            if (CircleFire.TIME_COUNT_DOWN_ATTACK <= 0) {
                                g2.drawString("Done", xString, yString);
                            } else {
                                g2.drawString(String.valueOf(CircleFire.TIME_COUNT_DOWN_ATTACK), xString, yString);
                            }
                        }
                    }
                }
            }
        }
        // skill 2
        x = 10 * gs.getTile();
        xString = x + gs.getTile()/4;
        drawSubWindow(x,y,w,h,g2);
        if(gs.loopy.getSkillAttackHave() == 2) {
            for (Map.Entry<String,Integer> entry : gs.Map_chooseSkillAttack.entrySet()) {
                if(entry.getValue() == 2) {
                    switch (entry.getKey()) {
                        case "ArrowLight" -> {
                            if (ArrowLight.TIME_COUNT_DOWN_ATTACK <= 0) {
                                g2.drawString("Done", xString, yString);
                            } else {
                                g2.drawString(String.valueOf(ArrowLight.TIME_COUNT_DOWN_ATTACK), xString, yString);
                            }
                        }
                        case "MultiArrowLight" -> {
                            if (MultiArrow.TIME_COUNT_DOWN_ATTACK <= 0) {
                                g2.drawString("Done", xString, yString);
                            } else {
                                g2.drawString(String.valueOf(MultiArrow.TIME_COUNT_DOWN_ATTACK), xString, yString);
                            }
                        }
                        case "MoonLight" -> {
                            if (MoonLight.TIME_COUNT_DOWN_ATTACK <= 0) {
                                g2.drawString("Done", xString, yString);
                            } else {
                                g2.drawString(String.valueOf(MoonLight.TIME_COUNT_DOWN_ATTACK), xString, yString);
                            }
                        }
                        case "CircleFire" -> {
                            if (CircleFire.TIME_COUNT_DOWN_ATTACK <= 0) {
                                g2.drawString("Done", xString, yString);
                            } else {
                                g2.drawString(String.valueOf(CircleFire.TIME_COUNT_DOWN_ATTACK), xString, yString);
                            }
                        }
                    }
                }
            }
        }
        // skill support 1
        x = 11 * gs.getTile();
        xString = x + gs.getTile()/4;
        drawSubWindow(x,y,w,h,g2);
        if(gs.loopy.getSkillSuportHave() > 1) {
            for (Map.Entry<String,Integer> entry : gs.Map_chooseSkillSupport.entrySet()) {
                if(entry.getValue() == 1) {
                    switch (entry.getKey()) {
                        case "Flicker" -> {
                            if (Flicker.TIME_COUNT_DOWN <= 0) {
                                g2.drawString("Done", xString, yString);
                            } else {
                                g2.drawString(String.valueOf(Flicker.TIME_COUNT_DOWN), xString, yString);
                            }
                        }
                        case "Sprint" -> {
                            if (Sprint.TIME_COUNT_DOWN <= 0) {
                                g2.drawString("Done", xString, yString);
                            } else {
                                g2.drawString(String.valueOf(Sprint.TIME_COUNT_DOWN), xString, yString);
                            }
                        }
                        case "Restore" -> {
                            if (Restore.TIME_COUNT_DOWN <= 0) {
                                g2.drawString("Done", xString, yString);
                            } else {
                                g2.drawString(String.valueOf(Restore.TIME_COUNT_DOWN), xString, yString);
                            }
                        }
                    }
                }
            }
        }
        // skill support 2
        x = 12 * gs.getTile();
        xString = x + gs.getTile()/4;
        drawSubWindow(x,y,w,h,g2);
        if(gs.loopy.getSkillSuportHave() == 2) {
            for (Map.Entry<String,Integer> entry : gs.Map_chooseSkillSupport.entrySet()) {
                if(entry.getValue() == 2) {
                    switch (entry.getKey()) {
                        case "Flicker" -> {
                            if (Flicker.TIME_COUNT_DOWN <= 0) {
                                g2.drawString("Done", xString, yString);
                            } else {
                                g2.drawString(String.valueOf(Flicker.TIME_COUNT_DOWN), xString, yString);
                            }
                        }
                        case "Sprint" -> {
                            if (Sprint.TIME_COUNT_DOWN <= 0) {
                                g2.drawString("Done", xString, yString);
                            } else {
                                g2.drawString(String.valueOf(Sprint.TIME_COUNT_DOWN), xString, yString);
                            }
                        }
                        case "Restore" -> {
                            if (Restore.TIME_COUNT_DOWN <= 0) {
                                g2.drawString("Done", xString, yString);
                            } else {
                                g2.drawString(String.valueOf(Restore.TIME_COUNT_DOWN), xString, yString);
                            }
                        }
                    }
                }
            }
        }
    }
    private void drawFrameHpPlayer() {
        int x = gs.getTile();
        int y = gs.getTile();
        int w = 128;
        int h = 64;
        g2.drawImage(HP,x,y,w,h,null);
    }
    private void drawHpPlayer() {
        int x = gs.getTile();
        int y = gs.getTile();
        double currentHp = ((double)gs.player.getHP()/100) * 125;
        g2.setColor(Color.red);
        g2.drawRect(x,y,125,30);
        g2.fillRect(x,y,(int)currentHp,30);
    }

    //====================================================================================
    // getter / setter
    public Font getMaruMonica() {
        return maruMonica;
    }
    private int getXforCenterdText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gs.getWindowWidth()/2 - length/2;
    }
    public Font getPurisaB() {
        return purisaB;
    }
    public void setDrawNotice(boolean b) {
        isDrawNotice = b;
    }
    public boolean isDrawNotice() {
        return isDrawNotice;
    }
    public boolean isDrawExitGame() {
        return isDrawExitGame;
    }
    public void setDrawExitGame(boolean drawExitGame) {
        isDrawExitGame = drawExitGame;
    }
    public boolean isPlayerSay() {
        return isPlayerSay;
    }
    public void setPlayerSay(boolean playerSay) {
        isPlayerSay = playerSay;
    }
    public boolean isDrawChooseSkillsSupport() {
        return isDrawChooseSkillsSupport;
    }
    public void setDrawChooseSkillsSupport(boolean drawChooseSkills) {
        isDrawChooseSkillsSupport = drawChooseSkills;
    }
    public boolean isDrawChooseSkillsAttack() {
        return isDrawChooseSkillsAttack;
    }

    public void setDrawChooseSkillsAttack(boolean drawChooseSkillsAttack) {
        isDrawChooseSkillsAttack = drawChooseSkillsAttack;
    }
    public boolean isDrawUpgradeSkill() {
        return isDrawUpgradeSkill;
    }

    public void setDrawUpgradeSkill(boolean drawUpgradeSkill) {
        isDrawUpgradeSkill = drawUpgradeSkill;
    }

}
