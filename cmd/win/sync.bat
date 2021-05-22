if "%1"=="hide" goto CmdBegin
start mshta vbscript:createobject("wscript.shell").run("""%~0"" hide",0)(window.close)&&exit
:CmdBegin
@echo off 
java -cp D://temp/libs/*;D://temp/Jnotify-1.0.jar com.lame.jnotify.Jnotify --pull D://temp/cfg/workmd/jnotify.properties D://temp/cfg/workmd/project
exit