package Quadtree;

import Entity.Entity;

public class PointQ {
    int x, y;
    RectangleQ bound;
    Entity userData; // đối tượng cần sử dụng
    public PointQ(int x, int y, Entity userData, RectangleQ bound) {
        this.x = x;
        this.y = y;
        this.bound = bound;
        this.userData = userData;
    }
    public RectangleQ getBounds() {
        return bound;
    }
    public Entity getUserData() {return userData;}
}
