package com.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayDeque;
import javax.swing.JPanel;

public class BoardPanel extends JPanel {
    
	public static final int BOARD_WIDTH = 30;
    public static final int BOARD_HEIGHT = 48;
    public static final int TILE_SIZE = 20;
    private static final Font FONT = new Font("Tahoma", Font.BOLD, 25);
    private ClientFrame clientFrame;

    public BoardPanel(ClientFrame clientFrame) {
        super();
        this.clientFrame = clientFrame;

        setPreferredSize(new Dimension(BOARD_HEIGHT * TILE_SIZE, BOARD_WIDTH * TILE_SIZE));
        setBackground(new Color(60, 63, 65));
    }

    @Override
    public synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int x = 0; x < BOARD_HEIGHT; x++) {
            for(int y = 0; y < BOARD_WIDTH; y++) {
                BrickType type = clientFrame.getField().getType(x, y);
                if(type != BrickType.EMPTY) {
                    drawBrick(x * TILE_SIZE, y * TILE_SIZE, type, g);
                }
            }
        }

        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        for(int x = 0; x < BOARD_HEIGHT; x++) {
            for(int y = 0; y < BOARD_WIDTH; y++) {
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
            if(clientFrame.isNewGame()) {
                largeMessage = "Get ready to play!";
                smallMessage = "Press Enter to start";
            } else if(clientFrame.isGameOver()) {
                largeMessage = "Game Over!";
                smallMessage = "Press Enter to Restart";
            }
            g.setFont(FONT);
            g.drawString(largeMessage, centerX - g.getFontMetrics().stringWidth(largeMessage) / 2, centerY - 50);
            g.drawString(smallMessage, centerX - g.getFontMetrics().stringWidth(smallMessage) / 2, centerY + 50);
        }
    }

    private synchronized void drawBrick(int x, int y, BrickType type, Graphics g) {

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
