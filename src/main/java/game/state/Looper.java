package game.state;

import java.util.ResourceBundle;

public class Looper implements Runnable {

    private ResourceBundle res = ResourceBundle.getBundle("game_properties");
    private int FPS = Integer.parseInt(res.getString("FPS"));
    private State menu;

    public Looper(State menu) {
        this.menu = menu;
    }
    @Override
    public void run() {
        long quanta = 1000000000/FPS;
        long delta = 0;
        long last = System.nanoTime();

        while (true) {
            long now = System.nanoTime();
            delta += now - last;
            last = now;
            if (delta > quanta) {
                delta = 0;
                try {
                    State.getState().tick();
                    State.getState().render();
                } catch(RuntimeException e) {
                    State.setState(menu);
                }
            }
        }
    }
}
