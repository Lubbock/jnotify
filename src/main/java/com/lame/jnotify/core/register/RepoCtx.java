package com.lame.jnotify.core.register;

import com.lame.jnotify.utils.PropertiesUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class RepoCtx {
    public  String GitUri;
    public  String GitUsername;
    public  String GitPwd;
    public  String GitBasePkg ;
    public  String SchedulePeriod;
    public  String  SchedulePullPeriod;
    public static Set<String> MonitorDir = new HashSet<>();

    private Properties properties = new Properties();


    public String prop;

    public RepoCtx() {

    }
    public RepoCtx(String prop) {
        this.prop = prop;
        try (FileInputStream fis = new FileInputStream(prop)) {
            this.properties.load(fis);
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        this.GitUri = PropertiesUtils.getProperties("git.url");
        this.GitUsername= PropertiesUtils.getProperties("git.username");
        this.GitUsername = PropertiesUtils.getProperties("git.username");
        this.GitPwd = PropertiesUtils.getProperties("git.password");
        this.GitBasePkg = PropertiesUtils.getProperties("repo.base.package");
        this.SchedulePeriod = PropertiesUtils.getProperties("schedule.period");
        this.SchedulePullPeriod= PropertiesUtils.getProperties("schedule.pullperiod");
    }
}
