package Quadtree;

public class RectangleQ {
    private int x;
    private int y;
    private int w;
    private int h;

    public RectangleQ(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    // kiểm tra xem một điểm có nằm trong khoaảng hình chữ nhật không
    public boolean contains(PointQ point) {
        return (point.x >= this.x - this.w &&
                point.x <= this.x + this.w &&
                point.y >= this.y - this.h &&
                point.y <= this.y + this.h);
    }

    // kiểm tra xem 2 hình chữ nhật có giao nhau không
    public boolean intersects(RectangleQ range) {
        return !(range.getX() - range.getW() >= this.x + this.w ||
                range.getX() + range.getW() <= this.x - this.w ||
                range.getY() - range.getH() >= this.y + this.h ||
                range.getY() + range.getH() <= this.y - this.h);
    }

    // Getters and setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }
}

