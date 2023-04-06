package game.utils;

import game.model.entities.Ship;
import game.model.entities.Wall;
import game.model.utils.Coordinate;
import game.model.world.WorldState;
import game.view.gameview.GameViewField;
import game.view.entityview.ShipView;
import game.view.entityview.WallView;

public class EntityFactories {

    public static Ship createShip(WorldState worldState, GameViewField field, Coordinate c) {
        Ship s = new Ship(worldState, c);
        ShipView sv = new ShipView(s.getCoordinate());
        s.addListener(sv);
        worldState.addEntity(s);
        field.addEntity(sv);
        return s;
    }

    public static Wall createWall(WorldState worldState, GameViewField field, Coordinate c) {
        Wall w = new Wall(c);
        WallView wv = new WallView(w.getCoordinate());
        w.addListener(wv);
        worldState.addEntity(w);
        field.addEntity(wv);
        return w;
    }

}
