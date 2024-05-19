package Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class ChangeScene {
    boolean alive = false;
    BufferedImage start = null;
    BufferedImage[] imageIn1;
    BufferedImage[] imageOut1;
    BufferedImage[] imageIn2;
    BufferedImage[] imageOut2;
    BufferedImage[] imageIn3;
    BufferedImage[] imageOut3;
    BufferedImage currentImage;

    public int getNumberDraw() {
        return numberDraw;
    }
    int index = 0;
    boolean isRandom = false;
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
                if(!isRandom) {
                    Random random = new Random();
                    index = random.nextInt(3) + 1;
                    isRandom = true;
                }
                drawIn(g2,index);
                if(numberSprite == 0) {
                    numberDraw = 2;
                    numberSprite = 1;
                    countSprite = 0;
                    isRandom = false;
                }
            }
            if(numberDraw == 2) {
                if(!isRandom) {
                    Random random = new Random();
                    index = random.nextInt(3) + 1;
                    isRandom = true;
                }
                drawOut(g2,index);
                if(numberSprite == 0) {
                    countSprite = 0;
                    numberDraw = 1;
                    numberSprite = 1;
                    alive = false;
                    gs.changeState = true;
                    isRandom = false;
                }
            }
        }
    }
    private void drawIn(Graphics2D g2, int index) {
        if(index == 1) {
            currentImage = imageIn1[numberSprite];
        }else if(index == 2) {
            currentImage = imageIn2[numberSprite];
        }else if(index == 3) {
            currentImage = imageIn3[numberSprite];
        }
        g2.drawImage(currentImage,0,0,null);
    }
    private void drawOut(Graphics2D g2, int index) {
        if(index == 1) {
            currentImage = imageOut1[numberSprite];
        }else if(index == 2) {
            currentImage = imageOut2[numberSprite];
        }else if(index == 3) {
            currentImage = imageOut3[numberSprite];
        }
        g2.drawImage(currentImage,0,0,null);
    }
    private void init() {
        imageIn1 = new BufferedImage[8];
        imageOut1 = new BufferedImage[8];
        imageIn2 = new BufferedImage[8];
        imageOut2 = new BufferedImage[8];
        imageIn3 = new BufferedImage[8];
        imageOut3 = new BufferedImage[8];
        try {
            for(int i = 1; i <= 3; i ++) {
                for(int j = 0; j < 8; j++) {
                    String path = "/changeScene/in/in_" + i + "_" + (j+1) + ".png";
                    if(i == 1) {
                        imageIn1[j] = loadImage(path);
                    }else if(i == 2) {
                        imageIn2[j] = loadImage(path);
                    }else {
                        imageIn3[j] = loadImage(path);
                    }
                }
            }
            for(int i = 1; i <= 3; i ++) {
                for(int j = 0; j < 8; j++) {
                    String path = "/changeScene/out/out_" + i + "_" + (j+1) + ".png";
                    if(i == 1) {
                        imageOut1[j] = loadImage(path);
                    }else if(i == 2) {
                        imageOut2[j] = loadImage(path);
                    }else {
                        imageOut3[j] = loadImage(path);
                    }
                }
            }

            currentImage = loadImage("/changeScene/in/start.png");
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
