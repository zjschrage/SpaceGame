package game.view.entityview;

import game.model.utils.Coordinate;
import game.view.assets.AssetEnum;
import game.view.assets.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Drawable {

    private BufferedImage image;
    private Coordinate centroid;
    private Coordinate position;
    private Rectangle hitbox;
    private boolean active;

    public Drawable(AssetEnum assetEnum, Coordinate c, int xSize, int ySize) {
        this.image = Assets.getImage(assetEnum);
        this.centroid = new Coordinate(image.getWidth()/2, image.getHeight()/2);
        this.position = c;
        this.hitbox = new Rectangle((int)c.x() - xSize/2, (int)c.y() - ySize/2, xSize, ySize);
        this.active = true;
    }

    public BufferedImage getImage() {
        return image;
    }
    public Rectangle getHBox() {
        return hitbox;
    }
    public Coordinate getDrawPosition() {
        return new Coordinate(position.x() - centroid.x(), position.y() - centroid.y());
    }
    public boolean getActive() {
        return active;
    }

    public void setPosition(Coordinate c) {
        this.position = c;
    }

    public void setHBox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public void setInactive() {
        active = false;
    }

}
