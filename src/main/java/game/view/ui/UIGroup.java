package game.view.ui;

import game.view.ViewField;

import java.util.ResourceBundle;

public abstract class UIGroup {

    protected final ResourceBundle res = ResourceBundle.getBundle("view_properties");
    protected final String titleInfoString = "TITLE";
    protected final String xInfoString = "X";
    protected final String yInfoString = "Y";
    protected final String wInfoString = "WIDTH";
    protected final String hInfoString = "HEIGHT";

    public UIGroup(ViewField field) {

    }

    public abstract void initGroupVariables();

    protected UIInfoRecord generateInfoRecord(String prefix) {
        String title = res.getString(prefix + titleInfoString);
        int x = stoi(res.getString(prefix + xInfoString));
        int y = stoi(res.getString(prefix + yInfoString));
        int width = stoi(res.getString(prefix + wInfoString));
        int height = stoi(res.getString(prefix + hInfoString));
        return new UIInfoRecord(title, x, y, width, height);
    }

    protected int stoi(String str) {
        return Integer.parseInt(str);
    }

}
