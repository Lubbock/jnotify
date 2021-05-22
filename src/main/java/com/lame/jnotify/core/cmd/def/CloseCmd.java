package com.lame.jnotify.core.cmd.def;

import com.lame.jnotify.core.cmd.ClientDirect;
import com.lame.jnotify.core.cmd.model.CmdCtx;
import com.lame.jnotify.core.cmd.Command;
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
