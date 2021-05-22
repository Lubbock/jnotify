package com.lame.jnotify.clis.cmd.util;

import com.lame.jnotify.Jnotify;
import com.lame.jnotify.clis.cmd.CmdCtx;
import com.lame.jnotify.clis.cmd.Command;

import java.util.List;

public class MonitorCmd implements Command {
    @Override
    public void execute(CmdCtx ctx, List<String> cmdArray) {

        // 属性文件地址 定义了多个git
        String properPath = cmdArray.get(1);

        //定义了多个项目
        String pjpath = cmdArray.get(2);
    ctx.channelHandlerContext()
        .writeAndFlush("开始监控本地文件夹 propPath:" + cmdArray.get(1) + " pjpath:" + cmdArray.get(2));
        try {
            Jnotify.monitor();
        } catch (Exception e) {
            e.printStackTrace();
      ctx.channelHandlerContext().writeAndFlush("monitor failed ");
        }
    }
}
