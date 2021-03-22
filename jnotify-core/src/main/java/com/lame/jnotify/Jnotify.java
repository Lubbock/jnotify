package com.lame.jnotify;

import com.lame.jnotify.notify.SyncFileListener;
import com.lame.jnotify.notify.execute.ConsumerExecute;
import com.lame.jnotify.notify.execute.ScheduledConsumerExecute;
import com.lame.jnotify.notify.jobs.GitSyncJob;
import com.lame.jnotify.notify.jobs.Job;
import com.lame.jnotify.notify.jobs.RepoSyncJob;
import com.lame.jnotify.register.ConfigReg;
import com.lame.jnotify.register.GitRepoRegister;
import com.lame.jnotify.register.RepoCtx;
import com.lame.jnotify.utils.JFileUtil;
import com.lame.jnotify.utils.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Jnotify {
    public static final String JNOTIFY_CFG = "JNOTIFY_CFG";

    public static List<ConfigReg> loadJnotifyCfg() {
        String jnotify_cfg = System.getenv(JNOTIFY_CFG);
        if (StringUtils.isEmpty(jnotify_cfg)) {
            jnotify_cfg = ".";
        }
        File f = new File(jnotify_cfg);
        if (!f.exists()) {
            throw new RuntimeException("配置文件不存在");
        }
        File[] files = f.listFiles();
        List<ConfigReg> regs = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory()) {
                File jnotifyCfg = new File(file.getAbsolutePath(), "jnotify.properties");
                File porject = new File(file.getAbsolutePath(), "project");
                if (jnotifyCfg.exists() && porject.exists()) {
                    ConfigReg cfg = new ConfigReg(jnotifyCfg.getAbsolutePath(), porject.getAbsolutePath());
                    regs.add(cfg);
                }
            }
        }
        return regs;
    }

    public static FileAlterationObserver jnotify(String f1, String f2) {
        IOFileFilter directories = FileFilterUtils.and(FileFilterUtils.directoryFileFilter(), HiddenFileFilter.VISIBLE);
        IOFileFilter files = FileFilterUtils.and(
                FileFilterUtils.fileFileFilter());
        IOFileFilter filter = FileFilterUtils.or(directories, files);
        // 使用过滤器
        FileAlterationObserver observer = new FileAlterationObserver(new File(f1), filter);
        observer.addListener(new SyncFileListener(f1, f2));
        return observer;
    }

    public static void monitor() throws Exception {
        List<ConfigReg> regs = loadJnotifyCfg();
        Optional.ofNullable(regs).ifPresent(cfgs ->{
            cfgs.parallelStream().forEach(cfg->{
                log.info(String.format("开始加载配置文件 jnotify.props=%s,project=%s", cfg.getJnotifyCfg(), cfg.getProject()));
                PropertiesUtils propertiesUtils = PropertiesUtils.initConfig(cfg.getJnotifyCfg());
                RepoCtx repoCtx = new RepoCtx(propertiesUtils);
                GitRepoRegister gitRepoRegister = new GitRepoRegister(repoCtx);
                List<Job> jobs = new ArrayList<>();
                try {
                    gitRepoRegister.init();
                    List<FileAlterationObserver> observers = new ArrayList<>();
                    gitRepoRegister.foreachSyncProject(((source, syncpkg) -> {
                        if (!repoCtx.MonitorDir.contains(source)){
                            System.out.println(String.format("检测到注册项目[%s]-[%s]\n准备第一次全文件夹同步", source, syncpkg));
                            JFileUtil.copyTree(source, syncpkg, JFileUtil.PJ_DES_EXCLUDE);
                            FileAlterationObserver observer = jnotify(source, syncpkg);
                            System.out.println(String.format("生成[%s]-[%s]文件夹同步配置", source, syncpkg));
                            observers.add(observer);
                        }
                        repoCtx.MonitorDir.add(source);
                    }));
//                    一个库只需要一次同步
                    Job job = new GitSyncJob(repoCtx);
                    jobs.add(job);
                    long interval = TimeUnit.SECONDS.toMillis(1);
                    FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observers.toArray(new FileAlterationObserver[0]));
                    monitor.start();
                } catch (Exception e) {
                    log.error("", e);
                }
                //这里有问题
                log.info("开始文件同步服务");
                ConsumerExecute.start();
                log.info("开始定时提交服务");
                ScheduledConsumerExecute.run(repoCtx, jobs);
            });
        });
    }

    public static void realsync() throws Exception {
        System.out.println("开启同步远程文件");
        List<ConfigReg> regs = loadJnotifyCfg();
        Optional.ofNullable(regs).ifPresent(cfgs -> {
            cfgs.parallelStream().forEach(cfg->{
                log.info(String.format("开始加载配置文件 jnotify.props=%s,project=%s", cfg.getJnotifyCfg(), cfg.getProject()));
                PropertiesUtils propertiesUtils = PropertiesUtils.initConfig(cfg.getJnotifyCfg());
                RepoCtx repoCtx = new RepoCtx(propertiesUtils);
                GitRepoRegister gitRepoRegister = new GitRepoRegister(repoCtx);
                try {
                    gitRepoRegister.init();
                    RepoSyncJob repoSyncJob = new RepoSyncJob(repoCtx);
                    repoSyncJob.doJob();
                    gitRepoRegister.foreachSyncProject(((source , syncpkg) -> {
                        if (!repoCtx.MonitorDir.contains(source)){
                            log.info(String.format("检测到注册项目[%s]-[%s]\n准备第一次全文件夹同步", source, syncpkg));
                            JFileUtil.copyTree(syncpkg, source, JFileUtil.PJ_DES_EXCLUDE);
                            log.info(String.format("开始[%s]-[%s]文件夹同步配置", syncpkg, source));
                        }
                    }));
                } catch (Exception e) {
                    log.error("", e);
                }
            });
        });
    }
    public static void main(String[] args) throws Exception{
        if (args.length == 0) {
            monitor();
        }
        switch (args[0]) {
            case "0":
                monitor();
                break;
            case "1":
                realsync();
                break;
        }
    }
}
