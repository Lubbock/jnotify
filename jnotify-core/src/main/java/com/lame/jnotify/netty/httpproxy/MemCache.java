package com.lame.jnotify.netty.httpproxy;

import java.util.HashMap;
import java.util.Map;

public class MemCache {
    private static Map<String, Object> kv = new HashMap<>();

    public static void put(String key, Object val) {
        kv.put(key, val);
    }

    public static Object get(String key) {
        return kv.get(key);
    }
}
