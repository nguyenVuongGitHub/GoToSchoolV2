package CollisionSystem;

import java.util.List;

public class PointX {
    double x,y;

    public PointX(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public PointX sumVector(PointX other)
    {
        PointX v = new PointX( x + other.x, y + other.y);
        return v;
    }
    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }
    public PointX minusVector(PointX other)
    {
        PointX v = new PointX( x - other.x, y - other.y);
        return v;
    }
    public PointX multipleVector(double amount)
    {
        PointX v = new PointX( x * amount, y * amount);
        return v;
    }
    public PointX DivineVector(double amount)
    {
        PointX v = new PointX(x / amount, y / amount);
        return v;
    }
    public double length()
    {
        return Math.sqrt(x*x+y*y);
    }
    public double distance(PointX other)
    {
        return Math.sqrt(Math.pow(x-other.x, 2) + Math.pow(y- other.y, 2));
    }
    public double dot(PointX other)
    {
        return x * other.y + y * other.x;
    }
    public PointX normalize()
    {
        PointX v = new PointX(x/length(), y/length());
        return v;
    }
    public static PointX getCenterPointFromList(List<PointX> vertices)
    {
        double x=0, y =0;
        for(int i=0; i<vertices.size(); i++)
        {
            x += vertices.get(i).x;
            y += vertices.get(i).y;
        }
        x /= vertices.size();
        y /= vertices.size();

        return new PointX(x, y);
    }

    @Override
    public String toString() {
        return "X: " + x +" Y: " + y;
    }
}
