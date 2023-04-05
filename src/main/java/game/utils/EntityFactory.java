package game.utils;

import game.model.entities.Entity;
import game.model.utils.Coordinate;
import game.model.world.WorldState;
import game.view.ViewField;

public interface EntityFactory {
    public Entity createEntity(WorldState worldState, ViewField field, Coordinate c);
}
