package com.lame.jnotify.autojump.utils;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;

/**
 * 粘帖版
 * */
public class Clipboard {

    public static String getSysClipboardText() throws Exception {
        String ret = "";
        java.awt.datatransfer.Clipboard sysclip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable clipTf = sysclip.getContents(null);
        if (clipTf != null) {
            if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                ret = (String) clipTf.getTransferData(DataFlavor.stringFlavor);
            }
        }
        return ret;
    }

    /**
     * 设置clipboard
     **/
    public static void setSysClipboardText(String writeMe) {
        java.awt.datatransfer.Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection tText = new StringSelection(writeMe);
        clip.setContents(tText, null);
    }

    /**
     * 4.复制图片到剪切板。
     */
    public static void setClipboardImage(final Image image) throws Exception {
        Transferable trans = new Transferable() {
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[]{DataFlavor.imageFlavor};
            }

            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return DataFlavor.imageFlavor.equals(flavor);
            }

            public Object getTransferData(DataFlavor flavor)
                    throws UnsupportedFlavorException, IOException {
                if (isDataFlavorSupported(flavor))
                    return image;
                throw new UnsupportedFlavorException(flavor);
            }

        };
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(trans,
                null);
    }

    /**
     * 5.通过流获取，可读取图文混合 *.rtf文件
     */
    public void getImageAndTextFromClipboard(String fp) throws Exception {
        java.awt.datatransfer.Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable clipTf = sysClip.getContents(null);
        DataFlavor[] dataList = clipTf.getTransferDataFlavors();
        int wholeLength = 0;
        for (int i = 0; i < dataList.length; i++) {
            DataFlavor data = dataList[i];
            if (data.getSubType().equals("rtf")) {
                Reader reader = data.getReaderForText(clipTf);
                OutputStreamWriter osw = new OutputStreamWriter(
                        new FileOutputStream(fp));
                char[] c = new char[1024];
                int leng = -1;
                while ((leng = reader.read(c)) != -1) {
                    osw.write(c, wholeLength, leng);
                }
                osw.flush();
                osw.close();
            }
        }
    }
}

