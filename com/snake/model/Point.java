package com.snake;

public class Point {
    private int x;
    private int y;

    public void SetX(int x) {
        this.x = x;
    }

    public void SetY(int y) {
        this.y = y;
    }

    public Set(int x, int y){
        this.x = x;
        this.y = y;

    }

    public boolean Equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Point)) return false;
        if (!super.equals(object)) return false;

        Point point = (Point) object;

        if (x != point.x) return false;
        if (y != point.y) return false;

        return true;
    }

    public int GetX() {
        return x;
    }

    public int GetY() {
        return y;
    }
}