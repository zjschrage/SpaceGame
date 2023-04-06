package game.net.server;

import game.net.message.Message;
import game.net.message.MessageScope;
import game.net.message.MessageType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

public class User implements Runnable {

    private Socket socket;
    private DataInputStream serverIn;
    private DataOutputStream serverOut;
    private Map<Byte, User> users;
    private byte id;

    public User(Socket socket, DataInputStream serverIn, DataOutputStream serverOut, Map<Byte, User> users, byte id) {
        this.socket = socket;
        this.serverIn = serverIn;
        this.serverOut = serverOut;
        this.users = users;
        this.id = id;
    }

    @Override
    public void run() {
        notifyClientId(id);
        byte[] stream;
        boolean running = true;
        while (running && socket.isConnected()) {
            try {
                stream = serverIn.readNBytes(Message.MAX_MESSAGE_LEN);
                running = interpret(stream);
            } catch(IOException e) {
                System.out.println("IO Exception Thrown, Closing Connection");
                running = false;
            }
        }
        System.out.println("Closing connection");
        try {
            users.remove(id);
            //notifyDisconnect(id);
            serverIn.close();
            serverOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataOutputStream getOutputStream() {
        return serverOut;
    }

    private void send(DataOutputStream out, Message m) throws IOException {
        out.write(m.streamify(), 0, Message.MAX_MESSAGE_LEN);
    }


    private void notifyClientId(byte id) {
        Message m = new Message();
        m.src = (byte) -1;
        m.dst = id;
        m.scope = (byte) MessageScope.UNICAST.ordinal();
        m.type = (byte) MessageType.ASSIGN_ID.ordinal();
        try {
            send(serverOut, m);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean interpret(byte[] stream) throws IOException {
        try {
            Message m = new Message(stream);
            MessageScope scope = m.getScope();
            if (m.getType() == MessageType.CLOSE) {
                close(m);
                return false;
            }
            if (scope == MessageScope.UNICAST) unicast(m);
            if (scope == MessageScope.BROADCAST) broadcast(m);
            return true;
        } catch (IllegalArgumentException e) {}
        return false;
    }

    private void unicast(Message m) throws IOException {
        byte dst = m.dst;
        if (users.containsKey(dst)) send(users.get(dst).getOutputStream(), m);
    }

    private void broadcast(Message m) throws IOException {
        for (Byte b : users.keySet()) {
            if (users.containsKey(b)) send(users.get(b).getOutputStream(), m);
        }
    }

    private void close(Message m) throws IOException {
        send(serverOut, m);
    }

}
