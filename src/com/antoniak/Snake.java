package com.antoniak;



import java.util.ArrayDeque;
import java.util.Random;
import java.lang.Math;

public class Snake
{
    //private static final int START_LENGHT = 3;
    public enum Directions {UP, DOWN, RIGHT, LEFT}
    private Directions direction;
    private Pair<Integer, Integer> head;
    private ArrayDeque<Pair<Integer, Integer>> locationOfSnake;
    public Snake(Field field, CorrectionInitializationTest test)
    {
        locationOfSnake = new ArrayDeque<Pair<Integer, Integer>>();
        int forbiddenPositionsX = (int)(0.2*field.getWidth());
        int forbiddenPositionsY = (int)(0.2*field.getHeight());
        int fieldHeight = field.getHeight();
        int fieldWidth = field.getWidth();
        Random generator = new Random();

        /* left = X, right = y*/
        int X, Y;
        do {
            X = generator.nextInt(fieldWidth - 2*forbiddenPositionsX) + forbiddenPositionsX;
            Y = generator.nextInt(fieldHeight - 2*forbiddenPositionsY) + forbiddenPositionsY;
            head = new Pair<Integer, Integer>(X, Y);
        } while(!test.isCorrect(head));

        locationOfSnake.add(head);

        if(head.getLeft() >= fieldWidth/2) {
            locationOfSnake.addLast(new Pair<Integer, Integer>(head.getLeft() + 1, head.getRight()));
            locationOfSnake.addLast(new Pair<Integer, Integer>(head.getLeft() + 2, head.getRight()));
            direction = Directions.LEFT;
        }
        else {
            locationOfSnake.addLast(new Pair<Integer, Integer>(head.getLeft() - 1, head.getRight()));
            locationOfSnake.addLast(new Pair<Integer, Integer>(head.getLeft() - 2, head.getRight()));
            direction = Directions.RIGHT;
        }
        //
    }

    public void setDirection(Directions direction) {
        this.direction = direction;
    }

    public void move() {
        switch(direction) {
            case UP: {
                head = new Pair<Integer, Integer>(head.getLeft(), head.getRight() + 1);
                locationOfSnake.addFirst(head);
                break;
            }
            case DOWN: {
                head = new Pair<Integer, Integer>(head.getLeft(), head.getRight() - 1);
                locationOfSnake.addFirst(head);
                break;
            }
            case RIGHT: {
                head = new Pair<Integer, Integer>(head.getLeft() + 1, head.getRight());
                locationOfSnake.addFirst(head);
                break;
            }
            case LEFT: {
                head = new Pair<Integer, Integer>(head.getLeft() - 1, head.getRight());
                locationOfSnake.addFirst(head);
                break;
            }
        }
    }

    public Pair<Integer, Integer> getHead() {
        return head;
    }

    public ArrayDeque<Pair<Integer, Integer>> getPosition() {
        return locationOfSnake.clone();
    }

    public void printSnake() {
        for (Pair pair : locationOfSnake) {
            pair.printPair();
        }
        System.out.println("Head = " + +head.getLeft() + ", " + head.getRight());
    }

    public static void main(String args[]){
        /*test na dzialanie correction test, jesli dziala to k = 0*/
        Field field = new Field();
        int k = 0;
        for(int i = 0; i < 1000; ++i) {
            CorrectionInitializationTest test = new CorrectionInitializationTest();

            Snake s1 = new Snake(field, test);

            Snake s2 = new Snake(field, test);
            if(Math.abs(s1.getHead().getLeft() - s2.getHead().getLeft()) < 3 &&
               Math.abs(s1.getHead().getRight() - s2.getHead().getRight()) < 2)
            {
                s1.printSnake();
                s2.printSnake();
                k++;
                System.out.println();
            }
        }
        System.out.print("Wynik = " + k);
    }
}
