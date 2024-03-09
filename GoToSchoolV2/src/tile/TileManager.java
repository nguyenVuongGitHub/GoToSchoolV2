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
    int mapTileNum[][];

    public TileManager(GameState gs) {
        this.gs = gs;

        tile = new Tile[10];
        mapTileNum = new int[gs.getMaxWorldCol()][gs.getMaxWorldRow()];
        getTileImage();
//        loadMap(pathMap);
    }

    public void getTileImage() {
        try {

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/map_1.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/map_2.png"));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/map_3.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/map_4.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/map_5.png"));

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/map_6.png"));

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

                    String numbers[] = line.split("");

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
