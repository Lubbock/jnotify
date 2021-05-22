package com.lame.jnotify.core.cmd.def;

import com.lame.jnotify.core.cmd.model.CmdCtx;
import com.lame.jnotify.core.cmd.Command;
import com.lame.jnotify.core.register.GitRepoRegister;
import com.lame.jnotify.core.register.RepoCtx;
import com.lame.jnotify.utils.JFileUtil;

import java.util.List;

public class UninstallCmd implements Command {

    @Override
    public void execute(CmdCtx cmdCtx, List<String> cmdArray) {
        final RepoCtx ctx = new RepoCtx();
        GitRepoRegister gitRepoRegister = new GitRepoRegister(ctx);
    try {
      gitRepoRegister.foreachSyncProject(
          ((source, syncpkg) -> {
            JFileUtil.rmTree(source);
            JFileUtil.rmTree(syncpkg);
          }));
    } catch (Exception e) {
      cmdCtx.channelHandlerContext().writeAndFlush("卸载失败");
    }
    }
}
