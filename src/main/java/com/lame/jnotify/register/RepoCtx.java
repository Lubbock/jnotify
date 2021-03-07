package com.lame.jnotify.register;

import com.lame.jnotify.utils.PropertiesUtils;

public class RepoCtx {
    public static String GitUri = PropertiesUtils.getProperties("git.uri");
    public static String GitUsername = PropertiesUtils.getProperties("git.username");
    public static String GitPwd = PropertiesUtils.getProperties("git.password");
    public static String GitBasePkg = PropertiesUtils.getBasePackage();
    public static String SchedulePeriod = PropertiesUtils.getProperties("schedule.period");
}
