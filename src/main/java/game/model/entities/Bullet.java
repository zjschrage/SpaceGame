package game.model.entities;

import game.model.utils.Coordinate;
import game.model.utils.Vector;
import game.model.world.WorldState;
import game.utils.Info;

import java.awt.*;
import java.util.ResourceBundle;

public class Bullet extends Entity<Info> {

    private ResourceBundle res = ResourceBundle.getBundle("view_properties");
    private int size = Integer.parseInt(res.getString("DEFAULT_TILE_SIZE"))/4;

    private WorldState worldState;
    private Vector velocity;
    public Bullet(WorldState worldState, Coordinate c, int xSize, int ySize, Vector velocity) {
        super(c, xSize, ySize);
        this.worldState = worldState;
        this.velocity = velocity;
    }

    @Override
    public void tick() {
        if (worldState.checkEntityColliding(this, getHBox())) {
            detachListeners();
            setInactive();
        }
        move();
    }

    private void move() {
        Coordinate newC = new Coordinate(getCoordinate().x() + velocity.x(), getCoordinate().y() + velocity.y());
        Rectangle newHitbox = new Rectangle((int)(newC.x() - size/2), (int)(newC.y() - size/2), size, size);
        updateState(newC, newHitbox);
    }

    private void updateState(Coordinate c, Rectangle hitbox) {
        Info oldInfo = new Info(this.getCoordinate(), this.getHBox());
        Info newInfo = new Info(c, hitbox);
        setCoordinate(c);
        setHBox(hitbox);
        notifyListeners(oldInfo, newInfo);
    }

}
