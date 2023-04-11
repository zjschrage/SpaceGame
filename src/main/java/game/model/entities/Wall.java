package game.model.entities;

import game.model.utils.Coordinate;
import game.utils.Observer;
import java.awt.*;

public class Wall extends Entity<Integer> {

    public Wall(Coordinate c, int xSize, int ySize) {
        super(c, xSize, ySize);
    }

    @Override
    public void tick() {

    }

}
