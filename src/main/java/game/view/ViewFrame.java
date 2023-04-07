package game.view;

import javax.swing.*;

public class ViewFrame extends JFrame {
    
    public ViewFrame(int x, int y, String title, int closeOperation) {
        setTitle(title);
        setSize(x, y);
        setLocationRelativeTo(null);  
        setDefaultCloseOperation(closeOperation);
        setVisible(true);  
    }

    public void addPanelComponent(JPanel panel) {
        add(panel);
    }

}
