package com.lame.jnotify.register;

import com.lame.jnotify.utils.PropertiesUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepoRegister {

    private String basePkg = "";

    public void init() throws Exception {
        basePkg = PropertiesUtils.getBasePackage();
        File file = new File(basePkg);
        file.mkdirs();
        for (String spj : getSyncProject()) {
            String[] pjArray = spj.split(",");
            File temp = new File(basePkg, pjArray[1]);
            if (!temp.exists()) {
                temp.mkdirs();
            }
        }
    }

    public void addNewProject(String f1) throws Exception {
        File souce = new File(f1);
        File syncdir = new File(basePkg, souce.getName() + "-" + System.currentTimeMillis());
        String file = Object.class.getResource("/project").getFile();
        List<String> lines = new ArrayList<>();
        lines.add(souce.getAbsolutePath() + "," + syncdir.getAbsolutePath());
        FileUtils.writeLines(new File(file), "utf-8", lines, true);
    }

    public List<String> getSyncProject() throws Exception {
        String file = Object.class.getResource("/project").getFile();
        List<String> pjs = FileUtils.readLines(new File(file), Charset.forName("utf-8"));
        return pjs;
    }

    public interface RepoDes{
        void accept(String source, String syncpkg) throws Exception;
    }

    public void foreachSyncProject(RepoDes repoDes)throws Exception {
        final List<String> syncProject = getSyncProject();
        syncProject.stream().forEach(s->{
            String[] split = s.split(",");
            try {
                repoDes.accept(split[0], split[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
