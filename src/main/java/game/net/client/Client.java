package game.net.client;

import game.net.message.Data;
import game.net.message.Message;
import game.net.message.MessageScope;
import game.net.message.MessageType;
import game.utils.Handler;

import java.io.*;
import java.net.*;

public class Client {

    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private ClientInformation info;

    private Handler handler;

    public Client(String address, int port) {
        establishConnection(address, port);
        info = new ClientInformation();
    }

    public void setupClientReciever(Handler handler) {
        this.handler = handler;
        new Thread(new ClientReciever(this, in, info, handler)).start();
    }

    public void sendMessage(MessageType type) throws IOException {
        switch (type) {
            case INFORM_PRESENCE -> informPresence();
            case REQUEST_PRESENCE -> requestPresence();
            case DATA_REPORT -> reportData();
            case CLOSE -> cmdClose();
            default -> throw new IllegalArgumentException("Unexpected value: " + type);
        }
    }

    private void establishConnection(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void send(Message m) throws IOException {
        out.write(m.streamify(), 0, Message.MAX_MESSAGE_LEN);
    }

    private void informPresence() throws IOException {
        Message m = new Message();
        m.src = (byte) info.id;
        m.dst = (byte) -1;
        m.scope = (byte) MessageScope.BROADCAST.ordinal();
        m.type = (byte) MessageType.INFORM_PRESENCE.ordinal();
        send(m);
        System.out.println("Informing of my own presence as " + info.id);
    }

    private void requestPresence() throws IOException {
        Message m = new Message();
        m.src = (byte) info.id;
        m.dst = (byte) -1;
        m.scope = (byte) MessageScope.BROADCAST.ordinal();
        m.type = (byte) MessageType.REQUEST_PRESENCE.ordinal();
        send(m);
        System.out.println("Requesting all presence to be sent back to me at " + info.id);
    }

    private void reportData() throws IOException {
        Message m = new Message();
        m.src = (byte) info.id;
        m.dst = (byte) -1;
        m.scope = (byte) MessageScope.BROADCAST.ordinal();
        m.type = (byte) MessageType.DATA_REPORT.ordinal();
        Data.encodeData(handler.getOwnShip(), m.body);
        send(m);
    }

    private void cmdClose() throws IOException {
        Message m = new Message();
        m.src = (byte) info.id;
        m.dst = (byte) info.id;
        m.scope = (byte) MessageScope.BROADCAST.ordinal();
        m.type = (byte) MessageType.CLOSE.ordinal();
        send(m);
        close();
    }

    void respondWithPresence(Message m) throws IOException {
        Message newM = new Message();
        newM.src = (byte) info.id;
        newM.dst = m.src;
        newM.scope = (byte) MessageScope.UNICAST.ordinal();
        newM.type = (byte) MessageType.INFORM_PRESENCE.ordinal();
        send(newM);
        System.out.println("Sending player " + m.src + " my info (" + info.id + ")");
    }

    private void close() {
        try {
            in.close();
            out.close();
            socket.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String args[]) {
        new Client(args[0], 5000);
    }
}
