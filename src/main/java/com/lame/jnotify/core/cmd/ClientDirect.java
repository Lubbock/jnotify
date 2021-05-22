package com.lame.jnotify.core.cmd;

import com.lame.jnotify.core.cmd.model.CmdCtx;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

//命令分发器
public class ClientDirect {

    static Map<String, Client> cacheClient = new HashMap<>();
    static JnotifyCli cmd = new JnotifyCli();
    public static void addClient(String usr) {
        if (cacheClient.containsKey(usr)) {
            cacheClient.get(usr).active();
        }else {
            Client client = new Client();
            client.client(usr);
            client.active();
            cacheClient.put(usr, client);
        }
    }

    public static void direct(CmdCtx ctx, String msg) {
        ctx.client(getCurrentClient());
        cmd.execute(ctx, msg);
    }

    public static Client getCurrentClient() {
        String currentUser = getCurrentUser();
        return cacheClient.get(currentUser);
    }

    public static int loginUser() {
        return cacheClient.size();
    }

    public static String getCurrentUser() {
        String hostName = "anon";
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostName;
    }

    public static void logout() {
        String hostName = getCurrentUser();
        cacheClient.remove(hostName);
        System.out.println("用户退出:" + hostName);
    }
}
