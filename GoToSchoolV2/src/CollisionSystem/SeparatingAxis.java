package CollisionSystem;

import Entity.Entity;
import Main.GameState;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.stream.Collectors;


public class SeparatingAxis {
    static GameState gs;
    public SeparatingAxis(GameState gs) {
        SeparatingAxis.gs = gs;
    }
    final static double INF = 999999999;
    /**
     * Check collision of two entity polygon by using SAT
     * @param e1 Polygon
     * @param e2 Polygon
     * @param move1 Entity 1 is able to move (automatically correct location)
     * @param move2 Entity 1 is able to move (automatically correct location)
     * @return {boolean} True when two entity are collided. Otherwise, return false
     * */
    public static boolean polygonCollisionDetectFirstStatic(Entity e1, Entity e2, boolean move1, boolean move2)
    {
        double depth = INF;
        PointX normal = null;
        for(int i=0; i<e1.getVertices().size(); i++)
        {
            PointX va = e1.getVertices().get(i);
            PointX vb = e1.getVertices().get((i+1) % e1.getVertices().size());
            PointX edge = vb.minusVector(va);
            PointX axis = new PointX(-edge.y, edge.x);
            axis = axis.normalize();

            double min1 = INF;
            double max1 = -INF;

            double proj;

            for(int j=0; j<e1.getVertices().size(); j++)
            {
                proj = axis.dot(e1.getVertices().get(j));
                if(proj < min1)
                {
                    min1 = proj;
                }
                if(proj > max1)
                {
                    max1 = proj;
                }
            }

            double min2 = INF;
            double max2 = -INF;

            for(int j=0; j<e2.getVertices().size(); j++)
            {
                proj = axis.dot(e2.getVertices().get(j));
                if(proj < min2)
                {
                    min2 = proj;
                }

                if(proj > max2)
                {
                    max2 = proj;
                }
            }
            if(min1 >= max2 || min2 >= max1)
            {
                return false;
            }
            
            double axisDepth = Math.min(max2 - min1, max1 - min2);
            
            if(axisDepth < depth)
            {
                depth = axisDepth;
                normal = axis;
            }
        }
        for(int i=0; i<e2.getVertices().size(); i++)
        {
            PointX va = e2.getVertices().get(i);
            PointX vb = e2.getVertices().get((i+1) % e2.getVertices().size());
            PointX edge = vb.minusVector(va);
            PointX axis = new PointX(-edge.y, edge.x);
            axis = axis.normalize();

            double min1 = INF;
            double max1 = -INF;

            double proj;

            for(int j=0; j<e1.getVertices().size(); j++)
            {
                proj = axis.dot(e1.getVertices().get(j));
                if(proj < min1)
                {
                    min1 = proj;
                }
                if(proj > max1)
                {
                    max1 = proj;
                }
            }

            double min2 = INF;
            double max2 = -INF;

            for(int j=0; j<e2.getVertices().size(); j++)
            {
                proj = axis.dot(e2.getVertices().get(j));
                if(proj < min2)
                {
                    min2 = proj;
                }
                if(proj > max2)
                {
                    max2 = proj;
                }
            }

            if(min1 >= max2 || min2 >= max1)
            {
                return false;
            }

            double axisDepth = Math.min(max2 - min1, max1 - min2);

            if(axisDepth < depth)
            {
                depth = axisDepth;
                normal = axis;
            }
        }
        e2.setCollision(true);
        e1.setCollision(true);

        PointX centerA = PointX.getCenterPointFromList(e1.getVertices());
        PointX centerB = PointX.getCenterPointFromList(e2.getVertices());

        PointX direction = centerB.minusVector(centerA);

        if(normal.dot(direction) < 0)
        {
            normal = normal.multipleVector(-1);
        }
        if(move1)
        {
            double nextX = e1.getWorldX() - depth / 2 * normal.getX();
            double nextY = e1.getWorldY() - depth /2 * normal.getY();
            int tile = gs.tileM.getLayer((int) nextX / gs.getTile(),(int) nextY / gs.getTile() , TileManager.highestLayer);
            int tile2 = gs.tileM.getLayer((int) (nextX + gs.getTile()) / gs.getTile(),(int) nextY / gs.getTile(), TileManager.highestLayer);
            int tile3 = gs.tileM.getLayer((int) nextX / gs.getTile(),(int) (nextY + gs.getTile()) / gs.getTile(), TileManager.highestLayer);
            int tile4 = gs.tileM.getLayer((int) (nextX + gs.getTile()) / gs.getTile(),(int) (nextY + gs.getTile()) / gs.getTile(), TileManager.highestLayer);
            if(gs.tileM.getWall().stream().noneMatch(w -> w.getValue() == tile) &&
                    gs.tileM.getWall().stream().noneMatch(w -> w.getValue() == tile2) &&
                    gs.tileM.getWall().stream().noneMatch(w -> w.getValue() == tile3) &&
                    gs.tileM.getWall().stream().noneMatch(w -> w.getValue() == tile4)) {
                e1.setWorldX(e1.getWorldX() - depth / 2 * normal.getX());
                e1.setWorldY(e1.getWorldY() - depth /2 * normal.getY());
            }
        }
        if(move2)
        {
            double nextX = e2.getWorldX() + depth / 2 * normal.getX();
            double nextY = e2.getWorldY() + depth /2 * normal.getY();
            int tile = gs.tileM.getLayer((int) nextX / gs.getTile(),(int) nextY / gs.getTile(), TileManager.highestLayer);
            int tile2 = gs.tileM.getLayer((int) (nextX + gs.getTile()) / gs.getTile(),(int) nextY / gs.getTile(), TileManager.highestLayer);
            int tile3 = gs.tileM.getLayer((int) nextX / gs.getTile(),(int) (nextY + gs.getTile()) / gs.getTile(), TileManager.highestLayer);
            int tile4 = gs.tileM.getLayer((int) (nextX + gs.getTile()) / gs.getTile(),(int) (nextY + gs.getTile()) / gs.getTile(), TileManager.highestLayer);
            if(gs.tileM.getWall().stream().noneMatch(w -> w.getValue() == tile) &&
                    gs.tileM.getWall().stream().noneMatch(w -> w.getValue() == tile2) &&
                    gs.tileM.getWall().stream().noneMatch(w -> w.getValue() == tile3) &&
                    gs.tileM.getWall().stream().noneMatch(w -> w.getValue() == tile4)) {
                e2.setWorldX(e2.getWorldX() + depth / 2 * normal.getX());
                e2.setWorldY(e2.getWorldY() + depth /2 * normal.getY());
            }
        }
        return true;
    }
    /**
     * Check collision of two entity circle
     * @param e1 circle
     * @param e2 circle
     * @param move1 Entity 1 is able to move (automatically correct location)
     * @param move2 Entity 2 is able to move (automatically correct location)
     * @return {boolean} True when two entity are collided. Otherwise, return false
     * */
    public static boolean CircleCollisionDetect(Entity e1, Entity e2, boolean move1, boolean move2)
    {
        PointX normal = null;
        double depth = 0;
        PointX center1 = PointX.getCenterPointFromList(e1.getVertices());
        PointX center2 = PointX.getCenterPointFromList(e2.getVertices());
        double distance = center1.distance(center2);
        double sumRadius = e1.getRadius() + e2.getRadius();
        if(distance >= sumRadius)
        {
            return false;
        }
        normal = center1.minusVector(center2).normalize();
        depth = sumRadius - distance;

        if(move1)
        {
            double nextX = e1.getWorldX() + normal.getX() * depth /2;
            double nextY = e1.getWorldY() + normal.getY() * depth /2;
            int tile = gs.tileM.getLayer((int) nextX / gs.getTile(),(int) nextY / gs.getTile(), TileManager.highestLayer);
            int tile2 = gs.tileM.getLayer((int) (nextX + gs.getTile()) / gs.getTile(),(int) nextY / gs.getTile(), TileManager.highestLayer);
            int tile3 = gs.tileM.getLayer((int) nextX / gs.getTile(),(int) (nextY + gs.getTile()) / gs.getTile(), TileManager.highestLayer);
            int tile4 = gs.tileM.getLayer((int) (nextX + gs.getTile()) / gs.getTile(),(int) (nextY + gs.getTile()) / gs.getTile(), TileManager.highestLayer);
            if(gs.tileM.getWall().stream().noneMatch(w -> w.getValue() == tile) &&
                    gs.tileM.getWall().stream().noneMatch(w -> w.getValue() == tile2) &&
                    gs.tileM.getWall().stream().noneMatch(w -> w.getValue() == tile3) &&
                    gs.tileM.getWall().stream().noneMatch(w -> w.getValue() == tile4)) {
                e1.setWorldX(e1.getWorldX() + normal.getX() * depth /2);
                e1.setWorldY(e1.getWorldY() + normal.getY() * depth /2);
            }
        }
        if(move2)
        {
            double nextX = e2.getWorldX() - normal.getX() * depth /2;
            double nextY = e2.getWorldY() - normal.getY() * depth /2;
            int tile = gs.tileM.getLayer((int) nextX / gs.getTile(),(int) nextY / gs.getTile(), TileManager.highestLayer);
            int tile2 = gs.tileM.getLayer((int) (nextX + gs.getTile()) / gs.getTile(),(int) nextY / gs.getTile(), TileManager.highestLayer);
            int tile3 = gs.tileM.getLayer((int) nextX / gs.getTile(),(int) (nextY + gs.getTile()) / gs.getTile(), TileManager.highestLayer);
            int tile4 = gs.tileM.getLayer((int) (nextX + gs.getTile()) / gs.getTile(),(int) (nextY + gs.getTile()) / gs.getTile(), TileManager.highestLayer);
            if(gs.tileM.getWall().stream().noneMatch(w -> w.getValue() == tile) &&
                    gs.tileM.getWall().stream().noneMatch(w -> w.getValue() == tile2) &&
                    gs.tileM.getWall().stream().noneMatch(w -> w.getValue() == tile3) &&
                    gs.tileM.getWall().stream().noneMatch(w -> w.getValue() == tile4)) {
                e2.setWorldX(e2.getWorldX() - normal.getX() * depth /2);
                e2.setWorldY(e2.getWorldY() - normal.getY() * depth /2);
            }
        }
        return true;
    }
    /**
     *  Find the closest point form polygon to circle
     * @param e1 Polygon
     * @param e2 Circle
     * @return {int} Index of the closest point
     * */
    public static int FindClosestPointFromPolygonToCircle(Entity e1, Entity e2)
    {
        int result = -1;
        double minDistance = INF;
        double distance = 0;
        PointX tmp;
        for(int i=0; i<e1.getVertices().size(); i++)
        {
            tmp = e1.getVertices().get(i);
            PointX centerCir = PointX.getCenterPointFromList(e2.getVertices());
            distance = tmp.distance(centerCir);
            if(distance < minDistance) {
                minDistance = distance;
                result = i;
            }
        }
        return result;
    }
    /**
     * Check collision of two entity circle and polygon by using SAT
     * @param e1 Polygon
     * @param e2 Circle
     * @param move1 Entity 1 is able to move (automatically correct location)
     * @param move2 Entity 2 is able to move (automatically correct location)
     * @return {boolean} True when two entity are collided. Otherwise, return false
     * */
    public static boolean CirclePolygonCollisionDectect(Entity e1, Entity e2, boolean move1, boolean move2)
    {
        double depth = INF;
        PointX normal = null;
        double axisDepth;
        for(int i=0; i<e1.getVertices().size(); i++) {
            PointX va = e1.getVertices().get(i);
            PointX vb = e1.getVertices().get((i + 1) % e1.getVertices().size());
            PointX edge = vb.minusVector(va);
            PointX axis = new PointX(-edge.y, edge.x);
            axis = axis.normalize();

            double min1 = INF;
            double max1 = -INF;

            double proj;

            for (int j = 0; j < e1.getVertices().size(); j++) {
                proj = axis.dot(e1.getVertices().get(j));
                if (proj < min1) {
                    min1 = proj;
                }
                if (proj > max1) {
                    max1 = proj;
                }
            }

            double min2;
            double max2;

            PointX directionRadius = axis.normalize().multipleVector(e2.getRadius());

            PointX vectorRadius1 = PointX.getCenterPointFromList(e2.getVertices()).sumVector(directionRadius);
            PointX vectorRadius2 = PointX.getCenterPointFromList(e2.getVertices()).minusVector(directionRadius);

            min2 = axis.dot(vectorRadius1);
            max2 = axis.dot(vectorRadius2);
            if (min2 > max2) {
                double tmp = min2;
                min2 = max2;
                max2 = tmp;
            }
            if (min1 >= max2 || min2 >= max1) {
                return false;
            }
            axisDepth = Math.min(max2 - min1, max1 - min2);
            if (axisDepth < depth) {
                depth = axisDepth;
                normal = axis;
            }
        }
        int cpIndex = FindClosestPointFromPolygonToCircle(e1, e2);
        PointX cp = e1.getVertices().get(cpIndex);
        PointX axis = cp.minusVector(PointX.getCenterPointFromList(e2.getVertices()));
        axis = axis.normalize();
        double min1 = INF;
        double max1 = -INF;
        for(int i=0; i<e1.getVertices().size(); i++)
        {
            double proj = axis.dot(e1.getVertices().get(i));
            if(proj < min1)
            {
                min1 = proj;
            }
            if(proj > max1)
            {
                max1 = proj;
            }
        }
        double min2;
        double max2;
        PointX directionRadius = axis.normalize().multipleVector(e2.getRadius());

        PointX vectorRadius1 = PointX.getCenterPointFromList(e2.getVertices()).sumVector(directionRadius);
        PointX vectorRadius2 = PointX.getCenterPointFromList(e2.getVertices()).minusVector(directionRadius);

        min2 = axis.dot(vectorRadius1);
        max2 = axis.dot(vectorRadius2);
        if(min2 > max2)
        {
            double tmp = min2;
            min2 = max2;
            max2 = tmp;
        }
        if(min1 >= max2 || min2 >= max1)
        {
            return false;
        }
        axisDepth = Math.min(max2 - min1, max1 - min2);
        if(axisDepth < depth)
        {
            depth = axisDepth;
            normal = axis;
        }

        PointX centerPolygon = PointX.getCenterPointFromList(e1.getVertices());
        PointX centerCircle = PointX.getCenterPointFromList(e2.getVertices());
        PointX direction = centerCircle.minusVector(centerPolygon);
        if(normal.dot(direction) < 0)
        {
            normal.multipleVector(-1);
        }

        if(move1)
        {
            double nextX = e1.getWorldX() + normal.getX() * depth /2;
            double nextY = e1.getWorldY() + normal.getY() * depth /2;
            int tile = gs.tileM.getLayer((int) nextX / gs.getTile(),(int) nextY / gs.getTile(), TileManager.highestLayer);
            int tile2 = gs.tileM.getLayer((int) (nextX + gs.getTile()) / gs.getTile(),(int) nextY / gs.getTile(), TileManager.highestLayer);
            int tile3 = gs.tileM.getLayer((int) nextX / gs.getTile(),(int) (nextY + gs.getTile()) / gs.getTile(), TileManager.highestLayer);
            int tile4 = gs.tileM.getLayer((int) (nextX + gs.getTile()) / gs.getTile(),(int) (nextY + gs.getTile()) / gs.getTile(), TileManager.highestLayer);
            if(gs.tileM.getWall().stream().noneMatch(w -> w.getValue() == tile) &&
                    gs.tileM.getWall().stream().noneMatch(w -> w.getValue() == tile2) &&
                    gs.tileM.getWall().stream().noneMatch(w -> w.getValue() == tile3) &&
                    gs.tileM.getWall().stream().noneMatch(w -> w.getValue() == tile4)) {
                e1.setWorldX(e1.getWorldX() + normal.getX() * depth /2);
                e1.setWorldY(e1.getWorldY() + normal.getY() * depth /2);
            }
        }
        if(move2)
        {
            double nextX = e2.getWorldX() + direction.normalize().getX() * depth /2;
            double nextY = e2.getWorldY() + direction.normalize().getY() * depth /2;
            int tile = gs.tileM.getLayer((int) nextX / gs.getTile(),(int) nextY / gs.getTile(), TileManager.highestLayer);
            int tile2 = gs.tileM.getLayer((int) (nextX + gs.getTile()) / gs.getTile(),(int) nextY / gs.getTile(), TileManager.highestLayer);
            int tile3 = gs.tileM.getLayer((int) nextX / gs.getTile(),(int) (nextY + gs.getTile()) / gs.getTile(), TileManager.highestLayer);
            int tile4 = gs.tileM.getLayer((int) (nextX + gs.getTile()) / gs.getTile(),(int) (nextY + gs.getTile()) / gs.getTile(), TileManager.highestLayer);
            if(gs.tileM.getWall().stream().noneMatch(w -> w.getValue() == tile) &&
                    gs.tileM.getWall().stream().noneMatch(w -> w.getValue() == tile2) &&
                    gs.tileM.getWall().stream().noneMatch(w -> w.getValue() == tile3) &&
                    gs.tileM.getWall().stream().noneMatch(w -> w.getValue() == tile4)) {
                e2.setWorldX(e2.getWorldX() + direction.normalize().getX() * depth /2);
                e2.setWorldY(e2.getWorldY() + direction.normalize().getY() * depth /2);
            }
        }
        return true;
    }

}
