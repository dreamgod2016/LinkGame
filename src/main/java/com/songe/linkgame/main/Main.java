package com.songe.linkgame.main;


import com.songe.linkgame.frame.MenuFrame;
import com.songe.linkgame.shared.SharedVars;

public class Main {

    public static void main(String[] arg0)
    {
        //开始；
        initSetting();
        MenuFrame.open();
    }


    public static void initSetting()
    {
        SharedVars.game_stage = SharedVars.GAME_EASY;
//        SharedVars.game_mode = SharedVars.MODE_SINGLE;

    }
}
