package com.snake.model;

import java.io.Serializable;

/**
 * <h1>MessageToServer</h1>
 * Class uses in communications protocol. Contains data sends from clients to server.
 * Players sends only directions, all calculations are made on server.
 * @see Serializable
 * @see com.snake.controller.Server
 * @see com.snake.controller.Client
 */

public class MessageToServer implements Serializable {

    private Direction direction;
    private ProtocolFlag protocolFlag;

    public MessageToServer(ProtocolFlag protocolFlag) {
        this.protocolFlag = protocolFlag;
        this.direction = Direction.UP;
    }

    public MessageToServer() {
        this.protocolFlag = ProtocolFlag.GAMESTATE;
        this.direction = Direction.UP;
    }

    /**
     *
     * @return actually sending protocol flag
     * @see ProtocolFlag
     */
    public ProtocolFlag getProtocolFlag() {
        return protocolFlag;
    }

    public void setProtocolFlag(ProtocolFlag protocolFlag) {
        this.protocolFlag = protocolFlag;
    }

    /**
     *
     * @return move which come from player
     */
    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
