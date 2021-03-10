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
import java.util.concurrent.locks.ReentrantLock;

public class JGitUtils {

    public static ReentrantLock lock = new ReentrantLock();

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

    public static void gitInit(String gitUri, String dir,String username,String password) throws GitAPIException {
        File gitexist = new File(dir, ".git");
        if (!gitexist.exists()) {
            System.out.println("项目不存在，重新初始化项目" + dir);
            CredentialsProvider provider = new UsernamePasswordCredentialsProvider(username, password);  //生成身份信息
            Git.cloneRepository().setURI(gitUri).setDirectory(new File(dir)).setCredentialsProvider(provider).call();
        }
    }

    public static void add(Git git) throws Exception {
        git.add().addFilepattern(".").call(); //添加全部文件
    }

    public static boolean commit(Git git,String username,String password) throws Exception {
        CredentialsProvider provider = new UsernamePasswordCredentialsProvider(username, password);  //生成身份信息
//        git.fetch().setRemote("origin").setRefSpecs(new RefSpec("master")).setCredentialsProvider(provider).call();
        git.pull().setRemote("origin")
                .setRemoteBranchName("master")
                .setCredentialsProvider(provider).call();
        final Status status = status(git);
        if (status.isClean()) {
            System.out.println("无文件变动，不做修改");
            return false;
        }
        add(git);
        RevCommit progos_commit = git.commit().setAll(true).setMessage("progos commit").call();
        System.out.println("文件提交 commit_id=" + progos_commit);
        return true;
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

    public static void pull(Git git, String username, String password) throws Exception{
        CredentialsProvider provider = new UsernamePasswordCredentialsProvider(username, password);  //生成身份信息
        git.pull().setRemote("origin")
                .setRemoteBranchName("master").setCredentialsProvider(provider).call();
    }
}
