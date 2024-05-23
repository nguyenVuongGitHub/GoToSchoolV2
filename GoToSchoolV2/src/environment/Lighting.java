package environment;
import Main.GameState;

import java.awt.*;
import  java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Lighting {
    GameState gs;
    BufferedImage darknessFilter;
    public Lighting(GameState gs, int circleSize) {
        darknessFilter = new BufferedImage(gs.getWindowWidth(), gs.getWindowHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        // create a screen-sized rectangle area
        Area screenArea = new Area(new Rectangle2D.Double(0,0,gs.getWindowWidth(),gs.getWindowHeight()));

        // get the center x and y of the light circle
        int centerX = gs.player.getScreenX() + gs.getTile();
        int centerY = gs.player.getScreenY() + gs.getTile();

        // get the top left x and y of the light circle
        double x = centerX - (circleSize/2);
        double y = centerY - (circleSize/2);

        // create a light circle shape
        Shape circleShape = new Ellipse2D.Double(x,y,circleSize,circleSize);

        // create a light circle area
        Area lightArea = new Area(circleShape);

        // subtract the light circle from the screen rectangle;
        screenArea.subtract(lightArea);

        // create a gradation effect within the light circle
        Color[] colors = new Color[11];
        float fraction[] = new float[11];

        colors[0] = new Color(0,0,0,0f);
        colors[1] = new Color(0,0,0,0.1f);
        colors[2] = new Color(0,0,0,0.2f);
        colors[3] = new Color(0,0,0,0.3f);
        colors[4] = new Color(0,0,0,0.4f);
        colors[5] = new Color(0,0,0,0.5f);
        colors[6] = new Color(0,0,0,0.6f);
        colors[7] = new Color(0,0,0,0.7f);
        colors[8] = new Color(0,0,0,0.8f);
        colors[9] = new Color(0,0,0,0.9f);
        colors[10] = new Color(0,0,0,0.97f);

        fraction[0] = 0f;
        fraction[1] = 0.1f;
        fraction[2] = 0.2f;
        fraction[3] = 0.3f;
        fraction[4] = 0.4f;
        fraction[5] = 0.5f;
        fraction[6] = 0.6f;
        fraction[7] = 0.7f;
        fraction[8] = 0.8f;
        fraction[9] = 0.9f;
        fraction[10] = 1f;
        // create a gradation paint setting for the light circle
        RadialGradientPaint gPaint = new RadialGradientPaint(centerX,centerY,((float) circleSize /2),fraction,colors);

        // set the gradient data on g2
        g2.setPaint(gPaint);
        // draw the light circle
        g2.fill(lightArea);

        //draw the screen rectangle without the light circle area
        g2.fill(screenArea);

        g2.dispose();
    }
    public void draw(Graphics2D g2) {
        g2.drawImage(darknessFilter,0,0,null);
    }

}
