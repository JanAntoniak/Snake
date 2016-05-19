# Connection protocol used in the game

### Connection establishing
    Client :arrow_right: REQUEST Server
    Client :arrow_left: ACCEPT [random_player_number]  Server


### Starting the game
    Client :arrow_right: STARTGAME Server
    Client :arrow_left:  NEWGAME   Server


### The game
    Client :arrow_left: SNAKEBEGIN [chain_of_integer_pairs] Server
    Client :arrow_left: SNAKEEND                            Server

>to avoid improper data interpretetion the value of SNAKEEND is negative integer

    Client :arrow_left:  FRUIT [pair_of_integers] Server
    Client :arrow_right: MOVE  [direction_value]  Server


### Ending the game
    Client :arrow_right: ENDGAME Server
OR

    Client :arrow_left: COLLISION [id_of_winner]  Server

AND

    Client :arrow_right: EXIT  Server
OR

    Client :arrow_right: STARTGAME  Server