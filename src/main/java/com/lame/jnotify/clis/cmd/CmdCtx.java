package com.lame.jnotify.clis.cmd;

import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class CmdCtx {
    ChannelHandlerContext channelHandlerContext;

    Client client;
}
