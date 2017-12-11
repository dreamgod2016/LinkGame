package com.songe.linkgame.utils;

import javax.swing.*;
import java.awt.*;

public class ImagesFactory {

    //存储所有图片的单元
    private static Image[] images = new Image[100];


    public static Image getImage(int index)
    {
        if(index<0)
        {
            //小于0的情况。
            //待处理
            return null;
        }
        if (images[index] == null)
        {
//            ClassLoader classloader =
            ImageIcon icon = null;
            try {
//                System.out.println(ImagesFactory.class.getResource("/images/" + index + ".png"));
                icon = new ImageIcon(ImagesFactory.class.getResource("/images/" + index + ".png"));
            }
            catch (NullPointerException e )
            {
                e.printStackTrace();
                System.out.println(index);
                JOptionPane.showMessageDialog(null,"Miss something!\n");
                return null;
            }
            images[index] = icon.getImage();
        }
        return images[index];
    }
}
