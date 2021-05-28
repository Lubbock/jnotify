package com.lame.jnotify;

import com.lame.jnotify.clis.controller.SingProcCli;
import com.lame.jnotify.core.SyncFileListener;
import com.lame.jnotify.core.execute.ConsumerExecute;
import com.lame.jnotify.core.execute.ScheduledConsumerExecute;
import com.lame.jnotify.core.jobs.GitSyncJob;
import com.lame.jnotify.core.jobs.Job;
import com.lame.jnotify.core.jobs.RepoSyncJob;
import com.lame.jnotify.core.register.GitRepoRegister;
import com.lame.jnotify.core.register.RepoCtx;
import com.lame.jnotify.utils.JFileUtil;
import com.lame.jnotify.utils.PropertiesUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Jnotify {

    boolean install = false;

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

    public static void monitor(String prop) throws Exception{
        final RepoCtx ctx = new RepoCtx(prop);
        GitRepoRegister gitRepoRegister = new GitRepoRegister(ctx);
        gitRepoRegister.init();
        List<FileAlterationObserver> observers = new ArrayList<>();
        List<Job> jobs = new ArrayList<>();
        gitRepoRegister.foreachSyncProject(((source, syncpkg) -> {
            if (!RepoCtx.MonitorDir.contains(source)){
                System.out.println(String.format("检测到注册项目[%s]-[%s]\n准备第一次全文件夹同步", source, syncpkg));
                JFileUtil.copyTree(source, syncpkg, JFileUtil.PJ_DES_EXCLUDE);
                FileAlterationObserver observer = jnotify(source, syncpkg);
                System.out.println(String.format("生成[%s]-[%s]文件夹同步配置", source, syncpkg));
                observers.add(observer);
                Job job = new GitSyncJob(ctx);
                jobs.add(job);
            }
            RepoCtx.MonitorDir.add(source);
        }));
        long interval = TimeUnit.SECONDS.toMillis(1);
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observers.toArray(new FileAlterationObserver[0]));
        monitor.start();
        ConsumerExecute.start();
        System.out.println("开启文件同步服务");
        ScheduledConsumerExecute.run(ctx, jobs);
    }

    public static void realsync() throws Exception {
        System.out.println("开启同步远程文件");
        final RepoCtx ctx = new RepoCtx();
        GitRepoRegister gitRepoRegister = new GitRepoRegister(ctx);
        gitRepoRegister.init();
        RepoSyncJob repoSyncJob = new RepoSyncJob(ctx);
        repoSyncJob.doJob();
        gitRepoRegister.foreachSyncProject(((source , syncpkg) -> {
            if (!RepoCtx.MonitorDir.contains(source)){
                System.out.println(String.format("检测到注册项目[%s]-[%s]\n准备第一次全文件夹同步", source, syncpkg));
                JFileUtil.copyTree(syncpkg, source, JFileUtil.PJ_DES_EXCLUDE);
                System.out.println(String.format("开始[%s]-[%s]文件夹同步配置", syncpkg, source));
            }
        }));
    }

    public static void install() throws Exception {
        String basePackage = PropertiesUtils.getBasePackage();
        File base = new File(basePackage);
        if (!base.exists()) {
            base.mkdirs();
        }
        RepoCtx ctx = new RepoCtx();
        GitRepoRegister gitRepoRegister = new GitRepoRegister(ctx);
        gitRepoRegister.init();
        System.out.println("初始化项目同步空间");
        File f = new File(".");
        final File[] files = f.listFiles();
        for (File file : files) {
            if (file.isFile()){
                FileUtils.copyFile(file, new File(basePackage, file.getName()));
            }else {
                FileUtils.copyDirectory(file, new File(basePackage, file.getName()));
            }
        }
        System.out.println("项目安装成功");
    }

  public static void inspectMe(String properties) {
        new Thread(new SingProcCli(properties)).start();
  }

    /**
     * 通过指定不同的配置文件 和 目录对应文件来完成
     * 2 从远程同步仓库到本地，然后复制文件到本地监控文件夹 (未启动监控)
     * 3 2 + 启动监控
     * **/
    public static void main(String[] args) throws Exception{
        for (String arg : args) {
            System.out.println(arg);
        }
        if (args.length > 0) {
            String arg = args[0];
            switch (arg) {
                case "--pull":
                    System.out.println("初始化本地文件");
                    String propsPath = args[1];
                    String pjPath = args[2];
                    PropertiesUtils.initConfig(propsPath);
                    GitRepoRegister.PJ_PATH = pjPath;
                    realsync();
                    inspectMe(propsPath);
                    System.out.println("本地文件初始化结束");
                    break;
                case "--pull-monitor":
                    System.out.println("开始监控本地文件");
                    System.out.println("加载配置文件" + args[1]);
                    PropertiesUtils.initConfig(args[1]);
                    GitRepoRegister.PJ_PATH = args[2];
                    inspectMe(args[1]);
                    monitor( args[1]);
                    break;
                default:
                    System.out.println("不合法参数！");
                    break;
            }

        }else {
            System.out.println("启动文件监控: 非法请求");
        }
    }
}
