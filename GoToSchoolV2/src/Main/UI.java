package Main;

import AttackSkill.*;
import SPSkill.Flicker;
import SPSkill.Restore;
import SPSkill.Sprint;
import Weapon.NormalAttack;
import baseAttributeSkills.BaseArrowLight;
import baseAttributeSkills.BaseCircleFire;
import baseAttributeSkills.BaseMoonLight;
import baseAttributeSkills.BaseMultiArrow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UI {
    GameState gs;
    Graphics2D g2;
    Font maruMonica, purisaB;
    BufferedImage HP;
    boolean isDrawNotice = false;
    boolean isDrawExitGame = false;
    boolean isPlayerSay = false;
    boolean isDrawChooseSkillsSupport = false;

    boolean isSoundPlayed = false;

    boolean isDrawUpgradeSkill = false;

    boolean isDrawChooseSkillsAttack = false;
    private List<BufferedImage> key_img = new ArrayList<BufferedImage>();

    public UI(GameState gs) {
        this.gs = gs;
        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
            HP = ImageIO.read(getClass().getResourceAsStream("/user/hp.png"));
            key_img.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/Q.png"))));
            key_img.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/E.png"))));
            key_img.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/R.png"))));
            key_img.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/F.png"))));
            key_img.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/1.png"))));
            key_img.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/2.png"))));
            key_img.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/space.png"))));
//           Icon   i
//            Q	    0
//            E	    1
//            R	    2
//            F	    3
//            1	    4
//            2	    5
//            spc	6
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
            drawSurvival();
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
    private void drawSubTitleSkillSupport(String nameSkill, int x, int y, int w, int h) {
        drawSubWindow(x,y,w,h,g2);
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,30F));
        int xString = x + gs.getTile()/2;
        int yString = y + gs.getTile();
        g2.drawString(nameSkill,xString,yString);
        yString += gs.getTile();
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,23F));
        if(nameSkill.equals("Flicker")) {
            g2.drawString("Teleports a certain distance",xString,yString);
            yString += gs.getTile();
            g2.drawString("in the target direction.",xString,yString);
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
    private void drawSubTitleSkillAttack(String nameSkill, int x, int y, int w, int h) {
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
            g2.drawString( BaseArrowLight.damage[BaseArrowLight.LEVER] + " damage.",xString,yString);
            yString += gs.getTile();
            g2.drawString(BaseArrowLight.timeReduce[BaseArrowLight.LEVER] + " s Cooldown.",xString,yString);
        }else if(nameSkill.equals("MultiArrowLight")) {
            xString = 15 * gs.getTile();
            if(BaseMultiArrow.LEVER+1 == BaseMultiArrow.MAX_LEVER) {
                g2.drawString("lever MAX",xString,yString);
            }else {
                g2.drawString("lever " + BaseMultiArrow.LEVER,xString,yString);
            }
            xString = x + gs.getTile()/2;
            yString += gs.getTile();
            g2.drawString(BaseMultiArrow.damage[BaseMultiArrow.LEVER] +" damage.",xString,yString);
            yString += gs.getTile();
            g2.drawString(BaseMultiArrow.timeReduce[BaseMultiArrow.LEVER] + " s Cooldown.",xString,yString);
        }else if(nameSkill.equals("MoonLight")) {
            xString = 15 * gs.getTile();
            if(BaseMoonLight.LEVER+1 == BaseMoonLight.MAX_LEVER) {
                g2.drawString("lever MAX",xString,yString);
            }else {
                g2.drawString("lever " + BaseMoonLight.LEVER,xString,yString);
            }
            xString = x + gs.getTile()/2;
            yString += gs.getTile();
            g2.drawString(BaseMoonLight.damage[BaseMoonLight.LEVER]+ " damage.",xString,yString);
            yString += gs.getTile();
            g2.drawString(BaseMoonLight.timeReduce[BaseMoonLight.LEVER] + " s Cooldown.",xString,yString);
        }else if(nameSkill.equals("CircleFire")) {
            xString = 15 * gs.getTile();
            if(BaseCircleFire.LEVER+1 == BaseCircleFire.MAX_LEVER) {
                g2.drawString("lever MAX",xString,yString);
            }else {
                g2.drawString("lever " + BaseCircleFire.LEVER,xString,yString);
            }
            xString = x + gs.getTile()/2;
            yString += gs.getTile();
            g2.drawString(BaseCircleFire.damage[BaseCircleFire.LEVER]+ " damage.",xString,yString);
            yString += gs.getTile();
            g2.drawString(BaseCircleFire.timeReduce[BaseCircleFire.LEVER] + " s Cooldown.",xString,yString);
        }
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,30F));
    }

    private void drawChooseSkillAttack() {
        //Draw background
        Color backgroundC = new Color(0, 0, 0, 180);
        g2.setColor(backgroundC);
        //draw dark mark
        g2.fillRect(0, 0, gs.getWindowWidth(), gs.getWindowHeight());

        int xTable = gs.getWindowWidth() / 8 - gs.getTile() * 2;
        int yTable = gs.getWindowHeight() / 8;
        int wTable = gs.getWindowWidth() * 3 / 4;
        int hTable = gs.getWindowHeight() * 3 / 4;
        int gap = gs.getTile();
        int xString = xTable + gap*2;
        int yString = yTable + gap;

        drawSubWindow(xTable,yTable,wTable,hTable,g2); // draw table
        drawSubWindow(xTable + wTable,yTable,wTable / 3 - gs.getTile(),hTable, g2); // draw sub table
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,30F));
        int squareSize = Math.min(((wTable - xTable) - 4 * gap) / 3, ((hTable - yTable) - 3 * gap) / 2);
        Color hoverColor = new Color(220, 210, 100);
        Color defaultC = new Color(255, 255, 255);

        for(int row = 0; row < 4; row ++) {
            int x = xTable + gap * 3 + row * (squareSize + gap) - gap/2;
            int y = yTable + gap * 2;

            // if not select -> default css
            if(!gs.loopy.getSelectedAttack()[row]) {
                drawSubWindow(x, y, squareSize, squareSize, g2);
                g2.drawImage(gs.survival.getItem_img().get(row + 2),
                        x + gap /2,
                        y + gap / 2,
                        gs.getTile() * 3 / 2, gs.getTile() * 3 / 2, null);
            }
            else {
                y -= gap/3;
                drawSubWindow(x, y, squareSize, squareSize, g2,5,Color.black,hoverColor,5,5,10,10,25,25);
                g2.drawImage(gs.survival.getItem_img().get(row + 2),
                        x + gap /2,
                        y + gap / 2,
                        gs.getTile() * 3 / 2,
                        gs.getTile() * 3 / 2,
                        null);
            }

            // Mouse hover item
            if (gs.mouseHandle.getWorldX() > x && gs.mouseHandle.getWorldX() < x + squareSize) {
                if (gs.mouseHandle.getWorldY() > y && gs.mouseHandle.getWorldY() < y + squareSize) {
                    switch (row) {
                        case 0 -> drawSubTitleSkillAttack("ArrowLight",xTable + wTable,yTable,wTable / 3 - gs.getTile(),hTable);
                        case 1 -> drawSubTitleSkillAttack("CircleFire",xTable + wTable,yTable,wTable / 3 - gs.getTile(),hTable);
                        case 2 -> drawSubTitleSkillAttack("MultiArrowLight",xTable + wTable,yTable,wTable / 3 - gs.getTile(),hTable);
                        case 3 -> drawSubTitleSkillAttack("MoonLight",xTable + wTable,yTable,wTable / 3 - gs.getTile(),hTable);
                    }

                    // change the effect
                    drawSubWindow(x, y, squareSize, squareSize, g2,5,Color.black,hoverColor,5,5,10,10,25,25);
                    g2.drawImage(gs.survival.getItem_img().get(row + 2),
                            x + gap / 2,
                            y + gap / 2,
                            gs.getTile() * 3 / 2,
                            gs.getTile() * 3 / 2,
                            null);

                        // click to item
                        if (gs.mouseHandle.isMouseLeftClick()) {
                            gs.playSE(17);
                            gs.mouseHandle.setMouseLeftClick(false);

                            if (gs.loopy.getSkillAttackHave() < 2) {
                                // able select
                                if (!gs.loopy.getSelectedAttack()[row]) {
                                    gs.loopy.setSelectedAttack(true, row);
                                    gs.loopy.setSkillAttackHave(gs.loopy.getSkillAttackHave() + 1);
                                }
                            }
                            // add to Map
                            if (gs.loopy.getSelectedAttack()[0]) {
                                if (!gs.Map_chooseSkillAttack.containsKey("ArrowLight")) {
                                    gs.Map_chooseSkillAttack.put("ArrowLight", gs.loopy.getSkillAttackHave());
                                }
                            } else {
                                gs.Map_chooseSkillAttack.remove("ArrowLight");
                            }
                            if (gs.loopy.getSelectedAttack()[1]) {
                                if (!gs.Map_chooseSkillAttack.containsKey("CircleFire")) {
                                    gs.Map_chooseSkillAttack.put("CircleFire", gs.loopy.getSkillAttackHave());
                                }
                            } else {
                                gs.Map_chooseSkillAttack.remove("CircleFire");
                            }
                            if (gs.loopy.getSelectedAttack()[2]) {
                                if (!gs.Map_chooseSkillAttack.containsKey("MultiArrowLight")) {
                                    gs.Map_chooseSkillAttack.put("MultiArrowLight", gs.loopy.getSkillAttackHave());
                                }
                            } else {
                                gs.Map_chooseSkillAttack.remove("MultiArrowLight");
                            }
                            if (gs.loopy.getSelectedAttack()[3]) {
                                if (!gs.Map_chooseSkillAttack.containsKey("MoonLight")) {
                                    gs.Map_chooseSkillAttack.put("MoonLight", gs.loopy.getSkillAttackHave());
                                }
                            } else {
                                gs.Map_chooseSkillAttack.remove("MoonLight");
                            }
                        }
                    }
                }


                // right click to reset
                if (gs.mouseHandle.isMouseRightClick()) {
                    gs.playSE(17);
                    gs.mouseHandle.setMouseRightClick(false);
                    for (int i = 0; i < 4; i++) {
                        gs.loopy.setSelectedAttack(false, i);
                    }
                    gs.Map_chooseSkillAttack.clear();
                    gs.loopy.setSkillAttackHave(0);
                }
            }
            // draw sub icon
            if (gs.Map_chooseSkillAttack.containsKey("ArrowLight")) {
                int value = gs.Map_chooseSkillAttack.get("ArrowLight") + 3;
                g2.drawImage(key_img.get(value),
                        xTable + gap * 4 - gap - 10,
                        yTable + gap * 2,
                        gs.getTile(),
                        gs.getTile(), null);
            }
            if (gs.Map_chooseSkillAttack.containsKey("CircleFire")) {
                int value = gs.Map_chooseSkillAttack.get("CircleFire") + 3;
                g2.drawImage(key_img.get(value),
                        xTable + gap * 4 + (squareSize + gap) - gap - 10,
                        yTable + gap * 2,
                        gs.getTile(),
                        gs.getTile(), null);
            }
            if (gs.Map_chooseSkillAttack.containsKey("MultiArrowLight")) {
                int value = gs.Map_chooseSkillAttack.get("MultiArrowLight") + 3;
                g2.drawImage(key_img.get(value),
                        xTable + gap * 4 + 2 * (squareSize + gap) - gap - 10,
                        yTable + gap * 2,
                        gs.getTile(),
                        gs.getTile(), null);
            }
            if (gs.Map_chooseSkillAttack.containsKey("MoonLight")) {
                int value = gs.Map_chooseSkillAttack.get("MoonLight") + 3;
                g2.drawImage(key_img.get(value),
                        xTable + gap * 4 + 3 * (squareSize + gap) - gap - 10,
                        yTable + gap * 2,
                        gs.getTile(),
                        gs.getTile(), null);
            }

        g2.setColor(defaultC);
        String title = "Choose Skill Attack";
        g2.drawString(title,xString,  yString + 10);

        String subTitle1 = "Left click to select. ";
        g2.drawString(subTitle1,xString,yString+gap*5 + 10);
        String subTitle2 = "Right click to reset. ";
        g2.drawString(subTitle2,xString,yString+gap*6 + 10);
        String subTitle3 = "The maximum skill to choose is 2.";
        g2.drawString(subTitle3,xString,yString+gap*7 + 10);
        String subTitle4 = "Esc to exit.";
        g2.drawString(subTitle4,xString,yString+gap*8 + 10);
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
        //Draw background
        Color backgroundC = new Color(0, 0, 0, 180);
        g2.setColor(backgroundC);
        //draw dark mark
        g2.fillRect(0, 0, gs.getWindowWidth(), gs.getWindowHeight());

        int xTable = gs.getWindowWidth() / 8 - gs.getTile() * 2;
        int yTable = gs.getWindowHeight() / 8;
        int wTable = gs.getWindowWidth() * 3 / 4;
        int hTable = gs.getWindowHeight() * 3 / 4;
        int gap = gs.getTile();
        int xString = xTable + gap*2;
        int yString = yTable + gap;

        drawSubWindow(xTable,yTable,wTable,hTable,g2); // draw table
        drawSubWindow(xTable + wTable,yTable,wTable / 3 - gs.getTile(),hTable, g2); // draw sub table
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,30F));
        int squareSize = Math.min(((wTable - xTable) - 4 * gap) / 3, ((hTable - yTable) - 3 * gap) / 2);
        Color hoverColor = new Color(220, 210, 100);
        Color defaultC = new Color(255, 255, 255);

        for(int row = 0; row < 3; row ++) {
            int x = xTable + gap * 4 + row * (squareSize + gap);
            int y = yTable + gap * 2;

            // if not select -> default css
            if(!gs.loopy.getSelectedSupport()[row]) {
                drawSubWindow(x, y, squareSize, squareSize, g2);
                g2.drawImage(gs.survival.getItem_img().get(row + 7),
                        x + gap /2,
                        y + gap / 2,
                        gs.getTile() * 3 / 2, gs.getTile() * 3 / 2, null);
            }
            else {
                y -= gap/3;
                drawSubWindow(x, y, squareSize, squareSize, g2,5,Color.black,hoverColor,5,5,10,10,25,25);
                g2.drawImage(gs.survival.getItem_img().get(row + 7),
                        x + gap /2,
                        y + gap / 2,
                        gs.getTile() * 3 / 2,
                        gs.getTile() * 3 / 2,
                        null);
            }

            // Mouse hover item
            if (gs.mouseHandle.getWorldX() > x && gs.mouseHandle.getWorldX() < x + squareSize) {
                if (gs.mouseHandle.getWorldY() > y && gs.mouseHandle.getWorldY() < y + squareSize) {
                    switch (row) {
                        case 0 -> drawSubTitleSkillSupport("Flicker",xTable + wTable,yTable,wTable / 3 - gs.getTile(),hTable);
                        case 1 -> drawSubTitleSkillSupport("Sprint",xTable + wTable,yTable,wTable / 3 - gs.getTile(),hTable);
                        case 2 -> drawSubTitleSkillSupport("Restore",xTable + wTable,yTable,wTable / 3 - gs.getTile(),hTable);
                    }

                    // change the effect
                    drawSubWindow(x, y, squareSize, squareSize, g2,5,Color.black,hoverColor,5,5,10,10,25,25);
                    g2.drawImage(gs.survival.getItem_img().get(row + 7),
                            x + gap / 2,
                            y + gap / 2,
                            gs.getTile() * 3 / 2,
                            gs.getTile() * 3 / 2,
                            null);

                    // click to item
                    if (gs.mouseHandle.isMouseLeftClick()) {
                        gs.playSE(17);
                        gs.mouseHandle.setMouseLeftClick(false);

                        if(gs.loopy.getSkillSuportHave() < 2) {
                            // able select
                            if(!gs.loopy.getSelectedSupport()[row]) {
                                gs.loopy.setSelectedSupport(true,row);
                                gs.loopy.setSkillSuportHave(gs.loopy.getSkillSuportHave() + 1);
                            }
                        }
                        // add to Map
                        if(gs.loopy.getSelectedSupport()[0]) {
                            if(!gs.Map_chooseSkillSupport.containsKey("Flicker")) {
                                gs.Map_chooseSkillSupport.put("Flicker",gs.loopy.getSkillSuportHave());
                            }
                        }else {
                            gs.Map_chooseSkillSupport.remove("Flicker");
                        }
                        if(gs.loopy.getSelectedSupport()[1]) {
                            if(!gs.Map_chooseSkillSupport.containsKey("Sprint")) {
                                gs.Map_chooseSkillSupport.put("Sprint",gs.loopy.getSkillSuportHave());
                            }
                        }
                        else {
                            gs.Map_chooseSkillSupport.remove("Sprint");
                        }
                        if(gs.loopy.getSelectedSupport()[2]) {
                            if(!gs.Map_chooseSkillSupport.containsKey("Restore")) {
                                gs.Map_chooseSkillSupport.put("Restore",gs.loopy.getSkillSuportHave());
                            }
                        }else {
                            gs.Map_chooseSkillSupport.remove("Restore");
                        }
                    }
                }
            }
            // right click to reset
            if(gs.mouseHandle.isMouseRightClick()) {
                gs.playSE(17);
                gs.mouseHandle.setMouseRightClick(false);
                for(int i = 0; i < 3; i++) {
                    gs.loopy.setSelectedSupport(false,i);
                }
                gs.Map_chooseSkillSupport.clear();
                gs.loopy.setSkillSuportHave(0);
            }
        }
        // draw sub icon
        if(gs.Map_chooseSkillSupport.containsKey("Flicker")) {
            int value = gs.Map_chooseSkillSupport.get("Flicker") + 3;
            g2.drawImage(key_img.get(value),
                    xTable + gap * 4 + gap / 2 - 15,
                    yTable + gap * 2 + gap /2 - 35,
                    gs.getTile(),
                    gs.getTile(), null);
        }
        if(gs.Map_chooseSkillSupport.containsKey("Sprint")) {
            int value = gs.Map_chooseSkillSupport.get("Sprint") + 3;
            g2.drawImage(key_img.get(value),
                    xTable + gap * 4 + (squareSize + gap) + gap /2 - 15,
                    yTable + gap * 2 + gap /2 - 35,
                    gs.getTile(),
                    gs.getTile(), null);
        }
        if(gs.Map_chooseSkillSupport.containsKey("Restore")) {
            int value = gs.Map_chooseSkillSupport.get("Restore") + 3;
            g2.drawImage(key_img.get(value),
                    xTable + gap * 4 + 2 * (squareSize + gap) + gap /2 - 15,
                    yTable + gap * 2 + gap /2 - 35,
                    gs.getTile(),
                    gs.getTile(), null);
        }

        g2.setColor(defaultC);
        String title = "Choose Skill Support";
        g2.drawString(title,xString,  yString + 10);

        String subTitle1 = "Left click to select. ";
        g2.drawString(subTitle1,xString,yString+gap*5 + 10);
        String subTitle2 = "Right click to reset. ";
        g2.drawString(subTitle2,xString,yString+gap*6 + 10);
        String subTitle3 = "The maximum skill to choose is 2.";
        g2.drawString(subTitle3,xString,yString+gap*7 + 10);
        String subTitle4 = "Esc to exit.";
        g2.drawString(subTitle4,xString,yString+gap*8 + 10);
    }
    private void drawUpgradeSkill() {
        //Draw background
        Color backgroundC = new Color(0, 0, 0, 180);
        g2.setColor(backgroundC);
        //draw dark mark
        g2.fillRect(0, 0, gs.getWindowWidth(), gs.getWindowHeight());

        int xTable = gs.getWindowWidth() / 8 - gs.getTile() * 2;
        int yTable = gs.getWindowHeight() / 8;
        int wTable = gs.getWindowWidth() * 3 / 4;
        int hTable = gs.getWindowHeight() * 3 / 4;
        int gap = gs.getTile();
        int xString = xTable + gap*2;
        int yString = yTable + gap;

        drawSubWindow(xTable,yTable,wTable,hTable,g2); // draw table
        drawSubWindow(xTable + wTable,yTable,wTable / 3 - gs.getTile(),hTable, g2); // draw sub table
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,30F));
        int squareSize = Math.min(((wTable - xTable) - 4 * gap) / 3, ((hTable - yTable) - 3 * gap) / 2);
        Color hoverColor = new Color(220, 210, 100);
        Color defaultC = new Color(255, 255, 255);

        for(int row = 0; row < 4; row ++) {
            int x = xTable + gap * 3 + row * (squareSize + gap) - gap/2;
            int y = yTable + gap * 2;

            // if not select -> default css
            if(!gs.loopy.getSelectedUpgrade()[row]) {
                drawSubWindow(x, y, squareSize, squareSize, g2);
                g2.drawImage(gs.survival.getItem_img().get(row + 2),
                        x + gap /2,
                        y + gap / 2,
                        gs.getTile() * 3 / 2, gs.getTile() * 3 / 2, null);
            }
            else {
                drawSubWindow(x, y, squareSize, squareSize, g2,5,Color.black,hoverColor,5,5,10,10,25,25);
                g2.drawImage(gs.survival.getItem_img().get(row + 2),
                        x + gap /2,
                        y + gap / 2,
                        gs.getTile() * 3 / 2,
                        gs.getTile() * 3 / 2,
                        null);
            }

            // Mouse hover item
            if (gs.mouseHandle.getWorldX() > x && gs.mouseHandle.getWorldX() < x + squareSize) {
                if (gs.mouseHandle.getWorldY() > y && gs.mouseHandle.getWorldY() < y + squareSize) {
                    switch (row) {
                        case 0 -> drawSubTitleSkillAttack("ArrowLight",xTable + wTable,yTable,wTable / 3 - gs.getTile(),hTable);
                        case 1 -> drawSubTitleSkillAttack("CircleFire",xTable + wTable,yTable,wTable / 3 - gs.getTile(),hTable);
                        case 2 -> drawSubTitleSkillAttack("MultiArrowLight",xTable + wTable,yTable,wTable / 3 - gs.getTile(),hTable);
                        case 3 -> drawSubTitleSkillAttack("MoonLight",xTable + wTable,yTable,wTable / 3 - gs.getTile(),hTable);
                    }

                    // change the effect
                    drawSubWindow(x, y, squareSize, squareSize, g2,5,Color.black,hoverColor,5,5,10,10,25,25);
                    g2.drawImage(gs.survival.getItem_img().get(row + 2),
                            x + gap / 2,
                            y + gap / 2,
                            gs.getTile() * 3 / 2,
                            gs.getTile() * 3 / 2,
                            null);

                    // click to item
                    if (gs.mouseHandle.isMouseLeftClick()) {
                        gs.playSE(17);
                        gs.mouseHandle.setMouseLeftClick(false);
                        if(!gs.loopy.getSelectedUpgrade()[row]) {
                            if(gs.user.getCoin() > gs.user.getCoinNeedUpgrade()) {
                                gs.loopy.setSelectedUpgrade(true,row);
                                gs.user.setCoin(gs.user.getCoin() - gs.user.getCoinNeedUpgrade());
                                gs.user.setCoinNeedUpgrade(gs.user.getCoinNeedUpgrade() + 10);
                            }
                        }
                    }
                    if(gs.loopy.getSelectedUpgrade()[0]) {
                        gs.loopy.setSelectedUpgrade(false,0);
                        if(BaseArrowLight.LEVER < 50)
                            BaseArrowLight.LEVER++;
                    }
                    else if(gs.loopy.getSelectedUpgrade()[1]) {
                        gs.loopy.setSelectedUpgrade(false,1);
                        if(BaseCircleFire.LEVER < 50)
                            BaseCircleFire.LEVER++;
                    }
                    else if(gs.loopy.getSelectedUpgrade()[2]) {
                        gs.loopy.setSelectedUpgrade(false,2);
                        if(BaseMultiArrow.LEVER < 50)
                            BaseMultiArrow.LEVER++;
                    }
                    else if(gs.loopy.getSelectedUpgrade()[3]) {
                        gs.loopy.setSelectedUpgrade(false,3);
                        if(BaseMoonLight.LEVER < 50)
                            BaseMoonLight.LEVER++;
                    }
                }
            }
        }

        g2.setColor(defaultC);
        String title = "Update Skill Attack";
        g2.drawString(title,xString,  yString + 10);
        String subTitle1 = "Left click to select. ";
        g2.drawString(subTitle1,xString,yString+gap*5 + 10);
        String subTitle2 = "Coin remaining: " + gs.user.getCoin();
        g2.drawString(subTitle2,xString,yString+gap*6 + 10);
        String subTitle3 = "Coin need update: " + gs.user.getCoinNeedUpgrade();
        g2.drawString(subTitle3,xString,yString+gap*7 + 10);
        String subTitle4 = "Esc to exit.";
        g2.drawString(subTitle4,xString,yString+gap*8 + 10);
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
        if(gs.campaign.getChoose() + gs.campaign.getStep() == 5
                || gs.campaign.getChoose() + gs.campaign.getStep() == 6
                || gs.campaign.getChoose() + gs.campaign.getStep() == 8) {
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
        yString = y + gs.getTile()/2 + 10;
        w = gs.getTile();
        h = gs.getTile();
        Color black_bg = new Color(0, 0, 0, 180);
        Color normalC = new Color(255,255,255);
        // skill 1
        x = 9 * gs.getTile();
        xString = x + gs.getTile()/4 + 7;
        drawSubWindow(x,y,w,h,g2);
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,20F));
        if(gs.loopy.getSkillAttackHave() >= 1) {
            for (Map.Entry<String,Integer> entry : gs.Map_chooseSkillAttack.entrySet()) {
                if(entry.getValue() == 1) {
                    switch (entry.getKey()) {
                        case "ArrowLight" -> {
                            g2.drawImage(gs.survival.getItem_img().get(2), x, y, w, h, null);
                            if (ArrowLight.TIME_COUNT_DOWN_ATTACK > 0) {
                                g2.setColor(black_bg);
                                g2.fillRect(x,y,w,h);
                                g2.setColor(normalC);
                                g2.drawString(String.valueOf(ArrowLight.TIME_COUNT_DOWN_ATTACK), xString, yString);
                            }
                        }
                        case "MultiArrowLight" -> {
                            g2.drawImage(gs.survival.getItem_img().get(4), x, y, w, h, null);
                            if (MultiArrow.TIME_COUNT_DOWN_ATTACK > 0) {
                                g2.setColor(black_bg);
                                g2.fillRect(x,y,w,h);
                                g2.setColor(normalC);
                                g2.drawString(String.valueOf(MultiArrow.TIME_COUNT_DOWN_ATTACK), xString, yString);
                            }
                        }
                        case "MoonLight" -> {
                            g2.drawImage(gs.survival.getItem_img().get(5), x, y, w, h, null);
                            if (MoonLight.TIME_COUNT_DOWN_ATTACK > 0) {
                                g2.setColor(black_bg);
                                g2.fillRect(x,y,w,h);
                                g2.setColor(normalC);
                                g2.drawString(String.valueOf(MoonLight.TIME_COUNT_DOWN_ATTACK), xString, yString);
                            }
                        }
                        case "CircleFire" -> {
                            g2.drawImage(gs.survival.getItem_img().get(3), x, y, w, h, null);
                            if (CircleFire.TIME_COUNT_DOWN_ATTACK > 0) {
                                g2.setColor(black_bg);
                                g2.fillRect(x,y,w,h);
                                g2.setColor(normalC);
                                g2.drawString(String.valueOf(CircleFire.TIME_COUNT_DOWN_ATTACK), xString, yString);
                            }
                        }
                    }
                }
            }
        }
        g2.drawImage(key_img.get(4), x,y - gs.getTile() /2,gs.getTile(), gs.getTile(), null);
        // skill 2
        x = 10 * gs.getTile();
        xString = x + gs.getTile()/4 + 7;
        drawSubWindow(x,y,w,h,g2);
        if(gs.loopy.getSkillAttackHave() == 2) {
            for (Map.Entry<String,Integer> entry : gs.Map_chooseSkillAttack.entrySet()) {
                if(entry.getValue() == 2) {
                    switch (entry.getKey()) {
                        case "ArrowLight" -> {
                            g2.drawImage(gs.survival.getItem_img().get(2), x, y, w, h, null);
                            if (ArrowLight.TIME_COUNT_DOWN_ATTACK > 0) {
                                g2.setColor(black_bg);
                                g2.fillRect(x,y,w,h);
                                g2.setColor(normalC);
                                g2.drawString(String.valueOf(ArrowLight.TIME_COUNT_DOWN_ATTACK), xString, yString);
                            }
                        }
                        case "MultiArrowLight" -> {
                            g2.drawImage(gs.survival.getItem_img().get(4), x, y, w, h, null);
                            if (MultiArrow.TIME_COUNT_DOWN_ATTACK > 0) {
                                g2.setColor(black_bg);
                                g2.fillRect(x,y,w,h);
                                g2.setColor(normalC);
                                g2.drawString(String.valueOf(MultiArrow.TIME_COUNT_DOWN_ATTACK), xString, yString);
                            }
                        }
                        case "MoonLight" -> {
                            g2.drawImage(gs.survival.getItem_img().get(5), x, y, w, h, null);
                            if (MoonLight.TIME_COUNT_DOWN_ATTACK > 0) {
                                g2.setColor(black_bg);
                                g2.fillRect(x,y,w,h);
                                g2.setColor(normalC);
                                g2.drawString(String.valueOf(MoonLight.TIME_COUNT_DOWN_ATTACK), xString, yString);
                            }
                        }
                        case "CircleFire" -> {
                            g2.drawImage(gs.survival.getItem_img().get(3), x, y, w, h, null);
                            if (CircleFire.TIME_COUNT_DOWN_ATTACK > 0) {
                                g2.setColor(black_bg);
                                g2.fillRect(x,y,w,h);
                                g2.setColor(normalC);
                                g2.drawString(String.valueOf(CircleFire.TIME_COUNT_DOWN_ATTACK), xString, yString);
                            }
                        }
                    }
                }
            }
        }
        g2.drawImage(key_img.get(5), x,y - gs.getTile() /2,gs.getTile(), gs.getTile(), null);
        // nomal attack
        x = 11 * gs.getTile();
        xString = x + gs.getTile()/4 + 7;
        drawSubWindow(x,y,w,h,g2);
        g2.drawImage(gs.survival.getItem_img().get(10), x, y, w, h, null);
        if(NormalAttack.TIME_COUNT_DOWN_ATTACK > 0) {
            g2.setColor(black_bg);
            g2.fillRect(x,y,w,h);
            g2.setColor(normalC);
            g2.drawString(String.valueOf(NormalAttack.TIME_COUNT_DOWN_ATTACK), xString, yString);
        }
        g2.drawImage(key_img.get(6), x,y - gs.getTile() /2,gs.getTile(), gs.getTile(), null);
        // skill support 1
        x = 12 * gs.getTile();
        xString = x + gs.getTile()/4 + 7;
        drawSubWindow(x,y,w,h,g2);
        if(gs.loopy.getSkillSuportHave() >= 1) {
            for (Map.Entry<String,Integer> entry : gs.Map_chooseSkillSupport.entrySet()) {
                if(entry.getValue() == 1) {
                    switch (entry.getKey()) {
                        case "Flicker" -> {
                            g2.drawImage(gs.survival.getItem_img().get(7), x, y, w, h, null);
                            if (Flicker.TIME_COUNT_DOWN > 0) {
                                g2.setColor(black_bg);
                                g2.fillRect(x,y,w,h);
                                g2.setColor(normalC);
                                g2.drawString(String.valueOf(Flicker.TIME_COUNT_DOWN), xString, yString);
                            }
                        }
                        case "Sprint" -> {
                            g2.drawImage(gs.survival.getItem_img().get(8), x, y, w, h, null);
                            if (Sprint.TIME_COUNT_DOWN > 0) {
                                g2.setColor(black_bg);
                                g2.fillRect(x,y,w,h);
                                g2.setColor(normalC);
                                g2.drawString(String.valueOf(Sprint.TIME_COUNT_DOWN), xString, yString);
                            }
                        }
                        case "Restore" -> {
                            g2.drawImage(gs.survival.getItem_img().get(9), x, y, w, h, null);
                            if (Restore.TIME_COUNT_DOWN > 0) {
                                g2.setColor(black_bg);
                                g2.fillRect(x,y,w,h);
                                g2.setColor(normalC);
                                g2.drawString(String.valueOf(Restore.TIME_COUNT_DOWN), xString, yString);
                            }
                        }
                    }
                }
            }
        }
        g2.drawImage(key_img.get(0), x,y - gs.getTile() /2,gs.getTile(), gs.getTile(), null);
        // skill support 2
        x = 13 * gs.getTile();
        xString = x + gs.getTile()/4 + 7;
        drawSubWindow(x,y,w,h,g2);
        if(gs.loopy.getSkillSuportHave() == 2) {
            for (Map.Entry<String,Integer> entry : gs.Map_chooseSkillSupport.entrySet()) {
                if(entry.getValue() == 2) {
                    switch (entry.getKey()) {
                        case "Flicker" -> {
                            g2.drawImage(gs.survival.getItem_img().get(7), x, y, w, h, null);
                            if (Flicker.TIME_COUNT_DOWN > 0) {
                                g2.setColor(black_bg);
                                g2.fillRect(x,y,w,h);
                                g2.setColor(normalC);
                                g2.drawString(String.valueOf(Flicker.TIME_COUNT_DOWN), xString, yString);
                            }
                        }
                        case "Sprint" -> {
                            g2.drawImage(gs.survival.getItem_img().get(8), x, y, w, h, null);
                            if (Sprint.TIME_COUNT_DOWN > 0) {
                                g2.setColor(black_bg);
                                g2.fillRect(x,y,w,h);
                                g2.setColor(normalC);
                                g2.drawString(String.valueOf(Sprint.TIME_COUNT_DOWN), xString, yString);
                            }
                        }
                        case "Restore" -> {
                            g2.drawImage(gs.survival.getItem_img().get(9), x, y, w, h, null);
                            if (Restore.TIME_COUNT_DOWN > 0) {
                                g2.setColor(black_bg);
                                g2.fillRect(x,y,w,h);
                                g2.setColor(normalC);
                                g2.drawString(String.valueOf(Restore.TIME_COUNT_DOWN), xString, yString);
                            }
                        }
                    }
                }
            }
        }
        g2.drawImage(key_img.get(1), x,y - gs.getTile() /2,gs.getTile(), gs.getTile(), null);
    }
    private void drawFrameHpPlayer() {
        int x = gs.getTile()/2;
        int y = gs.getTile()/2;
        int w = 128;
        int h = 64;
        g2.drawImage(HP,x,y,w,h,null);
    }
    private void drawHpPlayer() {
        int x = gs.getTile()/2 + 5;
        int y = gs.getTile()/2 + 5;
        int w = 115;
        int h = 20;
        double currentHp = ((double)gs.player.getHP()/100) * w;
        g2.setColor(Color.red);
        g2.drawRect(x,y,w,h);
        g2.fillRect(x,y,(int)currentHp,h);
    }

    private void drawSurvival()
    {
        drawHpPlayer();
        drawFrameHpPlayer();
        int x,y,w,h;
        int xString, yString;

        y = 12 * gs.getTile();
        yString = y + gs.getTile()*3/4;
        w = gs.getTile();
        h = gs.getTile();
        g2.setFont(getMaruMonica().deriveFont(Font.BOLD,25F));
        Color black_bg = new Color(0, 0, 0, 180);
        Color normalC = new Color(255,255,255);
        //Skill 1
        for(int i=0; i<4; i++) //Four square skill
        {
            x = (9 + i) * gs.getTile();
            xString = x + gs.getTile() / 2;
            g2.drawImage(gs.survival.getItem_img().get(gs.survival.getAbilities().get(i)), x, y, w, h, null);
            switch (gs.survival.getAbilities().get(i)){
                case 2-> {
                    if (!(ArrowLight.TIME_COUNT_DOWN_ATTACK <= 0)) {
                        g2.setColor(black_bg);
                        g2.fillRect(x,y,w,h);
                        g2.setColor(normalC);
                        g2.drawString(String.valueOf(ArrowLight.TIME_COUNT_DOWN_ATTACK), xString, yString);
                    }
                }
                case 3 -> {
                    if (!(CircleFire.TIME_COUNT_DOWN_ATTACK <= 0)) {
                        g2.setColor(black_bg);
                        g2.fillRect(x,y,w,h);
                        g2.setColor(normalC);
                        g2.drawString(String.valueOf(CircleFire.TIME_COUNT_DOWN_ATTACK), xString, yString);
                    }
                }
                case 4 -> {
                    if (!(MultiArrow.TIME_COUNT_DOWN_ATTACK <= 0)) {
                        g2.setColor(black_bg);
                        g2.fillRect(x,y,w,h);
                        g2.setColor(normalC);
                        g2.drawString(String.valueOf(MultiArrow.TIME_COUNT_DOWN_ATTACK), xString, yString);
                    }
                }
                case 5 -> {
                    if (!(MoonLight.TIME_COUNT_DOWN_ATTACK <= 0)) {
                        g2.setColor(black_bg);
                        g2.fillRect(x,y,w,h);
                        g2.setColor(normalC);
                        g2.drawString(String.valueOf(MoonLight.TIME_COUNT_DOWN_ATTACK), xString, yString);
                    }
                }
            }
            g2.drawImage(key_img.get(i), x,y - gs.getTile() /2,gs.getTile(), gs.getTile(), null);
        }
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
