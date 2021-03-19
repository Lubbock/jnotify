package com.lame.jnotify.notify;

import java.util.HashSet;
import java.util.Set;

/**
 * jnotify 统一使用的配置文件
 * **/
public class JnotifyCtx {
    private int key;

    //接收的url
    private String acceptUrl;

    //接收的
    private String username;

    private String pwd;

    private String baseScan;

    private String schedulePeriod;

    private String schedulePullPeriod;

    //monitor module
    Set<String> modules = new HashSet<>();
}
