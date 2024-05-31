package Scene;

import AttackSkill.ATTACK_SKILL;
import Entity.*;
import Main.GameState;
import Main.State;
import Main.UI;
import User.UserManager;
import Weapon.NormalAttack;
import Weapon.NormalAttack2;
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
import java.util.Objects;
import java.util.Random;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Survival {
    GameState gs;
    UserManager um;
    Graphics2D g2;
    UI ui;
    Font maruMonica, purisaB;
    private boolean mapExist = false;
    final int BASE_NUMBER_MOBS = 4;
    private int numberDay = 5;
    private boolean endOfDay = false, meeting = false;
    private int selected = 1;
    private int maxShopSlot = 6;
    private int maxShopItem = 7;
    private int maxBlessingSlot = 3;
    private int maxBlessing = 6;
    private int dayToGetToShop = 5;
    private List<Integer> abilities = Stream.of(2, 3, 4, 5, 0, 1, 0).collect(Collectors.toCollection(ArrayList::new)); // Q, E, R, F, Huge Fund(Passive), Final Reserves(Passive), right click
    private List<Integer> listBlessing = new ArrayList<Integer>();
    private List<Integer> listItem = new ArrayList<Integer>();
    private List<BufferedImage> item_img = new ArrayList<BufferedImage>();

    public Survival(UserManager um, GameState gs, UI ui) {
        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);

            item_img.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/huge-fund.png"))));
            item_img.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/final-reserves.png"))));
            item_img.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapon/iconArrow.png"))));
            item_img.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapon/iconCircle.png"))));
            item_img.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapon/iconMultiAr.png"))));
            item_img.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapon/iconMoon.png"))));
            item_img.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapon/iconPurple.png"))));
            item_img.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapon/iconFlash.png"))));
            item_img.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapon/iconGhost.png"))));
            item_img.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapon/iconRegen.png"))));
            item_img.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapon/iconFireBall.png"))));
//            Icon              i
//            huge fund         0
//            final reserves    1
//            arrow	            2
//            circle	        3
//            multiArr	        4
//            moon	            5
//            flash            	7
//            ghost	            8
//            regen	            9
//            fireBall          10
        } catch (FontFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.gs = gs;
        this.um = um;
        this.ui = ui;
    }


    public List<Integer> getListBlessing() {
        return listBlessing;
    }

    public List<Integer> getListItem() {
        return listItem;
    }

    public int getMaxShopSlot() {
        return maxShopSlot;
    }

    public void setMaxShopSlot(int amount) {
        maxShopSlot = amount;
    }

    public int getMaxBlessingSlot() {
        return maxBlessingSlot;
    }

    public void setMaxBlessingSlot(int amount) {
        maxBlessingSlot = amount;
    }

    public boolean getMeeting() {
        return meeting;
    }

    public void setMeeting(boolean condition) {
        meeting = condition;
    }

    public boolean getEndOfDay() {
        return endOfDay;
    }

    public void setEndOfDay(boolean condition) {
        endOfDay = condition;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int number) {
        selected = number;
    }

    public void setMapExist(boolean condition) {
        mapExist = condition;
    }

    public boolean isMapExist() {
        return mapExist;
    }

    public List<Integer> getAbilities() {
        return abilities;
    }
    public List<BufferedImage> getItem_img(){return item_img;}

    public void loadMap() {
        gs.stopMusic();
        gs.playMusic(11);
        gs.tileM.loadMap("/maps/survival_1.txt", 1);
        gs.tileM.loadMap("/maps/survival_2.txt", 2);
        gs.player.setWorldX(30 * gs.getTile());
        gs.player.setWorldY(22 * gs.getTile());
    }

    private void spawnMobs(int amount, int kind) {
        for (int i = 0; i < amount / kind; i++) {
            Entity tmp = new Skeleton(gs);
            tmp.setWorldX(Math.random() % gs.getTile() * 21 + gs.getTile() * 17);
            tmp.setWorldY(Math.random() % gs.getTile() * 21 + gs.getTile() * 17);
            gs.monsters.add(tmp);
        }
        for(int i = 0; i < amount / kind; i++)
        {
            Entity tmp = new Slime(gs);
            tmp.setWorldX(Math.random() % gs.getTile() * 22 + gs.getTile() * 27);
            tmp.setWorldY(Math.random() % gs.getTile() * 39 + gs.getTile() * 35);
            gs.monsters.add(tmp);
        }
    }

    private void spawnBoss()
    {
        if(numberDay % 20 == 0)
        {
            //spawn another boss or more boss
            for(int i=0; i<numberDay/10; i++)
            {
                Entity tmp = new Boss(gs);
                tmp.setWorldX(Math.random() % gs.getTile() * 50 + gs.getTile() * 45);
                tmp.setWorldY(Math.random() % gs.getTile() * 55 + gs.getTile() * 50);
                gs.monsters.add(tmp);
            }
        }
        else
        {
            // Golem boss
            Entity tmp = new Boss(gs);
            tmp.setWorldX(Math.random() % gs.getTile() * 50 + gs.getTile() * 45);
            tmp.setWorldY(Math.random() % gs.getTile() * 55 + gs.getTile() * 50);
            gs.monsters.add(tmp);
        }
    }


    private void newState() {
        spawnMobs((int) Math.floor(BASE_NUMBER_MOBS * ++numberDay * 0.6), 2);
        if(numberDay % 10 == 0)
        {
            spawnBoss();
        }
    }

    private void drawEssentialInfo(Graphics2D g2) {
        g2.setFont(maruMonica.deriveFont(Font.BOLD, 45F));
        g2.setColor(Color.white);
        g2.drawString("Day : " + numberDay, (float) 0.5 * gs.getTile(), (float) gs.getWindowHeight() - 20);
        g2.drawString("Enemies : " + gs.monsters.size(), gs.getWindowWidth()*7/16, gs.getTile() * 3/4);
    }

    public void applyBlessing(int choose) {
        switch (choose) {
            case 1:
                //Increase attack speed by 2%
                gs.aController.addBonusTime_Normal(2);
                break;
            case 2:
                //Increase 2 damage
                gs.player.setDamage(gs.player.getDamage() + 2);
                break;
            case 3:
                //Reduce skill cooldown by 2%
                gs.aController.addBonusTime_Skill(2);
                break;
            case 4:
                //Heal 100hp
                gs.player.setHP(100);
                break;
            case 5:
                //Increase 1 movement speed
                gs.player.setSpeed(gs.player.getSpeed() + 1);
                if (gs.player.getSpeed() > 30) {
                    gs.player.setSpeed(30);
                }
                break;
            case 6:
                //Sacrifice a half hp for damage
                gs.player.setHP(gs.player.getHP() / 2);
                gs.player.setDamage(gs.player.getDamage() + 4);
                break;
        }
    }

    private void chooseBlessing(Graphics2D g2) {
        Color c = new Color(0, 0, 0, 180);
        g2.setColor(c);
        //draw dark mark
        g2.fillRect(0, 0, gs.getWindowWidth(), gs.getWindowHeight());

        int x = gs.getTile() * 5,
                y = gs.getTile() * 4,
                width = gs.getTile() * 4,
                height = gs.getTile() * 5;
        int gap = gs.getTile();
        //draw choices table
        for (int i = 0; i < listBlessing.size(); i++) {
            ui.drawSubWindow(x + (width + gap) * i, y, width, height, g2);
            //Mouse Hover item
            if (gs.mouseHandle.getWorldX() > x + (width + gap) * i && gs.mouseHandle.getWorldX() < x + (width + gap) * i + width) {
                if (gs.mouseHandle.getWorldY() > y && gs.mouseHandle.getWorldY() < y + height) {
                    selected = i + 1;
                    if (gs.mouseHandle.isMouseLeftClick()) {
                        gs.playSE(17);
                        setEndOfDay(false);
                        //Give effect
                        applyBlessing(listBlessing.get(selected - 1));
                        gs.mouseHandle.setMouseLeftClick(false);
                    }
                }
            }
        }
        //Default text color
        Color c2 = new Color(255, 255, 255);
        //Hover text color
        Color c3 = new Color(220, 210, 100);

        g2.setFont(maruMonica.deriveFont(Font.BOLD, 20F));
        x += gs.getTile() / 2;
        y += gs.getTile() / 2;
        //Draw item inside case if true == hover
        for (int i = 0; i < listBlessing.size(); i++) {
            switch (listBlessing.get(i)) {
                case 1:
                    if (selected == i + 1) {
                        g2.setColor(c3);
                    } else {
                        g2.setColor(c2);
                    }
                    g2.drawString("Increase attack speed", x + (width + gap) * i, y + gs.getTile());
                    g2.drawString("Amount: 2%", x + (int) (gs.getTile() * 0.5) + (width + gap) * i, y + 2 * gs.getTile());
                    //Increase attack speed
                    break;
                case 2:
                    if (selected == i + 1) {
                        g2.setColor(c3);
                    } else {
                        g2.setColor(c2);
                    }
                    //Increase damage
                    g2.drawString("Increase normal damage", x + (width + gap) * i, y + gs.getTile());
                    g2.drawString("Amount: 2", x + (int) (gs.getTile() * 0.5) + (width + gap) * i, y + 2 * gs.getTile());
                    break;
                case 3:
                    if (selected == i + 1) {
                        g2.setColor(c3);
                    } else {
                        g2.setColor(c2);
                    }
                    //Reduce skill cooldown
                    g2.drawString("Reduce skill cooldown", x + (width + gap) * i, y + gs.getTile());
                    g2.drawString("Amount: 2%", x + (int) (gs.getTile() * 0.5) + (width + gap) * i, y + 2 * gs.getTile());
                    break;
                case 4:
                    if (selected == i + 1) {
                        g2.setColor(c3);
                    } else {
                        g2.setColor(c2);
                    }
                    //Heal 100 hp
                    g2.drawString("Heal heath point", x + (width + gap) * i, y + gs.getTile());
                    g2.drawString("Amount: 100", x + (int) (gs.getTile() * 0.5) + (width + gap) * i, y + 2 * gs.getTile());
                    break;
                case 5:
                    if (selected == i + 1) {
                        g2.setColor(c3);
                    } else {
                        g2.setColor(c2);
                    }
                    //Increase 1 movement speed
                    g2.drawString("Increase movement speed", x + (width + gap) * i, y + gs.getTile());
                    g2.drawString("Amount: 1", x + (int) (gs.getTile() * 0.5) + (width + gap) * i, y + 2 * gs.getTile());
                    break;
                case 6:
                    if (selected == i + 1) {
                        g2.setColor(c3);
                    } else {
                        g2.setColor(c2);
                    }
                    //Sacrifice a half hp for damage
                    g2.drawString("Sacrifice a half hp for damage", x + (width + gap) * i, y + gs.getTile());
                    g2.drawString("Amount hp: " + gs.player.getHP() / 2, x + (int) (gs.getTile() * 0.5) + (width + gap) * i, y + 2 * gs.getTile());
                    g2.drawString("Amount damage: 4", x + (int) (gs.getTile() * 0.5) + (width + gap) * i, y + 3 * gs.getTile());
                    break;
            }
        }

    }

    private void meetShopkeeper() {
        //Draw background
        Color backgroundC = new Color(0, 0, 0, 180);
        g2.setColor(backgroundC);
        //draw dark mark
        g2.fillRect(0, 0, gs.getWindowWidth(), gs.getWindowHeight());
        int xShop = gs.getWindowWidth() / 8 - gs.getTile() * 2;
        int yShop = gs.getWindowHeight() / 8;
        int widthShop = gs.getWindowWidth() * 3 / 4;
        int heightShop = gs.getWindowHeight() * 3 / 4;
        ui.drawSubWindow(xShop, yShop, widthShop, heightShop, g2); // Draw shop's table
        ui.drawSubWindow(xShop + widthShop, yShop, widthShop / 3 - gs.getTile() , heightShop, g2); // Info's item

        g2.setFont(ui.getMaruMonica().deriveFont(Font.BOLD, 30F));

        g2.drawString("Coins : " + gs.user.getSurvivalCoin(), xShop + gs.getTile(), yShop + gs.getTile()); // Show coin
        g2.drawString("ESC to Exit", xShop + widthShop - gs.getTile() * 5/2, yShop + gs.getTile()); // Show coin
        int gap = gs.getTile();
        int squareSize = Math.min(((widthShop - xShop) - 4 * gap) / 3, ((heightShop - yShop) - 3 * gap) / 2);
        g2.setFont(ui.getMaruMonica().deriveFont(Font.BOLD, 20F));
        Color hoverColor = new Color(220, 210, 100);
        Color defaultC = new Color(255, 255, 255);
        //Mouse Click esc
        if (gs.mouseHandle.getWorldX() > xShop + widthShop - gs.getTile() * 5/2 && gs.mouseHandle.getWorldX() < xShop + widthShop - gs.getTile() * 5/2 + squareSize * 3 / 4) {
            if (gs.mouseHandle.getWorldY() > yShop + gap / 2 && gs.mouseHandle.getWorldY() < yShop + gap / 2 + squareSize/4) {
                if (gs.mouseHandle.isMouseLeftPress()) {
                    gs.playSE(17);
                    meeting = false;
                }
            }
        }
        for (int row = 0; row < maxShopSlot / 3; row++) {
            for (int col = 0; col < maxShopSlot / 2; col++) {
                int x = xShop + gap * 4 + col * (squareSize + gap);
                int y = yShop + gap * 2 + row * (squareSize + gap);
                ui.drawSubWindow(x, y, squareSize, squareSize, g2);
                g2.drawImage(item_img.get(listItem.get(col + (row * 3))-1), x + gap /2, y + gap / 2, gs.getTile() * 3 / 2, gs.getTile() * 3 / 2, null);
                //Mouse Hover item
                if (gs.mouseHandle.getWorldX() > x && gs.mouseHandle.getWorldX() < x + squareSize) {
                    if (gs.mouseHandle.getWorldY() > y && gs.mouseHandle.getWorldY() < y + squareSize) {
                        selected = col + (row * 3) + 1;
                        if (gs.mouseHandle.isMouseLeftClick()) {
                            gs.playSE(17);
                            //Give effect
                            giveItem(listItem.get(selected - 1));
                            gs.mouseHandle.setMouseLeftClick(false);
                        }
                    }
                }
                //Draw item inside case if true == hover
                switch (listItem.get(col + (row * 3))) {
                    case 1:
                        if (selected == col + (row * 3) + 1) {
                            g2.setColor(hoverColor);
                            g2.drawString(String.valueOf(numberDay * 100), x + gap / 4, y + squareSize /2 + gap / 2);
                            g2.drawString("Give you 10% of your current coin", xShop + widthShop+ gap/2, yShop+ gap/2);
                            g2.drawString("when finished a day", xShop + widthShop + gap/2, yShop+ gap/2 + gs.getFont().getSize() *3/2);
                        } else {
                            g2.setColor(defaultC);
                        }
                        if(abilities.get(4) == 1)
                        {
                            g2.drawString("Owned", x + gap * 2, y + squareSize + gap / 2);
                        }
                        g2.drawString("Hedge Fund", x + gap / 4, y + squareSize + gap / 2);
                        break;
                    case 2:
                        if (selected == col + (row * 3) + 1) {
                            g2.setColor(hoverColor);
                            g2.drawString(String.valueOf(numberDay * 250), x + gap / 4, y + squareSize /2 + gap / 2);
                            g2.drawString("Give a second change when hp", xShop + widthShop+ gap/2, yShop+ gap/2);
                            g2.drawString("drop to 0", xShop + widthShop + gap/2, yShop+ gap/2 + gs.getFont().getSize() *3/2);
                        } else {
                            g2.setColor(defaultC);
                        }
                        if(abilities.get(5) == 1)
                        {
                            g2.drawString("Owned", x + gap * 2, y + squareSize + gap / 2);
                        }
                        g2.drawString("Final Reserves", x + gap / 4, y + squareSize + gap / 2);
                        break;
                    case 3:
                        if (selected == col + (row * 3) + 1) {
                            g2.setColor(hoverColor);
                            g2.drawString(String.valueOf(BaseArrowLight.LEVER * 30 + 10), x + gap / 4, y + squareSize /2 + gap / 2);
                            g2.drawString("Current Damage: " + BaseArrowLight.damage[BaseArrowLight.LEVER], xShop + widthShop+ gap/2, yShop+ gap/2);
                            g2.drawString("Current Level:" + BaseArrowLight.LEVER, xShop + widthShop + gap/2, yShop+ gap/2 + gs.getFont().getSize() *3/2);
                        } else {
                            g2.setColor(defaultC);
                        }
                        if (abilities.contains(2)) {
                            g2.drawString("Update ArrowLight: " + (BaseArrowLight.LEVER + 1), x + gap / 4, y + squareSize + gap / 2);
                        } else {
                            g2.drawString("Buy ArrowLight", x + gap / 4, y + squareSize + gap / 2);
                        }
                        break;
                    case 4:
                        if (selected == col + (row * 3) + 1) {
                            g2.setColor(hoverColor);
                            g2.drawString(String.valueOf(BaseCircleFire.LEVER * 30 + 10), x + gap / 4, y + squareSize /2 + gap / 2);
                            g2.drawString("Current Damage: " + BaseCircleFire.damage[BaseCircleFire.LEVER], xShop + widthShop+ gap/2, yShop+ gap/2);
                            g2.drawString("Current Level:" + BaseCircleFire.LEVER, xShop + widthShop + gap/2, yShop+ gap/2 + gs.getFont().getSize() *3/2);
                        } else {
                            g2.setColor(defaultC);
                        }
                        if (abilities.contains(3)) {
                            g2.drawString("Update CircleFire: " + (BaseCircleFire.LEVER + 1), x + gap / 4, y + squareSize + gap / 2);
                        } else {
                            g2.drawString("Buy CircleFire", x + gap / 4, y + squareSize + gap / 2);
                        }
                        break;
                    case 5:
                        if (selected == col + (row * 3) + 1) {
                            g2.setColor(hoverColor);
                            g2.drawString(String.valueOf(BaseMultiArrow.LEVER * 30 + 10), x + gap / 4, y + squareSize /2 + gap / 2);
                            g2.drawString("Current Damage: " + BaseMultiArrow.damage[BaseMultiArrow.LEVER], xShop + widthShop+ gap/2, yShop+ gap/2);
                            g2.drawString("Current Level:" + BaseMultiArrow.LEVER, xShop + widthShop + gap/2, yShop+ gap/2 + gs.getFont().getSize() *3/2);
                        } else {
                            g2.setColor(defaultC);
                        }
                        if (abilities.contains(5)) {
                            g2.drawString("Update MultiArrow: " + (BaseMultiArrow.LEVER + 1), x + gap / 4, y + squareSize + gap / 2);
                        } else {
                            g2.drawString("Buy MultiArrow", x + gap / 4, y + squareSize + gap / 2);
                        }
                        break;
                    case 6:
                        if (selected == col + (row * 3) + 1) {
                            g2.setColor(hoverColor);
                            g2.drawString(String.valueOf(BaseMoonLight.LEVER * 30 + 10), x + gap / 4, y + squareSize /2 + gap / 2);
                            g2.drawString("Current Damage: " + BaseMoonLight.damage[BaseMoonLight.LEVER], xShop + widthShop+ gap/2, yShop+ gap/2);
                            g2.drawString("Current Level:" + BaseMoonLight.LEVER, xShop + widthShop + gap/2, yShop+ gap/2 + gs.getFont().getSize() *3/2);
                        } else {
                            g2.setColor(defaultC);
                        }
                        if (abilities.contains(4)) {
                            g2.drawString("Update MoonLight: " + (BaseMoonLight.LEVER + 1), x + gap / 4, y + squareSize + gap / 2);
                        } else {
                            g2.drawString("Buy MoonLight", x + gap / 4, y + squareSize + gap / 2);
                        }
                        break;
                    case 7:
                        if (selected == col + (row * 3) + 1) {
                            g2.setColor(hoverColor);
                            g2.drawString(String.valueOf(1000), x + gap / 4, y + squareSize /2 + gap / 2);
                            g2.drawString("Unlock right click ability", xShop + widthShop+ gap/2, yShop+ gap/2);
                            g2.drawString("Name: Purple Ball", xShop + widthShop + gap/2, yShop+ gap/2 + gs.getFont().getSize()*3/2);
                            g2.drawString("Damage: " + ((IntSupplier)() -> {Entity n = new NormalAttack2(gs); return n.getDamage();}).getAsInt(), xShop + widthShop + gap/2, yShop+ gap/2 + gs.getFont().getSize()*6/2);
                        } else {
                            g2.setColor(defaultC);
                        }
                        g2.drawString("Purple Ball", x + gap / 4, y + squareSize + gap / 2);
                        if (abilities.contains(6)) {
                            g2.drawString("Owned", x + gap * 2, y + squareSize + gap / 2);
                        }
                        break;
                }
            }
        }
    }

    public void giveItem(int choose) {
        int xShop = gs.getWindowWidth() / 8;
        int yShop = gs.getWindowHeight() / 8;
        int widthShop = gs.getWindowWidth() * 3 / 4;
        int heightShop = gs.getWindowHeight() * 3 / 4;
        int gap = gs.getTile();
        int squareSize = Math.min(((widthShop - xShop) - 4 * gap) / 3, ((heightShop - yShop) - 3 * gap) / 2);
        switch (choose) {
            case 1:
                if (gs.user.getSurvivalCoin() >= numberDay * 100 && abilities.get(4) != 1) {
                    //Give huge fund
                    gs.user.setSurvivalCoin(gs.user.getSurvivalCoin() - numberDay * 100);
                    abilities.set(4, 1);
                }
                break;
            case 2:
                if (gs.user.getSurvivalCoin() >= numberDay * 250 && abilities.get(5) != 1) {
                    //Give Final Reserves
                    gs.user.setSurvivalCoin(gs.user.getSurvivalCoin() - numberDay * 250);
                    abilities.set(5, 1);
                }
                break;
            case 3:
                if (gs.user.getSurvivalCoin() >= BaseArrowLight.LEVER * 30 + 10) {
                    //Update ArrowLight
                    if(BaseArrowLight.LEVER + 1 < 50)
                    {
                        gs.user.setSurvivalCoin(gs.user.getSurvivalCoin() - BaseArrowLight.LEVER * 30);
                        BaseArrowLight.LEVER++;
                    }
                }
                break;
            case 4:
                if (gs.user.getSurvivalCoin() >= BaseCircleFire.LEVER * 30 + 10) {
                    //Update CircleFIre
                    if(BaseCircleFire.LEVER + 1 < 50)
                    {
                        gs.user.setSurvivalCoin(gs.user.getSurvivalCoin() - BaseCircleFire.LEVER * 30);
                        BaseCircleFire.LEVER++;
                    }
                }
                break;
            case 5:
                if (gs.user.getSurvivalCoin() >= BaseMultiArrow.LEVER * 30 + 10) {
                    //Update MultiArrow
                    if(BaseMultiArrow.LEVER + 1 < 50)
                    {
                        gs.user.setSurvivalCoin(gs.user.getSurvivalCoin() - BaseMultiArrow.LEVER * 30);
                        BaseMultiArrow.LEVER++;
                    }
                }
                break;
            case 6:
                if (gs.user.getSurvivalCoin() >= BaseMoonLight.LEVER * 30 + 10) {
                    //Update MoonLight
                    if(BaseMoonLight.LEVER + 1 < 50)
                    {
                        gs.user.setSurvivalCoin(gs.user.getSurvivalCoin() - BaseMoonLight.LEVER * 30);
                        BaseMoonLight.LEVER++;
                    }
                }
                break;
            case 7:
                if (gs.user.getSurvivalCoin() >=  1000 && !abilities.contains(6)) {
                    //Unlock Water ball
                    abilities.set(6, 6);
                    gs.user.setSurvivalCoin(gs.user.getSurvivalCoin() - 1000);
                }
                break;
        }
    }

    public void update() {
        if(gs.changeScene.getNumberDraw() == 2) {
            if (!isMapExist()) {
                loadMap();
                setMapExist(true);
            }
        }
        if(abilities.get(5) == 1 && gs.player.getHP() <=0)
        {
            abilities.set(5, 0);
            gs.player.setHP(100);
        }
        if(gs.keyHandle.isAccessReturnLoopy() || gs.player.getHP() <= 0){
            setMapExist(false);
            gs.keyHandle.resetAllKeyMoving();
            gs.changeScene.setAlive(true);
            gs.changeState = true;
            if(gs.changeScene.getNumberDraw() == 2)
            {
                numberDay--;
                gs.state = State.LOOPY;
                gs.monsters.clear();
                gs.player.setWorldX(20 * gs.getTile());
                gs.player.setWorldY((42 * gs.getTile()));
                gs.keyHandle.setAccessReturnLoopy(false);
            }
        }
        if (!(endOfDay || meeting)) {
            gs.updateBattle();
        }
        if (gs.monsters.isEmpty()) {
            if(abilities.get(2) == 1)
            {
                gs.user.setSurvivalCoin((int)(gs.user.getSurvivalCoin() * 11/10));
            }
            if (numberDay != 0) {
                //Appear table of the blessing at the end of day
                endOfDay = true;
                //Create random list of blessing
                listBlessing.clear();
                Random ran = new Random();
                do {
                    int ranNumber = new Random().nextInt(maxBlessing) + 1;
                    if (!listBlessing.contains(ranNumber)) {
                        listBlessing.add(ranNumber);
                    }
                }
                while (listBlessing.size() < maxBlessingSlot);

                if (numberDay % dayToGetToShop == 0) {
                    setMeeting(true);
                    //Create random list of item for shop
                    listItem.clear();
                    do {
                        int ranNumber = new Random().nextInt(maxShopItem) + 1;
                        if (!listItem.contains(ranNumber)) {
                            listItem.add(ranNumber);
                        }
                    }
                    while (listItem.size() < maxShopSlot);
                }
            }
            //Spawn mobs, begin new day
            newState();
        }
    }

    public void draw(Graphics2D g2) {
        // MAP
        this.g2 = g2;
        gs.tileM.draw(g2);

        // ENTITY
        if (gs.player != null) {
            gs.player.draw(g2);
        }

        if (endOfDay) {
            chooseBlessing(g2);
        } else if (meeting) {
            meetShopkeeper();
        } else {
            gs.drawBattle(g2);
            drawEssentialInfo(g2);
        }
    }
}

