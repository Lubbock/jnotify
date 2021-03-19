package com.lame.jnotify.notify.execute;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

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
