package game.view.ui.clientgroup;

import game.view.ViewField;
import game.view.ui.UIGroup;

public class ClientGroup extends UIGroup {

    //UI Components
    private JoinGameButton join;
    private ServerIPEntryField ipEntryField;

    public ClientGroup(ViewField field) {
        super(field);
        initGroupVariables();
        ipEntryField = new ServerIPEntryField(generateInfoRecord("IP_ENTRY_"));
        join = new JoinGameButton(generateInfoRecord("JOIN_BUTTON_"), ipEntryField);
        field.add(join);
        field.add(ipEntryField);
    }

    @Override
    public void initGroupVariables() {

    }

}
