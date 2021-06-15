package com.lame.jnotify.core.cmd.def;

import com.lame.jnotify.core.cmd.model.CmdCtx;
import com.lame.jnotify.core.cmd.Command;

import java.util.List;

public class HelpCmd implements Command {

    String banner = ",--------.              ,------.  ,--.        \n" +
            "'--.  .--',--,--.,--.--.|  .-.  \\ `--' ,---.  \n" +
            "   |  |  ' ,-.  ||  .--'|  |  \\  :,--.(  .-'  \n" +
            "   |  |  \\ '-'  ||  |   |  '--'  /|  |.-'  `) \n" +
            "   `--'   `--`--'`--'   `-------' `--'`----'  \n";

    String helpInfo = "*******************************************\n*sync \t本地目录将被同步到远程* \n" +
            "*stop \t jnotify 将被关闭*\n" +
            "*realsync \t 远程目录将被强制同步到本地文件夹\nbyte 退出管理平台*\n*******************************************\n";

    @Override
    public void execute(CmdCtx ctx, List<String> cmdArray) {
        ctx.channelHandlerContext().writeAndFlush(banner + helpInfo);
    }

    @Override
    public String getHelpInfo() {
        return "描述：help info： 参数：help";
    }
}
