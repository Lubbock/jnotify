package com.lame.jnotify.clis.cmd;

import java.util.List;

public interface Command {
    void execute(CmdCtx ctx, List<String> cmdArray);
}
