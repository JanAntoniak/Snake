package com.antoniak;


public class Field
{
    private static final int HEIGHT = 30;
    private static final int WIDTH = 48;
    private int[][] field;
    public Field()
    {
        field = new int[HEIGHT][WIDTH];
    }
    public int getHeight()
    {
        return HEIGHT;
    }
    public int getWidth()
    {
        return WIDTH;
    }
    public static void main(String args[])
    {
        /*test*/
        Field myField = new Field();
        for (int[] tab : myField.field)
        {
            for (int elem : tab)
                System.out.print(elem + " ");
            System.out.println("");
        }
    }
}
