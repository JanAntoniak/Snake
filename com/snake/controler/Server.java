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
        ServerSocketChannel server=null;
        ObjectInputStream ois=null;
        ObjectOutputStream oos = null;
        SocketChannel socket=null;
        ObjectInputStream ois2 =null;
        ObjectOutputStream oos2 = null;
        SocketChannel socket2=null;

        server=ServerSocketChannel.open();
        server.socket().bind(new InetSocketAddress(7777));
        socket=server.accept();
        socket2=server.accept();

        System.out.println("Nowe polaczenie przychodzace");

        ois=new ObjectInputStream(socket.socket().getInputStream());
        oos = new ObjectOutputStream(socket.socket().getOutputStream());
        ois2 =new ObjectInputStream(socket2.socket().getInputStream());
        oos2 = new ObjectOutputStream(socket2.socket().getOutputStream());


        try
        {
            MessageToServer obj = (MessageToServer) (ois.readObject());
            MessageToServer obj2 = (MessageToServer) (ois2.readObject());
            if(obj.protocolFlag != ProtocolFlag.REQUEST || obj2.protocolFlag != ProtocolFlag.REQUEST)
                System.exit(1);
            oos.writeObject(new MessageToClient(ProtocolFlag.ACCEPT));
            oos2.writeObject(new MessageToClient(ProtocolFlag.ACCEPT));

            while(true) {
                /**
                 * Tutaj wysyłamy komunikaty miedzy serwerem i klientem
                 */
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
        ois.close();
        server.close();
    }
    static public void main(String[] args) throws Exception {
        Server sv = new Server();
        sv.createServer();
    }
}