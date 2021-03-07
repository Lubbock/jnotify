package com.lame.jnotify.notify.execute;

import com.lame.jnotify.notify.jobs.Job;
import com.lame.jnotify.notify.jobs.RepoSyncJob;
import com.lame.jnotify.register.RepoCtx;
import com.lame.jnotify.utils.PropertiesUtils;

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
