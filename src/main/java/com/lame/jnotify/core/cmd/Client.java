package com.lame.jnotify.core.cmd;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class Client {

    private String client;

    private String cmd;

    private String argument;

    private long lastActiveTime;

    public void active() {
        this.lastActiveTime = System.currentTimeMillis();
    }
}
