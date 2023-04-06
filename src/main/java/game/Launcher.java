package game;

import game.net.client.Client;
import game.state.GameState;
import game.state.Looper;
import game.state.State;
import game.utils.Handler;

import java.io.IOException;

public class Launcher {

    public static void main(String[] args) throws IOException {

        GameState game = new GameState();
        State.setState(game);
        Thread gThread = new Thread(new Looper());

        Handler handler = new Handler(game.getShip(), game.getPlayerShips(), game.getWorldState(), game.getViewField());

        Client client = new Client("10.200.89.126", 5000);
        client.setupClientReciever(handler);

        game.associateClient(client);

        gThread.start();

    }
    
}
