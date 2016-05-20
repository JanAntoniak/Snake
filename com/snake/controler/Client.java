package com.snake;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Client {

    public Client(String host_name) {

    }
    public void createClient() throws Exception
    {
        SocketChannel socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois=null;

        socket = SocketChannel.open(new InetSocketAddress(host_name, 7777));
        oos = new ObjectOutputStream(socket.socket().getOutputStream());
        ois = new ObjectInputStream(socket.socket().getInputStream());

        oos.writeObject(new MessageToServer(ProtocolFlag.REQUEST));
        MessageToClient msgCl = (MessageToClient) ( ois.readObject() );
        if(msgCl.protocolFlag != ProtocolFlag.ACCEPT)
            System.exit(1);
        
        while(true) {
            
            if(msgCl.protocolFlag == ProtocolFlag.EXIT)
                break;
        }
        oos.flush();
        oos.close();
        socket.close();
    }
    public static void main(String[] args) throws Exception {
        Client cl = new Client();
        cl.createClient();
    }
}
