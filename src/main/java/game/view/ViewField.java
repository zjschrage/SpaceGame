package game.view;

import javax.swing.JPanel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewField extends JPanel {

    private ResourceBundle res = ResourceBundle.getBundle("view_properties");
    private Camera camera;
    private List<Drawable> entitiesList;

    public ViewField(Camera camera) {
        int x = Integer.parseInt(res.getString("FRAME_X"));
        int y = Integer.parseInt(res.getString("FRAME_Y"));
        setSize(x, y);
        setFocusable(true);
        setVisible(true);
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
        drawEntities(g2d);
    }

    private void drawEntities(Graphics2D g2d) {
        double xOffset = camera.getXOffset();
        double yOffset = camera.getYOffset();
        for (Drawable e : entitiesList) {
            g2d.drawImage(e.getImage(), (int) (e.getDrawPosition().x() - xOffset), (int) (e.getDrawPosition().y() - yOffset), null);
            g2d.drawRect((int)(e.getHitBox().x - xOffset), (int)(e.getHitBox().y - yOffset), e.getHitBox().width, e.getHitBox().height);
        }

    }

}
