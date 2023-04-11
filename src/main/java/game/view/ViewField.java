package game.view;

import javax.swing.*;
import java.util.ResourceBundle;

public abstract class ViewField extends JPanel {
    public ViewField(int x, int y) {
        setSize(x, y);
        setLayout(null);
        setFocusable(true);
        setVisible(true);
    }

}
