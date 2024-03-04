package CollisionSystem;

import Entity.Entity;


public class SeparatingAxis {
    final static double INF = 999999999;
    public static boolean polygonCollisionDetectFirstStatic(Entity e1, Entity e2)
    {
        double depth = INF;
        PointX normal = null;
        for(int i=0; i<e1.getVertices().size(); i++)
        {
            PointX va = e1.getVertices().get(i);
            PointX vb = e1.getVertices().get((i+1) % e1.getVertices().size());
            PointX edge = vb.minusVector(va);
            PointX axis = new PointX(-edge.y, edge.x);

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
                System.out.println(e2.getVertices().get(j).x);
                System.out.println(e2.getVertices().get(j).y);
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
        depth /= normal.length();
        normal = normal.normalize();
        PointX centerA = PointX.getCenterPointFromList(e1.getVertices());
        PointX centerB = PointX.getCenterPointFromList(e2.getVertices());
        
        PointX direction = centerB.minusVector(centerA);
        
        if(normal.dot(direction) < 0)
        {
            normal = normal.multipleVector(-1);
        }
        
        e1.setWorldX(e1.getWorldX() - depth);
        e1.setWorldY(e1.getWorldY() - depth);

        return true;
    }
}
