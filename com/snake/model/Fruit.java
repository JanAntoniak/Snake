package com.snake;

import com.sun.org.apache.xerces.internal.impl.dv.xs.YearDV;

import java.util.Random;

public class Fruit {

	private Point position;
    private SnakeGame game;
    private Random random;
    private int value;

    public Fruit(SnakeGame game) {
        this.game = game;
        this.random = new Random();
    }
    public void generateFruit() {
    	do {
    		int position.x = random.nextInt(BoardPanel.WIDTH);
       		int position.y = random.nextInt(BoardPanel.HEIGHT);
        	value = random.nextInt(3) + 1;	
 	   	} while(game.getField().getType(position.x, position.y) != BrickType.EMPTY)
       
        game.getField().setFruit(position);
    }
}