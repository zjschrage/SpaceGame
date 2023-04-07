package game.view.ui.clientgroup;

import game.net.client.Client;
import game.state.GameState;
import game.state.State;
import game.utils.Handler;
import game.view.ui.UIInfoRecord;
import game.view.ui.UIButton;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class JoinGameButton extends UIButton {

    private ServerIPEntryField ipEntryField;
    public JoinGameButton(UIInfoRecord bir, ServerIPEntryField ipEntryField) {
        super(bir);
        this.ipEntryField = ipEntryField;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            GameState game = new GameState();
            game.init();

            Handler handler = new Handler(game.getShip(), game.getPlayerShips(), game.getWorldState(), game.getViewField());

            Client client = new Client(ipEntryField.getText(), 5000);
            client.setupClientReciever(handler);
            game.associateClient(client);

            State.setState(game);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
