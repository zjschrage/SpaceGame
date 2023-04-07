package game;

import game.state.Looper;
import game.state.MenuState;
import game.state.State;

import java.io.IOException;

public class Launcher {

    public static void main(String[] args) throws IOException {

        State menu = new MenuState();
        State.setState(menu);
        Thread loopThread = new Thread(new Looper(menu));

        State.getState().init();
        loopThread.start();

    }
    
}
