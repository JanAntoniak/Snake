# Connection protocol used in the game

### Connection establishing
    Client ⟹ REQUEST Server
    Client ⟸ ACCEPT [random_player_number]  Server


### Starting the game
    Client ⟹ STARTGAME Server
    Client ⟸  NEWGAME   Server


### The game
    Client ⟸ SNAKEBEGIN [chain_of_integer_pairs] Server
    Client ⟸ SNAKEEND                            Server

>to avoid improper data interpretetion the value of SNAKEEND is negative integer

    Client ⟸  FRUIT [pair_of_integers] Server
    Client ⟹ MOVE  [direction_value]  Server


### Ending the game
    Client ⟹ ENDGAME Server
OR

    Client ⟸ COLLISION [id_of_winner]  Server

AND

    Client ⟹ EXIT  Server
OR

    Client ⟸ STARTGAME  Server
    
