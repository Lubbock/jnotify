package com.lame.jnotify.notify.jobs;

import com.lame.jnotify.utils.JGitUtils;
import org.eclipse.jgit.api.Git;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GitSyncJob implements Job{

    private String sync;

    public GitSyncJob(String sync) {
        this.sync = sync;
    }

    public static void main(String[] args) {

        GitSyncJob gitSyncJob = new GitSyncJob("D:/code/Jnotify");
        gitSyncJob.doJob();
    }

    @Override
    public void doJob() {
        try {
            Git git = JGitUtils.openRpo(sync);
            JGitUtils.commit(git);
            JGitUtils.push(git,"249725579@qq.com","xxx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
