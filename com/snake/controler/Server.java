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
<<<<<<< HEAD
=======
    private ServerSocketChannel server = null;
    SocketChannel socket = null;
    SocketChannel socket2 = null;
>>>>>>> ed09c33228c7686b3c47a2b5ed7f0a41a46a570c
    ObjectInputStream ois1 = null;
    ObjectOutputStream oos1 = null;
    ObjectInputStream ois2 = null;
    ObjectOutputStream oos2 = null;
    ServerController serverController = null;
    public Clock timer;
    private static final long FRAME_TIME = 1500L / 50L;
<<<<<<< HEAD
=======
    private boolean error1 = false;
    private boolean error2 = false;

>>>>>>> ed09c33228c7686b3c47a2b5ed7f0a41a46a570c

    public Server() {
        actualState = States.ESTABLISHING;
        fromPlayer1 = new MessageToServer();
        fromPlayer2 = new MessageToServer();
        toClient = new MessageToClient();
        this.timer = new Clock(2.5f);
    }

    public void Start() throws Exception {
<<<<<<< HEAD
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

=======

>>>>>>> ed09c33228c7686b3c47a2b5ed7f0a41a46a570c
        try {


            while (true) {
                switch (actualState) {
                    case ESTABLISHING:
<<<<<<< HEAD
=======
                        System.out.print("est ");
>>>>>>> ed09c33228c7686b3c47a2b5ed7f0a41a46a570c
                        actualState = Establish();
                        break;

                    case PREPARING:
<<<<<<< HEAD
=======
                        System.out.print("pre ");
>>>>>>> ed09c33228c7686b3c47a2b5ed7f0a41a46a570c
                        actualState = Prepare();
                        break;

                    case GAME:
<<<<<<< HEAD
=======
                        System.out.print("mv ");
>>>>>>> ed09c33228c7686b3c47a2b5ed7f0a41a46a570c
                        actualState = Move();
                        break;

                    case END:
<<<<<<< HEAD
=======
                        System.out.print("end ");
>>>>>>> ed09c33228c7686b3c47a2b5ed7f0a41a46a570c
                        actualState = EndGame();
                        break;

                    case ERROR:
<<<<<<< HEAD
=======
                        System.out.print("err ");
>>>>>>> ed09c33228c7686b3c47a2b5ed7f0a41a46a570c
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

<<<<<<< HEAD
        fromPlayer1 = (MessageToServer) (ois1.readObject());
        fromPlayer2 = (MessageToServer) (ois2.readObject());


=======
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

>>>>>>> ed09c33228c7686b3c47a2b5ed7f0a41a46a570c
        if (fromPlayer1.getProtocolFlag() != ProtocolFlag.REQUEST || fromPlayer2.getProtocolFlag() != ProtocolFlag.REQUEST)
            return States.ERROR;

        toClient.setProtocolFlag(ProtocolFlag.ACCEPT);

<<<<<<< HEAD
        sendMessageToClient(serverController.getSnake1().getIDSnake());
        sendMessageToClient(serverController.getSnake2().getIDSnake());
=======
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
>>>>>>> ed09c33228c7686b3c47a2b5ed7f0a41a46a570c

        return States.PREPARING;
    }

    private States Prepare() throws IOException, ClassNotFoundException, InterruptedException {
<<<<<<< HEAD
        fromPlayer1 = (MessageToServer) (ois1.readObject());
        fromPlayer2 = (MessageToServer) (ois2.readObject());
=======
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
>>>>>>> ed09c33228c7686b3c47a2b5ed7f0a41a46a570c

        if (fromPlayer1.getProtocolFlag() != ProtocolFlag.STARTGAME || fromPlayer2.getProtocolFlag() != ProtocolFlag.STARTGAME)
            return States.END;

        toClient.setProtocolFlag(ProtocolFlag.NEWGAME);

<<<<<<< HEAD
        sendMessageToClient(serverController.getSnake1().getIDSnake());
        sendMessageToClient(serverController.getSnake2().getIDSnake());
=======
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
>>>>>>> ed09c33228c7686b3c47a2b5ed7f0a41a46a570c

        serverController.resetGame();
        return States.GAME;
    }

    private synchronized States Move() throws IOException, ClassNotFoundException, InterruptedException {
<<<<<<< HEAD

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

        sendMessageToClient(serverController.getSnake1().getIDSnake());
        sendMessageToClient(serverController.getSnake2().getIDSnake());
=======

        serverController.setNewGame(true);
        serverController.setGameOver(false);
        while (!serverController.isGameOver()) {

            long start = System.nanoTime();
            timer.update();
>>>>>>> ed09c33228c7686b3c47a2b5ed7f0a41a46a570c

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

<<<<<<< HEAD
    private States ServeError() throws IOException {
        toClient.setProtocolFlag(ProtocolFlag.ERROR);

        sendMessageToClient(serverController.getSnake1().getIDSnake());
        sendMessageToClient(serverController.getSnake2().getIDSnake());
=======
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

>>>>>>> ed09c33228c7686b3c47a2b5ed7f0a41a46a570c

        return States.ESTABLISHING;
    }

<<<<<<< HEAD
    public void setMessageToClient(int IDSnake) {

=======
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
>>>>>>> ed09c33228c7686b3c47a2b5ed7f0a41a46a570c
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
