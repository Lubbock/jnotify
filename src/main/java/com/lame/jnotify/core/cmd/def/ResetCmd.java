package com.lame.jnotify.core.cmd.def;

import com.lame.jnotify.core.cmd.model.CmdCtx;
import com.lame.jnotify.core.cmd.Command;
import com.lame.jnotify.core.cmd.MacroCommand;
import com.lame.jnotify.core.lock.StateLock;

import java.util.ArrayList;
import java.util.List;

public class ResetCmd implements MacroCommand {
  List<Command> cmdChains = new ArrayList<>();

    public ResetCmd() {
    cmdChains.add(new UninstallCmd());
    cmdChains.add(new InstallCmd());
  }

    @Override
    public void execute(CmdCtx ctx, List<String> cmdArray) {
    ctx.channelHandlerContext().writeAndFlush("开始重置本地文件");
    StateLock.Now = StateLock.JnotifyState.RESET;
        for (Command cmd : cmdChains) {
          cmd.execute(ctx, cmdArray);
        }
    StateLock.Now = StateLock.JnotifyState.Normal;
    }

  @Override
  public String getHelpInfo() {
    return null;
  }

  @Override
    public void add(Command cmd) {

    }

    @Override
    public void remove(Command cmd) {

    }
}
