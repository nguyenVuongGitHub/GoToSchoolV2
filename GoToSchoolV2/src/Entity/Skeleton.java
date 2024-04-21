package Entity;

import Main.CollisionChecker;
import Main.GameState;
import Weapon.SkeletonWeapon;
import objects.Coin;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Skeleton extends Monster{
    public int TIME_COUNT_DOWN_ATTACK = 30;
    public final int TIME_ATTACK = 30;
    int HadCoins = 5;
    public Skeleton(GameState gs) {
        super(gs);
        init();
    }

    @Override
    public void generateCoin() {
        Random x = new Random();
        for(int i = 1; i <= HadCoins; i++) {
            Coin c = new Coin(gs);
            c.setWorldX(worldX);
            c.setWorldY(worldY);
            c.setAngleTarget(x.nextInt() % 360 + 1);
            c.setAlive(true);
            gs.coins.add(c);
        }
    }

    @Override
    public void init() {
        hp = 10;
        speed = 8;
        damage = 5;
        sight = 500;
        type = TYPE.MONSTER;
        typeMonster = TypeMonster.SKELETON;
        solidArea = new Rectangle(0,0,64,64);
        getImage();
        clearVertices();
        setPolygonVertices();
    }
    @Override
    public void draw(Graphics2D g2) {
        if (alive) {
            if (spriteNum == 1) {
                currentImage = up1;
            } else if (spriteNum == 2) {
                currentImage = up2;
            } else if (spriteNum == 3) {
                currentImage = up3;
            } else if (spriteNum == 4) {
                currentImage = up4;
            }

            screenX = (int) (worldX - gs.player.getWorldX() + gs.player.getScreenX());
            screenY = (int) (worldY - gs.player.getWorldY() + gs.player.getScreenY());

            if (direction.equals("left") || direction.equals("up")) {
                AffineTransform oldTransform = g2.getTransform();
                g2.scale(-1, 1);
                g2.drawImage(currentImage, -screenX - currentImage.getWidth(), screenY, null);
                g2.setTransform(oldTransform);
            } else {
                g2.drawImage(currentImage, screenX, screenY, null);
            }
//            g2.drawRect(screenX+getSolidArea().x,screenY+getSolidArea().y, getBounds().width, getBounds().height);
        }
    }

    public void setAI() {
        // nhin thay nguoi choi
        if(seePlayer()) {
            angleTarget = anglePlayerAndMonster();
            canMoving = false;
        }else {
            // con thoi gian dung im
            if(countdown >= 0) {
                canMoving = false;
                countdown--;
                randomDirectionMonster();
            }else {
                if(timeMoving > 0) {
                    canMoving = true;
                }else{
                    countdown = defaultCountdown;
                    timeMoving = defaultTimeMoving;
                }
            }
        }
    }

    @Override
    public void update() {

        if(hp <= 0) {
            generateCoin();
            alive = false;
            return;
        }

        setAI();
        if(seePlayer()) {
            if(TIME_COUNT_DOWN_ATTACK <= 0) {
                attacking();
                TIME_COUNT_DOWN_ATTACK = TIME_ATTACK;
            }else {
                TIME_COUNT_DOWN_ATTACK--;
            }
        }
        collisionOn = false;
        gs.CC.checkEntityWithTile(this);
        int collisionWith;

        // Trường hợp di chuyển bình thường khi  không có va chạm
        if(!collisionOn && canMoving){
            if(Objects.equals(direction, "up"))
                worldY -= speed;
            if(Objects.equals(direction, "down"))
                worldY += speed;
            if(Objects.equals(direction, "left"))
                worldX -= speed;
            if(Objects.equals(direction, "right"))
                worldX += speed;
            timeMoving--;
        }

        // Kiểm tra nếu có va chạm và đối tượng có thể di chuyển
        if(collisionOn && canMoving) {
            // Kiểm tra hướng di chuyển của đối tượng
            if(Objects.equals(direction, "up") || Objects.equals(direction, "down")) {
                // Kiểm tra va chạm với ô bên trái của đối tượng
                collisionWith = gs.CC.checkEntityWithTile(this, "left");
                // Nếu không có va chạm và vị trí của người chơi nằm bên trái đối tượng
                if(collisionWith != CollisionChecker.LEFT && gs.player.getWorldX() <= this.worldX) {
                    // Di chuyển đối tượng sang trái
                    worldX -= speed;
                }

                // Kiểm tra va chạm với ô bên phải của đối tượng
                collisionWith = gs.CC.checkEntityWithTile(this, "right");
                // Nếu không có va chạm và vị trí của người chơi nằm bên phải đối tượng
                if(collisionWith != CollisionChecker.RIGHT && gs.player.getWorldX() > this.worldX) {
                    // Di chuyển đối tượng sang phải
                    worldX += speed;
                }
            }

            // Kiểm tra hướng di chuyển của đối tượng
            if(Objects.equals(direction, "left") || Objects.equals(direction, "right")) {
                // Kiểm tra va chạm với ô phía trên của đối tượng
                collisionWith = gs.CC.checkEntityWithTile(this, "up");
                // Nếu không có va chạm và vị trí của người chơi nằm phía trên đối tượng
                if(collisionWith != CollisionChecker.UP && gs.player.getWorldY() <= this.worldY) {
                    // Di chuyển đối tượng lên trên
                    worldY -= speed;
                }

                // Kiểm tra va chạm với ô phía dưới của đối tượng
                collisionWith = gs.CC.checkEntityWithTile(this, "down");
                // Nếu không có va chạm và vị trí của người chơi nằm phía dưới đối tượng
                if(collisionWith != CollisionChecker.DOWN && gs.player.getWorldY() > this.worldY) {
                    // Di chuyển đối tượng xuống dưới
                    worldY += speed;
                }
            }

            // Giảm thời gian di chuyển của đối tượng
            timeMoving--;
        }
        // trong trường hợp có va chạm và thời gian còn di chuyển được ( đang trong trạng thái AI)
        if(collisionOn && timeMoving > 0) {
            // đổi các hướng của đối tượng
            if(Objects.equals(direction, "right"))
                direction = "left";
            if(Objects.equals(direction,"left"))
                direction = "right";
            if(Objects.equals(direction,"up"))
                direction = "down";
            if(Objects.equals(direction,"down"))
                direction = "up";
            timeMoving--;
        }

        spriteCounter++;
        if(spriteCounter > 4) {
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
                spriteNum = 1;
            }
        }
        clearVertices();
        setPolygonVertices();
    }

    public void attacking() {
        Entity skeletonAttack = new SkeletonWeapon(gs);
        skeletonAttack.worldX = this.worldX + (double) this.getBounds().width /2;
        skeletonAttack.worldY = this.worldY + (double) this.getBounds().height /2;
        skeletonAttack.setAngleTarget(anglePlayerAndMonster());
        gs.skeletonAttacks.add(skeletonAttack);
    }
    public void getImage() {
        try {

            BufferedImage largeImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/skeleton.png")));

            int x = 0;
            int y = 0;
            up1 = largeImage.getSubimage(x,y,gs.getTile(),gs.getTile());

            x+=64;
            up2 = largeImage.getSubimage(x,y,gs.getTile(),gs.getTile());

            x+=64;
            up3 = largeImage.getSubimage(x,y,gs.getTile(),gs.getTile());

            x+=64;
            up4 = largeImage.getSubimage(x,y,gs.getTile(),gs.getTile());

            currentImage = up1;
        }catch(IOException ignored) {

        }
    }
}
