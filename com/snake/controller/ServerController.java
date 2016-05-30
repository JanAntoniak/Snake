package com.snake.controller;

import com.snake.model.*;
import com.snake.view.*;
/**
 * The {@code ServerController} class is responsible for handling much of the game's logic.
 *
 */
public class ServerController {

	/**
	 * The Field instance.
	 */
    private Field field;
    
    /**
	 * The Snake1 instance.
	 */
    private Snake snake1;
    
    /**
	 * The Snake2 instance.
	 */
    private Snake snake2;
    
    /**
	 * The Fruit instance.
	 */
    private Fruit fruit;
    
    /**
	 * Flag that is true when there is called new game
	 */
    private boolean isNewGame;
    
    /**
	 * Flag that is true when the game is over
	 */
    private boolean isGameOver;
    
    /**
	 * The Clock instance. Clock determines the speed of 
	 * calculating positions and sending messages to clients 
	 */
    private Clock timer;

    /**
    * Constructor of this class. 
    */
    public ServerController() {
        this.resetGame();
    }

    /**
    * UpdateGame is a method that check if there is any collision,
     * if so it set Snake1 or Snake2 as winner, isGameOver as true and pause timer 
    */
    public synchronized void updateGame() {

        BrickType collision = updateSnake(snake1);
        BrickType collision2 = updateSnake(snake2);

        if(collision == BrickType.FRUIT || collision2 == BrickType.FRUIT) {
            fruit.generateFruit();
        } else if(collision == BrickType.SNAKE1 || collision == BrickType.SNAKE2
                || collision2 == BrickType.SNAKE1 || collision2 == BrickType.SNAKE2) {
            if(((collision == BrickType.SNAKE1 || collision == BrickType.SNAKE2)
               && collision2 != BrickType.SNAKE2 && collision2 != BrickType.SNAKE1) &&
                    !snake1.GetHead().Equals(snake2.GetHead())) {
                snake2.setWinner(true);
            }
            if(((collision2 == BrickType.SNAKE2 || collision2 == BrickType.SNAKE1) &&
                    collision != BrickType.SNAKE1 && collision != BrickType.SNAKE2 ) &&
                    !snake1.GetHead().Equals(snake2.GetHead())) {
                snake1.setWinner(true);
            }

            isGameOver = true;
            timer.setPaused(true);
        }
    }

    /**
     * updateSnake is a method that moves snakes and returns the value that represents 
     * the field area that snake's head moved on.
    */
    private synchronized BrickType updateSnake(Snake snake) {

        Direction direction = snake.directions.peekFirst();

        Point head = snake.GetHead();
        switch (direction) {
            case UP:
                snake.MoveOn(new Point(head.x  , head.y - 1));
                break;

            case DOWN:
                snake.MoveOn( new Point(head.x , head.y + 1));
                break;

            case LEFT:
                snake.MoveOn( new Point(head.x - 1, head.y ));
                break;

            case RIGHT:
                snake.MoveOn( new Point(head.x + 1, head.y ));
                break;
        }

        head = snake.GetHead();
        if(head.x < 0 || head.x >= BoardPanel.BOARD_WIDTH ||
                head.y < 0 || head.y >= BoardPanel.BOARD_HEIGHT) {
            if(snake.getIDSnake() == 1)
                return  BrickType.SNAKE1;
            else return BrickType.SNAKE2;
        }

        BrickType old = field.getType(head.x, head.y);
        if(old != BrickType.FRUIT) {
            Point tail = snake.removeTail();
            field.setNothing(tail.x, tail.y);
            old = field.getType(head.x, head.y);
        }
        else {
            snake.eatFruit(fruit);
        }

        if(old != BrickType.SNAKE1 && old != BrickType.SNAKE2) {
            field.setNothing(head.x, head.y);
            field.setSnake(head.x, head.y, snake.getIDSnake());
            if(snake.directions.size() > 1) {
                snake.directions.poll();
            }
        }

        return old;
    }

    /**
     * resetGame is a method that reset the game, create new snakes, field, fruit and timer. 
     */
    public void resetGame() {

        field = new Field();
        snake1 = new Snake(field, new Point(10,10), 1);
        snake2 = new Snake(field, new Point(20,20), 2);
        fruit = new Fruit(field);
        fruit.generateFruit();
        timer = new Clock(1.5f);
    }

	public boolean isNewGame() {
        return isNewGame;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public Snake getSnake1() {
        return snake1;
    }

    public Snake getSnake2() {
        return snake2;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setGameOver(boolean state) {
        isGameOver = state;
    }

    public void setNewGame(boolean state) {
        isNewGame = state;
    }

    public Field getField() {
        return this.field;
    }
}
