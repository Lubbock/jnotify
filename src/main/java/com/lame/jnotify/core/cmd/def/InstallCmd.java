package com.lame.jnotify.core.cmd.def;

import com.lame.jnotify.Jnotify;
import com.lame.jnotify.core.cmd.model.CmdCtx;
import com.lame.jnotify.core.cmd.Command;

import java.util.List;

public class InstallCmd implements Command {
    @Override
    public void execute(CmdCtx ctx, List<String> cmdArray) {
        try {
            ctx.channelHandlerContext().write("jnotify 开始安装..");
            Jnotify.install();
        } catch (Exception e) {
            ctx.channelHandlerContext().write(e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public String getHelpInfo() {
        return "描述：关闭服务： 参数：install";
    }
}
