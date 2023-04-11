package game.net.client;

import game.model.entities.Bullet;
import game.model.utils.Coordinate;
import game.model.utils.Vector;
import game.net.message.Data;
import game.net.message.Message;
import game.net.message.MessageType;
import game.net.utils.DisconnectTimer;
import game.utils.EntityFactories;
import game.utils.Handler;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ClientReciever implements Runnable {

    private Client client;
    private DataInputStream in;
    private ClientInformation info;
    private Handler handler;
    private Map<Integer, Long> timeout;

    public ClientReciever(Client client, DataInputStream in, ClientInformation info, Handler handler) {
        this.client = client;
        this.in = in;
        this.info = info;
        this.handler = handler;
        this.timeout = new HashMap<>();
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
                //addTransientEntity(m);
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
        new DisconnectTimer(timeout, handler);
    }

    private void registerPlayerShip(Message m) {
        if (m.src == info.id) return;
        int newPlayerId = m.src;
        if (handler.getWorldState().getExternalShips().containsKey(newPlayerId)) return;
        handler.getWorldState().getExternalShips().put(newPlayerId, EntityFactories.createShip(handler.getWorldState(), handler.getField(), new Coordinate(400, 400), false));
        timeout.put(newPlayerId, System.currentTimeMillis());
        System.out.println("Player Connected with ID " + newPlayerId);
    }

    private void updatePlayerShip(Message m) {
        if (m.src == info.id) return;
        int id = m.src;
        if (!(handler.getWorldState().getExternalShips().containsKey((id)))) return;
        Coordinate c = Data.extractShipCoordinate(m.body);
        handler.getWorldState().getExternalShips().get(id).place(c);
        timeout.put(id, System.currentTimeMillis());
    }

    private void addTransientEntity(Message m) {
        if (m.src == info.id) return;
        Bullet b = EntityFactories.createBullet(handler.getWorldState(), handler.getField(), new Coordinate(400, 400), new Vector(1, 1),false);
        handler.getWorldState().addExternalEntity(b);
    }

}
