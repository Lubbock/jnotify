package com.lame.jnotify.core.lock;

public class StateLock {

    public static String propPath;

    public static String pjPath;

  public static JnotifyState Now = JnotifyState.CLOSE;

    //reset 状态，删掉所有监控任务
   public enum JnotifyState{
        Normal,RESET,CLOSE
    }
}
