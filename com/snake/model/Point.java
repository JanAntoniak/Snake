package com.snake;

public class Point {
    int x;
    int y;

    public boolean Equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Point)) return false;
        if (!super.equals(object)) return false;

        Point point = (Point) object;

        if (x != point.x) return false;
        if (y != point.y) return false;

        return true;
    }
}