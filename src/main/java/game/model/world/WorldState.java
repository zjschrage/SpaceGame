package game.model.world;

import game.model.entities.Entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class WorldState {

    private List<Entity> entities;

    public WorldState() {
        entities = new ArrayList<>();
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    public boolean checkEntityColliding(Entity collider, Rectangle bounds) {
        checkActive();
        for (Entity e : entities) {
            if (e.equals(collider)) continue;
            if (e.getHBox().intersects(bounds)) return true;
        }
        return false;
    }

    private void checkActive() {
        Queue<Entity> destroyQueue = new LinkedList<>();
        for (Entity e : entities) {
            if (!e.getActive()) destroyQueue.add(e);
        }
        while (!destroyQueue.isEmpty()) {
            Entity e = destroyQueue.poll();
            entities.remove(e);
        }
    }
}
