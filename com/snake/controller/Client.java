package com.snake.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import com.snake.model.*;
import com.snake.view.*;

public class Client {

    private ClientFrame clientFrame;
    private static States actualState;
    private static MessageToClient fromServer;
    private static MessageToServer toServer;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private boolean windowOpen = false;
    private SocketChannel socket = null;


    public Client() {
        actualState = States.ESTABLISHING;
        fromServer = new MessageToClient();
        toServer = new MessageToServer();
    }

    public void closeAll() throws IOException {
        oos.flush();
        oos.close();
        socket.close();
    }

    public void Start(String hostName) throws Exception {

        socket = SocketChannel.open(new InetSocketAddress(hostName, 7777));
        oos = new ObjectOutputStream(socket.socket().getOutputStream());
        ois = new ObjectInputStream(socket.socket().getInputStream());

        while (true) {

            switch (actualState) {
                case ESTABLISHING:
                    System.out.print("est ");
                    actualState = Establish();
                    break;

                case PREPARING:
                    System.out.print("prep ");
                    actualState = Prepare();
                    break;

                case GAME:
                    System.out.print("game ");
                    actualState = Game();
                    break;

                case ERROR:
                    System.out.print("errr ");
                    actualState = ServeError();

            }

            if (actualState == States.END)
                break;
        }
    }

    private States Establish() throws IOException, ClassNotFoundException {
        toServer.setProtocolFlag(ProtocolFlag.REQUEST);

        try{
            oos.reset();
            oos.writeObject(toServer);
            oos.flush();
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
            oos.reset();
            oos.writeObject(toServer);
            oos.flush();
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

    private States Game() throws IOException, ClassNotFoundException, InterruptedException {

        toServer = new MessageToServer();
        toServer.setProtocolFlag(ProtocolFlag.STARTGAME);
        toServer.setDirection(clientFrame.getDirection());
        try {
            oos.reset();
            oos.writeObject(toServer);
            oos.flush();
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

    private synchronized States ServeError() throws IOException, ClassNotFoundException {
        clientFrame.setExit(true);
        System.out.print(clientFrame.isExit());
        clientFrame.repaint();

        return States.END;
    }


    public static void main(String args[]) throws Exception {
        Client cl = new Client();
        cl.Start("127.0.0.1");
    }
}


