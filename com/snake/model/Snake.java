package com.snake.model;

import java.io.Serializable;
import java.util.*;
import java.lang.Math;

/**
 * <h1>Snake</h1>
 * Class containing array representing body of snake and linked list with moves performed by player.
 * @see Serializable
 */

public class Snake implements Serializable{
    private int IDSnake;
	private int score;
    private ArrayDeque<Point> body;
    private Field field;
    public LinkedList<Direction> directions;
    private boolean isWinner = false;

    /**
     *
     * @param field Both snakes moves on the same field
     * @param startingPoint Snakes need to have different starting points
     * @param IDSnake Snakes are recognized with id number
     */
    public Snake(Field field, Point startingPoint, int IDSnake){
        body = new ArrayDeque<>();
        this.IDSnake = IDSnake;
        this.field = field;
        body.addFirst(startingPoint);
        directions = new LinkedList<>();
        this.setDirections(Direction.RIGHT);
    }

    /**
     * Constructor used in the beginning of game
     */
    public Snake() {
        body = new ArrayDeque<>();
        directions = new LinkedList<>();
        this.setDirections(Direction.RIGHT);
    }

    /**
     * Move is performed by adding point to the body array
     * @param point calculated on server
     * @see com.snake.controller.ServerController
     */
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

    /**
     * After move there is need to delete last point in body array
     * @return
     */
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
