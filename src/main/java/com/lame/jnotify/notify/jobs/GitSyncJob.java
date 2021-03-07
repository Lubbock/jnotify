package com.lame.jnotify.notify.jobs;

import com.lame.jnotify.register.RepoCtx;
import com.lame.jnotify.utils.JGitUtils;
import com.lame.jnotify.utils.PropertiesUtils;
import org.eclipse.jgit.api.Git;

import java.util.Properties;

public class GitSyncJob implements Job{

    private RepoCtx ctx;

    public GitSyncJob(RepoCtx ctx) {
        this.ctx = ctx;
    }

    @Override
    public void doJob() {
        try {
            Git git = JGitUtils.openRpo(ctx.GitBasePkg);
            JGitUtils.commit(git);
            JGitUtils.push(git, ctx.GitUsername,ctx.GitPwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
