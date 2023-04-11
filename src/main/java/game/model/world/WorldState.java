package game.model.world;

import game.model.entities.Entity;
import game.model.entities.Ship;

import java.awt.*;
import java.util.*;
import java.util.List;

public class WorldState {

    private List<Entity> entities; // All entities
    private List<Entity> transientEntities; // Transient subset (to be shared with other players)
    private List<Entity> tickableEntities; // All my entities
    private Map<Class<?>, List<Entity>> externalEntities; // External transient entities
    private Map<Integer, Ship> externalShips; // External ships

    public WorldState() {
        entities = new ArrayList<>();
        transientEntities = new ArrayList<>();
        tickableEntities = new ArrayList<>();
        externalEntities = new HashMap<>();
        externalShips = new HashMap<>();
    }

    public void addEntity(Entity e, boolean isTransient) {
        entities.add(e);

        tickableEntities.add(e);
        if (isTransient) transientEntities.add(e);
    }

    public void addExternalEntity(Entity e) {
        entities.add(e);

        List<Entity> es = externalEntities.get(e.getClass());
        if (es == null) externalEntities.put(e.getClass(), new ArrayList<>());
        externalEntities.get(e.getClass()).add(e);
    }

    public List<Entity> getTransientEntities() {
        return transientEntities;
    }

    public Map<Class<?>, List<Entity>> getExternalEntities() {
        return externalEntities;
    }

    public Map<Integer, Ship> getExternalShips() {
        return externalShips;
    }

    public boolean checkEntityColliding(Entity collider, Rectangle bounds) {
        try {
            for (Entity e : entities) {
                if (e.equals(collider)) continue;
                if (e.getHBox().intersects(bounds)) return true;
            }
        } catch (ConcurrentModificationException e) {

        }
        return false;
    }

    public void checkActive() {
        try {
            Queue<Entity> destroyQueue = new LinkedList<>();
            for (Entity e : entities) {
                if (!e.getActive()) destroyQueue.add(e);
            }
            while (!destroyQueue.isEmpty()) {
                Entity e = destroyQueue.poll();
                entities.remove(e);
                transientEntities.remove(e);
                tickableEntities.remove(e);
                List<Entity> ext = externalEntities.get(e.getClass());
                if (ext != null) ext.remove(e);
            }
        } catch (ConcurrentModificationException e) {

        }
    }

    public void tickAll() {
        try {
            for (Entity e : tickableEntities) {
                e.tick();
            }
        } catch (ConcurrentModificationException e) {

        }
    }

}
