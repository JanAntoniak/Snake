package com.snake;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {

    private static States actualState;
    private static MessageToServer fromPlayer1;
    private static MessageToServer fromPlayer2;
    private static MessageToClient toClient;
    ObjectInputStream ois1 = null;
    ObjectOutputStream oos1 = null;
    ObjectInputStream ois2 = null;
    ObjectOutputStream oos2 = null;
    ServerController serverController = null;
    public Clock timer;
    private static final long FRAME_TIME = 1500L / 50L;

    public Server() {
        actualState = States.ESTABLISHING;
        fromPlayer1 = new MessageToServer();
        fromPlayer2 = new MessageToServer();
        toClient = new MessageToClient();
        this.timer = new Clock(1.5f);
    }

    public void Start() throws Exception {
        ServerSocketChannel server = null;

        SocketChannel socket = null;
        SocketChannel socket2 = null;


        server = ServerSocketChannel.open();
        server.socket().bind(new InetSocketAddress(7777));
        socket = server.accept();
        socket2 = server.accept();

        System.out.println("Nowe polaczenie przychodzace");

        serverController = new ServerController();
        serverController.getSnake1().setIDSnake(1);
        serverController.getSnake2().setIDSnake(2);

        ois1 = new ObjectInputStream(socket.socket().getInputStream());
        oos1 = new ObjectOutputStream(socket.socket().getOutputStream());

        ois2 = new ObjectInputStream(socket2.socket().getInputStream());
        oos2 = new ObjectOutputStream(socket2.socket().getOutputStream());

        try {


            while (true) {
                switch (actualState) {
                    case ESTABLISHING:
                        actualState = Establish();
                        break;

                    case PREPARING:
                        System.out.print("prepare ");
                        actualState = Prepare();
                        break;

                    case GAME:
                        System.out.print("game ");
                        actualState = Move();
                        break;

                    case END:
                        actualState = EndGame();
                        break;

                    case ERROR:
                        actualState = ServeError();
                        break;


                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFound");
        } catch (Exception e) {
            System.out.println("Other exception");
            e.printStackTrace();
        }
        socket.close();
        ois1.close();
        server.close();
    }

    static public void main(String[] args) throws Exception {
        Server sv = new Server();
        sv.Start();
    }

    private States Establish() throws IOException, ClassNotFoundException {

        fromPlayer1 = (MessageToServer) (ois1.readObject());
        fromPlayer2 = (MessageToServer) (ois2.readObject());


        if (fromPlayer1.getProtocolFlag() != ProtocolFlag.REQUEST || fromPlayer2.getProtocolFlag() != ProtocolFlag.REQUEST)
            return States.ERROR;

        toClient.setProtocolFlag(ProtocolFlag.ACCEPT);

        oos1.reset();
        oos1.writeObject(toClient);
        oos1.flush();
        oos2.reset();
        oos2.writeObject(toClient);
        oos2.flush();

        return States.PREPARING;
    }

    private States Prepare() throws IOException, ClassNotFoundException, InterruptedException {
        fromPlayer1 = (MessageToServer) (ois1.readObject());
        fromPlayer2 = (MessageToServer) (ois2.readObject());

        if (fromPlayer1.getProtocolFlag() != ProtocolFlag.STARTGAME || fromPlayer2.getProtocolFlag() != ProtocolFlag.STARTGAME)
            return States.END;

        toClient.setProtocolFlag(ProtocolFlag.NEWGAME);

        oos1.reset();
        oos1.writeObject(toClient);
        oos1.flush();
        oos2.reset();
        oos2.writeObject(toClient);
        oos2.flush();

        serverController.resetGame();
        return States.GAME;
    }

    private synchronized States Move() throws IOException, ClassNotFoundException, InterruptedException {

        serverController.setNewGame(true);
        serverController.setGameOver(false);
        while (!serverController.isGameOver()) {

            long start = System.nanoTime();
            timer.update();

            fromPlayer1 = (MessageToServer) (ois1.readObject());
            fromPlayer2 = (MessageToServer) (ois2.readObject());

            if (fromPlayer1.getDirection() != null)
                serverController.getSnake1().setDirections(fromPlayer1.getDirection());
            if (fromPlayer2.getDirection() != null)
                serverController.getSnake2().setDirections(fromPlayer2.getDirection());

            if (timer.hasElapsedCycle()) {
                serverController.updateGame();
            }

            setMessageToClient(serverController.getSnake1().getIDSnake());
            sendMessageToClient(serverController.getSnake1().getIDSnake());

            setMessageToClient(serverController.getSnake2().getIDSnake());
            sendMessageToClient(serverController.getSnake2().getIDSnake());

            long delta = (System.nanoTime() - start) / 1000000L;
            if (delta < FRAME_TIME) {
                try {
                    Thread.sleep(FRAME_TIME - delta);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return States.PREPARING;
    }

    private States EndGame() throws IOException {
        toClient.setProtocolFlag(ProtocolFlag.GAMEOVER);

        oos1.reset();
        oos1.writeObject(toClient);
        oos1.flush();
        oos2.reset();
        oos2.writeObject(toClient);
        oos2.flush();

        return States.ESTABLISHING;
    }

    private States ServeError() throws IOException {
        toClient.setProtocolFlag(ProtocolFlag.ERROR);

        oos1.reset();
        oos1.writeObject(toClient);
        oos1.flush();
        oos2.reset();
        oos2.writeObject(toClient);
        oos2.flush();

        return States.ESTABLISHING;
    }

    public void setMessageToClient(int IDSnake) {

        switch(IDSnake) {
            case 1:
                toClient.setMySnake(serverController.getSnake1());
                toClient.setOppSnake(serverController.getSnake2());
                break;
            case 2:
                toClient.setMySnake(serverController.getSnake2());
                toClient.setOppSnake(serverController.getSnake1());
                break;
        }
        toClient.setFruit(serverController.getFruit());
        toClient.setGameOver(serverController.isGameOver());
    }

    public void sendMessageToClient(int IDSnake) throws IOException {

        switch(IDSnake) {
            case 1:
                oos1.reset();
                oos1.writeObject(toClient);
                oos1.flush();
                break;
            case 2:
                oos2.reset();
                oos2.writeObject(toClient);
                oos2.flush();
                break;
        }
    }

}