package Entity;

import Main.CollisionChecker;
import Main.GameState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public abstract class Monster extends Entity{
    protected int sight = 0;

    protected final int defaultCountdown = 10;
    protected int countdown = defaultCountdown;
    protected final int defaultTimeMoving = 100;
    protected int timeMoving = defaultTimeMoving;
    protected boolean canMoving = true;

    public Monster(GameState gs) {
        super(gs);
    }
    public void generateCoin(){}
    private double distanceToPlayer(Entity player) {
        return Math.sqrt((Math.pow(player.worldX - this.worldX,2)) + (Math.pow(player.worldY - this.worldY,2)));
    }
    protected void setAI() {

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
    public abstract void update();

    @Override
    public abstract void draw(Graphics2D g2);

    @Override
    public abstract void init();

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
