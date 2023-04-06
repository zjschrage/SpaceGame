package game.view.entityview;

import game.model.utils.Coordinate;
import game.utils.Info;
import game.utils.Listener;
import game.view.Drawable;
import game.view.assets.AssetEnums;
import game.view.assets.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ShipView implements Drawable, Listener<Info> {

    private BufferedImage image;
    private Coordinate centroid;
    private Coordinate position;
    private Rectangle hitbox;
    private boolean active;

    public ShipView(Coordinate c) {
        this.image = Assets.getImage(AssetEnums.SHIP);
        this.centroid = new Coordinate(image.getWidth()/2, image.getHeight()/2);
        this.position = c;
        this.hitbox = new Rectangle((int)c.x() - 16, (int)c.y() - 16, 32, 32);
        this.active = true;
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
    public boolean getActive() {
        return active;
    }

    @Override
    public void update(Info oldInfo, Info newInfo) {
        position = newInfo.c();
        hitbox = newInfo.hitbox();
    }

    @Override
    public void destroy() {
        active = false;
    }

}
