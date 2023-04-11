package game.view.ui;

import javax.swing.*;
import java.awt.event.ActionListener;

public abstract class UILabel extends JLabel implements ActionListener {

    public UILabel(UIInfoRecord uir) {
        setText(uir.name());
        setLocation(uir.x(), uir.y());
        setSize(uir.width(), uir.height());
        setVisible(true);
    }

}
