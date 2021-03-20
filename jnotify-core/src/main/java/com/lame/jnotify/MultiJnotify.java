package com.lame.jnotify;

import com.lame.jnotify.notify.JnotifyCtx;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MultiJnotify {


    public static void co() {
          List<JnotifyCtx> s = new ArrayList<>();

        final Map<String, JnotifyCtx> collect = s.stream().collect(Collectors.groupingBy(JnotifyCtx::toString, Collector.of(
                JnotifyCtx::new, (t1, t2) -> {
                }, (t1, t2) -> t1, a -> a, Collector.Characteristics.IDENTITY_FINISH
        )));
    }
}
