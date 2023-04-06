package game.utils;

import java.util.ArrayList;
import java.util.List;

public class Observer<T> {

    private List<Listener> listeners;

    public Observer() {
        listeners = new ArrayList<>();
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void detatchListeners() {
        for (Listener l : listeners) {
            l.destroy();
        }
        listeners = new ArrayList<>();
    }

    public void notifyListeners(T oldInfo, T newInfo) {
        for (Listener l : listeners) {
            l.update(oldInfo, newInfo);
        }
    }

}
