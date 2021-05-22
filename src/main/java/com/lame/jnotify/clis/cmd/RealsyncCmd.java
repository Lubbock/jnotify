package com.lame.jnotify.clis.cmd;

import com.lame.jnotify.Jnotify;
import com.lame.jnotify.register.GitRepoRegister;
import com.lame.jnotify.utils.PropertiesUtils;

import java.util.List;

public class RealsyncCmd implements Command,Help {
    @Override
    public void execute(CmdCtx ctx, List<String> cmdArray) {
        if (cmdArray.size() > 1) {
            //初始化指定目录
            String propsPath = cmdArray.get(1);
            String pjPath = cmdArray.get(2);
            PropertiesUtils.initConfig(propsPath);
            GitRepoRegister.PJ_PATH = pjPath;
            try {
                Jnotify.realsync();
                ctx.channelHandlerContext().write("初始化本地文件夹");
            } catch (Exception e) {
                ctx.channelHandlerContext().write(e.getMessage());
                e.printStackTrace();
            }
        }else {
            //初始化所有目录
            GitRepoRegister.PJ_PATH = "./project";
            try {
                Jnotify.realsync();
                ctx.channelHandlerContext().write("初始化本地文件夹");
            } catch (Exception e) {
                ctx.channelHandlerContext().write(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public String helpInfo() {
        return "\n"
                +" git仓库同步到本地目录，会覆盖掉本地的文件夹 参数大于2的时候，指定配置文件 \n"
                +""
                +"\n";
    }
}
