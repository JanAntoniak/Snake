package com.snake;

import java.io.Serializable;
import java.util.*;
import java.lang.Math;


public class Snake implements Serializable{
    private int IDSnake;
	private int score;
    private ArrayDeque<Point> body;
    private Field field;
    public LinkedList<Direction> directions;
    private boolean isWinner = false;

    public Snake(Field field, Point startingPoint, int IDSnake){
        body = new ArrayDeque<>();
        this.IDSnake = IDSnake;
        this.field = field;
        body.addFirst(startingPoint);
        directions = new LinkedList<>();
        this.setDirections(Direction.RIGHT);
    }

    public Snake() {
        body = new ArrayDeque<>();
        directions = new LinkedList<>();
        this.setDirections(Direction.RIGHT);
    }

    public void MoveOn(Point point){
        body.addFirst(point);
    }

    public Point GetHead() {
        return body.peekFirst();
    }

    public void setIDSnake(int IDSnake) {
	this.IDSnake = IDSnake;
    }

    public int getIDSnake() {
	return this.IDSnake;
    }

    public void eatFruit(Fruit f) {
    	this.score += f.getValue();
    }

    public int getScore() {
        return score;
    }

    public Point removeTail() {
        return body.removeLast();
    }

    public ArrayDeque<Point> getBody() {
        return this.body;
    }

    public void setDirections(Direction direction) {
        this.directions.addLast(direction);
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }
}
