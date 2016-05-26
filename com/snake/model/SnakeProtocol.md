# Connection protocol used in the game

Server and Client exchange information using classes MessageToClient and MessageToServer. 

### Connection establishing
    Client ⟹ REQUEST   Server
    Client ⟸ ACCEPT    Server


### Starting the game
    Client ⟹ STARTGAME    Server
    Client ⟸  NEWGAME     Server


### The game
    Client ⟸ GAMESTATE   Server
    Client ⟹ MOVE        Server


### Game over
    Client ⟹ ENDGAME Server
    Client ⟸ GAMEOVER Server
OR

    Client ⟸ GAMESTATE [with not null gameOver variable] Server

### Closing the game

    Client ⟹ ENDGAME  Server
    Client ⟸ GAMEOVER Server

### Restarting the game

    Client ⟸ STARTGAME  Server
    Client ⟸  NEWGAME   Server