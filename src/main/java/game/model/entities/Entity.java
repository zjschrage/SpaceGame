package game.model.entities;

import game.model.utils.Coordinate;
import game.utils.Observer;

import java.awt.*;

public abstract class Entity<T> extends Observer<T> {

    private Rectangle hitbox;
    private Coordinate coordinate;
    private boolean active;

    public Entity(Coordinate c) {
        this.hitbox = new Rectangle((int) c.x() - 16, (int) c.y() - 16, 32, 32);
        this.coordinate = c;
        this.active = true;
    }

    public Rectangle getHBox() {
        return hitbox;
    }

    public void setHBox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate c) {
        this.coordinate = c;
    }

    public boolean getActive() {
        return active;
    }

    public void setInactive() {
        active = false;
    }

}
