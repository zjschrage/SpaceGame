package game.view.ui;

import javax.swing.*;
import java.awt.event.ActionListener;

public abstract class UILabel extends JLabel implements ActionListener {

    public UILabel(UIInfoRecord bir) {
        setText(bir.name());
        setBounds(bir.x(), bir.y(), bir.width(), bir.height());
        setVisible(true);
    }

}
