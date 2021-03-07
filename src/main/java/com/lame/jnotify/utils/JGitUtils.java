package com.lame.jnotify.utils;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class JGitUtils {
    public static Git openRpo(String dir) {
        Git git = null;
        try {
            Repository repository = new FileRepositoryBuilder()
                    .setGitDir(Paths.get(dir, ".git").toFile())
                    .build();
            git = new Git(repository);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return git;
    }

    public static void gitInit(String gitUri, String dir) throws GitAPIException {
        File gitexist = new File(dir, ".git");
        if (!gitexist.exists()) {
            System.out.println("项目不存在，重新初始化项目" + dir);
            Git.cloneRepository().setURI(gitUri).setDirectory(new File(dir)).call();
        }
    }

    public static void add(Git git) throws Exception {
        git.add().addFilepattern(".").call(); //添加全部文件
    }

    public static void commit(Git git) throws Exception {
        git.pull().call();
        final Status status = status(git);
        if (status.isClean()) {
            System.out.println("无文件变动，不做修改");
            return;
        }
        add(git);
        RevCommit progos_commit = git.commit().setMessage("progos commit").call();
        System.out.println("文件提交 commit_id=" + progos_commit);
    }

    public static Status status(Git git)throws Exception {
        final Status call = git.status().call();
        return call;
    }

    public static void main(String[] args) throws Exception{
        final Git git = openRpo("D://temp");
        status(git);
    }

    public static void push(Git git, String userName, String password) throws Exception {
        CredentialsProvider provider = new UsernamePasswordCredentialsProvider(userName, password);  //生成身份信息
        git.push()
                .setRemote("origin")    //设置推送的URL名称
                .setRefSpecs(new RefSpec("master"))   //设置需要推送的分支,如果远端没有则创建
                .setCredentialsProvider(provider)   //身份验证
                .call();
    }
}
