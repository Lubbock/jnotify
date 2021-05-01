package com.lame.jnotify.ui.tty;

import io.termd.core.tty.TtyConnection;

public class EchoEvent {

    public static void handle(TtyConnection conn) {

        conn.setEventHandler((event, key) -> {
            switch (event) {
                case INTR:
                    conn.write("You did a Ctrl-C\n");
                    break;
                case SUSP:
                    conn.write("You did a Ctrl-Z\n");
                    break;
                case EOF:
                    conn.write("You did a Ctrl-D: closing\n");
                    conn.close();
                    break;
            }
        });

        conn.setSizeHandler(size -> {
            conn.write("You resized your terminal to " + size + "\n");
        });

        conn.setTerminalTypeHandler(term -> {
            conn.write("Your terminal is " + term + "\n");
        });

        conn.setStdinHandler(keys -> {
            for (int key : keys) {
//        127 删除
                switch (key) {
                    case 13: //回车
                        conn.write("\n");
                        break;
                    case 127:
                        conn.write(Character.toString((char) 8));
                        break;
                    default:
                        conn.write(Character.toString((char) key));
                        break;

                }
            }
        });
    }

}
