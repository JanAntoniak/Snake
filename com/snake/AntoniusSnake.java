package com.snake;

import com.snake.controller.*;

/**
 * <h1>AntoniusSnake</h1>
 * Main class running both (client and server) applications
 * The type of running depends on run parameter
 */
class AntoniusSnake {
	/**
	 * Main function in the project
	 * @param args type "server" or "client" to run
	 * @throws Exception
     */
    public static void main(String[] args) throws Exception {
    		if(args[0].equals("server")) {
    			Server server = new Server();
    			server.Start();
    		}
    		else if(args[0].equals("client")){
    			Client client = new Client();
    			client.Start(args[1]);
    		}
    }
}