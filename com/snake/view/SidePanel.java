package com.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.io.Serializable;

public class SidePanel extends JPanel implements Serializable{

    private ClientFrame clientFrame;

    private static final int STATISTICS_OFFSET = 100;
    private static final int CONTROLS_OFFSET = 290;
    private static final int MESSAGE_STRIDE = 30;
    private static final int SMALL_OFFSET = 30;
    private static final int LARGE_OFFSET = 50;
    private static final Font FONT_BIG = new Font("Garamond", Font.BOLD, 19);
    private static final Font FONT_SMALL = new Font("Garamond", Font.PLAIN, 13);

    public SidePanel(ClientFrame clientFrame) {
        this.clientFrame = clientFrame;
        setPreferredSize(new Dimension(200, 1050));
        setPreferredSize(new Dimension(300, BoardPanel.BOARD_WIDTH * BoardPanel.BOARD_HEIGHT ));
        setBackground( new Color(73, 144, 68) );
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        g.setColor(Color.WHITE);

        g.setFont(FONT_BIG);
        g.drawString("Statistics", SMALL_OFFSET, STATISTICS_OFFSET);
        g.drawString("How to play:", SMALL_OFFSET, CONTROLS_OFFSET);

        g.setFont(FONT_SMALL);

        Snake s1 = clientFrame.getSnakeOfMine();
        Snake s2 = clientFrame.getSnakeOfOpponent();
        Fruit f = clientFrame.getFruit();
        int drawY = STATISTICS_OFFSET;
        g.drawString("Your Total Score: " + s1.getScore(), LARGE_OFFSET, drawY += MESSAGE_STRIDE);
        g.drawString("Opponent Total Score: " + s2.getScore(), LARGE_OFFSET, drawY += MESSAGE_STRIDE);
        g.drawString("Fruit Score: " + f.getValue(), LARGE_OFFSET, drawY += MESSAGE_STRIDE);
        drawY = CONTROLS_OFFSET;
        g.drawString("Move Up: ↑", LARGE_OFFSET, drawY += MESSAGE_STRIDE);
        g.drawString("Move Down:\u2193", LARGE_OFFSET, drawY += MESSAGE_STRIDE);
        g.drawString("Move Left:\u2190", LARGE_OFFSET, drawY += MESSAGE_STRIDE);
        g.drawString("Move Right:→", LARGE_OFFSET, drawY += MESSAGE_STRIDE);
    }
}
