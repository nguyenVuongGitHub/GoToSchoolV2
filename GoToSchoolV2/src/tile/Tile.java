package tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Tile {

    public BufferedImage image;
    public boolean collision = false;
    Rectangle solidArea = new Rectangle(0,0,64,64);
    int value = 0;
    int worldX,worldY;
    public void setSolidArea(int x, int y, int w, int h) {
        solidArea.x = x;
        solidArea.y = y;
        solidArea.width = w;
        solidArea.height = h;
    }
    public Rectangle getBounds() {
        return new Rectangle(worldX+solidArea.x,worldY+solidArea.y,solidArea.width,solidArea.height);
    }
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
