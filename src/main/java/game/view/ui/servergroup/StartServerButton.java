package game.view.ui.servergroup;

import game.view.ui.UIInfoRecord;
import game.view.ui.UIButton;

import java.awt.event.ActionEvent;
import java.net.InetAddress;

public class StartServerButton extends UIButton {

    private Thread serverThread;
    private InetAddress ip;
    private ServerIPDisplay display;

    public StartServerButton(UIInfoRecord bir, Thread serverThread, InetAddress ip, ServerIPDisplay display) {
        super(bir);
        this.serverThread = serverThread;
        this.ip = ip;
        this.display = display;
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        serverThread.start();
        display.setText(ip.getHostAddress());
    }

}