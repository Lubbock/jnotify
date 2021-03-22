package com.lame.jnotify.notify.jobs;

import com.lame.jnotify.register.GitRepoRegister;
import com.lame.jnotify.register.RepoCtx;
import com.lame.jnotify.utils.JFileUtil;
import com.lame.jnotify.utils.JGitUtils;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;

@Slf4j
public class RepoSyncJob implements Job{

    private RepoCtx ctx;

    public RepoSyncJob(RepoCtx ctx) {
        this.ctx = ctx;
    }

    @Override
    public void doJob() {
        try {
            JGitUtils.lock.lock();
            Git git = JGitUtils.openRpo(ctx.GitBasePkg());
            final boolean commit = JGitUtils.commit(git, ctx.GitUsername(),ctx.GitPwd());
            if (commit) {
                JGitUtils.push(git, ctx.GitUsername(),ctx.GitPwd());
            }
            JGitUtils.pull(git, ctx.GitUsername(), ctx.GitPwd());
            GitRepoRegister gitRepoRegister = new GitRepoRegister(ctx);
            log.info("远程文件同步到本地");
            gitRepoRegister.foreachSyncProject(((source, syncpkg) -> {
                if (!ctx.MonitorDir.contains(source)){
                    log.info(String.format("检测到注册项目[%s]-[%s]\n准备第一次全文件夹同步", source, syncpkg));
                    JFileUtil.copyTree(syncpkg, source, JFileUtil.PJ_DES_EXCLUDE);
                    log.info(String.format("开始[%s]-[%s]文件夹同步配置", syncpkg, source));
                }
            }));
            //同步完仓库后，把自己同步到目标文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JGitUtils.lock.unlock();
        }
    }
}
