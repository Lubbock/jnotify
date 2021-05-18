package com.lame.jnotify.analy;

import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class Project {

    private String basePkg;

    public static boolean isLink(File file) {
        String cPath = "";
        try {
            cPath = file.getCanonicalPath();
        } catch (IOException ex) {
        }
        return !cPath.equals(file.getAbsolutePath());
    }

    public static boolean exclude(File file) {
        String name = file.getName();
        return name.equals(".git") || name.equals("~Envs")||name.equals("node_modules")||name.equals("Windows")||name.startsWith("gradle");
    }

    public static List<Project> scan(String base) {
        List<Project> scanProjects = new ArrayList<>();
        File baseDir = new File(base);
        File[] files = baseDir.listFiles();
        int weight = 0;
        for (File file : files) {
            if (
                    file.getName().equals("node_modules")||
                    file.getName().equals("settings.gradle") ||
                            file.getName().equals("build.gradle") ||
                            file.getName().equals(".idea") ||
                            file.getName().equals(".gradle")) {
                weight++;
            }
        }
        if (weight >= 2) {
            Project project = new Project();
            project.setBasePkg(baseDir.getAbsolutePath());
            System.out.println("扫描到工程：" + baseDir.getName());
            List<Project> projects = new ArrayList<>(3);
            projects.add(project);
            return projects;
        } else {
            if (files.length == 0) {
                return new ArrayList<>(0);
            } else {
                for (File file : files) {
                    if (file.isDirectory()) {
                        if (isLink(file)) {
                            continue;
                        }
                        if (exclude(file)) {
                            continue;
                        }
                        System.out.println("开始扫描目录：" + file.getAbsolutePath());
                        List<Project> projects = scan(file.getAbsolutePath());
                        scanProjects.addAll(projects);
                    }
                }
            }
        }
        return scanProjects;
    }

    public static void main(String[] args) {
        List<Project> projects = Project.scan("/media/lame/");
        System.out.println("******************");
        for (Project project : projects) {
            System.out.println(project.getBasePkg());
        }
    }
}
