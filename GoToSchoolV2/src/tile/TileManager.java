package tile;

import Entity.Entity;
import Main.GameState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;

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

        tile = new Tile[401];
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
                    int index = i * col + j + 1;
                    tile[index].image = largeImage.getSubimage(j * gs.getTile(), i * gs.getTile(), gs.getTile(), gs.getTile());

                    // Kiểm tra xem index có nằm trong danh sách các index cần collision không
                    if (isCollisionIndex(index)) {
                        tile[index].collision = true;
                        tile[index].setValue(index);
                        walls.add(tile[index]);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Danh sách các index cần collision
    private boolean isCollisionIndex(int index) {
        ArrayList<Integer> collisionIndexes = new ArrayList<>(Arrays.asList(
                4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 19, 20, 24, 25, 26, 27, 29,
                30, 31, 32, 33, 38, 39, 40, 44, 45, 46, 47, 48, 49, 50, 51, 52,
                53, 60, 65, 67, 69, 70, 71, 72, 73, 85, 101, 102, 103, 104, 114,
                115, 116, 127, 128, 129, 130, 131, 147, 148, 149, 150, 151, 167,
                168, 169, 170, 171, 181, 182, 183, 184, 185, 186, 192, 193, 194,
                195, 196, 197, 201, 201, 202, 203, 204, 205, 206, 212, 213, 214,
                215, 216, 217, 221, 222, 224, 225, 226, 232, 233, 234, 235, 236,
                237, 241, 242, 243, 244, 247, 248 ,249, 255, 256, 257, 261, 262,
                263, 264, 267, 269, 275, 276, 277, 281, 282, 283, 284, 285, 286,
                287, 288, 289, 295, 296, 297, 301, 302, 303, 304
        ));
        return collisionIndexes.contains(index);
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
