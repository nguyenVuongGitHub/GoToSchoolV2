package Weapon;
import Entity.Entity;
import Main.GameState;

import java.awt.*;

public abstract class BaseSkill extends Entity {
    protected double distance;

    public BaseSkill(GameState gs) {
        super(gs);
    }
    public double anglePlayerAndMouse() {
        // Lấy vị trí của người chơi
        double playerX = (double) gs.getWindowWidth() /2;
        double playerY = (double) gs.getWindowHeight() /2;

        // Tính toán khoảng cách giữa vị trí của chuột và người chơi
        double dx = gs.mouseHandle.getWorldX() - playerX;
        double dy = gs.mouseHandle.getWorldY() - playerY;

        // Tính toán góc dựa trên các thành phần dx và dy
        return Math.atan2(dy, dx);
    }
}
