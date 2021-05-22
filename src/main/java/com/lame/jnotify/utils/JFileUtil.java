package com.lame.jnotify.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.stream.Stream;

public class JFileUtil {

    public static FileFilter PJ_DES_EXCLUDE = (f) -> !f.getName().equals(".jnotify");

    public static void copyTree(String f1, String f2, FileFilter fileFilter) throws IOException {
        System.out.println(f1 + "\t" + f2);
        org.apache.commons.io.FileUtils.
                copyDirectory(new File(f1), new File(f2), fileFilter);
    }

    public static void rmTree( String f2) throws IOException {
    org.apache.commons.io.FileUtils.deleteDirectory(new File(f2));
    }
}
