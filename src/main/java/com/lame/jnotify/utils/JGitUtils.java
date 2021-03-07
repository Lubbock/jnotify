package com.lame.jnotify.utils;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.IOException;
import java.nio.file.Paths;

public class JGitUtils {
    public static Git openRpo(String dir){
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

    public static void add(Git git) throws Exception{
        git.add().addFilepattern(".").call(); //添加全部文件
    }

    public static void commit(Git git)throws Exception {
        add(git);
        final RevCommit progos_commit = git.commit().setMessage("progos commit").call();

        System.out.println(progos_commit);
    }

    public static void push(Git git, String userName, String password) throws Exception {
        CredentialsProvider provider = new UsernamePasswordCredentialsProvider(userName, password);  //生成身份信息
        git.push()
                .setRemote("origin")    //设置推送的URL名称
                .setRefSpecs(new RefSpec("master"))   //设置需要推送的分支,如果远端没有则创建
                .setCredentialsProvider(provider)   //身份验证
                .call();
    }

    public static void main(String[] args)throws Exception {
        Git git = JGitUtils.openRpo("D:\\code\\Jnotify");
        commit(git);
        push(git,"249725579@qq.com","XMCX@guo123");
    }
}
