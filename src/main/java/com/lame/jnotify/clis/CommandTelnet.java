package com.lame.jnotify.clis;

import com.lame.jnotify.utils.PropertiesUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class CommandTelnet implements Runnable {
    private int PORT;
    private ServerBootstrap serverBootstrap;

    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    public void open() throws InterruptedException {

        serverBootstrap = new ServerBootstrap();
        // 指定socket的一些属性
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)  // 指定是一个NIO连接通道
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new NettyTelnetInitializer());

        // 绑定对应的端口号,并启动开始监听端口上的连接
        Channel ch = serverBootstrap.bind(PORT).sync().channel();

        // 等待关闭,同步端口
        ch.closeFuture().sync();
    }

    public void close() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    @Override
    public void run() {
    PropertiesUtils.initConfig("D:\\code\\Jnotify\\src\\main\\resources\\jnotify.properties");
        this.PORT = Integer.parseInt(PropertiesUtils.getProperties("cmd.port"));
        try {
            open();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            close();
        }
    }

    public static void main(String[] args) {
        CommandTelnet telnet = new CommandTelnet();
        telnet.run();
    }
}
