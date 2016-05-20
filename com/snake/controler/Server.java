package com.snake;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server{

    public Server() {

    }

    public void createServer()throws Exception
    {
        ServerSocketChannel server = null;
        ObjectInputStream ois1 = null;
        ObjectOutputStream oos1 = null;
        SocketChannel socket = null;
        ObjectInputStream ois2 = null;
        ObjectOutputStream oos2 = null;
        SocketChannel socket2 = null;

        server=ServerSocketChannel.open();
        server.socket().bind(new InetSocketAddress(7777));
        socket=server.accept();
        socket2=server.accept();

        System.out.println("Nowe polaczenie przychodzace");

        ois1=new ObjectInputStream(socket.socket().getInputStream());
        oos1 = new ObjectOutputStream(socket.socket().getOutputStream());
        ois2 =new ObjectInputStream(socket2.socket().getInputStream());
        oos2 = new ObjectOutputStream(socket2.socket().getOutputStream());

        try
        {
            MessageToServer player1Message = (MessageToServer) (ois1.readObject());
            MessageToServer player2Message = (MessageToServer) (ois2.readObject());
            if(player1Message.protocolFlag != ProtocolFlag.REQUEST || player2Message.protocolFlag != ProtocolFlag.REQUEST)
                System.exit(1);
            oos1.writeObject(new MessageToClient(ProtocolFlag.ACCEPT));
            oos2.writeObject(new MessageToClient(ProtocolFlag.ACCEPT));

            int actualState = States.ESTABLISHING;
            MessageToServer fromPlayer1;
            MessageToServer fromPlayer2;
            MessageToClient toClient = new MessageToClient(ProtocolFlag.ACCEPT);

            while(true) {
                switch (actualState) {
                    case States.ESTABLISHING:
                        fromPlayer1 = (MessageToServer) (ois1.readObject());
                        fromPlayer2 = (MessageToServer) (ois2.readObject());

                        if(fromPlayer1.protocolFlag != ProtocolFlag.REQUEST || fromPlayer2.protocolFlag != ProtocolFlag.REQUEST) {
                            actualState = States.ERROR;
                            break;
                        }

                        oos1.writeObject(toClient);
                        oos2.writeObject(toClient);

                        actualState = States.PREPARING;
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
        sv.createServer();
    }
}