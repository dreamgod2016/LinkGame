package com.songe.linkgame.ui;

import com.songe.linkgame.frame.MenuFrame;
import com.songe.linkgame.frame.SingleGameFrame;
import com.songe.linkgame.shared.SharedVars;
import com.songe.linkgame.utils.Checker;
import com.songe.linkgame.utils.DrawHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static com.songe.linkgame.shared.SharedVars.*;

public class DrawArea extends JPanel implements MouseMotionListener,MouseListener{


    private int[][] nodes;
    private int[] paths;
    private boolean isShowPath;
    private Point currentMovePos = new Point(0, 0);
    private Point lastPos = new Point(0, 0);
    private Point currentPos = new Point(0, 0);
    private static TimePanel time;


    public DrawArea()
    {
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public DrawArea(int[][] nodes)
    {
        this();
        this.nodes = nodes;
    }
    @Override
    public void paint (Graphics g)
    {
        super.paint(g);
        //背景图片
        DrawHelper.drawBackGround(g,this, SharedVars.BACKGROUND_NUM);
        if (SharedVars.draw_able)
        {
            DrawHelper.drawNodes(nodes, g);
            DrawHelper.drawSelectRect(nodes, currentPos, g);
            DrawHelper.drawMoveRect(nodes, currentMovePos, g);
            if (isShowPath) {
                DrawHelper.drawLine(nodes, paths, g);
            }
        }
    }

    public TimePanel getTime() {
        return time;
    }
    public void setTime(TimePanel time)
    {
        this.time = time;
    }

    public boolean isShowPath() {
        return isShowPath;
    }

    public void setShowPath(boolean showPath) {
        isShowPath = showPath;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if (SharedVars.draw_able)
        {
            int i = DrawHelper.getI(e.getY());
            int j = DrawHelper.getJ(e.getX());
            if (i >= 1 && j >= 1 && i <= SharedVars.NODES_ROW - 2 && j <= SharedVars.NODES_COLUMN - 2
                    && nodes[i][j] > 0 && nodes[i][j] < SharedVars.IMAGES_SUM+1)
            //判断是不是点在了正确的位置，而且这个地方还有图。
            {
                //按下去的时候存之前的位置。
                lastPos.setLocation(currentPos);

            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        if (SharedVars.draw_able)
        {
            int i1 = DrawHelper.getI(e.getY());
            int j1 = DrawHelper.getJ(e.getX());
            int i2 = 0, j2 = 0;
            Checker newChecker = new Checker(nodes);
            if (lastPos!=null)
            {
                i2 = DrawHelper.getI((int) lastPos.getY());
                j2 = DrawHelper.getJ((int) lastPos.getX());
            }
            if (i1 >= 1 && j1 >= 1 && i1 <= SharedVars.NODES_ROW - 2
                    && j1 <= SharedVars.NODES_COLUMN - 2 && nodes[i1][j1] > 0 && nodes[i1][j1]<SharedVars.IMAGES_SUM+1)
            {
                //点到方块了
                currentPos.setLocation(e.getX(), e.getY());
                if((i1!=i2 || j1!= j2) && newChecker.removable(i1,j1,i2,j2))
                {
                    //这里需要判断一下是否可以消除，如果可以消除的话
                    paths = Checker.getPath();
                    for (int i = 0; i < paths.length; i++) {
                        System.out.print(paths[i] + "<>");
                    }
                    System.out.println();
                    if (SharedVars.time < 490)
                        SharedVars.time += 10;
                    SharedVars.score += 5;
                    isShowPath = true;
                    nodes[i1][j1] = 0;
                    nodes[i2][j2] = 0;
                    lastPos.setLocation(0, 0);
                    currentPos.setLocation(0, 0);

                    showPathAndBomb(true, i1, j1, i2, j2);
                }
            }
            repaint();
        }

    }

    public void restart()
    {
        TimePanel.initTime();
        SharedVars.score = 0;
        SharedVars.draw_able = true;
        nodes = DrawHelper.getNodes();
        time.begin();
        repaint();
    }


    public void clearPath() {
        new RePaintThread().start();
    }
    public static void back() {
        SharedVars.draw_able = true;
        TimePanel.initTime();
        SharedVars.score = 0;
        time.pause();
        SingleGameFrame.close();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MenuFrame.open();
    }
    public void showPathAndBomb(boolean isBomb, int i1, int j1, int i2, int j2) {
        System.out.println("执行了吗");
        new RePaintThread(isBomb, i1, j1, i2, j2).start();
    }


    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public int[][] getNodes() {
        return nodes;
    }

    public void setNodes(int[][] nodes) {
        this.nodes = nodes;
    }

    public int[] getPaths() {
        return paths;
    }

    public void setPaths(int[] paths) {
        this.paths = paths;
    }

    private class RePaintThread extends Thread
    {
        //多线程的第二种写法了
        private boolean isBomb;
        private int i1;
        private int i2;
        private int j1;
        private int j2;
        public RePaintThread(){}
        public RePaintThread(boolean isBomb, int i1, int j1, int i2, int j2) {
            this.isBomb = isBomb;
            this.i1 = i1;
            this.i2 = i2;
            this.j1 = j1;
            this.j2 = j2;
        }

        public void run()
        {
            boolean isWin = DrawHelper.isWin(nodes);
            try{
                Thread.sleep(150);
            }catch (Exception e)
            {
                e.printStackTrace();
            }

            if (!isWin && !Checker.solvable())
                nodes = new Checker(nodes).reset();
            if (isWin)
                SharedVars.isPause = true;
            repaint();
            if (isBomb)
            {
                for (int i = BOMB_BEGIN_IMAGE; i<=BOMB_END_IMAGE;i++)
                {
                    nodes[i1][j1] = i;
                    nodes[i2][j2] = i;
                    repaint();
                    try{
                        Thread.sleep(40);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                nodes[i1][j1] = 0;
                nodes[i2][j2] = 0;
            }
            if (isWin) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                SharedVars.isPause = true;
                for (int i = SharedVars.time; i >= 10; i--) {
                    //每秒三分
                    SharedVars.score += 3;
                    SharedVars.time--;
                    System.out.println(i);
                    time.repaint();
                    try {
                        //这里为了动画效果，各种暂停
                        Thread.sleep(3);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //这里还有一个排行的模块没写。
//                Ranking.getInstance().store(getLevel(), SharedVar.score);
                int state = JOptionPane.showConfirmDialog(null, "恭喜你取得胜利，本次得分为" + SharedVars.score
                        + "\n          开始新游戏？", "胜利", JOptionPane.OK_CANCEL_OPTION);
                if (state == JOptionPane.OK_OPTION)
                    restart();
                else
                    back();
            }


        }

    }



}
