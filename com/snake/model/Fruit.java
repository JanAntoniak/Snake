package com.snake;

import com.sun.org.apache.xerces.internal.impl.dv.xs.YearDV;

import java.util.Random;

public class Fruit {

    SnakeGame game;
    Random random;

    public Fruit(SnakeGame game) {
        this.game = game;
        this.random = new Random();
    }
    public void generateFruit() {
        int xIndex = 20;//random.nextInt(48);
        int yIndex = 20;random.nextInt(30);

        while(game.getField().getType(xIndex, yIndex) != BrickType.EMPTY)
        {
            xIndex = random.nextInt(48);
            yIndex = random.nextInt(30);
        }
        game.getField().setFruit(xIndex, yIndex);
    }
}