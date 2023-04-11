package game.state;

import game.io.KeyboardHandler;
import game.model.entities.Entity;
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

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class GameState extends State {

    private ResourceBundle res = ResourceBundle.getBundle("game_properties");
    private String worldFileName = res.getString("WORLD_FILE_NAME");

    private ResourceBundle resView = ResourceBundle.getBundle("view_properties");
    private int x = Integer.parseInt(resView.getString("FRAME_X"));
    private int y = Integer.parseInt(resView.getString("FRAME_Y"));
    private String title = resView.getString("FRAME_TITLE");

    private Assets assets;
    private WorldState worldState;
    private ViewFrame frame;
    private GameViewField field;
    private Camera camera;
    private Ship ship;

    private Client client;

    public GameState() throws IOException {

    }

    public void associateClient(Client client) {
        this.client = client;
    }

    public Ship getShip() {return ship;}
    public WorldState getWorldState() {return worldState;}
    public GameViewField getViewField() {return field;}

    @Override
    public void init() {
        assets = new Assets();

        //Backend
        worldState = new WorldState();

        //Frontend
        camera = new Camera(0, 0);
        frame = new ViewFrame(x, y, title, WindowConstants.DISPOSE_ON_CLOSE);
        windowListener();
        field = new GameViewField(camera, x, y);
        frame.addPanelComponent(field);

        ship = EntityFactories.createShip(worldState, field, new Coordinate(400, 400), true);

        try {
            generateTerrain();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        shiftCamera();
        camera.centerOnEntity(ship);
        worldState.checkActive();
        worldState.tickAll();
        broadcastEntityData();
    }

    @Override
    public void render() {
        field.repaint();
    }

    private void shiftCamera() {
        camera.setXOffset(ship.getCoordinate().x());
        camera.setYOffset(ship.getCoordinate().y());
    }

    private void broadcastEntityData() {
        try {
            client.sendMessage(MessageType.DATA_REPORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void windowListener() {
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    client.sendMessage(MessageType.CLOSE);
                } catch (IOException ioe) {
                    throw new RuntimeException(ioe);
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
    }

}
