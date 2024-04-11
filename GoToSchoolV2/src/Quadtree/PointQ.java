package Quadtree;

import Entity.Entity;

public class PointQ {
    int x, y;

    Entity userData; // đối tượng cần sử dụng
    public PointQ(int x, int y, Entity userData) {
        this.x = x;
        this.y = y;
        this.userData = userData;
    }
    public Entity getUserData() {return userData;}
}
