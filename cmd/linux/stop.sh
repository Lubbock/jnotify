ps -ef|grep 'Jnotify-1.0.jar'|grep -v 'grep'|awk '{print $2}'|xargs kill -9