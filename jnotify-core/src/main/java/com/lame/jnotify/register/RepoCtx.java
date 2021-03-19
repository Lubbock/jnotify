package com.lame.jnotify.register;

import com.lame.jnotify.utils.PropertiesUtils;

import java.util.HashSet;
import java.util.Set;

public class RepoCtx {
    public static String GitUri = PropertiesUtils.getProperties("git.url");
    public static String GitUsername = PropertiesUtils.getProperties("git.username");
    public static String GitPwd = PropertiesUtils.getProperties("git.password");
    public static String GitBasePkg = PropertiesUtils.getBasePackage();
    public static String SchedulePeriod = PropertiesUtils.getProperties("schedule.period");
    public static String  SchedulePullPeriod= PropertiesUtils.getProperties("schedule.pullperiod");
    public static Set<String> MonitorDir = new HashSet<>();
}
