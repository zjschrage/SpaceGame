package game.view.ui;

import javax.swing.*;
import java.awt.event.ActionListener;

public abstract class UITextField extends JTextField implements ActionListener {

    public UITextField(UIInfoRecord uir) {
        setText(uir.name());
        setBounds(uir.x(), uir.y(), uir.width(), uir.height());
        setVisible(true);
    }

}
