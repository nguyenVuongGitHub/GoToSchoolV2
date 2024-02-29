package Entity;

import Main.GameState;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Player extends Entity{

    public Player(GameState gs) {
        super(gs);
    }

    @Override
    public void update() {
        Random rand = new Random();
        double angleInDegrees = rand.nextDouble() * 360; // Random angle in degrees from 0 to 360
        double angleInRadians = Math.toRadians(angleInDegrees); // Convert degrees to radians
        double radius = rand.nextDouble() * 180; // Random radius from 0 to 180

        worldX +=  (radius * Math.cos(angleInRadians) * 0.01) ;
        worldY +=  (radius * Math.sin(angleInRadians) * 0.01) ;
        solidArea.x = (int) worldX;
        solidArea.y = (int) worldY;
        solidArea.height = 30;
        solidArea.width = 30;

    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawRect(solidArea.x,solidArea.y,solidArea.width,solidArea.height);
        if(collision) {
            g2.setColor(Color.green);
        }else{
            g2.setColor(Color.white);
        }
        g2.fillRect(solidArea.x,solidArea.y,solidArea.width,solidArea.height);
    }
    @Override
    public void init() {
        Random rand = new Random();
        worldX = rand.nextInt() % 500 + 500;
        worldY = rand.nextInt() % 500 + 500;
    }

    @Override
    public Rectangle getBounds() {
        return solidArea;
    }

}
