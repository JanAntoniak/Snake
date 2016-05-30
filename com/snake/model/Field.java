package com.snake.model;

import java.io.Serializable;
import com.snake.view.*;

public class Field implements Serializable{
    private BrickType[][] field;

    public Field() {
        field = new BrickType[BoardPanel.BOARD_HEIGHT + 1][BoardPanel.BOARD_WIDTH + 1];
        clearField();
    }

    public void setFruit(Point position) {
        field[position.y][position.x] = BrickType.FRUIT;
    }

    public void setSnake(int x, int y, int snakeID) {
        if(snakeID == 1)
            field[y][x] = BrickType.SNAKE1;
        if(snakeID == 2)
            field[y][x] = BrickType.SNAKE2;
    }
    
    public void setNothing(int x, int y) {
        field[y][x] = BrickType.EMPTY;
    }

    public void clearField() {
        for (int r=0; r<field.length; r++) {
            for (int c=0; c<field[r].length; c++) {
                field[r][c]= BrickType.EMPTY;
            }
        }
    }

    public BrickType getType(int x, int y) {
        return field[y][x];
    }

    public void add(Snake snake) {
        int ID = snake.getIDSnake();
        for (Point p: snake.getBody()
             ) {
            if(ID == 1)
                field[p.y][p.x] = BrickType.SNAKE1;
            else
                field[p.y][p.x] = BrickType.SNAKE2;
        }
    }
}
