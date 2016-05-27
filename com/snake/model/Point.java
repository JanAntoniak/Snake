package com.snake;

import java.io.Serializable;

public class Point implements Serializable{
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean Equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Point)) return false;
        //if (!super.equals(object)) return false;
        // ^ This condition falsely says that Point(30,10) and Point(30,10) are different.
        Point point = (Point) object;
        if (x != point.x) return false;
        if (y != point.y) return false;
        return true;
    }
}