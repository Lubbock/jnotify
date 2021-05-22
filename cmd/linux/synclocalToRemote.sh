#!/bin/bash
nohup java -cp /home/lame/.jnotify/libs/*:/home/lame/.jnotify/Jnotify-1.0.jar com.lame.jnotify.Jnotify --pull /home/lame/.jnotify/cfg/pkg/jnotify.properties /home/lame/.jnotify/cfg/pkg/project> nohup.out &
