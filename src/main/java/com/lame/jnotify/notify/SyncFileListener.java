package com.lame.jnotify.notify;

import com.lame.jnotify.notify.execute.ConsumerExecute;
import com.lame.jnotify.utils.JFileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.function.Consumer;

public class SyncFileListener extends FileAlterationListenerAdaptor {

    private String base = "";

    private String sync = "";

    public SyncFileListener(String base, String sync) {
        this.base = new File(base).getAbsolutePath();
        this.sync = new File(sync).getAbsolutePath();
    }

    public String transformer(String fp) {
        String innerFp = StringUtils.substringAfter(fp, base);
        return new File(sync, innerFp).getAbsolutePath();
    }

    /**
     * 文件创建执行
     */
    public void onFileCreate(File file) {
        ConsumerExecute.add(() -> {
            String transformer = transformer(file.getAbsolutePath());
            try {
                FileUtils.copyFile(file, new File(transformer));
                System.out.println("[新建]:" + transformer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 文件创建修改
     */
    public void onFileChange(File file) {
        ConsumerExecute.add(() -> {
            String transformer = transformer(file.getAbsolutePath());
            try {
                FileUtils.copyFile(file, new File(transformer));
                System.out.println("[修改]:" + transformer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 文件删除
     */
    public void onFileDelete(File file) {
        ConsumerExecute.add(() -> {
            String transformer = transformer(file.getAbsolutePath());
            FileUtils.deleteQuietly(new File(transformer));
            System.out.println("[删除]:" + transformer);
        });
    }


    /**
     * 目录创建
     */
    public void onDirectoryCreate(File directory) {
        ConsumerExecute.add(() -> {
            String transformer = transformer(directory.getAbsolutePath());
            try {
                FileUtils.copyDirectory(directory, new File(transformer));
                System.out.println("[新建]:" + transformer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public Consumer<File> syncParentDirector = (f) -> {
        String parent = new File(f.getParent()).getAbsolutePath();
        String tf = transformer(parent);
        try {
            JFileUtil.copyTree(parent,tf, JFileUtil.PJ_DES_EXCLUDE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    /**
     * 目录修改
     * 修改目录，直接同步整个目录
     */
    public void onDirectoryChange(File directory) {
        ConsumerExecute.add(() -> {

            try {
                String tf = transformer(directory.getAbsolutePath());
                JFileUtil.copyTree(directory.getAbsolutePath(), tf, JFileUtil.PJ_DES_EXCLUDE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("[修改]:" + directory.getAbsolutePath());
        });
    }

    /**
     * 目录删除
     */
    public void onDirectoryDelete(File directory) {
        ConsumerExecute.add(() -> {
            try {
                FileUtils.deleteDirectory(directory);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("[修改]:" + directory.getAbsolutePath());
        });
    }

    public void onStart(FileAlterationObserver observer) {
        // TODO Auto-generated method stub
        super.onStart(observer);
    }

    public void onStop(FileAlterationObserver observer) {
        // TODO Auto-generated method stub
        super.onStop(observer);
    }
}
