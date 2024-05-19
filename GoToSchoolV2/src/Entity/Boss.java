package Entity;

import Main.CollisionChecker;
import Main.GameState;
import Main.UtilityTool;
import Weapon.LazerBoss;
import baseAttributeMonsters.BaseBoss;
import objects.Coin;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Boss extends Monster{

    public int TIME_COUNT_DOWN_ATTACK = 30;
    public final int TIME_ATTACK = 30;
    int HadCoins = 50;
    BufferedImage[] imageMove;
    BufferedImage[] imageAttack;
    BufferedImage[] imageDie;
    String state = "move";
    boolean changeState = false;
    public Boss(GameState gs) {
        super(gs);
        init();
    }
    @Override
    public void generateCoin() {
        Random x = new Random();
        for(int i = 1; i <= HadCoins; i++) {
            Coin c = new Coin(gs);
            c.setWorldX(getBounds().x + (double) getBounds().width /2);
            c.setWorldY(getBounds().y + (double) getBounds().height /2);
            c.setAngleTarget(x.nextInt() % 360 + 1);
            c.setAlive(true);
            gs.coins.add(c);
        }
    }

    @Override
    public void init() {
        speed = 5;
        sight = 6000;
        type = TYPE.MONSTER;
        typeMonster = TypeMonster.BOSS;
        scale = 6;
        solidArea = new Rectangle(30*scale,30*scale,45*scale,40*scale);
        hp = BaseBoss.hp[BaseBoss.LEVER];
        damage = BaseBoss.damage[BaseBoss.LEVER];
        getImage();
        clearVertices();
        setPolygonVertices();
    }
    @Override
    public void draw(Graphics2D g2) {
        if (alive) {
            switch (state) {
                case "move" :
                    currentImage = imageMove[spriteNum - 1];
                    break;
                case "attack":
                    currentImage = imageAttack[spriteNum - 1];
                    break;
                case "die" :
                    currentImage = imageDie[spriteNum - 1];
                    break;
            }

            screenX = (int) (worldX - gs.player.getWorldX() + gs.player.getScreenX());
            screenY = (int) (worldY - gs.player.getWorldY() + gs.player.getScreenY());

            if (direction.equals("left")) {
                AffineTransform oldTransform = g2.getTransform();
                g2.scale(-1, 1);
                g2.drawImage(currentImage, -screenX - currentImage.getWidth(), screenY, null);
                g2.setTransform(oldTransform);
            } else {
                g2.drawImage(currentImage, screenX, screenY, null);
            }
//            g2.drawRect(screenX+getSolidArea().x,screenY+getSolidArea().y, getBounds().width, getBounds().height);
//            drawVertices(g2);
        }
    }

    public void setAI() {
        // nhin thay nguoi choi
        if(seePlayer()) {
            angleTarget = anglePlayerAndMonster();
            canMoving = false;
            state = "attack";
        }else {
            state = "move";
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
            state = "die";
            clearVertices();
        }else {
            setAI();
            if(gs.player.worldX <= this.worldX + (double) this.getBounds().width / 2) {
                direction = "left";
            }else {
                direction = "right";
            }
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
        }
        spriteCounter++;
        if(spriteCounter > 5) {
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
                spriteNum = 7;
            }
            else if(spriteNum == 7) {
                spriteNum = 8;
            }
            else if(spriteNum == 8) {
                spriteNum = 9;
            }
            else if(spriteNum == 9) {
                spriteNum = 10;
            }
            else if(spriteNum == 10) {
                spriteNum = 11;
            }
            else if(spriteNum == 11) {
                spriteNum = 12;
            }
            else if(spriteNum == 12) {
                spriteNum = 13;
            }
            else if(spriteNum == 13) {
                if(state.equals("die")) {
                    generateCoin();
                    alive = false;
                }
                spriteNum = 1;
            }
        }
        clearVertices();
        setPolygonVertices();
    }

    public void attacking() {
        if(gs.lazeBoss == null) {
            gs.lazeBoss = new LazerBoss(gs);
            gs.lazeBoss.worldX = this.worldX;
            gs.lazeBoss.worldY = this.worldY;
            gs.lazeBoss.direction = this.direction;
            Random random = new Random();
            int angle;
            Point centerBoss = new Point((int) (vertices.getFirst().getX() + getBounds().getWidth()/2), (int) (vertices.getFirst().getY() + getBounds().getHeight()/2));
            if(centerBoss.getY() < gs.player.getWorldY()) {
                if(centerBoss.getX() < gs.player.getWorldX()) {
                    angle  = random.nextInt(90); // 0 ~ 90
                }else {
                    angle  = random.nextInt(90) + 90; // 90 ~ 180
                }
            }else {
                if(centerBoss.getX() > gs.player.getWorldX()) {
                    angle  = random.nextInt(90) + 180; // 180 ~ 270

                }else {
                    angle  = random.nextInt(90) + 270; // 270 ~ 360
                }
            }
            gs.lazeBoss.setAngleTarget(Math.toRadians(angle));
        }
    }
    public void getImage() {
        try {

            BufferedImage largeImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/boss.png")));
            UtilityTool uTool = new UtilityTool();

            int col = largeImage.getWidth() / 100;
            imageMove = new BufferedImage[col];
            imageAttack = new BufferedImage[col];
            imageDie = new BufferedImage[col];
            int y;
            for(int x = 0; x < col; x ++) {
                y = 0;
                imageMove[x] = largeImage.getSubimage(x*100,y,100,100);
                imageMove[x] = uTool.scaleImage(imageMove[x],scale);
                y+=100;
                imageAttack[x] = largeImage.getSubimage(x*100,y,100,100);
                imageAttack[x] = uTool.scaleImage(imageAttack[x],scale);
                y+=100;
                imageDie[x] = largeImage.getSubimage(x*100,y,100,100);
                imageDie[x] = uTool.scaleImage(imageDie[x],scale);

            }

            currentImage = imageMove[0];
        }catch(IOException ignored) {

        }
    }
}
