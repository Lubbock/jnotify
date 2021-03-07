package com.lame.jnotify.notify.execute;

import com.lame.jnotify.notify.jobs.GitSyncJob;
import com.lame.jnotify.notify.jobs.Job;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledConsumerExecute {
    public static ScheduledExecutorService execute = Executors.newSingleThreadScheduledExecutor();

    public static void run(Job job)  {
        execute.scheduleAtFixedRate(() -> {
           job.doJob();
        }, 0,2,TimeUnit.MINUTES);
    }

    public static void main(String[] args) {
        run(new GitSyncJob(""));
    }
}
