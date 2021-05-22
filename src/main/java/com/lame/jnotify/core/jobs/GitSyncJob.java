package com.lame.jnotify.core.jobs;

import com.lame.jnotify.core.lock.StateLock;
import com.lame.jnotify.core.register.RepoCtx;
import com.lame.jnotify.utils.JGitUtils;
import org.eclipse.jgit.api.Git;

public class GitSyncJob implements Job{

    private RepoCtx ctx;

    public GitSyncJob(RepoCtx ctx) {
        this.ctx = ctx;
    }

    @Override
    public void doJob() {
        if (StateLock.Now.equals(StateLock.JnotifyState.RESET)) {
            return;
        }
        try {
            JGitUtils.lock.lock();
            Git git = JGitUtils.openRpo(ctx.GitBasePkg);
            final boolean commit = JGitUtils.commit(git, ctx.GitUsername,ctx.GitPwd);
            if (commit) {
                JGitUtils.push(git, ctx.GitUsername,ctx.GitPwd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JGitUtils.lock.unlock();
        }
    }
}
