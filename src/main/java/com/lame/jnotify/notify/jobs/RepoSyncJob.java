package com.lame.jnotify.notify.jobs;

import com.lame.jnotify.register.RepoCtx;
import com.lame.jnotify.utils.JGitUtils;
import org.eclipse.jgit.api.Git;

public class RepoSyncJob implements Job{

    private RepoCtx ctx;

    public RepoSyncJob(RepoCtx ctx) {
        this.ctx = ctx;
    }

    @Override
    public void doJob() {
        try {
            JGitUtils.lock.lock();
            Git git = JGitUtils.openRpo(ctx.GitBasePkg);
            final boolean commit = JGitUtils.commit(git);
            if (commit) {
                JGitUtils.push(git, ctx.GitUsername,ctx.GitPwd);
            }
            JGitUtils.push(git, ctx.GitUsername, ctx.GitPwd);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JGitUtils.lock.unlock();
        }
    }
}
