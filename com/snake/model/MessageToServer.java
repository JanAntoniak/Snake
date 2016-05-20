package com.snake;

public class MessageToServer implements Serializable {

	public Direction direction;
    public ProtocolFlag protocolFlag;

    MessageToServer(ProtocolFlag protocolFlag) {
        this.protocolFlag = protocolFlag;
    }
    
    MessageToServer(ProtocolFlag protocolFlag, Direction direction) {
        this.direction = direction;
        this.protocolFlag = protocolFlag;
    }
}
