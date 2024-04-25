package Main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {

    public UtilityTool() {

    }
    // Phương thức để scale hình ảnh
    public BufferedImage scaleImage(BufferedImage img, int scaleFactor) {
        int newWidth = img.getWidth() * scaleFactor;
        int newHeight = img.getHeight() * scaleFactor;
        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(img, 0, 0, newWidth, newHeight, null);
        g.dispose();
        return scaledImage;
    }
}
