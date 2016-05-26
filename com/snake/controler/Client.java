package com.snake;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Client {
    private static State actualState;
    private static MessageToClient fromServer;
    private static MessageToServer toServer;

    public Client() {
        actualState = States.ESTABLISHING;
        fromServer = new MessageToClient();
        toServer = new MessageToServer();
    }


    public Start(String hostName) throws Exception {
        SocketChannel socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois=null;

        socket = SocketChannel.open(new InetSocketAddress(hostName, 7777));
        oos = new ObjectOutputStream(socket.socket().getOutputStream());
        ois = new ObjectInputStream(socket.socket().getInputStream());
        
        while(true) {

            switch actualState {
                case States.ESTABLISHING:
                    actualState = Establish();
                    break;

                case States.PREPARING:
                    actualState = Prepare();
                    break;

                case States.GAME:
                    actualState = Move();
                    break;

                case States.ERROR:
                    actualState = ServeError();

            }
            
            if(actualState == States.END)
                break;
        }
        oos.flush();
        oos.close();
        socket.close();
    }

    private States Establish() {
        toServer.protocolFlag = ProtocolFlag.REQUEST;
        oos.writeObject(toServer);
        fromServer = (MessageToClient) ( ois.readObject() );

        if(fromServer.protocolFlag == ProtocolFlag.ACCEPT)
            return States.ESTABLISHING;
        else
            return States.ERROR;
    }

    private States Prepare() {
        toServer.protocolFlag = ProtocolFlag.STARTGAME;
        oos.writeObject(toServer);
        fromServer = (MessageToClient) (ois.readObject());

        if(fromServer.protocolFlag == ProtocolFlag.ACCEPT)
            return States.GAME;
        else
            return States.ERROR;
    }

    private States Game() {
        //sprawdzanie czy byla kolizja po odebraniu komunikatu od serwera
        //wysylanie kierunku
    }

    private States ServeError() {
        //wyswietlenie komunikatu i decyzja czy laczyc sie od nowa, czy zamknac aplikacje
    }

}
