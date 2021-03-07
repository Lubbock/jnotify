package com.lame.jnotify.notify.jobs;

import com.lame.jnotify.utils.JGitUtils;
import com.lame.jnotify.utils.PropertiesUtils;
import org.eclipse.jgit.api.Git;

import java.util.Properties;

public class GitSyncJob implements Job{

    private String sync;

    public GitSyncJob(String sync) {
        this.sync = sync;
    }

    @Override
    public void doJob() {
        try {
            Git git = JGitUtils.openRpo(sync);
            JGitUtils.commit(git);
            JGitUtils.push(git, PropertiesUtils.getProperties("git.username"),PropertiesUtils.getProperties("git.password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
