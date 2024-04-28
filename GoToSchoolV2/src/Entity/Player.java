package Entity;

import CollisionSystem.PointX;
import Main.CollisionChecker;
import Main.GameState;
import Main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
public class Player extends Entity{

    int timeCount = 0;
    String beforeDirection = direction;
    public Player(GameState gs) {
        super(gs);
        init();
        screenX = gs.getWindowWidth()/2 - (gs.getTile()/2);
        screenY = gs.getWindowHeight()/2 - (gs.getTile()/2);
    }

    @Override
    public void init() {
        worldX = gs.getTile() * 20;
        worldY = gs.getTile() * 37;
        type = TYPE.PLAYER;
        hp = 100;
        speed = 15;
        damage = 1;
        direction = "down";
        solidArea = new Rectangle(50,64,32,30);
        scale = 2;
        getImage();
        clearVertices();
        setPolygonVertices();
    }
    /*public void getImage() {
        try {


            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_up_1.png")));
            up1 = uTool.scaleImage(up1,scale);
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_up_2.png")));
            up2 = uTool.scaleImage(up2,scale);
            up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_up_3.png")));
            up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_up_4.png")));
            up5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_up_5.png")));
            up6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_up_6.png")));

            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_down_2.png")));
            down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_down_3.png")));
            down4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_down_4.png")));
            down5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_down_5.png")));
            down6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_down_6.png")));

            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_left_2.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_left_3.png")));
            left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_left_4.png")));
            left5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_left_5.png")));
            left6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_left_6.png")));

            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_right_2.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_right_3.png")));
            right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_right_4.png")));
            right5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_right_5.png")));
            right6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_right_6.png")));

            currentImage = down1;
        }catch(IOException e) {
            e.printStackTrace();
        }
    }*/
    @Override
    public void getImage() {
        try {
            loadUpImages();
            loadDownImages();
            loadLeftImages();
            loadRightImages();

            // Set default image
            currentImage = down1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUpImages() throws IOException {
        UtilityTool uTool = new UtilityTool();
        up1 = loadImage("/player/player_up_1.png");
        up2 = loadImage("/player/player_up_2.png");
        up3 = loadImage("/player/player_up_3.png");
        up4 = loadImage("/player/player_up_4.png");
        up5 = loadImage("/player/player_up_5.png");
        up6 = loadImage("/player/player_up_6.png");
        up1 = uTool.scaleImage(up1,scale);
        up2 = uTool.scaleImage(up2,scale);
        up3 = uTool.scaleImage(up3,scale);
        up4 = uTool.scaleImage(up4,scale);
        up5 = uTool.scaleImage(up5,scale);
        up6 = uTool.scaleImage(up6,scale);
    }

    private void loadDownImages() throws IOException {
        UtilityTool uTool = new UtilityTool();
        down1 = loadImage("/player/player_down_1.png");
        down2 = loadImage("/player/player_down_2.png");
        down3 = loadImage("/player/player_down_3.png");
        down4 = loadImage("/player/player_down_4.png");
        down5 = loadImage("/player/player_down_5.png");
        down6 = loadImage("/player/player_down_6.png");
        down1 = uTool.scaleImage(down1,scale);
        down2 = uTool.scaleImage(down2,scale);
        down3 = uTool.scaleImage(down3,scale);
        down4 = uTool.scaleImage(down4,scale);
        down5 = uTool.scaleImage(down5,scale);
        down6 = uTool.scaleImage(down6,scale);
    }

    private void loadLeftImages() throws IOException {
        UtilityTool uTool = new UtilityTool();
        left1 = loadImage("/player/player_left_1.png");
        left2 = loadImage("/player/player_left_2.png");
        left3 = loadImage("/player/player_left_3.png");
        left4 = loadImage("/player/player_left_4.png");
        left5 = loadImage("/player/player_left_5.png");
        left6 = loadImage("/player/player_left_6.png");
        left1 = uTool.scaleImage(left1,scale);
        left2 = uTool.scaleImage(left2,scale);
        left3 = uTool.scaleImage(left3,scale);
        left4 = uTool.scaleImage(left4,scale);
        left5 = uTool.scaleImage(left5,scale);
        left6 = uTool.scaleImage(left6,scale);
    }

    private void loadRightImages() throws IOException {
        UtilityTool uTool = new UtilityTool();
        right1 = loadImage("/player/player_right_1.png");
        right2 = loadImage("/player/player_right_2.png");
        right4 = loadImage("/player/player_right_4.png");
        right3 = loadImage("/player/player_right_3.png");
        right5 = loadImage("/player/player_right_5.png");
        right6 = loadImage("/player/player_right_6.png");
        right1 = uTool.scaleImage(right1,scale);
        right2 = uTool.scaleImage(right2,scale);
        right3 = uTool.scaleImage(right3,scale);
        right4 = uTool.scaleImage(right4,scale);
        right5 = uTool.scaleImage(right5,scale);
        right6 = uTool.scaleImage(right6,scale);
    }

    private BufferedImage loadImage(String path) throws IOException {
        return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
    }


    @Override
    public void update() {
            // CHECK COLLISION
            collisionOn = false;
            if(gs.keyHandle.isUpPress() && gs.keyHandle.isLeftPress()) {
                direction = "up-left";
                beforeDirection = direction;
            }else if(gs.keyHandle.isUpPress() && gs.keyHandle.isRightPress()) {
                direction = "up-right";
                beforeDirection = direction;
            }else if(gs.keyHandle.isDownPress() && gs.keyHandle.isLeftPress()) {
                direction = "down-left";
                beforeDirection = direction;
            }else if(gs.keyHandle.isDownPress() && gs.keyHandle.isRightPress()) {
                direction = "down-right";
                beforeDirection = direction;
            }else if(gs.keyHandle.isUpPress()) {
                direction = "up";
                beforeDirection = direction;
            }else if(gs.keyHandle.isDownPress()) {
                direction = "down";
                beforeDirection = direction;
            }else if(gs.keyHandle.isLeftPress()) {
                direction = "left";
                beforeDirection = direction;
            }else if(gs.keyHandle.isRightPress()) {
                direction = "right";
                beforeDirection = direction;
            }
            else {
                direction = "nan";
            }

            gs.CC.checkPlayerWithTile(this);

            if(!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                    case "up-left" -> {
                        worldX -= speed;
                        worldY -= speed;
                    }
                    case "up-right" -> {
                        worldX += speed;
                        worldY -= speed;
                    }
                    case "down-left" -> {
                        worldX -= speed;
                        worldY += speed;
                    }
                    case "down-right" -> {
                        worldY += speed;
                        worldX += speed;
                    }
                }
            }else {
                int collisionWith;
                switch (direction) {
                    case "up-left", "up-right" -> {
                        collisionWith = gs.CC.checkEntityWithTile(this, "up");
                        if (collisionWith != CollisionChecker.UP && gs.keyHandle.isUpPress()) {
                            direction = "up";
                            beforeDirection = direction;
                            worldY -= speed;
                        }

                        collisionWith = gs.CC.checkEntityWithTile(this, "left");
                        if (collisionWith != CollisionChecker.LEFT && gs.keyHandle.isLeftPress()) {
                            direction = "left";
                            beforeDirection = direction;
                            worldX -= speed;
                        }

                        collisionWith = gs.CC.checkEntityWithTile(this, "right");

                        if (collisionWith != CollisionChecker.RIGHT && gs.keyHandle.isRightPress()) {

                            direction = "right";
                            beforeDirection = direction;
                            worldX += speed;
                        }
                    }
                    case "down-left", "down-right" -> {
                        collisionWith = gs.CC.checkEntityWithTile(this, "down");
                        if (collisionWith != CollisionChecker.DOWN && gs.keyHandle.isDownPress()) {
                            direction = "down";
                            beforeDirection = direction;
                            worldY += speed;
                        }
                        collisionWith = gs.CC.checkEntityWithTile(this, "left");

                        if (collisionWith != CollisionChecker.LEFT && gs.keyHandle.isLeftPress()) {

                            direction = "left";
                            beforeDirection = direction;
                            worldX -= speed;
                        }

                        collisionWith = gs.CC.checkEntityWithTile(this, "right");

                        if (collisionWith != CollisionChecker.RIGHT && gs.keyHandle.isRightPress()) {

                            direction = "right";
                            beforeDirection = direction;
                            worldX += speed;
                        }
                    }
                    case "up", "down" -> {
                        collisionWith = gs.CC.checkEntityWithTile(this, "left");
                        if (collisionWith != CollisionChecker.LEFT && gs.keyHandle.isLeftPress()) {
                            direction = "left";
                            beforeDirection = direction;
                            worldX -= speed;
                        }
                        collisionWith = gs.CC.checkEntityWithTile(this, "right");
                        if (collisionWith != CollisionChecker.RIGHT && gs.keyHandle.isRightPress()) {
                            direction = "right";
                            beforeDirection = direction;
                            worldX += speed;
                        }
                    }
                    case "left", "right" -> {
                        collisionWith = gs.CC.checkEntityWithTile(this, "up");
                        if (collisionWith != CollisionChecker.UP && gs.keyHandle.isUpPress()) {
                            direction = "up";
                            beforeDirection = direction;
                            worldY -= speed;
                        }
                        collisionWith = gs.CC.checkEntityWithTile(this, "down");
                        if (collisionWith != CollisionChecker.DOWN && gs.keyHandle.isDownPress()) {
                            direction = "down";
                            beforeDirection = direction;
                            worldY += speed;
                        }
                    }
                }
            }

            if(!canTouch) {
                timeCount++;
                if(timeCount >= timeCanTouch) {
                    timeCount = 0;
                    canTouch = true;
                }

            }

            spriteCounter++;
            if(spriteCounter > 3) {
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
        if(gs.keyHandle.isRightPress()) {
            if(spriteNum == 1) {
                currentImage = right1;
            }
            if(spriteNum == 2) {
                currentImage = right2;
            }
            if(spriteNum == 3) {
                currentImage = right3;
            }
            if(spriteNum == 4) {
                currentImage = right4;
            }
            if(spriteNum == 5) {
                currentImage = right5;
            }
            if(spriteNum == 6) {
                currentImage = right6;
            }
        }
        if(gs.keyHandle.isLeftPress()) {
            if(spriteNum == 1) {
                currentImage = left1;
            }
            if(spriteNum == 2) {
                currentImage = left2;
            }
            if(spriteNum == 3) {
                currentImage = left3;
            }
            if(spriteNum == 4) {
                currentImage = left4;
            }
            if(spriteNum == 5) {
                currentImage = left5;
            }
            if(spriteNum == 6) {
                currentImage = left6;
            }
        }
        if(gs.keyHandle.isUpPress()) {
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
        }
        if(gs.keyHandle.isDownPress()) {
            if(spriteNum == 1) {
                currentImage = down1;
            }
            if(spriteNum == 2) {
                currentImage = down2;
            }
            if(spriteNum == 3) {
                currentImage = down3;
            }
            if(spriteNum == 4) {
                currentImage = down4;
            }
            if(spriteNum == 5) {
                currentImage = down5;
            }
            if(spriteNum == 6) {
                currentImage = down6;
            }
        }

        g2.drawImage(currentImage, screenX, screenY, null);
//        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, getBounds().width, getBounds().height);
//        drawVertices(g2);
    }
    public String getBeforeDirection() {
        return beforeDirection;
    }
}

