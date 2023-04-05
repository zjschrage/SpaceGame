package game.model.world;

import game.model.entities.Entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WorldState {

    private List<Entity> entities;

    public WorldState() {
        entities = new ArrayList<>();
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }
    
    public List<Entity> getEntity() {
        return entities;
    }

    public boolean checkEntityColliding(Entity collider, Rectangle bounds) {
        for (Entity e : entities) {
            if (e.equals(collider)) continue;
            if (e.getHBox().intersects(bounds)) return true;
        }
        return false;
    }
}
