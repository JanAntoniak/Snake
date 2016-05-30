package com.snake.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import com.snake.model.*;


public class Server {

    private static States actualState;
    private static MessageToServer fromPlayer1;
    private static MessageToServer fromPlayer2;
    private static MessageToClient toClient;
    private ServerSocketChannel server = null;
    SocketChannel socket = null;
    SocketChannel socket2 = null;
    ObjectInputStream ois1 = null;
    ObjectOutputStream oos1 = null;
    ObjectInputStream ois2 = null;
    ObjectOutputStream oos2 = null;
    ServerController serverController = null;
    public Clock timer;
    private static final long FRAME_TIME = 1500L / 50L;
    private boolean error1 = false;
    private boolean error2 = false;


    public Server() {
        actualState = States.ESTABLISHING;
        fromPlayer1 = new MessageToServer();
        fromPlayer2 = new MessageToServer();
        toClient = new MessageToClient();
        this.timer = new Clock(2.5f);
    }

    public void Start() throws Exception {

        try {


            while (true) {
                switch (actualState) {
                    case ESTABLISHING:
                        actualState = Establish();
                        break;

                    case PREPARING:
                        actualState = Prepare();
                        break;

                    case GAME:
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
            fromPlayer1 = (MessageToServer) (ois1.readObject());
        } catch(IOException e) {
            this.error1 = true;
            return States.ERROR;
        }

        try {
            fromPlayer2 = (MessageToServer) (ois2.readObject());
        } catch(IOException e) {
            this.error2 = true;
            return States.ERROR;
        }

        if (fromPlayer1.getProtocolFlag() != ProtocolFlag.REQUEST || fromPlayer2.getProtocolFlag() != ProtocolFlag.REQUEST)
            return States.ERROR;

        toClient.setProtocolFlag(ProtocolFlag.ACCEPT);

        try {
            sendMessageToClient(serverController.getSnake1().getIDSnake());
        } catch(IOException e) {
            this.error1 = true;
            return States.ERROR;
        }

        try {
            sendMessageToClient(serverController.getSnake2().getIDSnake());
        } catch(IOException e) {
            this.error2 = true;
            return States.ERROR;
        }

        return States.PREPARING;
    }

    private States Prepare() throws IOException, ClassNotFoundException, InterruptedException {
        try {
            fromPlayer1 = (MessageToServer) (ois1.readObject());
        } catch(IOException e) {
            this.error1 = true;
            return States.ERROR;
        }
        try {
            fromPlayer2 = (MessageToServer) (ois2.readObject());
        } catch(IOException e) {
            this.error2 = true;
            return States.ERROR;
        }

        if (fromPlayer1.getProtocolFlag() != ProtocolFlag.STARTGAME || fromPlayer2.getProtocolFlag() != ProtocolFlag.STARTGAME)
            return States.END;

        toClient.setProtocolFlag(ProtocolFlag.NEWGAME);

        try {
            sendMessageToClient(serverController.getSnake1().getIDSnake());
        } catch(IOException e) {
            this.error1 = true;
            return States.ERROR;
        }
        try {
            sendMessageToClient(serverController.getSnake2().getIDSnake());
        } catch(IOException e) {
            this.error2 = true;
            return States.ERROR;
        }

        serverController.resetGame();

        return States.GAME;
    }

    private synchronized States Move() throws IOException, ClassNotFoundException, InterruptedException {

        serverController.setNewGame(true);
        serverController.setGameOver(false);
        while (!serverController.isGameOver()) {

            long start = System.nanoTime();
            timer.update();

            try {
                fromPlayer1 = (MessageToServer) (ois1.readObject());
            } catch (IOException e) {
                this.error1 = true;
                return States.ERROR;
            }
            try {
                fromPlayer2 = (MessageToServer) (ois2.readObject());
            } catch (IOException e) {
                this.error2 = true;
                return States.ERROR;
            }

            if (fromPlayer1.getDirection() != null)
                serverController.getSnake1().setDirections(fromPlayer1.getDirection());
            if (fromPlayer2.getDirection() != null)
                serverController.getSnake2().setDirections(fromPlayer2.getDirection());

            if (timer.hasElapsedCycle()) {
                serverController.updateGame();
            }


            setMessageToClient(serverController.getSnake1().getIDSnake());
            try {
                sendMessageToClient(serverController.getSnake1().getIDSnake());
            } catch (IOException e) {
                this.error1 = true;
                return States.ERROR;
            }

            setMessageToClient(serverController.getSnake2().getIDSnake());
            try {
                sendMessageToClient(serverController.getSnake2().getIDSnake());
            } catch (IOException e) {
                this.error2 = true;
                return States.ERROR;
            }

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
        try {
            sendMessageToClient(serverController.getSnake1().getIDSnake());
        } catch(IOException e) {
            this.error1 = true;
            return States.ERROR;
        }
        try {
            sendMessageToClient(serverController.getSnake2().getIDSnake());
        } catch(IOException e) {
            this.error2 = true;
            return States.ERROR;
        }


        return States.ESTABLISHING;
    }

    private States ServeError() throws IOException {
        int socketclose = 0;
        if(error1 == false || error2 == false) {
            if(error1 == false) {
                toClient.setProtocolFlag(ProtocolFlag.ERROR);
                sendMessageToClient(serverController.getSnake1().getIDSnake());
                socket.close();
                socketclose++;
                oos2.close();
                ois2.close();
            }
            if(error2 == false) {
                toClient.setProtocolFlag(ProtocolFlag.ERROR);
                sendMessageToClient(serverController.getSnake2().getIDSnake());
                if(socketclose == 0)
                    socket.close();
                oos1.close();
                ois1.close();
            }
        }
        error1 = false;
        error2 = false;
        server.close();
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
