package com.lame.jnotify.register;

import com.lame.jnotify.utils.JFileUtil;
import com.lame.jnotify.utils.JGitUtils;
import com.lame.jnotify.utils.PropertiesUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class GitRepoRegister {

    private RepoCtx ctx;

    public GitRepoRegister(RepoCtx ctx) {
        this.ctx = ctx;
    }


    public void init() throws Exception {
        File file = new File(ctx.GitBasePkg);
        file.mkdirs();
        System.out.println(ctx.GitUri);
        JGitUtils.gitInit(
                ctx.GitUri
                , ctx.GitBasePkg);
        for (String spj : getSyncProject()) {
            String[] pjArray = spj.split(",");
            File temp = new File(pjArray[1]);
            if (!temp.exists()) {
                temp.mkdirs();
                JFileUtil.copyTree(pjArray[0], pjArray[1], JFileUtil.PJ_DES_EXCLUDE);
            }
        }
    }


    public void addNewProject(String f1) throws Exception {
        if (!RepoCtx.MonitorDir.contains(f1)) {
            File souce = new File(f1);
            File syncdir = new File(ctx.GitBasePkg, souce.getName() + "-" + System.currentTimeMillis());
            String file = "./project";
            List<String> lines = new ArrayList<>();
            lines.add(souce.getAbsolutePath() + "," + syncdir.getAbsolutePath());
            FileUtils.writeLines(new File(file), "utf-8", lines, true);
        }
    }

    public List<String> getSyncProject() throws Exception {
        String file = "./project";
        List<String> pjs = FileUtils.readLines(new File(file), Charset.forName("utf-8"));
        return pjs.stream().filter(e -> StringUtils.isNotBlank(e)).collect(Collectors.toList());
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
