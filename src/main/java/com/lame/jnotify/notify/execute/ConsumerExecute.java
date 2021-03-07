package com.lame.jnotify.notify.execute;


import org.apache.commons.lang3.ThreadUtils;

import java.util.concurrent.*;

public class ConsumerExecute {

    private static ExecutorService executor = Executors.newFixedThreadPool(1);

    private static final LinkedBlockingQueue<Runnable> syncActions = new LinkedBlockingQueue<>();

    public static void add(Runnable action) {
        syncActions.offer(action);
    }

    public static void start() {
        executor.submit(() -> {
            while (true) {
                Runnable peek = syncActions.take();
                peek.run();
            }
        });
    }
}
