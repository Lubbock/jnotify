package com.lame.jnotify.core.cmd;

import com.lame.jnotify.core.cmd.model.CmdCtx;

import java.util.List;

public interface Command {
    void execute(CmdCtx ctx, List<String> cmdArray);
}
