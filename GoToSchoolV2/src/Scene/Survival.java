package Scene;

import CollisionSystem.PointX;
import Entity.*;
import Main.GameState;
import Main.State;
import Main.UI;
import User.UserManager;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Survival {
    GameState gs;
    UserManager um;
    Graphics2D g2;
    UI ui;
    Font maruMonica, purisaB;
    boolean mapExist = false;
    final int BASE_NUMBER_MOBS = 10;
    int numberDay = 0;
    boolean endOfDay = false;
    int selected = 0;
    List<Integer> list = new ArrayList<Integer>();
    public Survival(UserManager um, GameState gs, UI ui) {
        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
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
    public boolean getEndOfDay()
    {
        return endOfDay;
    }
    public void setEndOfDay(boolean condition) {
        endOfDay = condition;
    }
    public int getSelected()
    {
        return selected;
    }
    public void setSelected(int number) {
        selected = number;
    }
    public void setMapExist(boolean condition)
    {
        mapExist = condition;
    }
    public boolean isMapExist()
    {
        return mapExist;
    }


    public void loadMap() {
        gs.tileM.loadMap("/maps/survival.txt", 1);
    }
    public void spawnMobs(int amount, int kind)
    {
        for(int i=0; i<amount/kind; i++)
        {
            Entity tmp = new Skeleton(gs);
            tmp.setWorldX(Math.random() % gs.getTile() * 14 + gs.getTile() * 12);
            tmp.setWorldY(Math.random() % gs.getTile() * 14 + gs.getTile() * 12);
            gs.monsters.add(tmp);
        }
    }
    public void newState()
    {
        spawnMobs((int)Math.floor(BASE_NUMBER_MOBS * ++numberDay * 1.1), 1);
    }
    private void drawEssentialInfo(Graphics2D g2)
    {
        g2.setFont(maruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,45F));
        g2.setColor(Color.white);
        g2.drawString("Day : " + numberDay, (float)0.5*gs.getTile(),(float)gs.getWindowHeight() - 20);
    }
    public void applyBlessing(int choose)
    {
        switch (choose)
        {
            case 1:
                //Increase attack speed
                break;
            case 2:
                //Increase damage
                break;
            case 3:
                //Increase health
                break;
            case 4:
                //Receive health point
                break;
            case 5:
                //Increase mana
                break;
            case 6:
                //Receive mana point
                break;
        }
    }

    public void chooseBlessing(Graphics2D g2)
    {
        Color c = new Color(0,0,0,180);
        g2.setColor(c);
        g2.fillRect(0, 0 ,gs.getWindowWidth(), gs.getWindowHeight());

        int x = gs.getWindowWidth() / 10 * gs.getTile(),
                y = gs.getWindowHeight() / 10 * gs.getTile() * 3,
                width = gs.getWindowWidth() / 10 * gs.getTile() * 3,
                height = gs.getWindowHeight() / 10 * gs.getTile() * 4;
        Color c1 = new Color(60, 40, 40);
        g2.setColor(c1);
        int gap = gs.getTile() *2;
        g2.fillRect(x, y ,width, height);
        g2.fillRect(x + width + gap, y ,width, height);
        g2.fillRect(x + (width + gap) * 2, y ,width, height);

        //Default text color
        Color c2 = new Color(220, 210, 200);
        //Hover text color
        Color c3 = new Color(255, 255, 255);

        g2.setFont(maruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));
        for(int i=0; i<list.size(); i++)
        {
            switch (list.get(i))
            {
                case 1:
                    if(selected == i+1)
                    {
                        g2.setColor(c3);
                    }
                    else
                    {
                        g2.setColor(c2);
                    }
                    g2.drawString("Increase attack speed" , x + (width + gap) * i, y + gs.getTile());
                    g2.drawString("Amount: 10%" , x + gs.getTile() + (width + gap) * i, y + 2 *gs.getTile());
                    //Increase attack speed
                    break;
                case 2:
                    if(selected == i+1)
                    {
                        g2.setColor(c3);
                    }
                    else
                    {
                        g2.setColor(c2);
                    }
                    //Increase damage
                    g2.drawString("Increase damage" , x + (width + gap) * i, y + gs.getTile());
                    g2.drawString("Amount: 2%" , x + gs.getTile() + (width + gap) * i, y + 2 *gs.getTile());
                    break;
                case 3:
                    if(selected == i+1)
                    {
                        g2.setColor(c3);
                    }
                    else
                    {
                        g2.setColor(c2);
                    }
                    //Increase health
                    g2.drawString("Increase health" , x + (width + gap) * i, y + gs.getTile());
                    g2.drawString("Amount: 10" , x + gs.getTile() + (width + gap) * i, y + 2 *gs.getTile());
                    break;
                case 4:
                    if(selected == i+1)
                    {
                        g2.setColor(c3);
                    }
                    else
                    {
                        g2.setColor(c2);
                    }
                    //Receive health point
                    g2.drawString("Receive health point" , x + (width + gap) * i, y + gs.getTile());
                    g2.drawString("Amount: 1" , x + gs.getTile() + (width + gap) * i, y + 2 *gs.getTile());
                    break;
                case 5:
                    if(selected == i+1)
                    {
                        g2.setColor(c3);
                    }
                    else
                    {
                        g2.setColor(c2);
                    }
                    //Increase mana
                    g2.drawString("Increase mana" , x + (width + gap) * i, y + gs.getTile());
                    g2.drawString("Amount: 10" , x + gs.getTile() + (width + gap) * i, y + 2 *gs.getTile());
                    break;
                case 6:
                    if(selected == i+1)
                    {
                        g2.setColor(c3);
                    }
                    else
                    {
                        g2.setColor(c2);
                    }
                    //Receive mana point
                    g2.drawString("Receive mana point" , x + (width + gap) * i, y + gs.getTile());
                    g2.drawString("Amount: 1" , x + gs.getTile() + (width + gap) * i, y + 2 *gs.getTile());
                    break;
            }
        }

    }
    public void update() {

        if(!endOfDay)
        {
            gs.updateBattle();
        }
        if(gs.monsters.isEmpty())
        {
            //Appear table of the blessing at the end of day
            endOfDay = true;
            //Create random list of blessing
            list.clear();
            Random ran = new Random();
            do
            {
                int ranNumber = new Random().nextInt(6-1 + 1) + 1;
                if(!list.contains(ranNumber))
                {
                    list.add(ranNumber);
                }
            }
            while(list.size()<3);
            //Spawn mobs , begin new day
            newState();
        }
        // add power up :
        // 1. Increase Damage
        // 2. Increase speed
        // 3. Increase health / mana
        // 4. Choose another skill (remove)
        // 5. Give health / mana point
        // 6. meet shopkeeper after 5 round
        //  - buy item { last stand, invest(each round you will earn income), lock target, gain health regeneration, abilities)
        //  - fresh item
        //  - play gambling

    }

    public void draw(Graphics2D g2) {
        // MAP
        this.g2 = g2;
        gs.tileM.draw(g2);

        // ENTITY
        if(gs.player != null) {
            gs.player.draw(g2);
        }

        if(endOfDay)
        {
            chooseBlessing(g2);
        }
        else
        {
            for(Entity monster : gs.monsters) {
                if(monster != null) {
                    monster.draw(g2);
                }
            }
            for(Entity skill : gs.skillAttacks) {
                if (skill != null) {
                    skill.draw(g2);
                }
            }
            drawEssentialInfo(g2);
        }
    }
}
