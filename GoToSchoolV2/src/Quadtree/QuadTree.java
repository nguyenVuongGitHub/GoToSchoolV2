package Quadtree;
import Main.GameState;
import java.util.ArrayList;
import java.util.List;

public class QuadTree {
    GameState gs;
    private final int MAX_LEVELS = 5;

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
        int subWidth = bounds.getW() / 2;
        int subHeight = bounds.getH() / 2;
        int x = bounds.getX();
        int y = bounds.getY();

        // north east
        northEast = new QuadTree(capacity, new RectangleQ(x + subWidth, y - subHeight, subWidth, subHeight),gs);
        // north west
        northWest = new QuadTree(capacity, new RectangleQ(x - subWidth, y - subHeight, subWidth, subHeight),gs);
        // south east
        southEast = new QuadTree(capacity, new RectangleQ(x + subWidth, y + subHeight, subWidth, subHeight),gs);
        // south west
        southWest = new QuadTree(capacity, new RectangleQ(x - subWidth, y + subHeight, subWidth, subHeight),gs);

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
        }else {
            // nếu khoảng không gian hiện tại chưa được chia nhỏ thì sẽ chia nhỏ bởi hàm subdivide
           if(!this.divided)
                subdivide();

           // sau khi chia xong sẽ tìm trong các khoảng không nhỏ, và chèn điểm đó vào
           if(northEast.insert(point))
               return true;
           else if (northWest.insert(point))
                return true;
           else if (southEast.insert(point))
               return true;
           else if (southWest.insert(point))
               return true;
        }
        return false;
    }
    public PointQ[] query(RectangleQ range) {
        return query(range, new ArrayList<PointQ>());
    }

    // truy vấn
    private PointQ[] query(RectangleQ range, List<PointQ> found) {

        /*
        Kiểm tra xem danh sách found đã được khởi tạo chưa. Nếu danh sách found không trống,
        nghĩa là nó đã được sử dụng từ lần gọi trước đó của hàm query(), ta cần xóa các phần tử hiện có của nó bằng cách tạo một danh sách mới.
        * */
        if (!found.isEmpty()) {
            found = new ArrayList<>();
        }
        /*
        Kiểm tra xem hình chữ nhật range có giao với giới hạn của nút hiện tại không.
        Nếu không có giao nhau, có nghĩa là không có điểm nào trong Quadtree nằm trong vùng range,
        ta trả về một mảng chứa các điểm đã tìm thấy.
        * */
        if (!range.intersects(bounds)) {
            return found.toArray(new PointQ[0]);
        }
        /*
        Nếu hình chữ nhật range có giao với giới hạn của nút hiện tại, ta kiểm tra từng điểm trong danh sách points của nút hiện tại.
        Nếu một điểm nằm trong hình chữ nhật range, ta thêm điểm đó vào danh sách found
        * */
        for (PointQ p : this.points) {
            if (range.contains(p)) {
                found.add(p);
            }
        }
        /*
        Nếu nút hiện tại đã được phân chia thành các nút con, ta tiếp tục gọi phương thức query()
        trên các nút con với cùng hình chữ nhật range và danh sách found.
        * */
        if (this.divided) {
            this.northWest.query(range, found);
            this.northEast.query(range, found);
            this.southWest.query(range, found);
            this.southEast.query(range, found);
        }

        //Cuối cùng, ta trả về một mảng chứa các điểm đã tìm thấy trong found.
        return found.toArray(new PointQ[0]);
    }

}
