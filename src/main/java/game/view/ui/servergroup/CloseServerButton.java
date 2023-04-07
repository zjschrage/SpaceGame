package game.view.ui.servergroup;

import game.view.ui.UIInfoRecord;
import game.view.ui.UIButton;

import java.awt.event.ActionEvent;

public class CloseServerButton extends UIButton {

    private Thread serverThread;
    private ServerIPDisplay display;

    public CloseServerButton(UIInfoRecord bir, Thread serverThread, ServerIPDisplay display) {
        super(bir);
        this.serverThread = serverThread;
        this.display = display;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        serverThread.interrupt();
        display.setText("-----");
    }
}
