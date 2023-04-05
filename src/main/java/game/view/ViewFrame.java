package game.view;

import javax.swing.*;
import java.util.ResourceBundle;

public class ViewFrame extends JFrame {

    private ResourceBundle res = ResourceBundle.getBundle("view_properties");
    
    public ViewFrame() {
        int x = Integer.parseInt(res.getString("FRAME_X"));
        int y = Integer.parseInt(res.getString("FRAME_Y"));
        setSize(x, y);
        setLocationRelativeTo(null);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setVisible(true);  
    }

    public void addPanelComponent(JPanel panel) {
        add(panel);
    }

}
