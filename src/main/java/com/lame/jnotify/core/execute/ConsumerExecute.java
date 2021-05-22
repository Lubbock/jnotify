package com.lame.jnotify.core.execute;


import com.lame.jnotify.core.lock.StateLock;

import java.util.concurrent.*;

public class ConsumerExecute {

    private static ExecutorService executor = Executors.newFixedThreadPool(1);

    private static final LinkedBlockingQueue<Runnable> syncActions = new LinkedBlockingQueue<>();

    public static void add(Runnable action) {
    if (StateLock.Now.equals(StateLock.JnotifyState.RESET)) {
      syncActions.clear();
      return;
    }
        syncActions.offer(action);
    }

    public static void start() {
        executor.submit(() -> {
            while (true) {
                if (StateLock.Now.equals(StateLock.JnotifyState.RESET)) {
                    syncActions.clear();
                }
                Runnable peek = syncActions.take();
                peek.run();
            }
        });
    }
}
