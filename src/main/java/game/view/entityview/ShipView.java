package game.view.entityview;

import game.model.utils.Coordinate;
import game.utils.Info;
import game.utils.Listener;
import game.view.assets.AssetEnum;

public class ShipView extends Drawable implements Listener<Info> {

    public ShipView(Coordinate c) {
        super(AssetEnum.SHIP, c);
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
