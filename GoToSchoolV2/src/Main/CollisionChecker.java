package Main;
import Entity.*;
import Quadtree.PointQ;
import Quadtree.RectangleQ;
import CollisionSystem.*;
import tile.TileManager;

import java.awt.*;
import java.util.List;

public class CollisionChecker {
    GameState gs;
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;
    public CollisionChecker(GameState gs) {
        this.gs = gs;
    }
    public boolean checkEntityEvent(Entity entity, int x, int y) {
        Rectangle eventRect = new Rectangle(x*gs.getTile(),y*gs.getTile(),64,64);
        return entity.getBounds().intersects(eventRect);
    }

    /**
     *
     * @param entity
     * @param direction
     * @return 1 - Up, 2 - down, 3 - left, 4 - right, -1 - null
     */
    public int checkEntityWithTile(Entity entity, String direction) {
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height;

        int leftTileCol = entityLeftWorldX / gs.getTile();
        int rightTileCol = entityRightWorldX / gs.getTile();
        int topTileRow = entityTopWorldY / gs.getTile();
        int bottomTileRow = entityBottomWorldY / gs.getTile();

        int adjacentTile1, adjacentTile2;

        if(direction.equals("up")) {
            topTileRow = (entityTopWorldY - entity.getSpeed()) / gs.getTile();
            adjacentTile1 = gs.tileM.getLayer(leftTileCol, topTileRow, TileManager.highestLayer);
            adjacentTile2 = gs.tileM.getLayer(rightTileCol, topTileRow, TileManager.highestLayer);
            if(gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                entity.setCollisionOn(true);
                return UP;
            }
        }
        if(direction.equals("down")) {
            bottomTileRow = (entityBottomWorldY + entity.getSpeed()) / gs.getTile();
            adjacentTile1 = gs.tileM.getLayer(leftTileCol, bottomTileRow, TileManager.highestLayer);
            adjacentTile2 = gs.tileM.getLayer(rightTileCol, bottomTileRow, TileManager.highestLayer);
            if(gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                entity.setCollisionOn(true);
                return DOWN;
            }
        }
        if(direction.equals("left")) {
            leftTileCol = (entityLeftWorldX - entity.getSpeed()) / gs.getTile();
            adjacentTile1 = gs.tileM.getLayer(leftTileCol, topTileRow, TileManager.highestLayer);
            adjacentTile2 = gs.tileM.getLayer(leftTileCol, bottomTileRow, TileManager.highestLayer);
            if(gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                entity.setCollisionOn(true);
                return LEFT;
            }
        }
        if(direction.equals("right")) {
            rightTileCol = (entityRightWorldX + entity.getSpeed()) / gs.getTile();
            adjacentTile1 = gs.tileM.getLayer(rightTileCol, topTileRow, TileManager.highestLayer);
            adjacentTile2 = gs.tileM.getLayer(rightTileCol, bottomTileRow, TileManager.highestLayer);
            if(gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                entity.setCollisionOn(true);
                return RIGHT;
            }
        }
        return -1;
    }

    public void checkEntityWithTile(Entity entity) {
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height;

        int leftTileCol = entityLeftWorldX / gs.getTile();
        int rightTileCol = entityRightWorldX / gs.getTile();
        int topTileRow = entityTopWorldY / gs.getTile();
        int bottomTileRow = entityBottomWorldY / gs.getTile();

        int adjacentTile1, adjacentTile2;
        Rectangle rect1,rect2;
        switch (entity.getDirection())
        {
            case "up":
                topTileRow = (entityTopWorldY - entity.getSpeed()) / gs.getTile();
                adjacentTile1 = gs.tileM.getLayer(leftTileCol, topTileRow, TileManager.highestLayer);
                adjacentTile2 = gs.tileM.getLayer(rightTileCol, topTileRow, TileManager.highestLayer);
//                rect1 = new Rectangle(leftTileCol*gs.getTile(),topTileRow*gs.getTile(),gs.tileM.getTile(adjacentTile1).getBounds().width,gs.tileM.getTile(adjacentTile1).getBounds().height);
//                rect2 = new Rectangle(rightTileCol*gs.getTile(),topTileRow*gs.getTile(),gs.tileM.getTile(adjacentTile2).getBounds().width,gs.tileM.getTile(adjacentTile2).getBounds().height);
//                if (
//                        (gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision)
//                                && (entity.getBounds().intersects(rect1) || entity.getBounds().intersects(rect2))
//                ){
                if(gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                    entity.setCollisionOn(true);
                }
                break;
            case "down":
                bottomTileRow = (entityBottomWorldY + entity.getSpeed()) / gs.getTile();
                adjacentTile1 = gs.tileM.getLayer(leftTileCol, bottomTileRow, TileManager.highestLayer);
                adjacentTile2 = gs.tileM.getLayer(rightTileCol, bottomTileRow, TileManager.highestLayer);
//                rect1 = new Rectangle(leftTileCol*gs.getTile(),bottomTileRow*gs.getTile(),gs.tileM.getTile(adjacentTile1).getBounds().width,gs.tileM.getTile(adjacentTile1).getBounds().height);
//                rect2 = new Rectangle(rightTileCol*gs.getTile(),bottomTileRow*gs.getTile(),gs.tileM.getTile(adjacentTile2).getBounds().width,gs.tileM.getTile(adjacentTile2).getBounds().height);
//                if (
//                        (gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision)
//                                && (entity.getBounds().intersects(rect1) || entity.getBounds().intersects(rect2))
//                ){
                if(gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                    entity.setCollisionOn(true);
                }
                break;
            case "left":
                leftTileCol = (entityLeftWorldX - entity.getSpeed()) / gs.getTile();
                adjacentTile1 = gs.tileM.getLayer(leftTileCol, topTileRow, TileManager.highestLayer);
                adjacentTile2 = gs.tileM.getLayer(leftTileCol, bottomTileRow, TileManager.highestLayer);
//                rect1 = new Rectangle(leftTileCol*gs.getTile(),topTileRow*gs.getTile(),gs.tileM.getTile(adjacentTile1).getBounds().width,gs.tileM.getTile(adjacentTile1).getBounds().height);
//                rect2 = new Rectangle(leftTileCol*gs.getTile(),bottomTileRow*gs.getTile(),gs.tileM.getTile(adjacentTile2).getBounds().width,gs.tileM.getTile(adjacentTile2).getBounds().height);
//                if (
//                        (gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision)
//                                && (entity.getBounds().intersects(rect1) || entity.getBounds().intersects(rect2))
//                ){
                if(gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                    entity.setCollisionOn(true);
                }
                break;
            case "right":
                rightTileCol = (entityRightWorldX + entity.getSpeed()) / gs.getTile();
                adjacentTile1 = gs.tileM.getLayer(rightTileCol, topTileRow, TileManager.highestLayer);
                adjacentTile2 = gs.tileM.getLayer(rightTileCol, bottomTileRow, TileManager.highestLayer);
//                rect1 = new Rectangle(rightTileCol*gs.getTile(),topTileRow*gs.getTile(),gs.tileM.getTile(adjacentTile1).getBounds().width,gs.tileM.getTile(adjacentTile1).getBounds().height);
//                rect2 = new Rectangle(rightTileCol*gs.getTile(),bottomTileRow*gs.getTile(),gs.tileM.getTile(adjacentTile2).getBounds().width,gs.tileM.getTile(adjacentTile2).getBounds().height);
//                if (
//                        (gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision)
//                                && (entity.getBounds().intersects(rect1) || entity.getBounds().intersects(rect2))
//                ){
                if(gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                    entity.setCollisionOn(true);
                }
                break;
        }
    }
    public void checkPlayerWithTile(Entity entity) {
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height;

        int leftTileCol = entityLeftWorldX / gs.getTile();
        int rightTileCol = entityRightWorldX / gs.getTile();
        int topTileRow = entityTopWorldY / gs.getTile();
        int bottomTileRow = entityBottomWorldY / gs.getTile();

        int adjacentTile1, adjacentTile2, adjacentTile3, adjacentTile4;

        switch (entity.getDirection()) {
            case "up" : {
                topTileRow = (entityTopWorldY - entity.getSpeed()) / gs.getTile();
                adjacentTile1 = gs.tileM.getLayer(leftTileCol, topTileRow, TileManager.highestLayer);
                adjacentTile2 = gs.tileM.getLayer(rightTileCol, topTileRow, TileManager.highestLayer);
                if(gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                    entity.setCollisionOn(true);
                }
            }
            break;
            case "down" : {
                bottomTileRow = (entityBottomWorldY + entity.getSpeed()) / gs.getTile();
                adjacentTile1 = gs.tileM.getLayer(leftTileCol, bottomTileRow, TileManager.highestLayer);
                adjacentTile2 = gs.tileM.getLayer(rightTileCol, bottomTileRow, TileManager.highestLayer);
                if(gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                    entity.setCollisionOn(true);
                }
            }
            break;
            case "left" : {
                leftTileCol = (entityLeftWorldX - entity.getSpeed()) / gs.getTile();
                adjacentTile1 = gs.tileM.getLayer(leftTileCol, topTileRow, TileManager.highestLayer);
                adjacentTile2 = gs.tileM.getLayer(leftTileCol, bottomTileRow, TileManager.highestLayer);
                if(gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                    entity.setCollisionOn(true);
                }
            }
            break;
            case "right" : {
                rightTileCol = (entityRightWorldX + entity.getSpeed()) / gs.getTile();
                adjacentTile1 = gs.tileM.getLayer(rightTileCol, topTileRow, TileManager.highestLayer);
                adjacentTile2 = gs.tileM.getLayer(rightTileCol, bottomTileRow, TileManager.highestLayer);
                if(gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                    entity.setCollisionOn(true);
                }
            }
            break;
            case "up-left" : {
                topTileRow = (entityTopWorldY - entity.getSpeed()) / gs.getTile();
                leftTileCol = (entityLeftWorldX - entity.getSpeed()) / gs.getTile();
                adjacentTile1 = gs.tileM.getLayer(leftTileCol, topTileRow, TileManager.highestLayer);
                adjacentTile2 = gs.tileM.getLayer(rightTileCol, topTileRow, TileManager.highestLayer);
                adjacentTile3 = gs.tileM.getLayer(leftTileCol, topTileRow, TileManager.highestLayer);
                adjacentTile4 = gs.tileM.getLayer(leftTileCol, bottomTileRow, TileManager.highestLayer);
                if((gs.tileM.getTile(adjacentTile1).collision
                        || gs.tileM.getTile(adjacentTile2).collision
                        || gs.tileM.getTile(adjacentTile3).collision
                    || gs.tileM.getTile(adjacentTile4).collision))
                    entity.setCollisionOn(true);
            }
            break;
            case "up-right": {
                topTileRow = (entityTopWorldY - entity.getSpeed()) / gs.getTile();
                rightTileCol = (entityRightWorldX + entity.getSpeed()) / gs.getTile();
                adjacentTile1 = gs.tileM.getLayer(leftTileCol, topTileRow, TileManager.highestLayer);
                adjacentTile2 = gs.tileM.getLayer(rightTileCol, topTileRow, TileManager.highestLayer);
                adjacentTile3 = gs.tileM.getLayer(rightTileCol, topTileRow, TileManager.highestLayer);
                adjacentTile4 = gs.tileM.getLayer(rightTileCol, bottomTileRow, TileManager.highestLayer);

                if((gs.tileM.getTile(adjacentTile1).collision
                        || gs.tileM.getTile(adjacentTile2).collision
                        || gs.tileM.getTile(adjacentTile3).collision
                        || gs.tileM.getTile(adjacentTile4).collision))
                    entity.setCollisionOn(true);

            }
            break;
            case "down-left": {
                bottomTileRow = (entityBottomWorldY + entity.getSpeed()) / gs.getTile();
                leftTileCol = (entityLeftWorldX - entity.getSpeed()) / gs.getTile();
                adjacentTile1 = gs.tileM.getLayer(leftTileCol, bottomTileRow, TileManager.highestLayer);
                adjacentTile2 = gs.tileM.getLayer(rightTileCol, bottomTileRow, TileManager.highestLayer);
                adjacentTile3 = gs.tileM.getLayer(leftTileCol, topTileRow, TileManager.highestLayer);
                adjacentTile4 = gs.tileM.getLayer(leftTileCol, bottomTileRow, TileManager.highestLayer);
                if((gs.tileM.getTile(adjacentTile1).collision
                        || gs.tileM.getTile(adjacentTile2).collision
                        || gs.tileM.getTile(adjacentTile3).collision
                        || gs.tileM.getTile(adjacentTile4).collision))
                    entity.setCollisionOn(true);

            }
            break;
            case "down-right" : {
                bottomTileRow = (entityBottomWorldY + entity.getSpeed()) / gs.getTile();
                rightTileCol = (entityRightWorldX + entity.getSpeed()) / gs.getTile();
                adjacentTile1 = gs.tileM.getLayer(leftTileCol, bottomTileRow, TileManager.highestLayer);
                adjacentTile2 = gs.tileM.getLayer(rightTileCol, bottomTileRow, TileManager.highestLayer);
                adjacentTile3 = gs.tileM.getLayer(rightTileCol, topTileRow, TileManager.highestLayer);
                adjacentTile4 = gs.tileM.getLayer(rightTileCol, bottomTileRow, TileManager.highestLayer);
                if((gs.tileM.getTile(adjacentTile1).collision
                        || gs.tileM.getTile(adjacentTile2).collision
                        || gs.tileM.getTile(adjacentTile3).collision
                        || gs.tileM.getTile(adjacentTile4).collision))
                    entity.setCollisionOn(true);

            }
            break;
        }
    }
    public void checkPlayerWithCoins(Entity player, List<Entity> coins) {
        gs.quadTree.clear();

        for (Entity m : coins) {
            SeparatingAxis.polygonCollisionDetectFirstStatic(player, m ,false, false);
            Rectangle bounds = m.getBounds();
            RectangleQ bound = new RectangleQ(m.getBounds());
            PointQ p = new PointQ(bounds.x, bounds.y, m, bound);
            gs.quadTree.insert(p);
        }

        RectangleQ playerBounds = new RectangleQ(player.getBounds());
        gs.found = gs.quadTree.query(playerBounds);

        if (gs.found != null) {
            for (PointQ other : gs.found) {
                Entity check = other.getUserData();
                if(player != check) {
                    if (SeparatingAxis.polygonCollisionDetectFirstStatic(player, check, false, false)) {
                        long currentCoin = gs.user.getCoin();
                        gs.user.setCoin( currentCoin + 1);
                        check.setAlive(false);
                    }
                }
            }
        }
    }
    public void checkSkillWithMonster(List<Entity> skills, List<Entity> target) {
        gs.quadTree.clear();

        for(Entity e : target) {
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
                    if (SeparatingAxis.CircleCollisionDetect(check,skill,false,false)) {
                        skill.setAlive(false);
                        int newHP = check.getHP();
                        newHP -= skill.getDamage() + gs.player.getDamage();
                        check.setHP(newHP);
                        if(check.getHP() <= 0) {
                            check.setAlive(false);
                        }
                        if(!check.getAlive()) {
                            check.generateCoin();
                        }
                    }
                }
            }
        }

    }
    public void checkMonsterWithMonster(Entity thisMonster,List<Entity> monsters) {
        gs.quadTree.clear();
        for (Entity m : monsters) {
            if(m != thisMonster) {
                Rectangle bounds = m.getBounds();
                RectangleQ bound = new RectangleQ(m.getBounds());
                PointQ p = new PointQ(bounds.x, bounds.y, m, bound);
                gs.quadTree.insert(p);
            }
        }
        RectangleQ playerBounds = new RectangleQ(thisMonster.getBounds());
        gs.found = gs.quadTree.query(playerBounds);
        if (gs.found != null) {
            for (PointQ other : gs.found) {
                Entity check = other.getUserData();
                if(thisMonster != check) {
                    SeparatingAxis.polygonCollisionDetectFirstStatic(thisMonster, check, true, true);
                }
            }
        }
    }
    public void checkPlayerAndMonsters(Entity player, List<Entity> monsters) {
        gs.quadTree.clear();

        for (Entity m : monsters) {
            Rectangle bounds = m.getBounds();
            RectangleQ bound = new RectangleQ(m.getBounds());
            PointQ p = new PointQ(bounds.x, bounds.y, m, bound);
            gs.quadTree.insert(p);
        }

        RectangleQ playerBounds = new RectangleQ(player.getBounds());
        gs.found = gs.quadTree.query(playerBounds);

        if (gs.found != null) {
            for (PointQ other : gs.found) {
                Entity check = other.getUserData();
                if (player != check) {
                    if (SeparatingAxis.polygonCollisionDetectFirstStatic(player, check, false, true)) {
                        if (player.isCanTouch()) {
                            int newHP = player.getHP() - check.getDamage();
                            player.setHP(newHP);
                            player.setCanTouch(false);
                        }
                        if (player.getHP() <= 0) {
                            player.setAlive(false);
                        }
                    }
                }
            }
        }
    }

}
