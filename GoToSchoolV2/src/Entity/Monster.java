package Entity;

import Main.CollisionChecker;
import Main.GameState;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class Monster extends Entity{

    private final int defaultCountdown = 10;
    private int countdown = defaultCountdown;
    private final int defaultTimeMoving = 100;
    private int timeMoving = defaultTimeMoving;
    private boolean canMoving = true;

    public Monster(GameState gs) {
        super(gs);
        init();
    }
    private double distanceToPlayer(Entity player) {
        return Math.sqrt((Math.pow(player.worldX - this.worldX,2)) + (Math.pow(player.worldY - this.worldY,2)));
    }
    private void setAI() {

        final int sight = 500;
        // nhin thay nguoi choi
        if(distanceToPlayer(gs.player) <= sight) {
            angleTarget = anglePlayerAndMonster();
            canMoving = true;
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

        setAI();
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

        clearVertices();
        setPolygonVertices();
    }

    @Override
    public void draw(Graphics2D g2) {
        if(alive) {
            screenX = (int) (worldX - gs.player.getWorldX() + gs.player.getScreenX());
            screenY = (int) (worldY - gs.player.getWorldY() + gs.player.getScreenY());
            g2.drawRect(screenX,screenY,solidArea.width,solidArea.height);
            if(collision) {
                g2.setColor(Color.red);
            }else {
                g2.setColor(Color.green);
            }
            g2.fillRect(screenX,screenY,solidArea.width,solidArea.height);
        }

    }

    @Override
    public void init() {
        type = TYPE.MONSTER;
        setPolygonVertices();
        hp = 5;
        speed = 3;
        damage = 0;
        solidArea = new Rectangle(0,0,gs.getTile() /2,gs.getTile() /2);

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)worldX + solidArea.x,(int)worldY + solidArea.y,solidArea.width,solidArea.height);
    }
    private void randomDirectionMonster() {

        int angleDegrees = new Random().nextInt() % 4;

        // phán đoán hướng di chuyển
            if (angleDegrees == 0) {
                direction = "right";
            } else if (angleDegrees == 1) {
                direction = "down";
            } else if (angleDegrees == 2) {
                direction = "up";
            } else {
                direction = "left";
            }
    }
    private double anglePlayerAndMonster() {
        // Lấy vị trí của người chơi
        double playerX = gs.player.getWorldX() + (double) gs.getTile() /2;
        double playerY = gs.player.getWorldY() + (double) gs.getTile() /2;

        // Tính toán khoảng cách giữa vị trí của monster và người chơi
        double dx = playerX - worldX;
        double dy = playerY - worldY;
        double angle = Math.atan2(dy,dx);

        // Chuyển góc thành độ
        double angleDegrees = Math.toDegrees(angle);

        // phán đoán hướng di chuyển
        if (angleDegrees >= -45 && angleDegrees < 45) {
            direction = "right";
        } else if (angleDegrees >= 45 && angleDegrees < 135) {
            direction = "down";
        } else if (angleDegrees >= -135 && angleDegrees < -45) {
            direction = "up";
        } else {
            direction = "left";
        }

        // Tính toán góc dựa trên các thành phần dx và dy
        return angle;
    }
}
