package Weapon;
import Entity.Entity;
import Entity.TYPE;
import Main.GameState;

import java.awt.*;

public class Bullet extends Entity{

    public Bullet(GameState gs) {
        super(gs);
        init();
    }

    @Override
    public void update() {
        if(alive) {
            worldX += Math.cos(angleTarget) * speed;
            worldY += Math.sin(angleTarget) * speed;
        }

        solidArea.x = (int)worldX;
        solidArea.y = (int)worldY;
    }

    @Override
    public void draw(Graphics2D g2) {
        if(alive) {
            g2.drawRect((int)worldX,(int)worldY,10,10);
            g2.setColor(Color.magenta);
            g2.fillRect((int)worldX,(int)worldY,10,10);
        }
    }

    @Override
    public void init() {
        worldX = gs.player.getBounds().x + (double) gs.player.getBounds().width / 2;
        worldY = gs.player.getBounds().y + (double) gs.player.getBounds().height / 2;
        baseDamage = 2;
        type = TYPE.WEAPON;
        speed = 20;
        solidArea = new Rectangle((int)worldX,(int)worldY,10,10);
        angleTarget = angleMouseAndPlayer();
        alive = true;
    }

    @Override
    public Rectangle getBounds() {
        return solidArea;
    }
    private double angleMouseAndPlayer() {
        // Lấy vị trí của người chơi
        double playerX = gs.player.getBounds().x;
        double playerY = gs.player.getBounds().y;

        // Tính toán khoảng cách giữa vị trí của chuột và người chơi
        double dx = gs.mouseHandle.getWorldX() - playerX;
        double dy = gs.mouseHandle.getWorldY() - playerY;

        // Tính toán góc dựa trên các thành phần dx và dy
        return Math.atan2(dy, dx);
    }

}
