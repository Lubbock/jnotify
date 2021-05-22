package com.lame.jnotify.clis.cmd.util;

import com.lame.jnotify.clis.cmd.CmdCtx;
import com.lame.jnotify.clis.cmd.Command;

import java.util.List;

public class SayHaiCommand implements Command {

    @Override
    public void execute(CmdCtx ctx, List<String> cmdArray) {
        System.out.println("sayhai");
        ctx.channelHandlerContext().write("未找到对应指令！\n");
    }
}
