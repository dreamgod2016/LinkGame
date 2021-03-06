package com.songe.linkgame.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


import com.songe.linkgame.frame.MenuFrame;
import com.songe.linkgame.frame.Rank;
import com.songe.linkgame.frame.SingleGameFrame;
import com.songe.linkgame.shared.SharedVars;
import com.songe.linkgame.utils.*;

import static com.songe.linkgame.shared.SharedVars.*;

public class MenuPanel extends JPanel implements MouseListener,MouseMotionListener{

    private Image start;
    private Image rank;
    private Image about;
    //接下来是很魔幻的定位。
    private int firstX = 260;
    private int firstY = 200;
    private int space = 20;
    private int item_width = 200;
    private int item_height = 50;

    private Rank rankDialog;

    public MenuPanel()
    {
        //游戏开始的主界面。
        initImage();
        addMouseListener(this);
        addMouseMotionListener(this);

    }

    private void initImage()
    {
        //初始化各个按钮的图片
        //图片的序号再说吧233
        start = ImagesFactory.getImage(MENU_START_BUTTON_RUN);
        rank = ImagesFactory.getImage(MENU_RANK_BUTTON_RUN);
        about = ImagesFactory.getImage(MENU_ABOUT_BUTTON_RUN);

    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        //点击事件的处理。
        switch (getIndex(e))
        {
            case 1:
                //点击了开始
                //关闭MenuFrame
                //检查游戏模式，准备开始游戏
                MenuFrame.close();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                SingleGameFrame.open();
                TimePanel.initTime();
                break;
            case 2:
                rankDialog.getInstance().show();
                break;
            case 3:
                JOptionPane.showMessageDialog(this,"By:松鹅\nGithub:https://github.com/dreamgod2016\n");
                break;
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //移动上可以搞一些事情 但是比较懒就算了hhh

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //还要画一下菜单
        drawMenu(g);
    }
    private void drawMenu(Graphics g)
    {
        //下面这个是背景
        g.drawImage(ImagesFactory.getImage(MENU_BACKGROUND_NUM), 0, 0, 750, 500, null);
        g.drawImage(start, firstX, firstY, item_width, item_height, null);
        //接下来要画多少hh
        g.drawImage(rank, firstX, firstY+item_height + space, item_width, item_height, null);
        g.drawImage(about, firstX, firstY+item_height*2 + space * 2, item_width, item_height, null);

    }

    private int getIndex(MouseEvent e)
    {
        //接下来的操作就比较魔幻了
        //根据坐标来计算 是点到了哪个图
        int x = e.getX();
        int y = e.getY();
        if (x>firstX && x<firstX+item_width)
        {
            //横坐标在范围。
            if (y>firstY && y<firstY+item_height)
                return 1;
            else if (y>firstY + item_height + space && y<firstY + item_height*2 + space)
                return 2;
            else if (y>firstY + item_height*2 + space*2 && y<firstY + item_height*3 + space*2)
                return 3;
        }
        return -1;
    }
}
