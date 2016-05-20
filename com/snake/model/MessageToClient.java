package com.snake;

import java.io.Serializable;

public class MessageToClient implements Serializable {
    public ProtocolFlag protocolFlag;
    public Snake snake1;
    public Snake snake2;
    public Fruit fruit;
    boolean winner;

    public MessageToClient(Snake snake1, Snake snake2, Fruit fruit, ProtocolFlag protocolFlag, boolean winner) {
        this.protocolFlag = protocolFlag;
        this.snake1 = snake1;
        this.snake2 = snake2;
        this.fruit = fruit;
        this.winner = winner;
    }

    public MessageToClient(ProtocolFlag protocolFlag) {
        this.protocolFlag = protocolFlag;
    }
}