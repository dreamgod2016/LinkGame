package com.songe.linkgame.frame;

import javax.swing.*;

import com.songe.linkgame.ui.MenuPanel;
import com.songe.linkgame.utils.ShowHelper;

import java.awt.*;

public class MenuFrame extends JFrame {

    private static MenuFrame frame;
    public MenuFrame()
    {
        init();
    }

    public void init()
    {
        //这里要有一个新的Panel，初始化这个Panel就可以
        MenuPanel menuPanel = new MenuPanel();
        add(menuPanel, BorderLayout.CENTER);
    }

    public static void open()
    {
        frame = new MenuFrame();
        frame.setTitle("LinkGame -- By 松鹅");
        frame.setSize(750,500);
        //居中
        ShowHelper.setCenter(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void close()
    {
        if (frame!=null)
            frame.dispose();
        frame = null;
    }
}
