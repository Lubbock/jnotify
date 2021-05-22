package com.lame.jnotify.core.cmd;

public interface MacroCommand extends Command {
    void add(Command cmd);

    void remove(Command cmd);
}
