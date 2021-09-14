package game.engine;

import game.engine.interfaces.Updateable;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UpdateManager {

    private static ArrayList<Updateable> updateables;

    UpdateManager() {
        updateables = new ArrayList<>();
    }

    void start() {
        Thread t = new Thread(this::update);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(t, 0, 16667, TimeUnit.MICROSECONDS); // 60
    }

    private void update() {
        updateables.forEach(Updateable::update);
    }

    public static int registerUpdateable(Updateable updateable) {
        updateables.add(updateable);
        return 0; // TODO: unique ID?
    }

}
