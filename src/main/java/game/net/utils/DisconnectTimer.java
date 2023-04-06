package game.net.utils;

import game.utils.Handler;

import java.util.*;

public class DisconnectTimer {

    private static final long TIMER_DELAY = 3000L;
    private static final long TIMEOUT = 3000L;


    public DisconnectTimer(Map<Integer, Long> timeout, Handler handler) {
        TimerTask task = new TimerTask() {
            public void run() {
                Queue<Integer> removeQueue = new LinkedList<>();
                for (Integer i : timeout.keySet()) {
                    if (System.currentTimeMillis() - timeout.get(i) > TIMEOUT) {
                        handler.getPlayerShips().get(i).detatchListeners();
                        handler.getPlayerShips().remove(i);
                        removeQueue.add(i);
                        System.out.println("Player with ID " + i + " Disconnected");
                    }
                }
                while (!removeQueue.isEmpty()) {
                    int i = removeQueue.poll();
                    timeout.remove(i);
                }
            }
        };

        Timer timer = new Timer("Disconnect Timer");
        long delay = TIMER_DELAY;
        timer.schedule(task, 0, delay);
    }

}
