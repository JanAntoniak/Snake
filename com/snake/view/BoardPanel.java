package com.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

public class BoardPanel extends JPanel {
    
	public static final int BOARD_HEIGHT = 30;
    public static final int BOARD_WIDTH = 48;
    public static final int TILE_SIZE = 20;
    private static final Font FONT = new Font("Tahoma", Font.BOLD, 25);
    private ClientFrame clientFrame;

    public BoardPanel(ClientFrame clientFrame) {
        super();
        this.clientFrame = clientFrame;

        setPreferredSize(new Dimension(BOARD_WIDTH * TILE_SIZE, BOARD_HEIGHT * TILE_SIZE));
        setBackground(new Color(60, 63, 65));
    }

    @Override
    public synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.print("qweqweqweqw ");
        for(int x = 0; x < BOARD_WIDTH; x++) {
            for(int y = 0; y < BOARD_HEIGHT; y++) {
                BrickType type = clientFrame.getField().getType(x, y);
                if(type != BrickType.EMPTY) {
                    drawBrick(x * TILE_SIZE, y * TILE_SIZE, type, g);
                }
            }
        }

        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        for(int x = 0; x < BOARD_WIDTH; x++) {
            for(int y = 0; y < BOARD_HEIGHT; y++) {
                g.drawLine(x * TILE_SIZE, 0, x * TILE_SIZE, getHeight());
                g.drawLine(0, y * TILE_SIZE, getWidth(), y * TILE_SIZE);
            }
        }

        if(clientFrame.isGameOver() || clientFrame.isNewGame()) {
            g.setColor(Color.WHITE);

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            String largeMessage = null;
            String smallMessage = null;
            String winner = null;
            if(clientFrame.isNewGame()) {
                largeMessage = "Get ready to play!";
                smallMessage = "Press Enter to start";
                winner = "";
            } else if(clientFrame.isGameOver()) {
                if(clientFrame.getSnakeOfMine().isWinner() == true)
                    winner = "YOU WIN!";
                else
                    winner = "YOU LOST!";

                largeMessage = "Game Over!";
                smallMessage = "Press Enter to Restart";
            }
            g.setFont(FONT);
            g.drawString(winner, centerX - g.getFontMetrics().stringWidth(winner) / 2, centerY - 100);
            g.drawString(largeMessage, centerX - g.getFontMetrics().stringWidth(largeMessage) / 2, centerY - 50);
            g.drawString(smallMessage, centerX - g.getFontMetrics().stringWidth(smallMessage) / 2, centerY + 50);
        }
        if(clientFrame.isExit() == true) {
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            String error = "There was an connection error.";
            String close = "Close the application, please.";
            g.setColor(new Color(178, 177, 163) );
            g.fillRect(0, 0, TILE_SIZE*BOARD_WIDTH, TILE_SIZE*BOARD_HEIGHT);
            g.setColor(new Color(1, 1, 1) );
            g.setFont(FONT);
            g.drawString(error, centerX - g.getFontMetrics().stringWidth(error) / 2, centerY - 50);
            g.drawString(close, centerX - g.getFontMetrics().stringWidth(close) / 2, centerY + 50);
        }

    }

    private void drawBrick(int x, int y, BrickType type, Graphics g) {

        switch(type) {

            case FRUIT:
                g.setColor(Color.RED);
                g.fillOval(x + 2, y + 2, TILE_SIZE - 4, TILE_SIZE - 4);
                break;

            case SNAKE1:
                g.setColor(new Color(68, 178, 52) );
                g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                break;

            case SNAKE2:
                g.setColor(new Color(54, 56, 114) );
                g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                break;
        }
    }
}