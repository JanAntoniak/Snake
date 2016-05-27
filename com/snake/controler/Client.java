package com.snake;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Client {

    private ClientFrame clientFrame;
    private static States actualState;
    private static MessageToClient fromServer;
    private static MessageToServer toServer;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private boolean windowOpen = false;

    public Client() {
        actualState = States.ESTABLISHING;
        fromServer = new MessageToClient();
        toServer = new MessageToServer();
    }

    public void Start(String hostName) throws Exception {
        SocketChannel socket = null;

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
        oos.flush();
        oos.close();
        socket.close();
    }

    private States Establish() throws IOException, ClassNotFoundException {
        toServer.setProtocolFlag(ProtocolFlag.REQUEST);
        oos.reset();
        oos.writeObject(toServer);
        oos.flush();
        fromServer = (MessageToClient) (ois.readObject());
        if (fromServer.getProtocolFlag() == ProtocolFlag.ACCEPT)
            return States.PREPARING;
        else
            return States.ERROR;
    }

    private synchronized States Prepare() throws IOException, ClassNotFoundException, InterruptedException {
        if(windowOpen == false) {
            clientFrame = new ClientFrame();
            windowOpen = true;
        }
        clientFrame.setReady(false);
        while(clientFrame.getReady() != true)
            continue;
        toServer = new MessageToServer(ProtocolFlag.STARTGAME);
        oos.reset();
        oos.writeObject(toServer);
        oos.flush();
        fromServer = (MessageToClient) (ois.readObject());

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
        oos.reset();
        oos.writeObject(toServer);
        oos.flush();

        fromServer = new MessageToClient();
        fromServer = (MessageToClient) (ois.readObject());
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

    private States ServeError() throws IOException, ClassNotFoundException {
        //wyswietlenie komunikatu i decyzja czy laczyc sie od nowa, czy zamknac aplikacje
        return States.END;
    }

    public static void main(String args[]) throws Exception {
        Client cl = new Client();
        cl.Start("127.0.0.1");
    }
}


