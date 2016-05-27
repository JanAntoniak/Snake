package com.snake;

public class ServerController {

    private Field field;
    private Snake snake1;
    private Snake snake2;
    private Fruit fruit;
    private boolean isNewGame;
    private boolean isGameOver;
    private Clock timer;

    public ServerController() {
        this.resetGame();
    }

    public boolean isNewGame() {
        return isNewGame;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public synchronized void updateGame() {

        BrickType collision = updateSnake(snake1);
        BrickType collision2 = updateSnake(snake2);

        if(collision == BrickType.FRUIT || collision2 == BrickType.FRUIT) {
            fruit.generateFruit();
        } else if(collision == BrickType.SNAKE1 || collision == BrickType.SNAKE2
                || collision2 == BrickType.SNAKE1 || collision2 == BrickType.SNAKE2) {
            isGameOver = true;
            timer.setPaused(true);
        }
    }

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
        if(head.x < 0 || head.x >= BoardPanel.BOARD_HEIGHT ||
                head.y < 0 || head.y >= BoardPanel.BOARD_WIDTH ) {
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

    public void resetGame() {

        field = new Field();
        snake1 = new Snake(field, new Point(10,10), 1);
        snake2 = new Snake(field, new Point(20,20), 2);
        fruit = new Fruit(field);
        fruit.generateFruit();
        timer = new Clock(1.5f);

        //this.setNewGame(true);
        //this.setGameOver(false);
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
