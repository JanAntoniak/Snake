package com.snake;

import java.util.*;
import java.lang.Math;

public class Snake{
    private Directions direction;
    private ArrayDeque<Point> body;
    private Field field;

    public Snake(Field field, Point startingPoint){
        this.field = field;
        body.addFirst(startingPoint);
    }

    public void MoveOn(Point point){
        body.addFirst(point);
        body.removeLast();
    }

    public Point GetHead() {
        return body.peekFirst();
    }

    public void EraseSnake() {
        body.clear();
    }
}