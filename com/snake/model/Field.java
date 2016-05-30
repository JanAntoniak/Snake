package com.snake.model;

import java.io.Serializable;
import com.snake.view.*;

/**
 * <h1>Field</h1>
 * Class containing all field of game.
 * Interface Serializable is use to sending data in network.
 *
 * @author  Jan Antoniak
 */

public class Field implements Serializable{
    private BrickType[][] field;

    public Field() {
        field = new BrickType[BoardPanel.BOARD_HEIGHT + 1][BoardPanel.BOARD_WIDTH + 1];
        clearField();
    }

    /**
     * @param position position get from Fruit
     * @see Fruit
     */
    public void setFruit(Point position) {
        field[position.y][position.x] = BrickType.FRUIT;
    }

    /**
     * Place snakes on the field.
     * @param x coordinate of Field
     * @param y coordinate of Field
     * @param snakeID used for setting different colors
     */
    public void setSnake(int x, int y, int snakeID) {
        if(snakeID == 1)
            field[y][x] = BrickType.SNAKE1;
        if(snakeID == 2)
            field[y][x] = BrickType.SNAKE2;
    }

    /**
     * This method set indicated bricks to empty, which means that snakes can move over it
     * @param x coordinate of Field
     * @param y coordinate of Field
     */
    public void setNothing(int x, int y) {
        field[y][x] = BrickType.EMPTY;
    }

    /**
     * This method set all bricks to empty
     */
    public void clearField() {
        for (int r=0; r<field.length; r++) {
            for (int c=0; c<field[r].length; c++) {
                field[r][c]= BrickType.EMPTY;
            }
        }
    }

    /**
     * Getting type of point on the field
     * @param x coordinate of Field
     * @param y coordinate of Field
     * @return BrickType
     */
    public BrickType getType(int x, int y) {
        return field[y][x];
    }

    /**
     * This method adds snakes to the field
     * @param snake
     * @see Snake
     */
    public void add(Snake snake) {
        int ID = snake.getIDSnake();
        for (Point p: snake.getBody()) {
            if(ID == 1)
                field[p.y][p.x] = BrickType.SNAKE1;
            else
                field[p.y][p.x] = BrickType.SNAKE2;
        }
    }
}
