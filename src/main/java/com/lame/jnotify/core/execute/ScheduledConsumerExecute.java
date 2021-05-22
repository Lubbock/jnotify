package com.lame.jnotify.core.execute;

import com.lame.jnotify.core.jobs.Job;
import com.lame.jnotify.core.jobs.RepoSyncJob;
import com.lame.jnotify.core.register.RepoCtx;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledConsumerExecute {

    public static ScheduledExecutorService execute = Executors.newSingleThreadScheduledExecutor();

    public static void run(RepoCtx ctx, List<Job> jobs) {
        execute.scheduleAtFixedRate(() -> {
            for (Job job : jobs) {
                job.doJob();
            }
        }, 0, Integer.parseInt(ctx.SchedulePeriod), TimeUnit.SECONDS);

        Job reposync = new RepoSyncJob(ctx);
        execute.scheduleAtFixedRate(() -> {
            reposync.doJob();
        }, 0, Integer.parseInt(ctx.SchedulePullPeriod), TimeUnit.SECONDS);
    }
}
