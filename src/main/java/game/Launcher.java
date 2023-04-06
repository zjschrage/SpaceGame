package game;

import game.net.client.Client;
import game.net.message.MessageType;
import game.state.GameState;
import game.utils.Handler;

import java.io.IOException;

public class Launcher {

    public static void main(String[] args) throws IOException {

        GameState game = new GameState();
        Thread gThread = new Thread(game);

        Handler handler = new Handler(game.getShip(), game.getPlayerShips(), game.getWorldState(), game.getViewField());
        //Handler handler = game.initHandler();

        Client client = new Client("10.200.105.21", 5000);
        client.setupClientReciever(handler);

        game.associateClient(client);

        gThread.start();

    }
    
}
