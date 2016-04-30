package com.antoniak;

import java.util.ArrayList;

/* Check if snake is not on the top of the other. */
public class CorrectionInitializationTest {

    private static final int FORBIDDEN_X = 5;
    private static final int FORBIDDEN_Y = 3;

    private ArrayList<Rectangle> forbiddenInitiationPositions;

    public CorrectionInitializationTest() {
        forbiddenInitiationPositions = new ArrayList<Rectangle>();
    }

    public boolean isCorrect(Pair<Integer, Integer> snakeHead) {

        if(forbiddenInitiationPositions.isEmpty()) {
            forbiddenInitiationPositions.add(new Rectangle(FORBIDDEN_X, FORBIDDEN_Y,
                    new Pair<Integer, Integer>(snakeHead.getLeft() - 2, snakeHead.getRight() - 1)));
            return true;
        }
        else {
            Rectangle currentSnake = new Rectangle(FORBIDDEN_X, FORBIDDEN_Y,
                    new Pair<Integer, Integer>(snakeHead.getLeft() - 2, snakeHead.getRight() - 1));
            for (Rectangle i : forbiddenInitiationPositions){
                if(currentSnake.intersection(i))
                    return false;
            }
        }
        return true;
    }

}




/* class to represent a snake with the surrounding field */

class Rectangle {
    private int sideLengthX;
    private int sideLengthY;
    private Pair<Integer, Integer> pointLeftTop;

    public Rectangle(int sideLengthX, int sideLengthY, Pair<Integer, Integer> pointLeftTop) {
        this.sideLengthX = sideLengthX;
        this.sideLengthY = sideLengthY;
        this.pointLeftTop = pointLeftTop;
    }

    public boolean intersection(Rectangle other) {
        int a = this.sideLengthX;
        int b = this.sideLengthY;
        int x = pointLeftTop.getLeft();
        int y = pointLeftTop.getRight();
        int aOther = other.sideLengthX;
        int bOther = other.sideLengthY;
        int xOther = other.pointLeftTop.getLeft();
        int yOther = other.pointLeftTop.getRight();

        if((xOther <= x && x <= xOther + aOther &&
           yOther <= y && y <= yOther +bOther)  ||

           (xOther <= x && x <= xOther + aOther &&
            y <= yOther && yOther <= y + b)     ||

           (x <= xOther && xOther <= x + a &&
            yOther <= y && y <= yOther +bOther)     ||

           (x <= xOther && xOther <= x + a &&
            y <= yOther && yOther <= y + b))
            return true;
        else
            return false;
    }

    public static void main(String Args[])
    {
        /*test*/
        Rectangle pierwszy = new Rectangle(5,3, new Pair(22,19));
        Rectangle drugi = new Rectangle(5,3, new Pair(21,20));
        if(pierwszy.intersection(drugi) == true)
            System.out.println("True");
        else
            System.out.println("False");

    }

}