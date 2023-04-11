package game.utils;

import game.model.entities.Ship;
import game.model.world.WorldState;
import game.view.gameview.GameViewField;

import java.util.Map;

public class Handler {

    private Ship ownShip;
    private WorldState worldState;
    private GameViewField field;

    public Handler(Ship ownShip, WorldState worldState, GameViewField field) {
        this.ownShip = ownShip;
        this.worldState = worldState;
        this.field = field;
    }

    public Ship getOwnShip() {
        return ownShip;
    }

    public WorldState getWorldState() {
        return worldState;
    }

    public GameViewField getField() {
        return field;
    }

}
