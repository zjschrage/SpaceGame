package game.view.entityview;

import game.model.utils.Coordinate;
import game.utils.Info;
import game.utils.Listener;
import game.view.assets.AssetEnum;

public class BulletView extends Drawable implements Listener<Info> {
    public BulletView(Coordinate c, int xSize, int ySize) {
        super(AssetEnum.BULLET, c, xSize, ySize);
    }

    @Override
    public void update(Info oldInfo, Info newInfo) {
        setPosition(newInfo.c());
        setHBox(newInfo.hitbox());
    }

    @Override
    public void destroy() {
        setInactive();
    }
}
