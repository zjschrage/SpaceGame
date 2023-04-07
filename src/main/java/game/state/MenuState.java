package game.state;

import game.view.ViewFrame;
import game.view.menuview.MenuViewField;
import game.view.ui.clientgroup.ClientGroup;
import game.view.ui.servergroup.ServerGroup;

import javax.swing.*;
import java.util.ResourceBundle;

public class MenuState extends State {

    private ResourceBundle res = ResourceBundle.getBundle("view_properties");
    private int x = Integer.parseInt(res.getString("MENU_FRAME_X"));
    private int y = Integer.parseInt(res.getString("MENU_FRAME_Y"));
    private String title = res.getString("MENU_FRAME_TITLE");

    private ViewFrame frame;
    private MenuViewField field;

    public MenuState() {

    }

    @Override
    public void init() {
        //Frontend
        frame = new ViewFrame(x, y, title, WindowConstants.EXIT_ON_CLOSE);
        field = new MenuViewField(x, y);
        frame.addPanelComponent(field);

        //UI Components
        new ServerGroup(field);
        new ClientGroup(field);
    }
    @Override
    public void tick() {

    }

    @Override
    public void render() {
        field.repaint();
    }
}
