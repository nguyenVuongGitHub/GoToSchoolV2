package Quadtree;
import Main.GameState;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QuadTree {
    GameState gs;
    private final int capacity;
    private List<PointQ> points;
    private RectangleQ bounds;
    private QuadTree northWest;
    private QuadTree northEast;
    private QuadTree southWest;
    private QuadTree southEast;
    private boolean divided;

    public QuadTree(int capacity, RectangleQ bounds, GameState gs) {
        this.gs = gs;
        this.capacity = capacity;
        this.points = new ArrayList<>();
        this.bounds = bounds;
        divided = false;
    }

    public void clear() {
        points.clear(); // Xóa tất cả các đối tượng từ nút hiện tại

        if (divided) {
            // Nếu nút hiện tại đã được phân chia thành các nút con, tiến hành xóa các nút con
            northEast.clear();
            northWest.clear();
            southEast.clear();
            southWest.clear();
            divided = false; // Đánh dấu rằng Quadtree không còn được phân chia nữa
        }
    }

    /*
    * Hàm này sẽ chia QuadTree hiện tại thành 4 nút con đuợc đánh dấu là , northEast, northWest, southEast, southWest
    * */
    private void subdivide() {
        int w = bounds.getW() / 2;
        int h = bounds.getH() / 2;
        int x = bounds.getX();
        int y = bounds.getY();

        // north east
        northEast = new QuadTree(capacity, new RectangleQ(x + w, y - h, w, h),gs);
        // north west
        northWest = new QuadTree(capacity, new RectangleQ(x - w, y - h, w, h),gs);
        // south east
        southEast = new QuadTree(capacity, new RectangleQ(x + w, y + h, w, h),gs);
        // south west
        southWest = new QuadTree(capacity, new RectangleQ(x - w, y + h, w, h),gs);

        this.divided = true;
    }

    public boolean insert(PointQ point) {
        // nếu trong khoảng không gian hiện tại của quadTree không chứa điểm cần xét thì bỏ qua
        if(!bounds.contains(point)) {
            return false;
        }
        // nếu danh sách các điểm hiện tại vẫn nhỏ hơn sức chứa có thể thì chèn vào danh sách điểm hiện tại
        if(this.points.size() < this.capacity) {
           points.add(point);
           return true;
        }
        // nếu khoảng không gian hiện tại chưa được chia nhỏ thì sẽ chia nhỏ bởi hàm subdivide
       if(!this.divided) {
            subdivide();
       }

       // sau khi chia xong sẽ tìm trong các khoảng không nhỏ, và chèn điểm đó vào

        return this.northEast.insert(point)
                || this.northWest.insert(point)
                || this.southEast.insert(point)
                || this.southWest.insert(point);
    }
    // truy vấn
    public List<PointQ> query(RectangleQ range) {
        List<PointQ> found = new ArrayList<>();

        if (!range.intersects(bounds)) {
            return found;
        }

        for (PointQ p : this.points) {
            if (range.contains(p)) {
                found.add(p);
            }
        }

        if (this.divided) {
            found.addAll(northWest.query(range));
            found.addAll(northEast.query(range));
            found.addAll(southWest.query(range));
            found.addAll(southEast.query(range));
        }
        return found;
    }

}
