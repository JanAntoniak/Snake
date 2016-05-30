package com.snake;

import com.snake.controller.*;

class AntoniusSnake {
    public static void main(String[] args) throws Exception {
    		if(args[0] == "server") {
    			Server server = new Server();
    			server.Start();
    		}
    		else if(args[0] == "client"){
    			Client client = new Client();
    			client.Start(args[1]);
    		}
    }
}