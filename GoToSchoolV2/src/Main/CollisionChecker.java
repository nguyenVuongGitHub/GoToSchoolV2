package Main;
import Entity.Entity;
import Quadtree.PointQ;
import Quadtree.RectangleQ;

public class CollisionChecker {
    GameState gs;
    public CollisionChecker(GameState gs) {
        this.gs = gs;
    }

    public void checkEntity(Entity[] target) {
        gs.quadTree.clear();

        for(Entity e : target) {
            e.setCollision(false);
            RectangleQ bound = new RectangleQ(e.getBounds().x,e.getBounds().y,e.getBounds().width,e.getBounds().height);
            PointQ p = new PointQ(e.getBounds().x,e.getBounds().y,e,bound);
            gs.quadTree.insert(p);
        }

        for (Entity e : target) {
            RectangleQ bound = new RectangleQ(e.getBounds().x,e.getBounds().y,e.getBounds().width,e.getBounds().height);
            gs.found = gs.quadTree.query(bound);
            if(gs.found != null) {
                for(PointQ other : gs.found) {
                    Entity check = other.getUserData();
                    if(e != check) {
                        if (e.getBounds().intersects(check.getBounds())) {
                            e.setCollision(true);
                            check.setCollision(true);
                        }
                    }
                }
            }
        }
    }
}
