package com.lame.jnotify.clis.cmd;

import java.util.List;

public class SayHaiCommand implements Command {

    @Override
    public void execute(CmdCtx ctx, List<String> cmdArray) {
        System.out.println("sayhai");
        ctx.channelHandlerContext().write("未找到对应指令！\n");
    }
}
