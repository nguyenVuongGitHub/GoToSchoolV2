package Main;
import Entity.Entity;
import Quadtree.PointQ;
import Quadtree.RectangleQ;
import CollisionSystem.*;

import java.awt.*;
import java.util.List;

public class CollisionChecker {
    GameState gs;
    public CollisionChecker(GameState gs) {
        this.gs = gs;
    }
    public boolean checkEntityWithTile(Entity entity) {
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() - entity.getSolidArea().x + entity.getBounds().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() - entity.getSolidArea().y + entity.getBounds().height;

        int leftTileCol = entityLeftWorldX / gs.getTile();
        int rightTileCol = entityRightWorldX / gs.getTile();
        int topTileRow = entityTopWorldY / gs.getTile();
        int bottomTileRow = entityBottomWorldY / gs.getTile();
        int adjacentTile1, adjacentTile2;

        switch(entity.getDirection()) {
            case "up":
                topTileRow = (entityTopWorldY - entity.getSpeed()) / gs.getTile();
                adjacentTile1 = gs.tileM.getMapTileNum(leftTileCol, topTileRow);
                adjacentTile2 = gs.tileM.getMapTileNum(rightTileCol, topTileRow);
                if (gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                    entity.setCollisionOn(true);
                    return true;
                }
                break;
            case "down":
                bottomTileRow = (entityBottomWorldY + entity.getSpeed()) / gs.getTile();

                adjacentTile1 = gs.tileM.getMapTileNum(leftTileCol, bottomTileRow);
                adjacentTile2 = gs.tileM.getMapTileNum(rightTileCol, bottomTileRow);

                if (gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                    entity.setCollisionOn(true);
                    return true;
                }
                break;
            case "left":
                leftTileCol = (entityLeftWorldX - entity.getSpeed()) / gs.getTile();
                adjacentTile1 = gs.tileM.getMapTileNum(leftTileCol, topTileRow);
                adjacentTile2 = gs.tileM.getMapTileNum(leftTileCol, bottomTileRow);
                if (gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                    entity.setCollisionOn(true);
                    return true;
                }
                break;
            case "right":
                rightTileCol = (entityRightWorldX + entity.getSpeed()) / gs.getTile();
                adjacentTile1 = gs.tileM.getMapTileNum(rightTileCol, topTileRow);
                adjacentTile2 = gs.tileM.getMapTileNum(rightTileCol, bottomTileRow);
                if (gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                    entity.setCollisionOn(true);
                    return true;
                }
                break;
        }
        return false;
    }
    public void checkSkillWithMonster(List<Entity> skills, List<Entity> target) {
        gs.quadTree.clear();

        for(Entity e : target) {
            e.setCollision(false);
            RectangleQ bound = new RectangleQ(e.getBounds());
            PointQ p = new PointQ(e.getBounds().x,e.getBounds().y,e,bound);
            gs.quadTree.insert(p);
        }
        for(Entity skill : skills) {
            Rectangle bulletBound = skill.getBounds();
            RectangleQ bound = new RectangleQ(bulletBound);
            gs.found = gs.quadTree.query(bound);

            if(gs.found != null) {
                for(PointQ other : gs.found) {
                    Entity check = other.getUserData();
                    if(check != skill && skill.getAlive() && check.getAlive()) {
                        if (bulletBound.intersects(check.getBounds())) {
                            skill.setAlive(false);
                            int newHP = check.getHP();
                            newHP -= skill.getDamage() + gs.player.getDamage();
                            check.setHP(newHP);
                            if(check.getHP() <= 0) {
                                check.setAlive(false);
                            }
                        }
                    }
                }
            }
        }

    }

    public void checkPlayerAndMonsters(Entity player, List<Entity> monsters) {
        gs.quadTree.clear();

        for (Entity m : monsters) {
            m.setCollision(false);
            Rectangle bounds = m.getBounds();
            RectangleQ bound = new RectangleQ(m.getBounds());
            PointQ p = new PointQ(bounds.x, bounds.y, m, bound);
            gs.quadTree.insert(p);
        }

        RectangleQ playerBounds = new RectangleQ(player.getBounds());
        gs.found = gs.quadTree.query(playerBounds);

        if (gs.found != null) {

            Rectangle playerRect = new Rectangle(player.getBounds());

            for (PointQ other : gs.found) {
                Entity check = other.getUserData();

                if(player == check) {

                    if (SeparatingAxis.polygonCollisionDetectFirstStatic(player, check)) {

                        player.setCollision(true);
                        check.setCollision(true);
                    }
                }
            }
        }
    }

}
