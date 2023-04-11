package game.view.entityview;

import game.model.utils.Coordinate;
import game.utils.Listener;
import game.view.assets.AssetEnum;

public class WallView extends Drawable implements Listener<Integer> {

    public WallView(Coordinate c, int xSize, int ySize) {
        super(AssetEnum.WALL, c, xSize, ySize);
    }

    @Override
    public void update(Integer oldInfo, Integer newInfo) {

    }

    @Override
    public void destroy() {
        setInactive();
    }

}
