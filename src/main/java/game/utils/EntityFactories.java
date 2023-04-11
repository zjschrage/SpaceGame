package game.utils;

import game.model.entities.Bullet;
import game.model.entities.Ship;
import game.model.entities.Wall;
import game.model.utils.Coordinate;
import game.model.utils.Vector;
import game.model.world.WorldState;
import game.view.entityview.BulletView;
import game.view.gameview.GameViewField;
import game.view.entityview.ShipView;
import game.view.entityview.WallView;

import java.util.ResourceBundle;

public class EntityFactories {

    private static ResourceBundle res = ResourceBundle.getBundle("view_properties");
    private static int defaultSize = Integer.parseInt(res.getString("DEFAULT_TILE_SIZE"));

    public static Ship createShip(WorldState worldState, GameViewField field, Coordinate c, boolean jurisdiction) {
        Ship s = new Ship(worldState, field, c, defaultSize, defaultSize);
        ShipView sv = new ShipView(s.getCoordinate(), defaultSize, defaultSize);
        s.addListener(sv);
        if (jurisdiction) worldState.addEntity(s, false);
        field.addEntity(sv);
        return s;
    }

    public static Wall createWall(WorldState worldState, GameViewField field, Coordinate c, boolean jurisdiction) {
        Wall w = new Wall(c, defaultSize, defaultSize);
        WallView wv = new WallView(w.getCoordinate(), defaultSize, defaultSize);
        w.addListener(wv);
        if (jurisdiction) worldState.addEntity(w, false);
        field.addEntity(wv);
        return w;
    }

    public static Bullet createBullet(WorldState worldState, GameViewField field, Coordinate c, Vector velocity, boolean jurisdiction) {
        Bullet b = new Bullet(worldState, c, defaultSize/4, defaultSize/4, velocity);
        BulletView bv = new BulletView(b.getCoordinate(), defaultSize/4, defaultSize/4);
        b.addListener(bv);
        if (jurisdiction) worldState.addEntity(b, true);
        field.addEntity(bv);
        return b;
    }

}
