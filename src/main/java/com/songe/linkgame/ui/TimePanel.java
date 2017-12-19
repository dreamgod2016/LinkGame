package com.songe.linkgame.ui;

import com.songe.linkgame.shared.SharedVars;
import com.songe.linkgame.utils.DrawHelper;
import com.songe.linkgame.utils.ImagesFactory;

import javax.swing.*;
import java.awt.*;

import static com.songe.linkgame.shared.SharedVars.*;

public class TimePanel extends JPanel
{
    private int life_length = 500;
    private Image lifeSpare;
    private Image lifeUse;
    private Image time_Image;
    private Image score_Image;
    private Font font = new Font("Times New Roman", Font.BOLD, 20);
    private TimeThread timeThread;
    private DrawArea area;

    public TimePanel()
    {
        initImage();
        timeThread = new TimeThread();
    }
    public void start() {
        new Thread(timeThread).start();
        SharedVars.isStart = true;
    }

    public void begin() {
        SharedVars.isPause = false;
        timeThread.begin();
    }

    public void pause() {
        SharedVars.isPause = true;
    }

    private void initImage() {
        lifeSpare = ImagesFactory.getImage(LIFETIME_IMAGE);
        lifeUse = ImagesFactory.getImage(LIFEUSE_IMAGE);
        time_Image = ImagesFactory.getImage(TIME_LABEL_IMAGE);
        score_Image = ImagesFactory.getImage(SCORE_LABEL_IMAGE);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        DrawHelper.drawBackGround(g,this,SMALL_BACKGROUND_NUM);
        drawLife(g);
    }

    public DrawArea getArea() {
        return area;
    }

    public void setArea(DrawArea area) {
        this.area = area;
    }

    //时间线
    private void drawLife(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(font);
        g2d.drawImage(time_Image, 0, 4, 100, 30, null);
        g2d.drawImage(lifeSpare, 100, 10, life_length, 18, null);
        g2d.drawImage(lifeUse, 98 + SharedVars.time, 13, life_length - SharedVars.time - 1, 14, null);
        g2d.drawImage(score_Image, 150 + life_length, 4, 100, 30, null);
        g2d.setColor(Color.WHITE);
        g2d.drawString(SharedVars.score + "", 250 + life_length, 25);
    }
    public static void initTime() {

        switch (SharedVars.game_stage) {
            case SharedVars.GAME_EASY:
                SharedVars.time = 500;
                SharedVars.sleep_time = 200;
                break;
            case SharedVars.GAME_GENERAL:
                SharedVars.time = 500;
                SharedVars.sleep_time = 270;
                break;
            default:
                SharedVars.time = 500;
                SharedVars.sleep_time = 400;
        }

    }




    //TimeThread
    private class TimeThread implements Runnable
    {
        public synchronized void pause()
        {
            //暂停。
            try{
                wait();
            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        public synchronized void begin()
        {
            notify();
        }

        public void run()
        {
            while (true)
            {
                while (SharedVars.time > 10)
                {
                    if (SharedVars.isPause)
                        pause();
                    SharedVars.time-=1;
                    if (SharedVars.time < 100)
                        lifeSpare = ImagesFactory.getImage(TIME_OUT_IMAGE);
                    else
                        lifeSpare = ImagesFactory.getImage(SharedVars.LIFETIME_IMAGE);
                    repaint();
                    try{
                        Thread.sleep(SharedVars.sleep_time);
                    }catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                lose();
            }
        }
        public void lose()
        {
            int state = JOptionPane.showConfirmDialog(null, "You lose.\nrestart?","Lose", JOptionPane.OK_CANCEL_OPTION);
            if (state == JOptionPane.OK_OPTION)
                area.restart();
            else
                area.back();
        }
    }

}
