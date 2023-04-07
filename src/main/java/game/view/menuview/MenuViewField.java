package game.view.menuview;

import game.view.ViewField;

import java.awt.*;

public class MenuViewField extends ViewField {

    public MenuViewField(int x, int y) {
        super(x, y);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
    }

}
