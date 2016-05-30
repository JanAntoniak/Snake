package com.snake.model;

import java.io.Serializable;
import java.util.ArrayDeque;

/**
 * <h1>MessageToClient</h1>
 * Class uses in communications protocol. Contains data sends from server to clients.
 * Both players gets very similar messages, Snakes makes difference.
 * @see Serializable
 * @see com.snake.controller.Server
 * @see com.snake.controller.Client
 */
public class MessageToClient implements Serializable {

    private Snake MySnake;
    private Snake OppSnake;
    private Fruit fruit;
    private boolean gameOver;
    private boolean newGame;
    private ProtocolFlag protocolFlag;

    public MessageToClient() {
        MySnake = new Snake();
        OppSnake = new Snake();
        fruit = new Fruit();
    }

    /**
     *
     * @return actually sending protocol flag
     * @see ProtocolFlag
     */
    public ProtocolFlag getProtocolFlag() {
        return protocolFlag;
    }

    /**
     *
     * @return body of player's snake
     */
    public Snake getMySnake() {
        return MySnake;
    }

    /**
     *
     * @return body of opponent's snake
     */
    public Snake getOppSnake() {
        return OppSnake;
    }

    /**
     *
     * @return actual fruit on the field
     */
    public Fruit getFruit() {
        return fruit;
    }

    /**
     * @return boolean variable informing if game is ended
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Sets which snake is owned by the player
     * @param mySnake
     */
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