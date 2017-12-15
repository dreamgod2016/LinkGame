package com.songe.linkgame.ui;

import com.songe.linkgame.shared.SharedVars;
import com.songe.linkgame.utils.Checker;
import com.songe.linkgame.utils.DrawHelper;
import com.songe.linkgame.utils.ImagesFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static com.songe.linkgame.shared.SharedVars.*;

public class ControlPanel extends JPanel implements MouseListener, MouseMotionListener {
    //主要的控制Panel

    private boolean isRun = true;
    private Image start;
    private Image refresh;
    private Image reset;
    private Image tip;
    private Image menu;
    private int firstX = 10;
    private int firstY = 50;
    private int space = 80;
    private DrawArea area;
    private TimePanel time;
    public ControlPanel()
    {
        //空的构造函数
        initImage();
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    public ControlPanel(DrawArea area, TimePanel time)
    {
        this();
        this.area = area;
        this.time = time;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        DrawHelper.drawBackGround(g, this, BACKGROUND_NUM);
        drawMenu(g);
    }

    private void initImage()
    {
        //初始化图片
        if (isRun)
            start = ImagesFactory.getImage(START_IMAGE_RUN);
        else
            start = ImagesFactory.getImage(START_IMAGE);
        refresh = ImagesFactory.getImage(REFRESH_IMAGE);
        tip = ImagesFactory.getImage(TIP_IMAGE);
        menu = ImagesFactory.getImage(MENU_IMAGE);
        reset = ImagesFactory.getImage(RESET_IMAGE);
    }

    private void drawMenu(Graphics g) {
        g.drawImage(start, firstX, firstY, 60, 60, null);
        g.drawImage(refresh, firstX, firstY + space, 60, 60, null);
        g.drawImage(reset, firstX, firstY + space * 2, 60, 60, null);
        g.drawImage(tip, firstX, firstY + space * 3, 60, 60, null);
        g.drawImage(menu, firstX, firstY + space * 4, 60, 60, null);
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        switch (getIndex(e))
        {
            case 1:
                if (isRun)
                {
                    //暂停时间。
                    //但是时间好像还没停止
                    isRun = false;
                    time.pause();
                    SharedVars.draw_able = false;
                    area.repaint();
                    start = ImagesFactory.getImage(START_IMAGE);
                }
                else
                {
                    isRun = true;
                    time.begin();
                    SharedVars.draw_able = true;
                    area.repaint();
                    start = ImagesFactory.getImage(START_IMAGE_RUN);
                }
                break;
            case 2:
                refresh = ImagesFactory.getImage(64);
                refresh();
                break;
            case 3:
                reset = ImagesFactory.getImage(48);
                SharedVars.draw_able = true;
                restart();
                break;
            case 4:
                tip = ImagesFactory.getImage(44);
                if (SharedVars.draw_able) {
//                    if (SharedVars.effct_music)
//                        EffectSound.getAudio(EffectSound.TIP).play();
                    showTip();
                }
                break;
            case 5:
                menu = ImagesFactory.getImage(46);
                backMenu();
                break;
            default:
                break;
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        //为了UI效果，这里可以增加一些，当鼠标移进来，图标有变化。
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    //这个方法也是非常可怕了- -
    private int getIndex(MouseEvent e) {
        if (e.getX() > firstX && e.getX() < firstX + 60 && e.getY() > firstY
                && e.getY() < firstY + 60) {
            return 1;
        }
        if (e.getX() > firstX && e.getX() < firstX + 60 && e.getY() > firstY + space
                && e.getY() < firstY + space + 60) {
            return 2;
        }
        if (e.getX() > firstX && e.getX() < firstX + 60 && e.getY() > firstY + space * 2
                && e.getY() < firstY + space * 2 + 60) {
            return 3;
        }
        if (e.getX() > firstX && e.getX() < firstX + 60 && e.getY() > firstY + space * 3
                && e.getY() < firstY + space * 3 + 60) {
            return 4;
        }
        if (e.getX() > firstX && e.getX() < firstX + 60 && e.getY() > firstY + space * 4
                && e.getY() < firstY + space * 4 + 60) {
            return 5;
        }
        return -1;
    }

    private void refresh()
    {
        Checker refreshChecker = new Checker(area.getNodes());
        area.setNodes(refreshChecker.reset());
        if (SharedVars.score > 0)
            SharedVars.score -= 5;
        area.repaint();
    }

    private void restart() {
        area.setNodes(DrawHelper.getNodes());
        TimePanel.initTime();
        SharedVars.score = 0;
        isRun = true;
        initImage();
//        if (SharedVars.effct_music)
//            EffectSound.getAudio(EffectSound.RESTART).play();
        area.repaint();
    }

    private void backMenu() {
        int state = -233;
        if (isRun)
            state = JOptionPane.showConfirmDialog(null, "Want to go back to menu?", "confirm",
                    JOptionPane.OK_CANCEL_OPTION);
        if (state == JOptionPane.OK_OPTION || state == -233)
        {
            DrawArea.back();
        }
    }

    private void showTip() {
        int[] path = Checker.getTip();
        if (path == null)
            return;
        area.setPaths(Checker.getPath());
        if (SharedVars.score > 0)
            SharedVars.score -= 5;
        area.setShowPath(true);
        area.clearPath();
        area.repaint();
    }
}
