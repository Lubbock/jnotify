package com.lame.jnotify.clis.cmd;

public interface MacroCommand extends Command {
    void add(Command cmd);

    void remove(Command cmd);
}
