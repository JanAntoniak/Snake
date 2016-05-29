package com.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

public class ClientFrame extends JFrame {

    boolean ready = false;
    private static final int MOVES_BUFFER = 3;
    private BoardPanel board;
    private SidePanel side;
    private LinkedList<Direction> directions;
    private Direction lastDirection;
    private boolean isNewGame = true;
    private boolean isGameOver = false;
    private static final int windowHeight = 600;
    private static final int windowWidth = 1260;
    private Field field;
    private Snake snake1;
    private Snake snake2;
    private Fruit fruit;


    public ClientFrame() {
        super("Snake");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	// Here we can add whatever we want to
                System.exit(0);
            }
        });
        
        setResizable(false);

        directions = new LinkedList<>();
        setSize(windowWidth, windowHeight);

        field = new Field();
        fruit = new Fruit(this.field);
        snake1 = new Snake(field, new Point(10,10), 1);
        snake2 = new Snake(field, new Point(15,15), 2);



        setLocationRelativeTo(null);
        setVisible(true);

        this.board = new BoardPanel(this);
        this.side = new SidePanel(this);
        add(side, BorderLayout.WEST);
        add(board, BorderLayout.EAST);
        setLocationRelativeTo(null);
        setVisible(true);
        this.addKeyListener();
        this.isNewGame = true;
    }

    public synchronized Snake getSnakeOfMine() {
        return this.snake1;
    }

    public synchronized Snake getSnakeOfOpponent() {
        return this.snake2;
    }

    public synchronized Field getField() {
        return field;
    }

    public synchronized Fruit getFruit() {
        return fruit;
    }

    public Direction getDirection() {
        return directions.pollFirst();
    }

    public synchronized void setFruit(Fruit fruit) {
        this.fruit = fruit;
        this.field.setFruit(fruit.getPosition());
    }

    public synchronized void setSnakeOfMine(Snake snake) {
        this.snake1 = snake;
        this.field.add(snake);
    }

    public synchronized void setSnakeOfOpponent(Snake snake) {
        this.snake2 = snake;
        this.field.add(snake);
    }

    public synchronized void repaint() {
        board.repaint();
        side.repaint();
    }

    public synchronized boolean isNewGame() {
        return isNewGame;
    }

    public synchronized void setNewGame(boolean state) {
        isNewGame = state;
    }

    public synchronized boolean isGameOver() {
        return isGameOver;
    }

    public synchronized void setGameOver(boolean state) {
        this.isGameOver = state;
    }

    private void addKeyListener() {
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {

                    case KeyEvent.VK_UP:
                        if (!isGameOver) {
                            if (directions.size() < MOVES_BUFFER) {
                                if (lastDirection != Direction.DOWN && lastDirection != Direction.UP) {
                                    lastDirection = Direction.UP;
                                    directions.addLast(Direction.UP);
                                }
                            }
                        }
                        break;

                    case KeyEvent.VK_DOWN:
                        if (!isGameOver) {
                            if (directions.size() < MOVES_BUFFER) {
                                if (lastDirection != Direction.UP && lastDirection != Direction.DOWN) {
                                    lastDirection = Direction.DOWN;
                                    directions.addLast(Direction.DOWN);
                                }
                            }
                        }
                        break;

                    case KeyEvent.VK_LEFT:
                        if (!isGameOver) {
                            if (directions.size() < MOVES_BUFFER) {
                                if (lastDirection != Direction.RIGHT && lastDirection != Direction.LEFT) {
                                    lastDirection = Direction.LEFT;
                                    directions.addLast(Direction.LEFT);
                                }
                            }
                        }
                        break;

                    case KeyEvent.VK_RIGHT:
                        if (!isGameOver) {
                            if (directions.size() < MOVES_BUFFER) {
                                if (lastDirection != Direction.LEFT && lastDirection != Direction.RIGHT) {
                                    lastDirection = Direction.RIGHT;
                                    directions.addLast(Direction.RIGHT);
                                }
                            }
                        }
                        break;

                    case KeyEvent.VK_ENTER:
                        if(isNewGame || isGameOver) {
                            setReady(true);
                            isNewGame = true;
                            //resetGame();
                        }
                        break;

                }
            }

        });


    }

    public synchronized void setReady(boolean state) {
        ready = state;
    }

    public synchronized boolean getReady() {
        return ready;
    }

}
