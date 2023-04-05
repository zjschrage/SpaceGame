package game.view;

import game.model.utils.Coordinate;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface Drawable {

    public BufferedImage getImage();
    public Rectangle getHitBox();
    public Coordinate getDrawPosition();

}
