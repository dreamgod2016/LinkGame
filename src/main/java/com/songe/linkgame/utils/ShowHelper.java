package com.songe.linkgame.utils;

import java.awt.*;

public class ShowHelper {

    public static void setCenter(Window window)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = window.getSize();
        if (frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
        if (frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
        //设置中央的位置。
        window.setLocation((screenSize.width-frameSize.width)/2,(screenSize.height - frameSize.width)/2);

    }
}
