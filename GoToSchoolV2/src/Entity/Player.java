package Entity;

import Main.GameState;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Player extends Entity{

    public Player(GameState gs) {
        super(gs);
        init();
    }

    @Override
    public void update() {

        if(gs.keyHandle.isLeftPress()) {
            worldX -=  speed ;
        }
        if(gs.keyHandle.isRightPress()) {
            worldX +=  speed ;
        }
        if(gs.keyHandle.isDownPress()) {
            worldY +=  speed ;
        }
        if(gs.keyHandle.isUpPress()) {
            worldY -=  speed ;
        }


        solidArea.x = (int) worldX;
        solidArea.y = (int) worldY;
    }

    @Override
    public void draw(Graphics2D g2) {
        if(alive) {
            g2.drawRect(solidArea.x,solidArea.y,solidArea.width,solidArea.height);
            g2.setColor(Color.white);
            g2.fillRect(solidArea.x,solidArea.y,solidArea.width,solidArea.height);
        }
    }
    @Override
    public void init() {
        worldX = 100;
        worldY = 100;
        type = TYPE.PLAYER;
        hp = 100;
        speed = 5;
        baseDamage = 1;
        solidArea = new Rectangle((int) worldX,(int) worldY,30,30);
    }

    @Override
    public Rectangle getBounds() {
        return solidArea;
    }

}
