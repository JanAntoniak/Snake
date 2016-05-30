package com.snake.model;

import java.io.Serializable;

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

    public ProtocolFlag getProtocolFlag() {
        return protocolFlag;
    }

    public void setProtocolFlag(ProtocolFlag protocolFlag) {
        this.protocolFlag = protocolFlag;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
