package game.state;

import game.io.KeyboardHandler;
import game.model.entities.Ship;
import game.model.utils.Coordinate;
import game.model.utils.Vector;
import game.model.world.WorldState;
import game.net.client.Client;
import game.net.message.MessageType;
import game.parser.WorldParser;
import game.utils.EntityFactories;
import game.utils.Handler;
import game.view.Camera;
import game.view.ViewField;
import game.view.ViewFrame;
import game.view.assets.Assets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameState implements Runnable {

    private Assets assets;
    private KeyboardHandler kh;
    private WorldState worldState;
    private ViewFrame frame;
    private ViewField field;
    private Camera camera;
    private Ship ship;
    private Map<Integer, Ship> playerShips;

    private Client client;

    public GameState() throws IOException {
        init();
        generateTerrain();
        this.ship = EntityFactories.createShip(worldState, field, new Coordinate(400, 400));
        this.playerShips = new HashMap<>();
    }

    @Override
    public void run() {
        gameLoop(ship, field, kh);
    }

    public void associateClient(Client client) {
        this.client = client;
    }

    public Handler initHandler() {
        return new Handler(ship, playerShips, worldState, field);
    }

    private void init() {
        assets = new Assets();
        kh = new KeyboardHandler();

        //Backend
        worldState = new WorldState();

        //Frontend
        camera = new Camera(0, 0);
        frame = new ViewFrame();
        field = new ViewField(camera);
        frame.addPanelComponent(field);
        field.addKeyListener(kh);
    }

    private void generateTerrain() throws IOException {
        WorldParser worldParser = new WorldParser("world.txt", worldState, field);
        worldParser.readInEntities();
        int tilesInRow = worldParser.getTilesInRow();
        int tilesInCol = worldParser.getTilesInCol();
        camera.setTiles(tilesInRow, tilesInCol);
    }

    private void gameLoop(Ship s, ViewField field, KeyboardHandler kh) {
        int FPS = 60;
        long quanta = 1000000000/FPS;
        long delta = 0;
        long last = System.nanoTime();

        while (true) {
            long now = System.nanoTime();
            delta += now - last;
            last = now;
            if (delta > quanta) {
                delta = 0;
                handleInput(kh, s);
                shiftCamera();
                camera.centerOnEntity(s);
                s.move();
                try {
                    client.sendMessage(MessageType.DATA_REPORT);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                for (Integer shipId : playerShips.keySet()) {
                    Ship pShip = playerShips.get(shipId);
                    pShip.place(pShip.getCoordinate());
                }
                field.repaint();
            }
        }
    }

    private void shiftCamera() {
        camera.setXOffset(ship.getCoordinate().x());
        camera.setYOffset(ship.getCoordinate().y());
    }

    private void handleInput(KeyboardHandler kh, Ship s) {
        if (kh.getKeys().size() > 0) {
            for (Character c : kh.getKeys()) {
                switch(c) {
                    case 'w':
                        s.accelerate(new Vector(0, -0.4));
                        break;
                    case 'a':
                        s.accelerate(new Vector(-0.4, 0));
                        break;
                    case 's':
                        s.accelerate(new Vector(0, 0.4));
                        break;
                    case 'd':
                        s.accelerate(new Vector(0.4, 0));
                        break;
                }
            }
        }
    }

}
