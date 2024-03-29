package Entity;

import CollisionSystem.PointX;
import Main.CollisionChecker;
import Main.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
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
        worldX = gs.getTile() * 17;
        worldY = gs.getTile() * 35;
        type = TYPE.PLAYER;
        hp = 100;
        speed = 5;
        damage = 1;
        direction = "down";
        solidArea = new Rectangle(20,8,64,64);
        getPlayerImage();
        clearVertices();
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

            currentImage = down1;
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {

            // CHECK COLLISION
            collisionOn = false;
            gs.CC.checkPlayerWithTile(this);

            if(!collisionOn) {
                if(gs.keyHandle.isUpPress()) {
                    direction = "up";
                    worldY -=  speed ;
                }
                if(gs.keyHandle.isDownPress()) {
                    direction = "down";
                    worldY +=  speed ;
                }
                if(gs.keyHandle.isLeftPress()) {
                    direction = "left";
                    worldX -=  speed ;
                }
                if(gs.keyHandle.isRightPress()) {
                    direction = "right";
                    worldX +=  speed ;
                }
            }else {
                int collisionWith;
                // Kiểm tra hướng di chuyển của đối tượng
                if(gs.keyHandle.isUpPress() || gs.keyHandle.isDownPress()) {
                    // Kiểm tra va chạm với ô bên trái của đối tượng
                    collisionWith = gs.CC.checkEntityWithTile(this, "left");
                    // Nếu không có va chạm
                    if(collisionWith != CollisionChecker.LEFT && gs.keyHandle.isLeftPress()) {
                        // Di chuyển đối tượng sang trái
                        worldX -= speed;
                    }

                    // Kiểm tra va chạm với ô bên phải của đối tượng
                    collisionWith = gs.CC.checkEntityWithTile(this, "right");
                    // Nếu không có va chạm
                    if(collisionWith != CollisionChecker.RIGHT && gs.keyHandle.isRightPress()) {
                        // Di chuyển đối tượng sang phải
                        worldX += speed;
                    }
                }

                // Kiểm tra hướng di chuyển của đối tượng
                if(gs.keyHandle.isLeftPress() || gs.keyHandle.isRightPress()) {
                    // Kiểm tra va chạm với ô phía trên của đối tượng
                    collisionWith = gs.CC.checkEntityWithTile(this, "up");
                    // Nếu không có va chạm
                    if (collisionWith != CollisionChecker.UP && gs.keyHandle.isUpPress()) {
                        // Di chuyển đối tượng lên trên
                        worldY -= speed;
                    }

                    // Kiểm tra va chạm với ô phía dưới của đối tượng
                    collisionWith = gs.CC.checkEntityWithTile(this, "down");
                    // Nếu không có va chạm
                    if (collisionWith != CollisionChecker.DOWN && gs.keyHandle.isDownPress()) {
                        // Di chuyển đối tượng xuống dưới
                        worldY += speed;
                    }
                }

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
            clearVertices();
            setPolygonVertices();

    }

    @Override
    public void draw(Graphics2D g2) {
//
//        if(gs.keyHandle.isLeftPress() && gs.keyHandle.isUpPress()) {
//            if(spriteNum == 1) {
//                currentImage = left1;
//            }
//            if(spriteNum == 2) {
//                currentImage = up2;
//            }
//            if(spriteNum == 3) {
//                currentImage = left3;
//            }
//            if(spriteNum == 4) {
//                currentImage = up4;
//            }
//            if(spriteNum == 5) {
//                currentImage = left5;
//            }
//            if(spriteNum == 6) {
//                currentImage = up6;
//            }
//        }else if(gs.keyHandle.isLeftPress() && gs.keyHandle.isDownPress()) {
//            if(spriteNum == 1) {
//                currentImage = left1;
//            }
//            if(spriteNum == 2) {
//                currentImage = down2;
//            }
//            if(spriteNum == 3) {
//                currentImage = left3;
//            }
//            if(spriteNum == 4) {
//                currentImage = down4;
//            }
//            if(spriteNum == 5) {
//                currentImage = left5;
//            }
//            if(spriteNum == 6) {
//                currentImage = down6;
//            }
//        }else if(gs.keyHandle.isRightPress() && gs.keyHandle.isUpPress()) {
//            if(spriteNum == 1) {
//                currentImage = right1;
//            }
//            if(spriteNum == 2) {
//                currentImage = up2;
//            }
//            if(spriteNum == 3) {
//                currentImage = right3;
//            }
//            if(spriteNum == 4) {
//                currentImage = up4;
//            }
//            if(spriteNum == 5) {
//                currentImage = right5;
//            }
//            if(spriteNum == 6) {
//                currentImage = up6;
//            }
//        }else if(gs.keyHandle.isRightPress() && gs.keyHandle.isDownPress()) {
//            if(spriteNum == 1) {
//                currentImage = right1;
//            }
//            if(spriteNum == 2) {
//                currentImage = down2;
//            }
//            if(spriteNum == 3) {
//                currentImage = right3;
//            }
//            if(spriteNum == 4) {
//                currentImage = down4;
//            }
//            if(spriteNum == 5) {
//                currentImage = right5;
//            }
//            if(spriteNum == 6) {
//                currentImage = down6;
//            }
//        }else {
//
//            if(gs.keyHandle.isRightPress()) {
//                if(spriteNum == 1) {
//                    currentImage = right1;
//                }
//                if(spriteNum == 2) {
//                    currentImage = right2;
//                }
//                if(spriteNum == 3) {
//                    currentImage = right3;
//                }
//                if(spriteNum == 4) {
//                    currentImage = right4;
//                }
//                if(spriteNum == 5) {
//                    currentImage = right5;
//                }
//                if(spriteNum == 6) {
//                    currentImage = right6;
//                }
//            }
//            else if(gs.keyHandle.isLeftPress()) {
//                if(spriteNum == 1) {
//                    currentImage = left1;
//                }
//                if(spriteNum == 2) {
//                    currentImage = left2;
//                }
//                if(spriteNum == 3) {
//                    currentImage = left3;
//                }
//                if(spriteNum == 4) {
//                    currentImage = left4;
//                }
//                if(spriteNum == 5) {
//                    currentImage = left5;
//                }
//                if(spriteNum == 6) {
//                    currentImage = left6;
//                }
//            }
//            else if(gs.keyHandle.isUpPress()) {
//                if(spriteNum == 1) {
//                    currentImage = up1;
//                }
//                if(spriteNum == 2) {
//                    currentImage = up2;
//                }
//                if(spriteNum == 3) {
//                    currentImage = up3;
//                }
//                if(spriteNum == 4) {
//                    currentImage = up4;
//                }
//                if(spriteNum == 5) {
//                    currentImage = up5;
//                }
//                if(spriteNum == 6) {
//                    currentImage = up6;
//                }
//            }
//            else if(gs.keyHandle.isDownPress()) {
//                if(spriteNum == 1) {
//                    currentImage = down1;
//                }
//                if(spriteNum == 2) {
//                    currentImage = down2;
//                }
//                if(spriteNum == 3) {
//                    currentImage = down3;
//                }
//                if(spriteNum == 4) {
//                    currentImage = down4;
//                }
//                if(spriteNum == 5) {
//                    currentImage = down5;
//                }
//                if(spriteNum == 6) {
//                    currentImage = down6;
//                }
//            }
//
//        }
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

        g2.drawImage(currentImage, screenX, screenY, gs.getTile(), gs.getTile(), null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)worldX + solidArea.x,(int) worldY + solidArea.y, solidArea.width, solidArea.height);
    }
}
