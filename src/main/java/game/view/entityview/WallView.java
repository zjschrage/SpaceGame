package game.view.entityview;

import game.model.utils.Coordinate;
import game.utils.Listener;
import game.view.Drawable;
import game.view.assets.AssetEnums;
import game.view.assets.Assets;

import java.awt.image.BufferedImage;
import java.awt.*;

public class WallView implements Drawable, Listener<Integer> {

    private BufferedImage image;
    private Coordinate centroid;
    private Coordinate position;
    private Rectangle hitbox;

    public WallView(Coordinate c) {
        this.image = Assets.getImage(AssetEnums.WALL);
        this.centroid = new Coordinate(image.getWidth()/2, image.getHeight()/2);
        this.position = c;
        this.hitbox = new Rectangle((int)c.x()-16, (int)c.y()-16, 32, 32);
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public Rectangle getHitBox() {
        return hitbox;
    }

    @Override
    public Coordinate getDrawPosition() {
        return new Coordinate(position.x() - centroid.x(), position.y() - centroid.y());
    }

    @Override
    public void update(Integer oldInfo, Integer newInfo) {

    }

}
