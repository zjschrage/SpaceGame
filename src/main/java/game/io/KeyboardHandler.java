package game.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class KeyboardHandler implements KeyListener {

    private Map<Character, Boolean> keys = new HashMap<>();

    public List<Character> getKeys() {
        List<Character> keysList = new ArrayList<>();
        for (Character c : keys.keySet()) {
            if (keys.get(c)) keysList.add(c);
        }
        return keysList;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys.put(e.getKeyChar(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys.put(e.getKeyChar(), false);
    }
}
