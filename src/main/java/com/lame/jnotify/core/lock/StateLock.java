package com.lame.jnotify.core.lock;

public class StateLock {

  public static JnotifyState Now = JnotifyState.Normal;

    //reset 状态，删掉所有监控任务
   public enum JnotifyState{
        Normal,RESET
    }
}
