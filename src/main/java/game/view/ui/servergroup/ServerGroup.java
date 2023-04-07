package game.view.ui.servergroup;

import game.net.server.Server;
import game.view.ViewField;
import game.view.ui.UIGroup;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerGroup extends UIGroup {

    //UI Components
    private StartServerButton start;
    private CloseServerButton close;
    private ServerIPDisplay display;

    //Shared Group Variables
    private Thread serverThread;
    private InetAddress ip;

    public ServerGroup(ViewField field) {
        super(field);
        initGroupVariables();
        display = new ServerIPDisplay(generateInfoRecord("IP_DISPLAY_"));
        start = new StartServerButton(generateInfoRecord("START_SERVER_BUTTON_"), serverThread, ip, display);
        close = new CloseServerButton(generateInfoRecord("CLOSE_SERVER_BUTTON_"), serverThread, display);
        field.add(start);
        field.add(close);
        field.add(display);
    }

    @Override
    public void initGroupVariables() {
        try {
            ip = InetAddress.getLocalHost();
            serverThread = new Thread(new Server(ip, 5000));
            System.out.println(ip.getHostAddress());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

}
