package com.songe.linkgame.utils;

import java.util.LinkedList;

import com.songe.linkgame.shared.SharedVars;
import com.songe.linkgame.shared.SharedVars.*;

public class Checker {

    private static int[][] arr;
    //path
    private static int width;
    private static int height;
    private static int numOfShape;
    private static Mode md;

    private static int[] path = new int[8];
    private static int[] tip = new int[4];
    //步骤较少，这里就用一维数组表示路径了


    public Checker(int[][] arr)
    {
        this.arr = arr;
    }
    //把这个问题分解成几个小问题
    public Checker(int width, int height, int numOfShape, Mode md)
    {
        //这里有个大坑。
        arr = new int[height+2][width+2];
        this.width = width;
        this.height = height;
        this.numOfShape = numOfShape;
        this.md = md;
    }




    public static int[][] newGame()
    {
        do {
            //新游戏的初始化。
            //诶 DrawHelper中有初始化的办法呀
            int len = height*width;
            int[] initial = new int[len];
            int[] random = new int[len];
            for (int i=0; i<len ; i +=2)
            {
                //初始化
                initial[i] =(int)(Math.random()*numOfShape)+1;
                initial[i+1] = initial[i];
            }
            //接下来打乱。
            for (int index = 0, lastIndex = len-1; index<len && lastIndex>0; index++,lastIndex--)
            {
                int rand = (int) (Math.random() * (lastIndex + 1));
                random[index] = initial[rand];
                initial[rand] = initial[lastIndex];
            }
            for (int i = 0; i < len; i++) {
                int y = i % height + 1;
                int x = i / width + 1;
                arr[x][y] = random[i];
                //把随机的结果赋给arr。
            }
        }while (!solvable());
        return arr;
    }

    public static int[][] reset()
    {
//        Queue<Integer> imageQue = new LinkedList<>();
        LinkedList<Integer> imageQue = new LinkedList<>();
        do {
            //按照现有的情况，对图片重新进行排序.
            //两次遍历全体，
            for (int i = 1; i < arr.length - 1; i++)
            {
                for (int j = 1; j < arr[0].length - 1; j++)
                {
                    if (arr[i][j] != 0)
                        imageQue.add(arr[i][j]);
                }
            }
            int queLen = imageQue.size();
            for (int i = 1; i< arr.length -1; i++)
            {
                for (int j = 1; j<arr[0].length -1 ;j++)
                {
                    if (arr[i][j] != 0)
                    {
                        //这里可能有坑
                        int index = (int) (Math.random() * queLen);
                        arr[i][j] = imageQue.get(index);
                        imageQue.remove(index);
                        queLen--;
                    }
                }
            }

        }while(!solvable());
        return arr;
    }

    public static int[] getPath()
    {
        return path;
    }

    public static int[][] getArr() {
        return arr;
    }
    public static int[] getTip()
    {
        return tip;
    }

    public static void setArr(int[][] arr) {
        Checker.arr = arr;
    }

    public static boolean solvable()
    {
        //这个方法还有很大的改进空间
        //算法可以再优化一下，比如 寻找相同的图像然后再判断。
        for (int i = 0; i < arr.length * arr[0].length; i++) {
            for (int j = 0; j < arr.length * arr[0].length; j++) {
                int x1 = i / arr[0].length;
                int y1 = i % arr[0].length;
                int x2 = j / arr[0].length;
                int y2 = j % arr[0].length;
                if (removable(x1, y1, x2, y2))
                {
                    tip[0] = x1;
                    tip[1] = y1;
                    tip[2] = x2;
                    tip[3] = y2;
                    return true;
                }
            }
        }
        return false;
    }


    public static boolean removable(int x1, int y1, int x2, int y2)
    {
        //判断这两点之间是否可以连线成功。
        if (x1==x2 && y1==y2)
            return false;
        if (arr[x1][y1] != arr[x2][y2] || (arr[x1][y1]+arr[x2][y2]==0))
            return false;
        return turnZero(x1, y1, x2, y2) || turnOnce(x1, y1, x2, y2) || turnTwice(x1, y1, x2, y2);
    }

    //可以写一个 自动移动的算法。

    private static boolean hasWay(int x1, int y1, int x2, int y2)
    {
        if (x1 == x2)
        {
            int max = (y1 > y2) ? y1 : y2;
            int min = (y1 > y2) ? y2 : y1;
            for (int y = min + 1; y < max; y++)
            {
                //检查这条路上有没有大于零的。
                //其实也可以累加，判断是不是大于零。这样能更快一些吧。
                if (arr[x1][y] > 0)
                {
                    return false;
                }
            }
            return true;
        }
        //这个和上面就类似了
        else if (y1 == y2)
        {
            int max = (x1 > x2) ? x1 : x2;
            int min = (x1 > x2) ? x2 : x1;
            for (int x = min + 1; x < max; x++)
            {
                if (arr[x][y1] > 0)
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }



    //按照不转，转一次，两次来判断

    private static boolean turnZero (int x1, int y1, int x2, int y2)
    {
        if (arr[x1][y1] == arr[x2][y2] && hasWay(x1, y1, x2, y2))
        {
            //判断是同一个形状，而且中间有直接到达的路线。
            SharedVars.turnCount = 0;
            path[0] = x1;
            path[1] = y1;
            path[2] = x2;
            path[3] = y2;
            return true;
        }
        return false;
    }

    private static boolean turnOnce (int x1, int y1, int x2, int y2)
    {
        //转一次的情况。
        //只可能是直角弯，所以判断一下直角弯的路径可不可以
        //还要确认一下直角弯的位置。
        if (arr[x1][y2] == 0)
        {
            if (hasWay(x1,y1,x1,y2) && hasWay(x1,y2,x2,y2))
            {
                SharedVars.turnCount = 1;
                path[0] = x1;
                path[1] = y1;
                path[2] = x1;
                path[3] = y2;
                path[4] = x2;
                path[5] = y2;
                return true;
            }
        }
        if (arr[x2][y1] == 0)
        {
            if (hasWay(x1,y1,x2,y1) && hasWay(x2,y1,x2,y2))
            {
                SharedVars.turnCount = 1;
                path[0] = x1;
                path[1] = y1;
                path[2] = x2;
                path[3] = y1;
                path[4] = x2;
                path[5] = y2;
                return true;

            }
        }
        return false;
    }

    private static boolean turnTwice(int x1, int y1, int x2, int y2)
    {
        //转两个弯
        //这里其实也可以调用turnOnce来处理。
        for (int middle = 0; middle < arr.length; middle++)
        {
            //从x轴选一个点进行转弯。
            if (arr[middle][y1]==0 && arr[middle][y2]==0)
            {
                if (hasWay(x1,y1,middle,y1) && hasWay(middle,y1,middle,y2)&& hasWay(middle,y2,x2,y2))
                {
                    SharedVars.turnCount = 2;
                    path[0] = x1;
                    path[1] = y1;
                    path[2] = middle;
                    path[3] = y1;
                    path[4] = middle;
                    path[5] = y2;
                    path[6] = x2;
                    path[7] = y2;
                    return true;
                }
            }
            //从y轴选一个点
            if (arr[x1][middle] == 0 && arr[x2][middle] == 0)
            {
                //判断两个节点
                if (hasWay(x1,y1,x1,middle) && hasWay(x1,middle,x2,middle)&& hasWay(x2,middle,x2,y2))
                {
                    SharedVars.turnCount = 2;
                    path[0] = x1;
                    path[1] = y1;
                    path[2] = x1;
                    path[3] = middle;
                    path[4] = x2;
                    path[5] = middle;
                    path[6] = x2;
                    path[7] = y2;
                    return true;
                }
            }
        }
        return false;
    }

}
