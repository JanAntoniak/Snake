package com.snake;

import java.io.Serializable;
import java.util.ArrayDeque;

public class MessageToClient implements Serializable {
    
    private Snake MySnake;
    private Snake OppSnake;
    private Fruit fruit;
    private boolean gameOver;
    private boolean newGame;
    
    MessageToClient() {
        MySnake = new Snake();
        OppSnake = new Snake();
        fruit = new Fruit();
    }

    public ProtocolFlag getProtocolFlag() {
        return protocolFlag;
    }

    public Snake getMySnake() {
        return MySnake;
    }

    public Snake getOppSnake() {
        return OppSnake;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    private ProtocolFlag protocolFlag;

    public void setMySnake(Snake mySnake) {
        MySnake = mySnake;
    }

    public void setProtocolFlag(ProtocolFlag protocolFlag) {
        this.protocolFlag = protocolFlag;
    }

    public void setOppSnake(Snake oppSnake) {
        OppSnake = oppSnake;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isNewGame() {
        return newGame;
    }

    public void setNewGame(boolean newGame) {
        this.newGame = newGame;
    }

}