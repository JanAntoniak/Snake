package com.snake.model;

import java.io.Serializable;
import java.util.Random;
import com.snake.view.*;

public class Fruit implements Serializable{

    private Point position;
    private Field field;
    private Random random;
    private int value;

    public Fruit(Field field) {
        this.position = new Point(1,10);
        this.field = field;
        this.random = new Random();
    }

    public Fruit() {
    }

    public void generateFruit() {
        do {
            int w = BoardPanel.BOARD_HEIGHT - 1;
            int h = BoardPanel.BOARD_WIDTH - 1;
            position.x = random.nextInt(h);
            position.y = random.nextInt(w);
            value = random.nextInt(3) + 1;
        } while(field.getType(position.x, position.y) != BrickType.EMPTY);

        field.setFruit(position);
    }

    public int getValue() {
        return value;
    }

    public Point getPosition() {
        return position;
    }
}