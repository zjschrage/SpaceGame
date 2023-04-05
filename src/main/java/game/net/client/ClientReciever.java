package game.net.client;

import game.model.utils.Coordinate;
import game.net.message.Data;
import game.net.message.Message;
import game.net.message.MessageType;
import game.utils.EntityFactories;
import game.utils.Handler;

import java.io.DataInputStream;
import java.io.IOException;

public class ClientReciever implements Runnable {

    private Client client;
    private DataInputStream in;
    private ClientInformation info;
    private Handler handler;

    public ClientReciever(Client client, DataInputStream in, ClientInformation info, Handler handler) {
        this.client = client;
        this.in = in;
        this.info = info;
        this.handler = handler;
    }

    @Override
    public void run() {
        byte[] stream;
        boolean running = true;
        while (running) {
            try {
                stream = in.readNBytes(Message.MAX_MESSAGE_LEN);
                running = interpret(stream);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private boolean interpret(byte[] stream) throws IOException {
        Message m = new Message(stream);
        switch (m.getType()) {
            case CLOSE:
                return false;
            case ASSIGN_ID:
                assignId(m);
                break;
            case INFORM_PRESENCE:
                registerPlayerShip(m);
                break;
            case REQUEST_PRESENCE:
                client.respondWithPresence(m);
                break;
            case DATA_REPORT:
                updatePlayerShip(m);
                break;
            default:
        }
        return true;
    }

    private void assignId(Message m) {
        info.id = m.dst;
        System.out.println("ID Assigned " + info.id);
        try {
            client.sendMessage(MessageType.INFORM_PRESENCE);
            client.sendMessage(MessageType.REQUEST_PRESENCE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void registerPlayerShip(Message m) {
        if (m.src == info.id) return;
        int newPlayerId = m.src;
        if (handler.getPlayerShips().containsKey(newPlayerId)) return;
        handler.getPlayerShips().put(newPlayerId, EntityFactories.createShip(handler.getWorldState(), handler.getField(), new Coordinate(400, 400)));
        System.out.println("Player Connected with ID " + newPlayerId);
    }

    private void updatePlayerShip(Message m) {
        if (m.src == info.id) return;
        if (!(handler.getPlayerShips().containsKey(((int)m.src)))) return;
        Coordinate c = Data.extractCoordinate(m.body);
        handler.getPlayerShips().get((int)m.src).place(c);
    }

}
