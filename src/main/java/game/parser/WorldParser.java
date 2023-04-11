package game.parser;

import game.model.utils.Coordinate;
import game.model.world.WorldState;
import game.utils.EntityFactories;
import game.utils.EntityFactory;
import game.view.gameview.GameViewField;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class WorldParser {

    private static String WORLD_FILE_PATH = "/world/";
    private static int ASCII_LINE_FEED = 10;
    private BufferedReader br;
    private Map<Integer, EntityFactory> factoryFunctionMapper;
    private WorldState worldState;
    private GameViewField field;
    private int tilesInRow;
    private int tilesInCol;

    public WorldParser(String path, WorldState worldState, GameViewField field) throws FileNotFoundException {
        this.br = new BufferedReader(new InputStreamReader(WorldParser.class.getResourceAsStream(WORLD_FILE_PATH + path)));
        this.factoryFunctionMapper = new HashMap<>();
        this.worldState = worldState;
        this.field = field;
        initFactoryFunctionMapper();
    }

    public void readInEntities() throws IOException {
        int row = 0;
        int col = 0;
        int i;
        while((i = br.read()) != -1) {
            if (i == ASCII_LINE_FEED) {
                col++;
                row = 0;
                continue;
            }
            i = Integer.parseInt(String.valueOf((char)i));
            if (!factoryFunctionMapper.containsKey(i)) {
                row++;
                continue;
            }
            Coordinate c = new Coordinate(32*(row++) + 16, 32*col + 16);
            factoryFunctionMapper.get(i).createEntity(worldState, field, c, true);
        }
        br.close();
        tilesInRow = row;
        tilesInCol = col;
    }

    public int getTilesInRow() {
        return tilesInRow;
    }

    public int getTilesInCol() {
        return tilesInCol;
    }

    private void initFactoryFunctionMapper() {
        factoryFunctionMapper.put(1, EntityFactories::createWall);
        factoryFunctionMapper.put(2, EntityFactories::createShip);
    }


}
