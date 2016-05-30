package com.snake.model;

/**
 * <h1>ProtocolFlag</h1>
 * Enum type used for synchronising and establishing connection between server and clients
 */
public enum ProtocolFlag {
        REQUEST,
        ACCEPT,
        STARTGAME,
        NEWGAME,
        GAMESTATE,
        ENDGAME,
        GAMEOVER,
        ERROR
        }