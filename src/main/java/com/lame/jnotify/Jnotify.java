package com.lame.jnotify;

import com.lame.jnotify.notify.SyncFileListener;
import com.lame.jnotify.notify.execute.ConsumerExecute;
import com.lame.jnotify.notify.execute.ScheduledConsumerExecute;
import com.lame.jnotify.notify.jobs.GitSyncJob;
import com.lame.jnotify.notify.jobs.Job;
import com.lame.jnotify.register.GitRepoRegister;
import com.lame.jnotify.register.RepoCtx;
import com.lame.jnotify.utils.JFileUtil;
import com.lame.jnotify.utils.PropertiesUtils;
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


    public static void main(String[] args) throws Exception{
        final RepoCtx ctx = new RepoCtx();
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
}
