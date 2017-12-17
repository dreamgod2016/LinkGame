package com.songe.linkgame.shared;

//import com.songe.linkgame.ui.DrawArea;
import com.songe.linkgame.ui.*;


public class SharedVars {
    //一些公用的变量。
    public static int time = 500;
    public static int sleep_time = 500;

    public static int score = 0;
    public static int turnCount = 0;

    public static final int GAME_EASY = 20;
    public static final int GAME_GENERAL = 21;

    public static boolean draw_able = true;
    public static boolean isPause;
    public static boolean isStart;
    public static int game_stage = GAME_EASY;


    //游戏进行的一些量
    /** 节点的宽 */
    public static final int NODE_WIDTH = 50;
    /** 节点的高 */
    public static final int NODE_HEIGHT = 50;
    /**行数*/
    public static final int NODES_ROW = 10;
    /** 列数 */
    public static final int NODES_COLUMN = 18;

    public static final int TOTAL_IMAGES = 20;

    public static final int CURSOR_MOVE_IN = 30;
    public static final int NODE_SELECT = 31;

    public static final int NODES_SUM = (NODES_COLUMN-2) * (NODES_ROW-2);
    public static final int IMAGES_SUM = 20;

    public static final int BACKGROUND_NUM = 67;
    public static final int MENU_BACKGROUND_NUM = 52;
    public static final int SMALL_BACKGROUND_NUM = 66;

    public static final int START_IMAGE_RUN = 43;
    public static final int MENU_START_BUTTON_RUN = 53;
    public static final int MENU_RANK_BUTTON_RUN = 55;
    public static final int MENU_ABOUT_BUTTON_RUN = 57;
    public static final int MENU_MODE_BUTTON_RUN = 54;
    public static final int MENU_SORT_IMAGE_RUN = 55;
//    public static final int START_IMAGE_RUN = 43;

    public static final int START_IMAGE = 41;
    public static final int REFRESH_IMAGE = 63;
    public static final int TIP_IMAGE = 45;
    public static final int MENU_IMAGE = 47;
    public static final int RESET_IMAGE = 49;
    public static final int TIME_OUT_IMAGE = 38;

    public static final int LIFEUSE_IMAGE = 36;
    public static final int LIFETIME_IMAGE = 37;
    public static final int TIME_LABEL_IMAGE = 51;
    public static final int SCORE_LABEL_IMAGE = 50;
    public static final int SELECT_IMAGE = 39;
    public static final int BOMB_BEGIN_IMAGE = 21;
    public static final int BOMB_END_IMAGE = 35;




    public static TimePanel timePanel = new TimePanel();
    public static DrawArea area = new DrawArea();

}
