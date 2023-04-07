package game.view.ui;

import java.awt.*;
import java.awt.event.ActionListener;

public abstract class UIButton extends Button implements ActionListener {

    public UIButton(UIInfoRecord bir) {
        setLabel(bir.name());
        setBounds(bir.x(), bir.y(), bir.width(), bir.height());
        setVisible(true);
    }

}
