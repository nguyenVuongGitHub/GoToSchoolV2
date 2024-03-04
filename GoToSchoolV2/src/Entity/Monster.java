package Entity;

import Main.GameState;

import java.awt.*;
import java.util.Random;

public class Monster extends Entity{

    public Monster(GameState gs) {
        super(gs);
        init();
    }
    private void setAI() {
        angleTarget = anglePlayerAndMonster();
        worldX += Math.cos(angleTarget)*speed;
        worldY += Math.sin(angleTarget)*speed;
    }
    @Override
    public void update() {
        setAI();
        solidArea.x = (int)worldX;
        solidArea.y = (int)worldY;
        clearVertices();
        setPolygonVertices();
    }

    @Override
    public void draw(Graphics2D g2) {
        if(alive) {
            g2.drawRect(solidArea.x,solidArea.y,solidArea.width,solidArea.height);
            if(collision) {
                g2.setColor(Color.red);
            }else {
                g2.setColor(Color.green);
            }
            g2.fillRect(solidArea.x,solidArea.y,solidArea.width,solidArea.height);
        }

    }

    @Override
    public void init() {
        type = TYPE.MONSTER;
        hp = 1;
        setPolygonVertices();
        int x = new Random().nextInt(8); // Random số từ 0 đến 7
        int screenWidth = gs.getWindowWidth();
        int screenHeight = gs.getWindowHeight();
        switch (x) {
            case 0: // Vị trí trên cùng, bên trái
                worldX = new Random().nextInt(-screenWidth, 0);
                worldY = new Random().nextInt(-screenHeight, 0);
                break;
            case 1: // Vị trí trên cùng, bên phải
                worldX = new Random().nextInt(screenWidth, screenWidth * 2);
                worldY = new Random().nextInt(-screenHeight, 0);
                break;
            case 2: // Vị trí dưới cùng, bên trái
                worldX = new Random().nextInt(-screenWidth, 0);
                worldY = new Random().nextInt(screenHeight, screenHeight * 2);
                break;
            case 3: // Vị trí dưới cùng, bên phải
                worldX = new Random().nextInt(screenWidth, screenWidth * 2);
                worldY = new Random().nextInt(screenHeight, screenHeight * 2);
                break;
            case 4: // Vị trí bên trái, giữa màn hình
                worldX = new Random().nextInt(-screenWidth, 0);
                worldY = new Random().nextInt(0, screenHeight);
                break;
            case 5: // Vị trí bên phải, giữa màn hình
                worldX = new Random().nextInt(screenWidth, screenWidth * 2);
                worldY = new Random().nextInt(0, screenHeight);
                break;
            case 6: // Vị trí trên cùng, giữa màn hình
                worldX = new Random().nextInt(0, screenWidth);
                worldY = new Random().nextInt(-screenHeight, 0);
                break;
            case 7: // Vị trí dưới cùng, giữa màn hình
                worldX = new Random().nextInt(0, screenWidth);
                worldY = new Random().nextInt(screenHeight, screenHeight * 2);
                break;
            default:
                // Các xử lý mặc định hoặc thông báo lỗi
        }
        hp = 5;
        speed = 5;
        baseDamage = 0;
        solidArea = new Rectangle((int)worldX,(int)worldY,35,35);
        angleTarget = new Random().nextDouble() % 360;
    }

    @Override
    public Rectangle getBounds() {
        return solidArea;
    }
    private double anglePlayerAndMonster() {
        // Lấy vị trí của người chơi
        double playerX = gs.player.getScreenX();
        double playerY = gs.player.getScreenY();

        // Tính toán khoảng cách giữa vị trí của monster và người chơi
        double dx = playerX - worldX;
        double dy = playerY - worldY;

        // Tính toán góc dựa trên các thành phần dx và dy
        return Math.atan2(dy, dx);
    }
}
