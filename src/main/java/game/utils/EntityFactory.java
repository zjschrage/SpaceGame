package game.utils;

import game.model.entities.Entity;
import game.model.utils.Coordinate;
import game.model.world.WorldState;
import game.view.gameview.GameViewField;

public interface EntityFactory {
    public Entity createEntity(WorldState worldState, GameViewField field, Coordinate c);
}
