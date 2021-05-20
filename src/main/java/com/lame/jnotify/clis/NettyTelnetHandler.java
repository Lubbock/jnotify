package com.lame.jnotify.clis;

import com.lame.jnotify.clis.cmd.ClientDirect;
import com.lame.jnotify.clis.cmd.CmdCtx;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;

public class NettyTelnetHandler extends SimpleChannelInboundHandler<String> {


    String banner = ",--------.              ,------.  ,--.        \n" +
            "'--.  .--',--,--.,--.--.|  .-.  \\ `--' ,---.  \n" +
            "   |  |  ' ,-.  ||  .--'|  |  \\  :,--.(  .-'  \n" +
            "   |  |  \\ '-'  ||  |   |  '--'  /|  |.-'  `) \n" +
            "   `--'   `--`--'`--'   `-------' `--'`----'  \n";

    String helpInfo = "*******************************************\n*sync \t本地目录将被同步到远程* \n" +
            "*stop \t jnotify 将被关闭*\n" +
            "*realsync \t 远程目录将被强制同步到本地文件夹\nbyte 退出管理平台*\n*******************************************\n";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String hostName = InetAddress.getLocalHost().getHostName();
        // Send greeting for a new connection.
        ClientDirect.addClient(hostName);
        ctx.write(banner);
        ctx.write("\n当前登陆人数：" + ClientDirect.loginUser() + "\n");
        ctx.write(helpInfo);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String request) throws Exception {
        CmdCtx cmdCtx = new CmdCtx();
        cmdCtx.channelHandlerContext(ctx);
        ClientDirect.direct(cmdCtx, request.toLowerCase());
    }
}