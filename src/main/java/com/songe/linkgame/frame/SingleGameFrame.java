package com.songe.linkgame.frame;

import com.songe.linkgame.shared.SharedVars;
import com.songe.linkgame.ui.*;
import com.songe.linkgame.utils.DrawHelper;
import com.songe.linkgame.utils.ShowHelper;

import javax.swing.*;
import java.awt.*;

public class SingleGameFrame extends JFrame {

    private static SingleGameFrame frame;
    private ControlPanel controlPanel;


    public SingleGameFrame()
    {
        init();
    }

    private void init()
    {
        SharedVars.area.setNodes(DrawHelper.getNodes());
        SharedVars.timePanel.setPreferredSize(new Dimension(100, 40));
        SharedVars.area.setTime(SharedVars.timePanel);
        SharedVars.timePanel.setArea(SharedVars.area);
        controlPanel = new ControlPanel(SharedVars.area, SharedVars.timePanel);
        controlPanel.setPreferredSize(new Dimension(80, 100));
        add(SharedVars.timePanel, BorderLayout.NORTH);
        add(SharedVars.area, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);
    }


    public static void open() {
        frame = new SingleGameFrame();
        frame.setTitle("松鹅的连连看");
        frame.setSize(980, 570);
        ShowHelper.setCenter(frame);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        if (!SharedVars.isStart) {
            SharedVars.timePanel.start();
        }
        else
            SharedVars.timePanel.begin();

//        if (SharedVars.backgroud_music) {
//            BackMusic.getInstance().play();
//        }
    }

    public static void close() {
        if (frame != null)
            frame.dispose();
        frame = null;
//        BackMusic.getInstance().stop();
    }

}
