package game.model.entities;

import game.model.utils.Coordinate;
import game.utils.Observer;
import java.awt.*;

public class Wall extends Observer<Integer> implements Entity {

    private Rectangle hitbox;
    private Coordinate c;
    private boolean active;

    public Wall(Coordinate c) {
        this.hitbox = new Rectangle((int)c.x()-16, (int)c.y()-16, 32, 32);
        this.c = c;
        this.active = true;
    }

    @Override
    public Rectangle getHBox() {
        return hitbox;
    }

    @Override
    public boolean getActive() {
        return active;
    }

    public Coordinate getCoordinate() {
        return c;
    }

}
