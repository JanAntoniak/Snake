package com.antoniak;

public class Update {
    public static void updateSnake(Snake snake) {
        snake.move();
    }
    public static void updateFruit(Fruit fruit) {
        fruit.generateFruit();
    }
}
