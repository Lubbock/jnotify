package com.lame.jnotify.notify.jobs;

import com.lame.jnotify.register.RepoCtx;
import com.lame.jnotify.utils.JGitUtils;
import org.eclipse.jgit.api.Git;

public class RealDirSyncJob implements Job{

    private RepoCtx ctx;

    public RealDirSyncJob(RepoCtx ctx) {
        this.ctx = ctx;
    }

    @Override
    public void doJob() {
        try {
            JGitUtils.lock.lock();
            Git git = JGitUtils.openRpo(ctx.GitBasePkg);
            final boolean commit = JGitUtils.commit(git, ctx.GitUsername,ctx.GitPwd);
            if (commit) {
                JGitUtils.push(git, ctx.GitUsername,ctx.GitPwd);
            }
            JGitUtils.pull(git, ctx.GitUsername, ctx.GitPwd);
            //同步完仓库后，把自己同步到目标文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JGitUtils.lock.unlock();
        }
    }
}
