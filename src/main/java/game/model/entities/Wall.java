package game.model.entities;

import game.model.utils.Coordinate;
import game.utils.Observer;
import java.awt.*;

public class Wall extends Observer<Integer> implements Entity {

    private Rectangle hitbox;
    private Coordinate c;

    public Wall(Coordinate c) {
        this.hitbox = new Rectangle((int)c.x()-16, (int)c.y()-16, 32, 32);
        this.c = c;
    }

    @Override
    public Rectangle getHBox() {
        return hitbox;
    }

    public Coordinate getCoordinate() {
        return c;
    }

}
