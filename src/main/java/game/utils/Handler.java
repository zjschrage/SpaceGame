package game.utils;

import game.model.entities.Ship;
import game.model.world.WorldState;
import game.view.ViewField;

import java.util.Map;

public class Handler {

    private Ship ownShip;
    private Map<Integer, Ship> playerShips;
    private WorldState worldState;

    public Handler(Ship ownShip, Map<Integer, Ship> playerShips, WorldState worldState, ViewField field) {
        this.ownShip = ownShip;
        this.playerShips = playerShips;
        this.worldState = worldState;
        this.field = field;
    }

    private ViewField field;

    public Ship getOwnShip() {
        return ownShip;
    }

    public Map<Integer, Ship> getPlayerShips() {
        return playerShips;
    }

    public WorldState getWorldState() {
        return worldState;
    }

    public ViewField getField() {
        return field;
    }

}
