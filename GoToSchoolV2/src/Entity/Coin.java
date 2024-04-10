package Entity;

import Main.GameState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Coin extends Entity{


    boolean move = true;
    int TimeMoving = 25;
    int countTimeMoving = 0;
    int timeExit = 200;
    int countTimeExit = 0;

    public Coin(GameState gs) {
        super(gs);
        init();

    }
    @Override
    public void init() {
        speed = 2;
        type = TYPE.OBJECT;
        solidArea = new Rectangle(0,0,gs.getTile(),gs.getTile());
        getImage();
        clearVertices();
        setPolygonVertices();
    }
    @Override
    public void update() {
        move();

        countTimeExit++;
        if(countTimeExit >= timeExit) {
            alive = false;
        }
        spriteCounter++;
        if(spriteCounter > 6) {
            spriteCounter = 0;
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
        clearVertices();
        setPolygonVertices();
    }

    @Override
    public void draw(Graphics2D g2) {
        if(alive) {
            if(spriteNum == 1) {
                currentImage = up1;
            }
            if(spriteNum == 2) {
                currentImage = up2;
            }
            if(spriteNum == 3) {
                currentImage = up3;
            }
            if(spriteNum == 4) {
                currentImage = up4;
            }
            if(spriteNum == 5) {
                currentImage = up5;
            }
            if(spriteNum == 6) {
                currentImage = up6;
            }
            screenX = (int) (worldX - gs.player.getWorldX() + gs.player.getScreenX());
            screenY = (int) (worldY - gs.player.getWorldY() + gs.player.getScreenY());
            g2.drawImage(currentImage, screenX, screenY, gs.getTile(), gs.getTile(), null);
        }
    }
    private void move() {
        if(distanceToPlayer(gs.player) <= 200) {
            angleTarget = anglePlayerAndCoin();
            speed = 8;
            move = true;
        }else {
            countTimeMoving++;
            if(countTimeMoving >= TimeMoving) {
                move = false;
            }
        }
        if(move) {
            worldX += Math.cos(angleTarget) * speed;
            worldY += Math.sin(angleTarget) * speed;
        }
    }
    private double distanceToPlayer(Entity player) {
        return Math.sqrt((Math.pow(player.worldX - this.worldX,2)) + (Math.pow(player.worldY - this.worldY,2)));
    }
    private double anglePlayerAndCoin() {
        // Lấy vị trí của người chơi
        double playerX = gs.player.getWorldX() + (double) gs.getTile() /2;
        double playerY = gs.player.getWorldY() + (double) gs.getTile() /2;

        // Tính toán khoảng cách giữa vị trí của coin và người chơi
        double dx = playerX - worldX;
        double dy = playerY - worldY;
        // Tính toán góc dựa trên các thành phần dx và dy
        return Math.atan2(dy,dx);
    }
    private void getImage() {
        try {
            BufferedImage largeImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/coin.png")));
            int x = 0;
            int y = 0;
            up1 = largeImage.getSubimage(x,y,gs.getTile(),gs.getTile());
            x+=gs.getTile();
            up2 = largeImage.getSubimage(x,y,gs.getTile(),gs.getTile());
            x+=gs.getTile();
            up3 = largeImage.getSubimage(x,y,gs.getTile(),gs.getTile());
            x+=gs.getTile();
            up4 = largeImage.getSubimage(x,y,gs.getTile(),gs.getTile());
            x+=gs.getTile();
            up5 = largeImage.getSubimage(x,y,gs.getTile(),gs.getTile());
            x+=gs.getTile();
            up6 = largeImage.getSubimage(x,y,gs.getTile(),gs.getTile());
            currentImage = up1;
        }catch(Exception ignored) {

        }
    }

}
