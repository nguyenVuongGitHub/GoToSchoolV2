package Main;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    GameState gs;
    Font maruMonica, purisaB;
    public UI(GameState gs) {
        this.gs = gs;
        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Font getMaruMonica() {
        return maruMonica;
    }

    public Font getPurisaB() {
        return purisaB;
    }
}
