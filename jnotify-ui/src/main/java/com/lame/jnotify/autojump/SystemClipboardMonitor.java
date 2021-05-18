package com.lame.jnotify.autojump;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

/**
 * clipboard监控器
 **/
public class SystemClipboardMonitor implements ClipboardOwner {
    private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    public SystemClipboardMonitor() {
        if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
            clipboard.setContents(clipboard.getContents(null), this);
        }
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        try {
            // 暂停一下，防止和操作系统冲突
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String text = null;
        if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
            try {
                text = (String) clipboard.getData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(text);
        //处理文本
        StringSelection tmp = new StringSelection(text);
        clipboard.setContents(tmp, this);
    }

    public static void main(String[] args) {
        SystemClipboardMonitor temp = new SystemClipboardMonitor();
        new JFrame().setVisible(true);
    }
}
