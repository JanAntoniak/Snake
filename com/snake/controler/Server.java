package com.snake;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server{

    private static int actualState;
    private static MessageToServer fromPlayer1;
    private static fromPlayer2;
    private static toClient;


    public Server() {
        int actualState = States.ESTABLISHING;
        MessageToServer fromPlayer1 = new MessageToServer();
        fromPlayer2 = new MessagerToServer();
        toClient = new MessageToClient();
    }

    public void Start()throws Exception
    {
        ServerSocketChannel server = null;

        ObjectInputStream ois1 = null;
        ObjectOutputStream oos1 = null;

        ObjectInputStream ois2 = null;
        ObjectOutputStream oos2 = null;

        SocketChannel socket = null;
        SocketChannel socket2 = null;


        server = ServerSocketChannel.open();
        server.socket().bind(new InetSocketAddress(7777));
        socket = server.accept();
        socket2 = server.accept();

        System.out.println("Nowe polaczenie przychodzace");

        ois1 = new ObjectInputStream(socket.socket().getInputStream());
        oos1 = new ObjectOutputStream(socket.socket().getOutputStream());

        ois2 = new ObjectInputStream(socket2.socket().getInputStream());
        oos2 = new ObjectOutputStream(socket2.socket().getOutputStream());

        try
        {


            while(true) {
                switch (actualState) {
                    case States.ESTABLISHING:
                        actualState = Establish();
                        break;

                    case States.PREPARING:
                        actualState = Prepare();
                        break;

                    case States.GAME:
                        actualState = Move();
                        break;

                    case States.END:
                        actualState = EndGame();
                        break;

                    case States.ERROR;
                        actualState = ServeError();
                        break;


                }
            }
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("ClassNotFound");
        }
        catch(Exception e)
        {
            System.out.println("Other exception");
        }
        socket.close();
        ois1.close();
        server.close();
    }
    static public void main(String[] args) throws Exception {
        Server sv = new Server();
        sv.Start();
    }

    private States Establish() {
        fromPlayer1 = (MessageToServer) (ois1.readObject());
        fromPlayer2 = (MessageToServer) (ois2.readObject());

        if(fromPlayer1.protocolFlag != ProtocolFlag.REQUEST || fromPlayer2.protocolFlag != ProtocolFlag.REQUEST)
            return States.ERROR;

        toClient.protocolFlag = ProtocolFlag.ACCEPT;

        oos1.writeObject(toClient);
        oos2.writeObject(toClient);

        return States.PREPARING;
    }

    private States Prepare() {
        fromPlayer1 = (MessageToServer) (ois1.readObject());
        fromPlayer2 = (MessageToServer) (ois2.readObject());

        if(fromPlayer1.protocolFlag != ProtocolFlag.STARTGAME || fromPlayer2.protocolFlag != ProtocolFlag.STARTGAME)
            return States.END;

        toClient.protocolFlag = ProtocolFlag.NEWGAME;

        oos1.writeObject(toClient);
        oos2.writeObject(toClient);

        return States.GAME;
    }

    private States Move() {
        //tu trzeba wysylac i odbierac wiadomosci do i od klientow odnosnie pozycji wunszy, ale tego ni umiem :D
    }

    private States End() {
        toClient.protocolFlag = ProtocolFlag.GAMEOVER;

        oos1.writeObject(toClient);
        oos2.writeObject(toClient);

        return States.ESTABLISHING;
    }

    private States ServeError() {
        toClient.protocolFlag = ProtocolFlag.ERROR;

        oos1.writeObject(toClient);
        oos2.writeObject(toClient);

        return States.ESTABLISHING;
    }


}