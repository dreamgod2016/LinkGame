package com.songe.linkgame.utils;

import com.songe.linkgame.shared.SharedVars;

import javax.swing.*;
import java.awt.*;

import static com.songe.linkgame.shared.SharedVars.*;

public class DrawHelper {

    private DrawHelper(){}

    public static void drawNodes(int[][] nodes, Graphics g)
    {
        //根据nodes来绘图
        for (int i = 0; i < nodes.length; i++)
        {
            //应该是一个正方形的，nodes就用0了
            for (int j = 0; j < nodes[0].length; j++)
            {
                if (nodes[i][j]!= 0)
                    g.drawImage(ImagesFactory.getImage(nodes[i][j]), getRealY(j), getRealX(i), SharedVars.NODE_WIDTH, SharedVars.NODE_HEIGHT, null);
            }
        }
    }

    public static void drawSelectRect(int[][] nodes, Point point, Graphics g)
    {
        //选中的状态
        if (point == null || nodes == null)
            return;
        int i = getI(point.y);
        int j = getJ(point.x);
        if (i < 1 || j < 1 || i > NODES_ROW - 2 || j > NODES_COLUMN - 2
                || nodes[i][j] < 1 || nodes[i][j] > 20)
            return;
        g.drawImage(ImagesFactory.getImage(SELECT_IMAGE), getRealY(j), getRealX(i), NODE_WIDTH,
                NODE_HEIGHT, null);

    }

    public static void drawMoveRect(int[][] nodes, Point point, Graphics g)
    {
        //鼠标移动进来的状态
    }

    public static void drawLine(int[][] nodes, int[] nums, Graphics g)
    {
        //画路径的
        if (nums == null || nodes == null) {
            return;
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.red);
        g2d.setStroke(new BasicStroke(5));
        for (int i = 0; i <= SharedVars.turnCount * 2; i += 2) {
            g2d.drawLine(getCenterX(nums[i+1]), getCenterY(nums[i]), getCenterX(nums[i + 3]),
                    getCenterY(nums[i + 2]));
        }
    }


    public static int[] imageIDArray()
    {
        //两个同时产生，就分别放在第一个和最后一个
        int[] images = new int[SharedVars.NODES_SUM];
        int[] random = new int[SharedVars.NODES_SUM];
        for (int index = 0, imageID = 1; index < images.length ; index ++)
        {
            images[index] = imageID;
            images[images.length - index - 1] = imageID;
            imageID++;
            //如果超出了范围，就重头开始分配。
            if(imageID >= SharedVars.IMAGES_SUM + 1)
                imageID = 1;
        }
        //多一个数组，来进行随机排序，抽取一个到新的，再把旧的补到前面去
        for (int index = 0, lastIndex = images.length-1; index<images.length && lastIndex>0; index++,lastIndex--)
        {
            int rand = (int) (Math.random() * (lastIndex + 1));
            random[index] = images[rand];
            images[rand] = images[lastIndex];
            //把最后一个移动到前面的坑中，这样就不会重复了。
        }
        return random;
    }

    public static void drawBackGround(Graphics g, JPanel panel, int index)
    {
        g.drawImage(ImagesFactory.getImage(index), 0, 0, panel.getWidth(), panel.getHeight(), null);
    }

    public static int[][] getNodes() {
        switch (SharedVars.game_stage) {
            case SharedVars.GAME_EASY:
                Checker easyCheck = new Checker(SharedVars.NODES_COLUMN - 2, SharedVars.NODES_ROW - 2,
                        SharedVars.TOTAL_IMAGES - 8, Mode.classic);
                return easyCheck.newGame();
            case SharedVars.GAME_GENERAL:
                Checker generalCheck = new Checker(SharedVars.NODES_COLUMN - 2, SharedVars.NODES_ROW - 2,
                        SharedVars.TOTAL_IMAGES - 8, Mode.classic);
                return generalCheck.newGame();
            default:
                break;
        }
        return null;
    }
    public static boolean isWin(int[][] nodes) {
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[0].length; j++) {
                if (nodes[i][j] > 0 && nodes[i][j] < 21)
                    return false;
            }
        }
        return true;
    }


    public static int getRealY(int j)
    {
        return j*SharedVars.NODE_WIDTH;
    }
    public static int getRealX(int i)
    {
        return i*SharedVars.NODE_HEIGHT;
    }
    public static int getI(int y)
    {
        return y/SharedVars.NODE_WIDTH;
    }
    public static int getJ(int x)
    {
        return x/SharedVars.NODE_HEIGHT;
    }
    public static int getCenterX(int j)
    {
        return j * SharedVars.NODE_WIDTH + SharedVars.NODE_WIDTH / 2;
    }
    public static int getCenterY(int i)
    {
        return i * SharedVars.NODE_HEIGHT + SharedVars.NODE_HEIGHT / 2;
    }
}
