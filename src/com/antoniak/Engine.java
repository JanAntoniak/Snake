package com.antoniak;

import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;


/* oblusga klawiatury w trakcie gry*/
public class Engine extends JFrame implements ActionListener, KeyListener
{
    public Engine(JFrame frame) {

    }
    public  void control(Snake snake) {

        this.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    public void printOn() {
        System.out.print("DZIALAM! ");
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) { //Up Arrow
            printOn();
        } else if (e.getKeyCode() == 40) { //Down Arrow
            printOn();
        } else if (e.getKeyCode() == 39) { // right
            printOn();
        } else if (e.getKeyCode() == 37) { // left
            printOn();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
