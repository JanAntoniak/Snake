package com.snake.model;

import java.io.Serializable;
import java.util.Random;
import com.snake.view.*;

/**
 * <h1>Fruit</h1>
 * Class use for fruit coordinates generating.
 *
 */
public class Fruit implements Serializable{

    private Point position;
    private Field field;
    private Random random;
    private int value;

    /**
     *
     * @param field Fruit is strictly connected with particular field
     */
    public Fruit(Field field) {
        this.position = new Point(1,10);
        this.field = field;
        this.random = new Random();
    }

    /**
     * Empty constructor used in initial messages
     * @see MessageToClient
     */
    public Fruit() {
    }

    /**
     * This method use Random class to generate new coordinates for the fruit, then it place it on the field
     * @see Field
     */
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

    /**
     *
     * @return value of actual fruit
     */
    public int getValue() {
        return value;
    }

    /**
     *
     * @return actual position of fruit
     */
    public Point getPosition() {
        return position;
    }
}