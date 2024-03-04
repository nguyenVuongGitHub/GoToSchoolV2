package Main;
import Entity.Entity;
import Quadtree.PointQ;
import Quadtree.RectangleQ;
import CollisionSystem.*;

import java.util.List;

public class CollisionChecker {
    GameState gs;
    public CollisionChecker(GameState gs) {
        this.gs = gs;
    }
    public void checkBulletWithMonster(List<Entity> bullets, List<Entity> target) {
        gs.quadTree.clear();

        for(Entity e : target) {
            e.setCollision(false);
            RectangleQ bound = new RectangleQ(e.getBounds().x,e.getBounds().y,e.getBounds().width,e.getBounds().height);
            PointQ p = new PointQ(e.getBounds().x,e.getBounds().y,e,bound);
            gs.quadTree.insert(p);
        }
        for(Entity bullet : bullets) {
            RectangleQ bound = new RectangleQ(bullet.getBounds().x - 20 ,bullet.getBounds().y - 20,bullet.getBounds().width + 20,bullet.getBounds().height + 20);
            gs.found = gs.quadTree.query(bound);
            if(gs.found != null) {
                for(PointQ other : gs.found) {
                    Entity check = other.getUserData();
                    if(bullet != check && bullet.getAlive() && check.getAlive()) {
                        if (bullet.getBounds().intersects(check.getBounds())) {
                            bullet.setAlive(false);
                            int newHP = check.getHP();
                            newHP -= bullet.getDame() + gs.player.getDame();
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

    public void checkEntity(Entity user, List<Entity> target) {
        gs.quadTree.clear();

        for(Entity e : target) {
            e.setCollision(false);
            RectangleQ bound = new RectangleQ(e.getBounds().x,e.getBounds().y,e.getBounds().width,e.getBounds().height);
            PointQ p = new PointQ(e.getBounds().x,e.getBounds().y,e,bound);
            gs.quadTree.insert(p);
        }


        RectangleQ bound = new RectangleQ(user.getBounds().x,user.getBounds().y,user.getBounds().width,user.getBounds().height);
        gs.found = gs.quadTree.query(bound);
        if(gs.found != null) {
            for(PointQ other : gs.found) {

                Entity check = other.getUserData();

                if(user == check) {

                    if (SeparatingAxis.polygonCollisionDetectFirstStatic(user, check)) {

                        user.setCollision(true);
                        check.setCollision(true);
                    }
                }
            }
        }
    }
}
