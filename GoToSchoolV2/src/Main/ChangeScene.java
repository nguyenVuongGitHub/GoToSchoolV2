package Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ChangeScene {
    boolean alive = false;
    BufferedImage[] imageIn;
    BufferedImage[] imageOut;
    BufferedImage currentImage;
    int numberDraw = 1;
    int numberSprite = 1;
    int countSprite = 0;
    GameState gs;
    public ChangeScene(GameState gs) {
        this.gs = gs;
        init();
    }
    public void update() {
        if (alive) {
            countSprite++;
            if(countSprite > 3) {
                countSprite = 0;
                if(numberSprite == 1) {
                    numberSprite = 2;
                }
                else if(numberSprite == 2) {
                    numberSprite = 3;
                }
                else if(numberSprite == 3) {
                    numberSprite = 4;
                }
                else if(numberSprite == 4) {
                    numberSprite = 5;
                }
                else if(numberSprite == 5) {
                    numberSprite = 6;
                }
                else if(numberSprite == 6) {
                    numberSprite = 7;
                }
                else if(numberSprite == 7) {
                    numberSprite = 0;
                }
            }
        }
    }
    public void draw(Graphics2D g2) {
        if(alive) {
            if(numberDraw == 1) {
                drawIn(g2);
                if(numberSprite == 0) {
                    numberDraw = 2;
                    numberSprite = 1;
                    countSprite = 0;
                }
            }
            if(numberDraw == 2) {
                drawOut(g2);
                if(numberSprite == 0) {
                    countSprite = 0;
                    numberDraw = 1;
                    numberSprite = 1;
                    alive = false;
                    gs.changeState = true;
                }
            }
        }
    }
    private void drawIn(Graphics2D g2) {
        currentImage = imageIn[numberSprite];
        g2.drawImage(currentImage,0,0,null);
    }
    private void drawOut(Graphics2D g2) {
        currentImage = imageOut[numberSprite];
        g2.drawImage(currentImage,0,0,null);
    }
    private void init() {
        imageIn = new BufferedImage[8];
        imageOut = new BufferedImage[8];
        try {
            for(int i = 0; i < 8; i++) {
                String path = "/changeScene/changeScene_in" + (i+1) + ".png";
                imageIn[i] = loadImage(path);
            }
            for(int i = 0; i < 8; i++) {
                String path = "/changeScene/changeScene_out" + (i+1) + ".png";
                imageOut[i] = loadImage(path);
            }
            currentImage = imageIn[0];
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
    private BufferedImage loadImage(String path) throws IOException {
        return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
    }
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
