package tile;

import Entity.Entity;
import Main.GameState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TileManager{
    public static final int highestLayer = 2;
    GameState gs;
    Tile[] tile;
    Set<Tile> walls = new HashSet<Tile>();
    public int getLayer(int i, int j,int layer) {
        if(layer == 1) {
            return layer1[i][j];
        }else {
            return layer2[i][j];
        }
    }
    public Tile getTile(int index) {
        return tile[index];
    }

    int[][] layer1;
    int[][] layer2;

    public TileManager(GameState gs) {
        this.gs = gs;

        tile = new Tile[201];
        layer1 = new int[gs.getMaxWorldCol()][gs.getMaxWorldRow()];
        layer2 = new int[gs.getMaxWorldCol()][gs.getMaxWorldRow()];
        getTileImage();
    }

    public void getTileImage() {
        try {
            BufferedImage largeImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/final_map_maybe.png")));
            int col = largeImage.getWidth() / gs.getTile();
            int row = largeImage.getHeight() / gs.getTile();

            for(int i = 0; i < tile.length; i++) {
                tile[i] = new Tile();
            }

            for(int i = 0; i < row; i++) {
                for(int j = 0; j < col; j++) {
                    int index = i*col + j + 1;

                    tile[index].image = largeImage.getSubimage(j * gs.getTile(), i * gs.getTile(), gs.getTile(), gs.getTile());
                    switch (index) {
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                        case 24:
                        case 25:
                        case 26:
                        case 27:
                        case 29:
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                        case 44:
                        case 45:
                        case 46:
                        case 47:
                        case 48:
                        case 49:
                        case 50:
                        case 51:
                        case 52:
                        case 53:
                        case 65:
                        case 70:
                        case 71:
                        case 72:
                        case 73:
                        case 85:
                        case 101:
                        case 102:
                        case 103:
                        case 104:
                        case 127:
                        case 128:
                        case 129:
                        case 130:
                        case 131:
                        case 147:
                        case 148:
                        case 149:
                        case 150:
                        case 151:
                        case 167:
                        case 168:
                        case 169:
                        case 170:
                        case 171:
                            if(index == 101 || index == 103) {
                                tile[index].setSolidArea(32,9,32,48);
                            }
                            else if(index == 102 || index == 104) {
                                tile[index].setSolidArea(0,0,32,48);
                            }else {
                                tile[index].setSolidArea(0,0,64,64);
                            }
                            tile[index].collision = true;
                            tile[index].setValue(index);
                            walls.add(tile[index]);
                    }
                }
            }


        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath, int layer) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gs.getMaxWorldCol() && row < gs.getMaxWorldRow()) {

                String line = br.readLine();

                while (col < gs.getMaxWorldCol()) {

                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);
                    if(layer == 1) {
                        layer1[col][row] = num;
                    }else if(layer == 2) {
                        layer2[col][row] = num;
                    }
                    col++;
                }
                if(col == gs.getMaxWorldCol()) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        }catch (Exception e) {

        }
    }

    public void draw(Graphics2D g2) {
        drawHelper(g2,1);
        drawHelper(g2,2);
        //
    }

    public void drawHelper(Graphics2D g2, int layer) {

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gs.getMaxWorldCol() && worldRow < gs.getMaxWorldRow()) {
            int tileNum = 0;
            if(layer == 1) {
                tileNum = layer1[worldCol][worldRow];
            }else if(layer == 2) {
                tileNum = layer2[worldCol][worldRow];
            }

            int worldX = worldCol * gs.getTile();
            int worldY = worldRow * gs.getTile();
            int screenX = worldX - gs.player.getWorldX() + gs.player.getScreenX();
            int screenY = worldY - gs.player.getWorldY() + gs.player.getScreenY();

            if(worldX + gs.getTile() > gs.player.getWorldX() - gs.player.getScreenX() &&
               worldX - gs.getTile() < gs.player.getWorldX() + gs.player.getScreenX() &&
               worldY + gs.getTile() > gs.player.getWorldY() - gs.player.getScreenY() &&
               worldY - gs.getTile() < gs.player.getWorldY() + gs.player.getScreenY()){
                g2.drawImage(tile[tileNum].image, screenX, screenY, gs.getTile(), gs.getTile(), null);
            }
            worldCol++;

            if(worldCol == gs.getMaxWorldCol()) {
                worldCol = 0;
                worldRow++;
            }
        }
    }



    public Set<Tile> getWall() {
        return walls;
    }
}
