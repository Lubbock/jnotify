package com.lame.jnotify.core.cmd.def;

import com.lame.jnotify.core.cmd.model.CmdCtx;
import com.lame.jnotify.core.cmd.Command;

import java.util.List;

public class SayHaiCommand implements Command {

    @Override
    public void execute(CmdCtx ctx, List<String> cmdArray) {
        System.out.println("sayhai");
        ctx.channelHandlerContext().write("未找到对应指令！\n");
    }
}
