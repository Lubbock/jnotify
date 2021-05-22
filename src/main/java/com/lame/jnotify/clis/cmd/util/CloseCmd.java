package com.lame.jnotify.clis.cmd.util;

import com.lame.jnotify.clis.cmd.ClientDirect;
import com.lame.jnotify.clis.cmd.CmdCtx;
import com.lame.jnotify.clis.cmd.Command;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.util.List;

public class CloseCmd implements Command {
    @Override
    public void execute(CmdCtx ctx, List<String> cmdArray) {
        ClientDirect.logout();
        ChannelFuture future = ctx.channelHandlerContext().write("退出登陆");
        ctx.channelHandlerContext().flush();
        future.addListener(ChannelFutureListener.CLOSE);
    }
}
