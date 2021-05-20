package com.lame.jnotify.clis.cmd;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

@Data
public class Jnotify {
    //远程到本地同步
    private Command sync;

    //删除本地，远程直接赋值到本地
    private Command realsync;

    //安装
    private Command install;

    //卸载
    private Command uninstall;

    //停止运行
    private Command stop;

    //停止连接
    private Command unconnect;

    //增加同步目录，本地到远程并且上传
    private Command append;

    //停止一些应用的监控
    private Command stopSome;

    //命令行参数解析
    private String cmdInfo;
    private static String cmdPkg = "com.lame.jnotify.clis.cmd";

    private Map<String, Command> commands = new HashMap<>();

    Command sayHi = new SayHaiCommand();

    public Jnotify() {
    }

    public Command loadCmd(String cmd) {
        Command load = sayHi;
        try {
            if (commands.containsKey(cmd)) {
                load = commands.get(cmd);
            } else {
                Class<?> loadCmd = Class.forName(cmdPkg + "." + cmd + "Cmd");
                Object o = loadCmd.newInstance();
                commands.put(cmd, (Command) o);
                load = (Command) o;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return load;
    }

    public Command direct(String cmd) {
        return loadCmd(cmd.substring(0, 1).toUpperCase() + cmd.substring(1));
    }

    public void execute(CmdCtx ctx, String cmdInfo) {
        if (StringUtils.isBlank(cmdInfo)) {
            ctx.channelHandlerContext().writeAndFlush("\n");
            return;
        }
        String[] s = cmdInfo.split(" ");
        List<String> cmdArrays = new ArrayList<>();
        for (String s1 : s) {
            if (StringUtils.isNotBlank(s1)) {
                cmdArrays.add(s1);
            }
        }
        Command command = direct(cmdArrays.get(0).toLowerCase(Locale.ROOT));
        command.execute(ctx, cmdArrays);
        ctx.channelHandlerContext().flush();
    }

    public static void main(String[] args) throws Exception {
        Class<?> loadCmd = Class.forName(cmdPkg + ".CloseCmd");
        loadCmd.newInstance();
        System.out.println(loadCmd.getName());
    }
}
