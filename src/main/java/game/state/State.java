package game.state;

public abstract class State {

    public static State state = null;

    public static void setState(State stateIn) {
        state = stateIn;
    }

    public static State getState() {
        return state;
    }

    public abstract void init();
    public abstract void tick();
    public abstract void render();
}
