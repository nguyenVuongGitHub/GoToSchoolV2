package Entity;

import CollisionSystem.PointX;
import Main.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
public class Player extends Entity{

    public Player(GameState gs) {
        super(gs);
        init();

        screenX = gs.getWindowWidth()/2 - (gs.getTile()/2);
        screenY = gs.getWindowHeight()/2 - (gs.getTile()/2);
    }

    @Override
    public void init() {
        worldX = 400;
        worldY = 500;
        type = TYPE.PLAYER;
        hp = 100;
        speed = 5;
        damage = 1;
        direction = "down";
        solidArea = new Rectangle((int) worldX, (int) worldY,64,64);
        getPlayerImage();
        setPolygonVertices();
    }

    public void getPlayerImage() {
        try {

            up1 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_1.png"));

            up2 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_3.png"));
            up4 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_4.png"));
            up5 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_5.png"));
            up6 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_6.png"));

            down1 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_2.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_3.png"));
            down4 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_4.png"));
            down5 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_5.png"));
            down6 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_6.png"));

            left1 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_3.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_4.png"));
            left5 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_5.png"));
            left6 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_6.png"));

            right1 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_3.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_4.png"));
            right5 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_5.png"));
            right6 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_6.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {

        if(gs.keyHandle.isUpPress() || gs.keyHandle.isDownPress()
                || gs.keyHandle.isLeftPress() || gs.keyHandle.isRightPress()) {
            if(gs.keyHandle.isLeftPress()) {
                direction = "left";
                worldX -=  speed ;
            }
            else if(gs.keyHandle.isRightPress()) {
                direction= "right";
                worldX +=  speed ;
            }
            else if(gs.keyHandle.isDownPress()) {
                direction = "down";
                worldY +=  speed ;
            }
            else if(gs.keyHandle.isUpPress()) {
                direction = "up";
                worldY -=  speed ;
            }

            spriteCounter++;
            if(spriteCounter > 10) {
                if(spriteNum == 1) {
                    spriteNum = 2;
                }
                else if(spriteNum == 2) {
                    spriteNum = 3;
                }
                else if(spriteNum == 3) {
                    spriteNum = 4;
                }
                else if(spriteNum == 4) {
                    spriteNum = 5;
                }
                else if(spriteNum == 5) {
                    spriteNum = 6;
                }
                else if(spriteNum == 6) {
                    spriteNum = 1;
                }
            }
            solidArea.x = (int) worldX;
            solidArea.y = (int) worldY;
            clearVertices();
            setPolygonVertices();
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch(direction) {
            case "up":
                if(spriteNum == 1) {
                    image = up1;
                }
                if(spriteNum == 2) {
                    image = up2;
                }
                if(spriteNum == 3) {
                    image = up3;
                }
                if(spriteNum == 4) {
                    image = up4;
                }
                if(spriteNum == 5) {
                    image = up5;
                }
                if(spriteNum == 6) {
                    image = up6;
                }
                break;
            case "down":
                if(spriteNum == 1) {
                    image = down1;
                }
                if(spriteNum == 2) {
                    image = down2;
                }
                if(spriteNum == 3) {
                    image = down3;
                }
                if(spriteNum == 4) {
                    image = down4;
                }
                if(spriteNum == 5) {
                    image = down5;
                }
                if(spriteNum == 6) {
                    image = down6;
                }
                break;
            case "left":
                if(spriteNum == 1) {
                    image = left1;
                }
                if(spriteNum == 2) {
                    image = left2;
                }
                if(spriteNum == 3) {
                    image = left3;
                }
                if(spriteNum == 4) {
                    image = left4;
                }
                if(spriteNum == 5) {
                    image = left5;
                }
                if(spriteNum == 6) {
                    image = left6;
                }
                break;
            case "right":
                if(spriteNum == 1) {
                    image = right1;
                }
                if(spriteNum == 2) {
                    image = right2;
                }
                if(spriteNum == 3) {
                    image = right3;
                }
                if(spriteNum == 4) {
                    image = right4;
                }
                if(spriteNum == 5) {
                    image = right5;
                }
                if(spriteNum == 6) {
                    image = right6;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gs.getTile(), gs.getTile(), null);
    }

    @Override
    public Rectangle getBounds() {
        return solidArea;
    }

}
