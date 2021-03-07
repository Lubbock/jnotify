package com.lame.jnotify;

import com.lame.jnotify.notify.SyncFileListener;
import com.lame.jnotify.notify.execute.ConsumerExecute;
import com.lame.jnotify.utils.JFileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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

    public static void registerPj() throws Exception{
        String file = Object.class.getResource("/project").getFile();
        List<String> lines = new ArrayList<>();
        lines.add("d:/adbs,d:/adbscp");
        FileUtils.writeLines(new File(file),"utf-8", lines, true);
    }

    public static void main(String[] args) throws Exception{
        registerPj();
        String file = Object.class.getResource("/project").getFile();
        final List<String> pjs = FileUtils.readLines(new File(file), Charset.forName("utf-8"));
        List<FileAlterationObserver> observers = new ArrayList<>();
        for (String pj : pjs) {
            String[] split = pj.split(",");
            System.out.println(String.format("检测到注册项目[%s]-[%s]\n准备第一次全文件夹同步", split[0], split[1]));
            JFileUtil.copyTree(split[0], split[1], JFileUtil.PJ_DES_EXCLUDE);
            FileAlterationObserver observer = jnotify(split[0], split[1]);
            System.out.println(String.format("生成[%s]-[%s]文件夹同步配置", split[0], split[1]));
            observers.add(observer);
        }
        long interval = TimeUnit.SECONDS.toMillis(1);
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observers.toArray(new FileAlterationObserver[observers.size()]));
        monitor.start();
        ConsumerExecute.start();
        System.out.println("开启文件同步服务");
    }
}
