package com.lame.jnotify.core.cmd.model;

import com.lame.jnotify.core.cmd.Client;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class CmdCtx {
    ChannelHandlerContext channelHandlerContext;

    Client client;
}
