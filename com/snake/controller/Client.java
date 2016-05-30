package com.snake.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import com.snake.model.*;
import com.snake.view.*;

/**
* Class Client that is responsible for take the 
* message from the server, call rendering and 
* send the message to the server.
* The protocol used here is describe in SnakeProtocol.md in catalog model.
*/
public class Client {

	/**
	 * The clientFrame instance.
	 */
    private ClientFrame clientFrame;
    
    /**
	 * The actual state is a variable that says what state the client is in.
	 */
    private static States actualState;
    
	/**
	 * FromServer is a MessageToClient instance. 
	 * This is an object that is taken from the server.
	 * It contains Snakes and Fruit positions, 
	 * flags if the game is over and if so the winner. 
	 */
    private static MessageToClient fromServer;
    
    /**
	 * ToServer is a MessageToServer instance. 
	 * This is an object that is sent to the server.
	 * It contains direction caught from the keyboard. 
	 */
    private static MessageToServer toServer;
    
    /**
	 * The ObjectOutputStream instance.
	 */
    private ObjectOutputStream oos = null;

    /**
	 * The ObjectInputStream instance.
	 */
    private ObjectInputStream ois = null;
    
    /**
	 * The flag that says if there is any window with the game open.
	 */
    private boolean windowOpen = false;
    
    /**
	 * The SocketChannel instance.
	 */
    private SocketChannel socket = null;

    /**
     * The constructor of Client class.
     * There are created new messages and set the first state.
     */
    public Client() {
        actualState = States.ESTABLISHING;
        fromServer = new MessageToClient();
        toServer = new MessageToServer();
    }

    /**
     * closeAll method closes the socket and stream.
     */
    public void closeAll() throws IOException {
        try{
            oos.flush();
            oos.close();
            socket.close();
        } catch(IOException e) {
            System.out.print("Fatal Error!");
            System.exit(1);
        }
    }

    /**
     * Start method starts the Client. It connects with server and
     * control states of the game calling propr methods. 
     */ 
    public void Start(String hostName) throws Exception {

        socket = SocketChannel.open(new InetSocketAddress(hostName, 7777));
        oos = new ObjectOutputStream(socket.socket().getOutputStream());
        ois = new ObjectInputStream(socket.socket().getInputStream());

        while (true) {

            switch (actualState) {
                case ESTABLISHING:
                    actualState = Establish();
                    break;

                case PREPARING:
                    actualState = Prepare();
                    break;

                case GAME:
                    actualState = Game();
                    break;

                case ERROR:
                    actualState = ServeError();

            }

            if (actualState == States.END)
                break;
        }
    }

    /**
     * Establish is a method that statrs whole protocol.
     */
    private States Establish() throws IOException, ClassNotFoundException {
        toServer.setProtocolFlag(ProtocolFlag.REQUEST);

        try{
            this.writeToServer(toServer);
        } catch(IOException e) {
            return States.ERROR;
        }
        try{
            fromServer = (MessageToClient) (ois.readObject());
        } catch(IOException e) {
            return States.ERROR;
        }
        if(fromServer.getProtocolFlag() == ProtocolFlag.ERROR)
            return States.ERROR;
        else if (fromServer.getProtocolFlag() == ProtocolFlag.ACCEPT)
            return States.PREPARING;
        else return States.ERROR;
    }

    /**
     * Prepare method prepares Client to play the game.
     */
    private synchronized States Prepare() throws IOException, ClassNotFoundException, InterruptedException {
        if(windowOpen == false) {
            clientFrame = new ClientFrame(this);
            windowOpen = true;
        }
        clientFrame.setReady(false);
        if(fromServer.getProtocolFlag() == ProtocolFlag.ERROR)
            return States.ERROR;
        while(clientFrame.getReady() != true) {
            continue;
        }
        toServer = new MessageToServer(ProtocolFlag.STARTGAME);
        try {
            this.writeToServer(toServer);
        } catch(IOException e) {
            return States.ERROR;
        }


        try{
            fromServer = (MessageToClient) (ois.readObject());
        } catch (IOException e) {
            return States.ERROR;
        }
        if(fromServer.getProtocolFlag() == ProtocolFlag.ERROR)
            return States.ERROR;
        if (fromServer.getProtocolFlag() == ProtocolFlag.NEWGAME) {
            return States.GAME;
        }
        else {
            return States.ERROR;
        }
    }

    /**
     * Game is a method that takes messages from server when the game is not ended,
     * calls render the window and sends message to server.   
     */ 
    private States Game() throws IOException, ClassNotFoundException, InterruptedException {

        toServer = new MessageToServer();
        toServer.setProtocolFlag(ProtocolFlag.STARTGAME);
        toServer.setDirection(clientFrame.getDirection());
        try {
            this.writeToServer(toServer);
        } catch(IOException e) {
            return States.ERROR;
        }

        fromServer = new MessageToClient();
        try {
            fromServer = (MessageToClient) (ois.readObject());
        } catch (IOException e) {
            return States.ERROR;
        }
        if(fromServer.getProtocolFlag() == ProtocolFlag.ERROR)
            return States.ERROR;

        clientFrame.setGameOver(fromServer.isGameOver());
        clientFrame.setNewGame(fromServer.isNewGame());
        if(fromServer.isGameOver() != true) {
            clientFrame.setNewGame(false);
            clientFrame.getField().clearField();
            clientFrame.setFruit(fromServer.getFruit());
            clientFrame.setSnakeOfMine(fromServer.getMySnake());
            clientFrame.setSnakeOfOpponent(fromServer.getOppSnake());
        }
        clientFrame.repaint();
        if(fromServer.isGameOver() == true) {
            clientFrame.getSnakeOfMine().setWinner(fromServer.getMySnake().isWinner());
            clientFrame.setGameOver(true);
            clientFrame.repaint();
            return States.PREPARING;
        }
        return States.GAME;
    }

    /**
     * ServerError method is called when there was an error
     * and application has to be closed.
     */ 
    private synchronized States ServeError() throws IOException, ClassNotFoundException {
        clientFrame.setExit(true);
        clientFrame.repaint();

        return States.END;
    }

    /**
     * writeToServer is a method that send messages to server
     */
    private void writeToServer(MessageToServer msg) throws IOException {
        oos.reset();
        oos.writeObject(msg);
        oos.flush();
    }

    public static void main(String args[]) throws Exception {
        Client cl = new Client();
        cl.Start("127.0.0.1");
    }
}


