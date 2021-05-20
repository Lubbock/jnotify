package com.lame.jnotify.clis.cmd;

import com.lame.jnotify.Jnotify;
import com.lame.jnotify.register.GitRepoRegister;
import com.lame.jnotify.utils.PropertiesUtils;

import java.util.List;

public class InstallCmd implements Command{
    @Override
    public void execute(CmdCtx ctx, List<String> cmdArray) {
        PropertiesUtils.initConfig("./jnotify.properties");
        GitRepoRegister.PJ_PATH = "./project";
        try {
            ctx.channelHandlerContext().write("jnotify 开始安装..");
            Jnotify.install();
        } catch (Exception e) {
            ctx.channelHandlerContext().write(e.getMessage());
            e.printStackTrace();
        }
    }
}
