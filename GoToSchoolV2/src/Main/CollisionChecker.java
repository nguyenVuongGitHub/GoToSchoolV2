package Main;
import AttackSkill.ATTACK_SKILL;
import Entity.*;
import Quadtree.PointQ;
import Quadtree.QuadTree;
import Quadtree.RectangleQ;
import CollisionSystem.*;
import tile.TileManager;

import java.awt.*;
import java.util.ArrayList;
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
    private boolean checkOutOfIndex(int index, int value) {
        return value < 0 || value >= index;
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
        switch (entity.getDirection())
        {
            case "up":
                topTileRow = (entityTopWorldY - entity.getSpeed()) / gs.getTile();

                if(checkOutOfIndex(64,topTileRow) || checkOutOfIndex(64, leftTileCol) || checkOutOfIndex(64, rightTileCol)) {
                    entity.setAlive(false);
                    break;
                }
                adjacentTile1 = gs.tileM.getLayer(leftTileCol, topTileRow, TileManager.highestLayer);
                adjacentTile2 = gs.tileM.getLayer(rightTileCol, topTileRow, TileManager.highestLayer);

                if(gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                    entity.setCollisionOn(true);
                }
                break;
            case "down":
                bottomTileRow = (entityBottomWorldY + entity.getSpeed()) / gs.getTile();
                if(checkOutOfIndex(64,bottomTileRow) || checkOutOfIndex(64, leftTileCol) || checkOutOfIndex(64, rightTileCol)) {
                    entity.setAlive(false);
                    break;
                }
                adjacentTile1 = gs.tileM.getLayer(leftTileCol, bottomTileRow, TileManager.highestLayer);
                adjacentTile2 = gs.tileM.getLayer(rightTileCol, bottomTileRow, TileManager.highestLayer);
                if(gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                    entity.setCollisionOn(true);
                }
                break;
            case "left":
                if(checkOutOfIndex(64,topTileRow) || checkOutOfIndex(64, leftTileCol) || checkOutOfIndex(64, bottomTileRow)) {
                    entity.setAlive(false);
                    break;
                }
                leftTileCol = (entityLeftWorldX - entity.getSpeed()) / gs.getTile();
                adjacentTile1 = gs.tileM.getLayer(leftTileCol, topTileRow, TileManager.highestLayer);
                adjacentTile2 = gs.tileM.getLayer(leftTileCol, bottomTileRow, TileManager.highestLayer);
                if(gs.tileM.getTile(adjacentTile1).collision || gs.tileM.getTile(adjacentTile2).collision) {
                    entity.setCollisionOn(true);
                }
                break;
            case "right":
                if(checkOutOfIndex(64,topTileRow) || checkOutOfIndex(64, bottomTileRow) || checkOutOfIndex(64, rightTileCol)) {
                    entity.setAlive(false);
                    break;
                }
                rightTileCol = (entityRightWorldX + entity.getSpeed()) / gs.getTile();
                adjacentTile1 = gs.tileM.getLayer(rightTileCol, topTileRow, TileManager.highestLayer);
                adjacentTile2 = gs.tileM.getLayer(rightTileCol, bottomTileRow, TileManager.highestLayer);
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
        if(coins.isEmpty()) return;

        QuadTree quadTree = new QuadTree(10,gs.boundsQuadTree,gs);

        for (Entity coin : coins) {
            Rectangle bounds = coin.getBounds();
            PointQ point = new PointQ(bounds.x, bounds.y, coin);
            quadTree.insert(point);
        }

        RectangleQ playerBounds = new RectangleQ(player.getBounds());
        List<PointQ> pointsNearPlayer = quadTree.query(playerBounds);
        for (PointQ point : pointsNearPlayer) {
            Entity other = point.getUserData();
            if(player != other) {
                if (SeparatingAxis.polygonCollisionDetectFirstStatic(player, other, false, false)) {
                    if(gs.state == State.CAMPAIGN)
                    {
                        long currentCoin = gs.user.getCoin();
                        long currentExperience = gs.user.getExperience();
                        gs.user.setExperience(currentExperience + 1);
                        gs.user.setCoin( currentCoin + 1);
                    }
                    else
                    {
                        gs.user.setSurvivalCoin(gs.user.getSurvivalCoin() + 1);
                    }
                    other.setAlive(false);
                    gs.playSE(5);
                }
            }
        }
    }
    private void checkSkillWithMonster(List<Entity> skills, List<Entity> monsters) {
        if(skills.isEmpty()) return;

        QuadTree quadTree = new QuadTree(5,gs.boundsQuadTree,gs);

        for(Entity monster : monsters) {
            Rectangle bound = monster.getBounds();
            PointQ point = new PointQ(bound.x ,bound.y ,monster);
            quadTree.insert(point);
        }
        for(Entity skill : skills) {
            int padding = 600;
            int x = skill.getBounds().x - padding;
            int y = skill.getBounds().y - padding;
            int w = skill.getBounds().width + padding*2;
            int h = skill.getBounds().height + padding*2;
            RectangleQ range = new RectangleQ(x,y,w,h);
            List<PointQ> pointsNearSkill =  quadTree.query(range);
            for(PointQ point : pointsNearSkill) {
                Entity other = point.getUserData();
                if(skill.getTypeSkill().typeAttack == ATTACK_SKILL.NORMAL
                        || skill.getTypeSkill().typeAttack == ATTACK_SKILL.ARROW_LIGHT
                        || skill.getTypeSkill().typeAttack == ATTACK_SKILL.MULTI_ARROW
                        || skill.getTypeSkill().typeAttack == ATTACK_SKILL.MOON_LIGHT
                        || skill.getTypeSkill().typeAttack == ATTACK_SKILL.PURPLE
                ) {
                    if(skill.getAlive()) {
                        if(SeparatingAxis.polygonCollisionDetectFirstStatic(skill,other,false,false)) {
                            if(skill.getTypeSkill().typeAttack != ATTACK_SKILL.MOON_LIGHT) {
                                skill.setAlive(false);
                            }
                            int newHP = other.getHP();
                            newHP -= skill.getDamage();
                            other.setHP(newHP);
                        }
                    }
                }
                else if(skill.getTypeSkill().typeAttack == ATTACK_SKILL.CIRCLE_FIRE
                ) {
                    if(skill.getAlive()) {
                        if(SeparatingAxis.CirclePolygonCollisionDectect(skill,other,false,false)) {
                            int newHP = other.getHP();
                            newHP -= skill.getDamage();
                            other.setHP(newHP);
                        }
                    }
                }
            }
        }
        quadTree.clear();
    }
    private void checkMonsterWithMonster(List<Entity> monsters) {
        QuadTree quadTree = new QuadTree(50,gs.boundsQuadTree,gs);

        for (Entity m : monsters) {
            Rectangle bounds = m.getBounds();
            PointQ p = new PointQ(bounds.x + bounds.width/2, bounds.y + bounds.height/2, m);
            quadTree.insert(p);
        }

        for(Entity monster : monsters) {
            // khoảng range lớn hơn khoảng va chạm phần đệm là 30
            int padding = 30;
            int x = monster.getBounds().x - padding;
            int y = monster.getBounds().y - padding;
            int w = monster.getBounds().width + padding*2;
            int h = monster.getBounds().height + padding*2;
            RectangleQ range = new RectangleQ(x,y,w,h);
            List<PointQ> points = quadTree.query(range);
            for (PointQ point : points) {
                Entity other = point.getUserData();
                if(monster != other) {
                    SeparatingAxis.polygonCollisionDetectFirstStatic(monster, other, true, true);
                }
            }
        }
        quadTree.clear();
    }

    private void checkPlayerAndMonsters(Entity player, List<Entity> monsters) {
        QuadTree quadTree = new QuadTree(20,gs.boundsQuadTree,gs);

        for (Entity m : monsters) {
            Rectangle bounds = m.getBounds();
            PointQ p = new PointQ(bounds.x + bounds.width/2, bounds.y + bounds.height/2, m);
            quadTree.insert(p);
        }
        // khoảng range lớn hơn khoảng va chạm của người chơi
        int padding = 600;
        int x = player.getBounds().x - padding;
        int y = player.getBounds().y - padding;
        int w = player.getBounds().width + padding*2;
        int h = player.getBounds().height + padding*2;
        RectangleQ range = new RectangleQ(x,y,w,h);
        List<PointQ> points = quadTree.query(range);

        for (PointQ point : points) {
            Entity other = point.getUserData();

            if (SeparatingAxis.polygonCollisionDetectFirstStatic(player, other, false, true)) {
                if (player.isCanTouch()) {
                    gs.playSE(14);
                    int newHP = player.getHP() - other.getDamage();
                            player.setHP(newHP);
                    player.setCanTouch(false);
                }
                if (player.getHP() <= 0) {
                    gs.playSE(14);
                    player.setAlive(false);
                }
            }
        }
        quadTree.clear();

    }
    private void checkSkeletonWeaponWithPlayer(Entity player, List<Entity> skeletonAttacks) {
        QuadTree quadTree = new QuadTree(20,gs.boundsQuadTree,gs);

        for (Entity sa : skeletonAttacks) {
            Rectangle bounds = sa.getBounds();
            PointQ point = new PointQ(bounds.x + bounds.width/2, bounds.y + bounds.height/2, sa);
            quadTree.insert(point);
        }

        RectangleQ range = new RectangleQ(player.getBounds());
        List<PointQ> points = quadTree.query(range);
        for (PointQ point : points) {
            Entity other = point.getUserData();
            if (SeparatingAxis.polygonCollisionDetectFirstStatic(player, other, false, false)) {
                other.setAlive(false);
                if (player.isCanTouch()) {
                    gs.playSE(14);
                    int newHP = player.getHP() - other.getDamage();
                    player.setHP(newHP);
                    player.setCanTouch(false);
                }
                if (player.getHP() <= 0) {
                    gs.playSE(14);
                    player.setAlive(false);
                }
            }
        }
        quadTree.clear();
    }
    private void checkPlayerWithLazeboss(Entity player, Entity lazerBoss) {
        if (SeparatingAxis.polygonCollisionDetectFirstStatic(player, lazerBoss, false, false) && lazerBoss.getSpriteNum() >= 9) {
            if (player.isCanTouch()) {
                gs.playSE(14);
                int newHP = player.getHP() - lazerBoss.getDamage();
                player.setHP(newHP);
                player.setCanTouch(false);
            }
            if (player.getHP() <= 0) {
                gs.playSE(14);
                player.setAlive(false);
            }
        }
    }
    public void checkAllEntity(Entity player, List<Entity> monsters, List<Entity> skills, List<Entity> coins, List<Entity> skeletonAttacks,List<Entity> lazerBoss) {
        checkPlayerAndMonsters(player,monsters);
        checkMonsterWithMonster(monsters);
        checkSkillWithMonster(skills,monsters);
        checkPlayerWithCoins(player,coins);
        checkSkeletonWeaponWithPlayer(player,skeletonAttacks);
        for(int i = 0; i< lazerBoss.size(); i++) {
            if(lazerBoss.get(i) != null) {
                checkPlayerWithLazeboss(player,lazerBoss.get(i));
            }
        }
    }
}
