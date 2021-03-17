#!/bin/bash
nohup java -cp /home/lame/.jnotify/libs/*:/home/lame/.jnotify/Jnotify-1.0.jar com.lame.jnotify.Jnotify 3 /home/lame/.jnotify/cfg/pkg/jnotify.properties /home/lame/.jnotify/cfg/pkg/project> nohup.out &
nohup java -cp /home/lame/.jnotify/libs/*:/home/lame/.jnotify/Jnotify-1.0.jar com.lame.jnotify.Jnotify 3 /home/lame/.jnotify/cfg/koalinner/jnotify.properties /home/lame/.jnotify/cfg/koalinner/project> nohup.out &
