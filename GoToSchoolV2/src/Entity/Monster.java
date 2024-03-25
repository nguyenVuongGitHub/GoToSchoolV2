package Entity;

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

        if(collisionOn && canMoving) {
            if(Objects.equals(direction, "up") || Objects.equals(direction, "down"))
            {
                if(gs.player.getWorldX() <= this.worldX) {
                    worldX -= speed;
                }else{
                    worldX += speed;
                }
            }
            if(Objects.equals(direction, "left") || Objects.equals(direction, "right"))
            {
                if(gs.player.getWorldY() <= this.worldY) {
                    worldY -= speed;
                }else{
                    worldY += speed;
                }
            }
            timeMoving--;
        }

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

        if(collisionOn && timeMoving > 0) {
            timeMoving--;

            if(Objects.equals(direction, "right"))
                direction = "left";
            if(Objects.equals(direction,"left"))
                direction = "right";
            if(Objects.equals(direction,"up"))
                direction = "down";
            if(Objects.equals(direction,"down"))
                direction = "up";

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
//        int x = new Random().nextInt(8); // Random số từ 0 đến 7
//        int screenWidth = gs.getWindowWidth();
//        int screenHeight = gs.getWindowHeight();
//        switch (x) {
//            case 0: // Vị trí trên cùng, bên trái
//                worldX = new Random().nextInt(-screenWidth, 0);
//                worldY = new Random().nextInt(-screenHeight, 0);
//                break;
//            case 1: // Vị trí trên cùng, bên phải
//                worldX = new Random().nextInt(screenWidth, screenWidth * 2);
//                worldY = new Random().nextInt(-screenHeight, 0);
//                break;
//            case 2: // Vị trí dưới cùng, bên trái
//                worldX = new Random().nextInt(-screenWidth, 0);
//                worldY = new Random().nextInt(screenHeight, screenHeight * 2);
//                break;
//            case 3: // Vị trí dưới cùng, bên phải
//                worldX = new Random().nextInt(screenWidth, screenWidth * 2);
//                worldY = new Random().nextInt(screenHeight, screenHeight * 2);
//                break;
//            case 4: // Vị trí bên trái, giữa màn hình
//                worldX = new Random().nextInt(-screenWidth, 0);
//                worldY = new Random().nextInt(0, screenHeight);
//                break;
//            case 5: // Vị trí bên phải, giữa màn hình
//                worldX = new Random().nextInt(screenWidth, screenWidth * 2);
//                worldY = new Random().nextInt(0, screenHeight);
//                break;
//            case 6: // Vị trí trên cùng, giữa màn hình
//                worldX = new Random().nextInt(0, screenWidth);
//                worldY = new Random().nextInt(-screenHeight, 0);
//                break;
//            case 7: // Vị trí dưới cùng, giữa màn hình
//                worldX = new Random().nextInt(0, screenWidth);
//                worldY = new Random().nextInt(screenHeight, screenHeight * 2);
//                break;
//            default:
//                // Các xử lý mặc định hoặc thông báo lỗi
//        }
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
        // Lấy vị trí của người chơi
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


        // Tính toán góc dựa trên các thành phần dx và dy
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
