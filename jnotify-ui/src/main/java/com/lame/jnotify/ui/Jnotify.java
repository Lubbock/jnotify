package com.lame.jnotify.ui;



import com.lame.jnotify.ui.tty.EchoEvent;
import examples.events.EventsExample;
import examples.events.TelnetEventsExample;
import io.termd.core.telnet.netty.NettyTelnetTtyBootstrap;
import io.termd.core.tty.TtyConnection;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Jnotify {
    public synchronized static void main(String[] args) throws Throwable {
        NettyTelnetTtyBootstrap bootstrap = new NettyTelnetTtyBootstrap().setOutBinary(true).setHost("localhost").setPort(4000);
        bootstrap.start(new Consumer<TtyConnection>() {
            @Override
            public void accept(TtyConnection conn) {
                EchoEvent.handle(conn);
            }
        }).get(10, TimeUnit.SECONDS);
        System.out.println("Telnet server started on localhost:4000");
        TelnetEventsExample.class.wait();
    }
}
