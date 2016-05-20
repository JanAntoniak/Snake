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
OR

    Client ⟸ GAMESTATE [with not null winner id] Server

### Closing the game

    Client ⟹ ENDGAME  Server

### Restarting the game

    Client ⟸ STARTGAME  Server