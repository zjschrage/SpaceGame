package game.view.gameview;

import game.view.ViewField;
import game.view.entityview.Drawable;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GameViewField extends ViewField {

    private Camera camera;
    private List<Drawable> entitiesList;

    public GameViewField(Camera camera, int x, int y) {
        super(x, y);
        this.camera = camera;
        this.entitiesList = new ArrayList<>();
    }

    public void addEntity(Drawable entity) {
        entitiesList.add(entity);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        checkActive();
        drawEntities(g2d);
    }

    private void checkActive() {
        try {
            Queue<Drawable> destroyQueue = new LinkedList<>();
            for (Drawable e : entitiesList) {
                if (!e.getActive()) destroyQueue.add(e);
            }
            while (!destroyQueue.isEmpty()) {
                Drawable d = destroyQueue.poll();
                entitiesList.remove(d);
            }
        } catch (ConcurrentModificationException e) {

        }
    }

    private void drawEntities(Graphics2D g2d) {
        try {
            double xOffset = camera.getXOffset();
            double yOffset = camera.getYOffset();
            for (Drawable e : entitiesList) {
                g2d.drawImage(e.getImage(), (int) (e.getDrawPosition().x() - xOffset), (int) (e.getDrawPosition().y() - yOffset), null);
                //g2d.drawRect((int)(e.getHitBox().x - xOffset), (int)(e.getHitBox().y - yOffset), e.getHitBox().width, e.getHitBox().height);
            }
        } catch (ConcurrentModificationException e) {

        }
    }

}
