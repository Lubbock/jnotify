package com.lame.jnotify.register;

import com.lame.jnotify.utils.PropertiesUtils;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class RepoCtx {

    public static Integer SchedulePeriod = 30;

    public static Integer SchedulePullPeriod = 30;

    public Set<String> MonitorDir = new HashSet<>();

    private PropertiesUtils propertiesUtils;

    public Set<String> getMonitorDir() {
        return this.MonitorDir;
    }

    public void setMonitorDir(Set<String> monitorDir) {
        this.MonitorDir = monitorDir;
    }

    public RepoCtx(PropertiesUtils propertiesUtils) {
        this.propertiesUtils = propertiesUtils;
    }

    public String GitUri() {
        return this.propertiesUtils.getProperties("git.url");
    }

    public String GitUsername() {
        return this.propertiesUtils.getProperties("git.username");

    }

    public String GitPwd() {
        return this.propertiesUtils.getProperties("git.password");
    }

    public String GitBasePkg() {
        return this.propertiesUtils.getBasePackage();
    }
}
