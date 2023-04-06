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
import game.view.gameview.Camera;
import game.view.gameview.GameViewField;
import game.view.ViewFrame;
import game.view.assets.Assets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class GameState extends State {

    private ResourceBundle res = ResourceBundle.getBundle("game_properties");
    private String worldFileName = res.getString("WORLD_FILE_NAME");
    private double acceleration = Double.parseDouble(res.getString("ACCELERATION"));

    private Assets assets;
    private KeyboardHandler kh;
    private WorldState worldState;
    private ViewFrame frame;
    private GameViewField field;
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

    public void associateClient(Client client) {
        this.client = client;
    }

    public Ship getShip() {return ship;}
    public Map<Integer, Ship> getPlayerShips() {return playerShips;}
    public WorldState getWorldState() {return worldState;}
    public GameViewField getViewField() {return field;}

    private void init() {
        assets = new Assets();
        kh = new KeyboardHandler();

        //Backend
        worldState = new WorldState();

        //Frontend
        camera = new Camera(0, 0);
        frame = new ViewFrame();
        field = new GameViewField(camera);
        frame.addPanelComponent(field);
        field.addKeyListener(kh);
    }

    private void generateTerrain() throws IOException {
        WorldParser worldParser = new WorldParser(worldFileName, worldState, field);
        worldParser.readInEntities();
        int tilesInRow = worldParser.getTilesInRow();
        int tilesInCol = worldParser.getTilesInCol();
        camera.setTiles(tilesInRow, tilesInCol);
    }
    @Override
    public void tick() {
        handleInput(kh, ship);
        shiftCamera();
        camera.centerOnEntity(ship);
        ship.move();
        try {
            client.sendMessage(MessageType.DATA_REPORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Integer shipId : playerShips.keySet()) {
            Ship pShip = playerShips.get(shipId);
            pShip.place(pShip.getCoordinate());
        }
    }

    @Override
    public void render() {
        field.repaint();
    }

    private void shiftCamera() {
        camera.setXOffset(ship.getCoordinate().x());
        camera.setYOffset(ship.getCoordinate().y());
    }

    private void handleInput(KeyboardHandler kh, Ship s) {
        if (kh.getKeys().size() > 0) {
            for (Character c : kh.getKeys()) {
                switch(c) {
                    case 'w' -> s.accelerate(new Vector(0, -acceleration));
                    case 'a' -> s.accelerate(new Vector(-acceleration, 0));
                    case 's' -> s.accelerate(new Vector(0, acceleration));
                    case 'd' -> s.accelerate(new Vector(acceleration, 0));
                }
            }
        }
    }

}
