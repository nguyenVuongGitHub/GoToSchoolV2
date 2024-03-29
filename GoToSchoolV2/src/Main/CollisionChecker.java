package Main;
import Entity.Entity;
import Quadtree.PointQ;
import Quadtree.RectangleQ;
import CollisionSystem.*;

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
        if(entity.getBounds().intersects(eventRect)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param entity
     * @param direction
     * @return 1 - Up, 2 - down, 3 - left, 4 - right, -1 - null
     */
    public int checkEntityWithTile(Entity entity, String direction) {
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() - entity.getSolidArea().x + entity.getBounds().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() - entity.getSolidArea().y + entity.getBounds().height;

        int leftTileCol = entityLeftWorldX / gs.getTile();
        int rightTileCol = entityRightWorldX / gs.getTile();
        int topTileRow = entityTopWorldY / gs.getTile();
        int bottomTileRow = entityBottomWorldY / gs.getTile();

        int adjacentTile1, adjacentTile2;

        if(direction.equals("up")) {
            topTileRow = (entityTopWorldY - entity.getSpeed()) / gs.getTile();
            adjacentTile1 = gs.tileM.getMapTileNum(leftTileCol, topTileRow);
            adjacentTile2 = gs.tileM.getMapTileNum(rightTileCol, topTileRow);
            if (gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                entity.setCollisionOn(true);
                return UP;
            }
        }
        if(direction.equals("down")) {
            bottomTileRow = (entityBottomWorldY + entity.getSpeed()) / gs.getTile();
            adjacentTile1 = gs.tileM.getMapTileNum(leftTileCol, bottomTileRow);
            adjacentTile2 = gs.tileM.getMapTileNum(rightTileCol, bottomTileRow);
            if (gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                entity.setCollisionOn(true);
                return DOWN;
            }
        }
        if(direction.equals("left")) {
            leftTileCol = (entityLeftWorldX - entity.getSpeed()) / gs.getTile();
            adjacentTile1 = gs.tileM.getMapTileNum(leftTileCol, topTileRow);
            adjacentTile2 = gs.tileM.getMapTileNum(leftTileCol, bottomTileRow);
            if (gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                entity.setCollisionOn(true);
                return LEFT;
            }
        }
        if(direction.equals("right")) {
            rightTileCol = (entityRightWorldX + entity.getSpeed()) / gs.getTile();
            adjacentTile1 = gs.tileM.getMapTileNum(rightTileCol, topTileRow);
            adjacentTile2 = gs.tileM.getMapTileNum(rightTileCol, bottomTileRow);
            if (gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                entity.setCollisionOn(true);
                return RIGHT;
            }
        }


//        switch (direction1)
//        {
//            case "up":
//                topTileRow = (entityTopWorldY - entity.getSpeed()) / gs.getTile();
//                adjacentTile1 = gs.tileM.getMapTileNum(leftTileCol, topTileRow);
//                adjacentTile2 = gs.tileM.getMapTileNum(rightTileCol, topTileRow);
//                if (gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
//                    entity.setCollisionOn(true);
//                    return 1;
//                }
//                break;
//            case "down":
//                bottomTileRow = (entityBottomWorldY + entity.getSpeed()) / gs.getTile();
//                adjacentTile1 = gs.tileM.getMapTileNum(leftTileCol, bottomTileRow);
//                adjacentTile2 = gs.tileM.getMapTileNum(rightTileCol, bottomTileRow);
//                if (gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
//                    entity.setCollisionOn(true);
//                    return 2;
//                }
//                break;
//            case "left":
//                leftTileCol = (entityLeftWorldX - entity.getSpeed()) / gs.getTile();
//                adjacentTile1 = gs.tileM.getMapTileNum(leftTileCol, topTileRow);
//                adjacentTile2 = gs.tileM.getMapTileNum(leftTileCol, bottomTileRow);
//                if (gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
//                    entity.setCollisionOn(true);
//                    return 3;
//                }
//                break;
//            case "right":
//                rightTileCol = (entityRightWorldX + entity.getSpeed()) / gs.getTile();
//                adjacentTile1 = gs.tileM.getMapTileNum(rightTileCol, topTileRow);
//                adjacentTile2 = gs.tileM.getMapTileNum(rightTileCol, bottomTileRow);
//                if (gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
//                    entity.setCollisionOn(true);
//                    return 4;
//                }
//                break;
//        }
        return -1;
    }

    public int checkEntityWithTile(Entity entity) {
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() - entity.getSolidArea().x + entity.getBounds().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() - entity.getSolidArea().y + entity.getBounds().height;

        int leftTileCol = entityLeftWorldX / gs.getTile();
        int rightTileCol = entityRightWorldX / gs.getTile();
        int topTileRow = entityTopWorldY / gs.getTile();
        int bottomTileRow = entityBottomWorldY / gs.getTile();

        int adjacentTile1, adjacentTile2;

        switch (entity.getDirection())
        {
            case "up":
                topTileRow = (entityTopWorldY - entity.getSpeed()) / gs.getTile();
                adjacentTile1 = gs.tileM.getMapTileNum(leftTileCol, topTileRow);
                adjacentTile2 = gs.tileM.getMapTileNum(rightTileCol, topTileRow);
                if (gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                    entity.setCollisionOn(true);
                    return 1;
                }
                break;
            case "down":
                bottomTileRow = (entityBottomWorldY + entity.getSpeed()) / gs.getTile();
                adjacentTile1 = gs.tileM.getMapTileNum(leftTileCol, bottomTileRow);
                adjacentTile2 = gs.tileM.getMapTileNum(rightTileCol, bottomTileRow);
                if (gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                    entity.setCollisionOn(true);
                    return 2;
                }
                break;
            case "left":
                leftTileCol = (entityLeftWorldX - entity.getSpeed()) / gs.getTile();
                adjacentTile1 = gs.tileM.getMapTileNum(leftTileCol, topTileRow);
                adjacentTile2 = gs.tileM.getMapTileNum(leftTileCol, bottomTileRow);
                if (gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                    entity.setCollisionOn(true);
                    return 3;
                }
                break;
            case "right":
                rightTileCol = (entityRightWorldX + entity.getSpeed()) / gs.getTile();
                adjacentTile1 = gs.tileM.getMapTileNum(rightTileCol, topTileRow);
                adjacentTile2 = gs.tileM.getMapTileNum(rightTileCol, bottomTileRow);
                if (gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                    entity.setCollisionOn(true);
                    return 4;
                }
                break;
        }
        return -1;
    }
    public void checkPlayerWithTile(Entity entity) {
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() - entity.getSolidArea().x + entity.getBounds().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() - entity.getSolidArea().y + entity.getBounds().height;

        int leftTileCol = entityLeftWorldX / gs.getTile();
        int rightTileCol = entityRightWorldX / gs.getTile();
        int topTileRow = entityTopWorldY / gs.getTile();
        int bottomTileRow = entityBottomWorldY / gs.getTile();

        int adjacentTile1, adjacentTile2, adjacentTile3, adjacentTile4
                , adjacentTile5, adjacentTile6, adjacentTile7, adjacentTile8;

        if(gs.keyHandle.isUpPress())
            topTileRow = (entityTopWorldY - entity.getSpeed()) / gs.getTile();
        adjacentTile1 = gs.tileM.getMapTileNum(leftTileCol, topTileRow);
        adjacentTile2 = gs.tileM.getMapTileNum(rightTileCol, topTileRow);

        if(gs.keyHandle.isDownPress())
            bottomTileRow = (entityBottomWorldY + entity.getSpeed()) / gs.getTile();
        adjacentTile3 = gs.tileM.getMapTileNum(leftTileCol, bottomTileRow);
        adjacentTile4 = gs.tileM.getMapTileNum(rightTileCol, bottomTileRow);

        if(gs.keyHandle.isLeftPress())
            leftTileCol = (entityLeftWorldX - entity.getSpeed()) / gs.getTile();
        adjacentTile5 = gs.tileM.getMapTileNum(leftTileCol, topTileRow);
        adjacentTile6 = gs.tileM.getMapTileNum(leftTileCol, bottomTileRow);

        if(gs.keyHandle.isRightPress())
            rightTileCol = (entityRightWorldX + entity.getSpeed()) / gs.getTile();
        adjacentTile7 = gs.tileM.getMapTileNum(rightTileCol, topTileRow);
        adjacentTile8 = gs.tileM.getMapTileNum(rightTileCol, bottomTileRow);

        if (gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision ||
                gs.tileM.getTile(adjacentTile3).collision || gs.tileM.getTile(adjacentTile4).collision ||
                gs.tileM.getTile(adjacentTile5).collision || gs.tileM.getTile(adjacentTile6).collision ||
                gs.tileM.getTile(adjacentTile7).collision || gs.tileM.getTile(adjacentTile8).collision) {
            entity.setCollisionOn(true);
        }

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
    public void checkMonsterWithMonster(Entity thisMonster,List<Entity> monsters) {
        gs.quadTree.clear();
        for (Entity m : monsters) {
            if(m != thisMonster) {
                SeparatingAxis.polygonCollisionDetectFirstStatic(thisMonster, m ,true, true);
//                if(!m.getCollision()) {
//                    m.setCollision(false);
//                }
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
                    if(SeparatingAxis.polygonCollisionDetectFirstStatic(thisMonster, check, true, true)) {
                        thisMonster.setCollision(true);
                        check.setCollision(true);
                    }else {
                        thisMonster.setCollision(false);
                        check.setCollision(false);
                    }
                }
            }
        }
    }
    public void checkPlayerAndMonsters(Entity player, List<Entity> monsters) {
        gs.quadTree.clear();

        for (Entity m : monsters) {
            SeparatingAxis.polygonCollisionDetectFirstStatic(player, m ,false, true);
//            m.setCollision(false);
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
                    if (SeparatingAxis.polygonCollisionDetectFirstStatic(player, check, false, true)) {
                        player.setCollision(true);
                        check.setCollision(true);
                    }else {
                        player.setCollision(false);
                        check.setCollision(false);
                    }
                }
            }
        }
    }

}
