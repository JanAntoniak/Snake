package com.snake;

public enum ProtocolFlag {
        REQUEST,
        ACCEPT,
        STARTGAME,
        NEWGAME,
        SNAKEBEGIN,
        SNAKEEND(-1),
        FRUIT,
        MOVE,
        COLLISION,
        ENDGAME,
        EXIT
        }