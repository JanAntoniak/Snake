package com.antoniak;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;


public class Game implements ActionListener, KeyListener {

    public Render render;

    private JButton startButton;

    private JButton exitButton;

    public Game() {

        JFrame jframe = new JFrame();
        //Timer timer = new Timer(20, this);

        Toolkit kit	=	Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        render = new Render();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = screenHeight/2;
        int windowWidth = screenWidth/2;

        jframe.add(render);
        jframe.setTitle("Snake");
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setSize(windowWidth, windowHeight);

        jframe.addKeyListener(this);
        jframe.setResizable(false);

        /*set background*/
        jframe.setLayout(new BorderLayout());
        ImageIcon background = new ImageIcon("background.jpg");
        background = new ImageIcon(background.getImage().getScaledInstance(windowWidth, windowHeight, Image.SCALE_DEFAULT));
        jframe.setContentPane(new JLabel((background)));
        jframe.setLayout(new FlowLayout());

        /*make button*/
        Image img = new ImageIcon("logo.png").getImage();
        jframe.setIconImage(img);

        ImageIcon pressedIcon;
        ImageIcon rolledOver;
        ImageIcon exitPressedIcon;
        ImageIcon exitRolledOver;

        pressedIcon = new ImageIcon("pressedIcon.png");
        pressedIcon= new ImageIcon(pressedIcon.getImage().getScaledInstance(100, 50, Image.SCALE_DEFAULT));

        ImageIcon buttonImg = new ImageIcon("start.png");
        buttonImg = new ImageIcon(buttonImg.getImage().getScaledInstance(100, 50, Image.SCALE_DEFAULT));

        rolledOver = new ImageIcon("rolledOver.png");
        rolledOver = new ImageIcon(rolledOver.getImage().getScaledInstance(100, 50, Image.SCALE_DEFAULT));

        exitPressedIcon = new ImageIcon("exitPressed.png");
        exitPressedIcon = new ImageIcon(exitPressedIcon.getImage().getScaledInstance(100, 50, Image.SCALE_DEFAULT));

        exitRolledOver = new ImageIcon("exitRolledOver.png");
        exitRolledOver = new ImageIcon(exitRolledOver.getImage().getScaledInstance(100, 50, Image.SCALE_DEFAULT));

        startButton = new JButton();
        startButton.setIcon(buttonImg);
        startButton.setPressedIcon(pressedIcon);
        startButton.setRolloverIcon(rolledOver);
        startButton.addActionListener(this);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);

        ImageIcon exitImage = new ImageIcon("exit.png");
        exitImage= new ImageIcon(exitImage.getImage().getScaledInstance(100, 50, Image.SCALE_DEFAULT));
        exitButton = new JButton();
        exitButton.setIcon(exitImage);
        exitButton.setPressedIcon(exitPressedIcon);
        exitButton.setRolloverIcon(exitRolledOver);
        exitButton.addActionListener(this);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);

        JPanel jpanel = new JPanel();
        jpanel.add(startButton);
        jpanel.add(exitButton);
        jpanel.setOpaque(false);

        Dimension expectedDimension = new Dimension(400, 300);
        jpanel.setPreferredSize(expectedDimension);
        jpanel.setMaximumSize(expectedDimension);
        jpanel.setMinimumSize(expectedDimension);
        jpanel.setLayout(new GridBagLayout());

        jframe.add(jpanel, BorderLayout.CENTER);
        jframe.setFocusable(true);
        jframe.setVisible(true);

    }

    private void startGame() {
        render.repaint();
    }

    public static void main(String Args[]) {
        Game game = new Game();
    }

    /*public void repaint(Graphics g) {

    }*/

    @Override
    public void keyReleased(KeyEvent e)
    {
        if(!startButton.isVisible()) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                System.out.print("hej");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (startButton.getModel().isArmed()) {
            startButton.setVisible(false);
            exitButton.setVisible(false);
            this.startGame();
        }
        if(exitButton.getModel().isArmed()) {
            System.exit(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {

    }

}
