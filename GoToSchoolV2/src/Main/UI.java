package Main;

import javax.imageio.ImageIO;
import java.awt.*;
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
        if(isPlayerSay) {
            playerSayAny("SAVE SUCCESSFUL");
        }
    }
    public void drawInformationPlayer() {
        int x = 2 * gs.getTile();
        int y = 2 * gs.getTile();
        int w = 7 * gs.getTile();
        int h = 10 * gs.getTile();
        drawSubWindow(x,y,w,h,g2);
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,30F));
        int xString = x+gs.getTile();
        int yString = y+gs.getTile();
        g2.drawString("Coins : " + gs.user.getCoin(),xString,yString);
        yString += gs.getTile();
        g2.drawString("Skill have : " + gs.user.getNumberSkillsUnlocked(),xString,yString);
        yString += gs.getTile();
        g2.drawString("Lever unlocked : " + gs.user.getNumberLeversUnlocked(),xString,yString);
        yString += gs.getTile();
        g2.drawString("Your skill Support: ",xString,yString);
        for(Map.Entry<String,Integer> entry : gs.Map_chooseSkill.entrySet()) {
            if(entry.getValue() == 1) {
                yString = 7 * gs.getTile();
                g2.drawString("Your skill support " + (entry.getValue()) + " : " + entry.getKey() ,xString,yString);
            }else {
                yString = 8 * gs.getTile();
                g2.drawString("Your skill support " + (entry.getValue()) + " : " + entry.getKey() ,xString,yString);
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
        int y = 2 * gs.getTile();
        int w = 7 * gs.getTile();
        int h = 10 * gs.getTile();
        drawSubWindow(x,y,w,h,g2);
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,30F));
        int xString = x+gs.getTile();
        int yString = y+gs.getTile();
        xString += gs.getTile();
        g2.drawString("SKILLS SUPPORT",xString,yString);
        yString += gs.getTile();
        xString = 3 * gs.getTile();
        if(gs.loopy.getSkillHave() <= 0) {
            if(gs.loopy.getChooseSkill() == 1) {
                g2.drawString("Flash",xString,yString);
                xString = 6 * gs.getTile();
                g2.drawString("<--",xString,yString);
                yString += gs.getTile();
                xString = 3 * gs.getTile();
                g2.drawString("Speed",xString,yString);
                yString += gs.getTile();
                g2.drawString("Healing",xString,yString);
            }
            else if(gs.loopy.getChooseSkill() == 2) {
                g2.drawString("Flash",xString,yString);
                yString += gs.getTile();
                g2.drawString("Speed",xString,yString);
                xString = 6 * gs.getTile();
                g2.drawString("<--",xString,yString);
                xString = 3 * gs.getTile();
                yString += gs.getTile();
                g2.drawString("Healing",xString,yString);
            }
            else if(gs.loopy.getChooseSkill() == 3) {
                g2.drawString("Flash",xString,yString);
                yString += gs.getTile();
                g2.drawString("Speed",xString,yString);
                yString += gs.getTile();
                xString = 6 * gs.getTile();
                g2.drawString("<--",xString,yString);
                xString = 3 * gs.getTile();
                g2.drawString("Healing",xString,yString);
            }
        }else {
            if(gs.loopy.getChooseSkill() == 1) {
                xString = 3 * gs.getTile();
                g2.drawString("Flash",xString,yString);
                if(gs.Map_chooseSkill.containsKey("flash")) {
                    xString = 5 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkill.get("flash").toString(),xString,yString);
                }
                xString = 6 * gs.getTile();
                g2.drawString("<--",xString,yString);
                yString += gs.getTile();
                xString = 3 * gs.getTile();
                g2.drawString("Speed",xString,yString);
                if(gs.Map_chooseSkill.containsKey("speed")) {
                    xString = 5 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkill.get("speed").toString(),xString,yString);
                }
                yString += gs.getTile();
                xString = 3 * gs.getTile();
                g2.drawString("Healing",xString,yString);
                if(gs.Map_chooseSkill.containsKey("healing")) {
                    xString = 5 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkill.get("healing").toString(),xString,yString);
                }
            }
            else if(gs.loopy.getChooseSkill() == 2) {
                xString = 3 * gs.getTile();
                g2.drawString("Flash",xString,yString);
                if(gs.Map_chooseSkill.containsKey("flash")) {
                    xString = 5 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkill.get("flash").toString(),xString,yString);
                }
                xString = 3 * gs.getTile();
                yString += gs.getTile();
                g2.drawString("Speed",xString,yString);
                xString = 6 * gs.getTile();
                g2.drawString("<--",xString,yString);
                if(gs.Map_chooseSkill.containsKey("speed")) {
                    xString = 5 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkill.get("speed").toString(),xString,yString);
                }
                yString += gs.getTile();
                xString = 3 * gs.getTile();
                g2.drawString("Healing",xString,yString);
                if(gs.Map_chooseSkill.containsKey("healing")) {
                    xString = 5 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkill.get("healing").toString(),xString,yString);
                }

            }
            else if(gs.loopy.getChooseSkill() == 3) {
                xString = 3 * gs.getTile();
                g2.drawString("Flash",xString,yString);
                if(gs.Map_chooseSkill.containsKey("flash")) {
                    xString = 5 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkill.get("flash").toString(),xString,yString);
                }
                xString = 3 * gs.getTile();
                yString += gs.getTile();
                g2.drawString("Speed",xString,yString);
                if(gs.Map_chooseSkill.containsKey("speed")) {
                    xString = 5 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkill.get("speed").toString(),xString,yString);
                }
                yString += gs.getTile();
                xString = 3 * gs.getTile();
                g2.drawString("Healing",xString,yString);
                xString = 6 * gs.getTile();
                g2.drawString("<--",xString,yString);
                if(gs.Map_chooseSkill.containsKey("healing")) {
                    xString = 5 * gs.getTile();
                    g2.drawString(gs.Map_chooseSkill.get("healing").toString(),xString,yString);
                }
            }
        }
        xString = 3 * gs.getTile();
        yString += gs.getTile();
        g2.drawString("SPACE to choose.",xString,yString);
        yString += gs.getTile();
        g2.drawString("Q to reset.",xString,yString);
        yString += gs.getTile();
        System.out.println(yString/gs.getTile());
        g2.drawString("ESC to exit.",xString,yString);
        yString += gs.getTile();
        if(!gs.Map_chooseSkill.isEmpty()) {
            for (Map.Entry<String,Integer> entry : gs.Map_chooseSkill.entrySet()) {
                if(entry.getValue() == 1) {
                    yString = 10 * gs.getTile();
                    g2.drawString("Your skill support " + (entry.getValue()) + " : " + entry.getKey() ,xString,yString);
                }else {
                    yString = 11 * gs.getTile();
                    g2.drawString("Your skill support " + (entry.getValue()) + " : " + entry.getKey() ,xString,yString);
                }
            }
        }

    }
    private void drawBuySkillSupport() {
        int x = 2 * gs.getTile();
        int y = 2 * gs.getTile();
        int w = 7 * gs.getTile();
        int h = 10 * gs.getTile();
        drawSubWindow(x,y,w,h,g2);
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,30F));
        int xString = x+gs.getTile();
        int yString = y+gs.getTile();
        xString += gs.getTile();
        g2.drawString("BUY SKILLS SUPPORT",xString,yString);
        xString = 3 * gs.getTile();
        g2.drawString("Coins : " + gs.user.getCoin(),xString,yString);
        yString += gs.getTile();

        yString += gs.getTile();
        g2.drawString("SPACE to choose.",xString,yString);
        yString += gs.getTile();
        g2.drawString("Q to reset.",xString,yString);
        yString += gs.getTile();
        System.out.println(yString/gs.getTile());
        g2.drawString("ESC to exit.",xString,yString);
    }
    private void drawDialogExitGame() {
        int x = gs.getWindowWidth()/2 - 3*gs.getTile();
        int y = 2 * gs.getTile();
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
}
