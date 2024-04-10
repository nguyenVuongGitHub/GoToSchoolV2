package Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    GameState gs;
    Graphics2D g2;
    Font maruMonica, purisaB;
    BufferedImage HP;
    public UI(GameState gs) {
        this.gs = gs;
        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
            HP = ImageIO.read(getClass().getResourceAsStream("/user/hp.png"));

        } catch (FontFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2) {
        this.g2 = g2;

        if(gs.state == State.CAMPAIGN && !gs.campaign.isShowDialog()) {
            drawCampaign();
        }else if(gs.state == State.LOOPY) {

        }
        else if(gs.state == State.SURVIVAL) {

        }
    }
    private void drawCampaign() {
        String numberOfEnemy = "Enemy : ";
        numberOfEnemy += String.valueOf(gs.monsters.size());
        int x = getXforCenterdText(numberOfEnemy);
        int y = gs.getTile() ;
        g2.setFont(maruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,45F));
        g2.setColor(Color.white);
        g2.drawString(numberOfEnemy,x,y);
        drawHpPlayer();
        drawFrameHpPlayer();
    }
    private void drawFrameHpPlayer() {
        int x = gs.getTile();
        int y = gs.getTile();
        int w = 128;
        int h = 64;
        g2.drawImage(HP,x,y,w,h,null);
    }
    private void drawHpPlayer() {
        int x = gs.getTile();
        int y = gs.getTile();
        double currentHp = ((double)gs.player.getHP()/100) * 125;
        g2.setColor(Color.red);
        g2.drawRect(x,y,125,30);
        g2.fillRect(x,y,(int)currentHp,30);
    }
    public Font getMaruMonica() {
        return maruMonica;
    }
    public int getXforCenterdText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gs.getWindowWidth()/2 - length/2;
    }
    public Font getPurisaB() {
        return purisaB;
    }
}
