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

public class TileManager {
    GameState gs;
    Tile[] tile;

    public int getMapTileNum(int i, int j) {
        return mapTileNum[i][j];
    }
    public Tile getTile(int i) {
        return tile[i];
    }
    public void setMapTileNum(int[][] mapTileNum) {
        this.mapTileNum = mapTileNum;
    }

    int mapTileNum[][];

    public TileManager(GameState gs) {
        this.gs = gs;

        tile = new Tile[55];
        mapTileNum = new int[gs.getMaxWorldCol()][gs.getMaxWorldRow()];
        getTileImage();
    }

    public void getTileImage() {
        try {
            BufferedImage largeImage = ImageIO.read(getClass().getResourceAsStream("/tiles/1.png"));
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
                        case 7:
                        case 8:
                        case 9:
                        case 14:
                        case 16:
                        case 18:
                        case 25:
                        case 26:
                        case 27:
                        case 36:
                        case 40:
                        case 47:
                        case 49:
                        case 50:
                            tile[index].collision = true;
                    }
                }
            }


        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
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
                    mapTileNum[col][row] = num;
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

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gs.getMaxWorldCol() && worldRow < gs.getMaxWorldRow()) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gs.getTile();
            int worldY = worldRow * gs.getTile();
            int screenX = worldX - gs.player.getWorldX() + gs.player.getScreenX();
            int screenY = worldY - gs.player.getWorldY() + gs.player.getScreenY();


            if(worldX + gs.getTile() > gs.player.getWorldX() - gs.player.getScreenX() &&
               worldX - gs.getTile() < gs.player.getWorldX() + gs.player.getScreenX() &&
               worldY + gs.getTile() > gs.player.getWorldY() - gs.player.getScreenY() &&
               worldY - gs.getTile() < gs.player.getWorldY() + gs.player.getScreenY()) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gs.getTile(), gs.getTile(), null);

            }
            worldCol++;

            if(worldCol == gs.getMaxWorldCol()) {
                worldCol = 0;
                worldRow++;
            }
        }

    }
}
