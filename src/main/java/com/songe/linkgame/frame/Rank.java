package com.songe.linkgame.frame;

import com.songe.linkgame.utils.MySQL;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.security.KeyStore;

public class Rank
{
    //以下是一个简单的单例模式。
    //不过，显然不是线程安全的。
    /*
    private Rank() {}

    private static Rank rank;

    public static Rank getInstance() {
        if (rank == null)
            rank = new Rank();
        return rank;
    }*/
    //下面是线程安全的单例模式。
    private Rank() {}
    private volatile static Rank instance = null;

    public static Rank getInstance()
    {
        if (instance == null)
        {
            synchronized (Rank.class)
            {
                if  (instance == null)
                    instance = new Rank();
            }
        }
        return instance;
    }


    public void show() {
        RankDialog rankDialog = new RankDialog();
        rankDialog.showTable();
    }
    public boolean insertItem(String name, int score)
    {
        RankDialog rankDialog = new RankDialog();
        return rankDialog.insertItem(name,score);
    }

    class RankDialog extends JDialog
    {

        private JTable table;
        private Object[] tittle = {"序号", "名字", "得分"};
        private Object[][] rankList;
        private JLabel titleLabel;
        private JPanel topPanel;
        private JPanel centerPanel;
        private boolean isInsert;


        public RankDialog()
        {
            //父子窗口
            setModal(true);
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setTitle("松鹅的连连看排行榜");


            //设置位置。
            Toolkit kit = Toolkit.getDefaultToolkit();
            Dimension scrSize = kit.getScreenSize();
            setSize(scrSize.width / 2, scrSize.height / 2);
            setLocation(scrSize.width / 4, scrSize.height / 4);
            setResizable(false);

            topPanel = new JPanel();
            centerPanel = new JPanel();
            titleLabel = new JLabel("排行榜");
            titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            titleLabel.setFont(new Font("华文行楷", 0, 32));
            topPanel.add(titleLabel);

            //测试
//            getRank();
//            table = new JTable(rankList, tittle);
//            centerPanel.add(new JScrollPane(table));



            setLayout(new BorderLayout());
            add(topPanel, BorderLayout.NORTH);
            add(centerPanel, BorderLayout.CENTER);

        }
        //这个地方再考虑一下，要不要重写。
        public void showTable()
        {
            rankList = getRank();//其实这一步赋值无所谓hh
            table = new JTable(rankList, tittle);
            centerPanel.removeAll();
            centerPanel.add(new JScrollPane(table));
            centerPanel.setLayout(new BorderLayout());
            centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);
            DefaultTableCellRenderer r = new DefaultTableCellRenderer();
            r.setHorizontalAlignment(JLabel.CENTER);
            table.setDefaultRenderer(Object.class, r);
            table.setRowHeight(20);
            table.setFont(new Font("微软雅黑", 0, 14));
            table.setGridColor(Color.BLACK);
            table.setSize(centerPanel.getWidth(), centerPanel.getHeight());
            table.setEnabled(false);
            setVisible(true);

        }

        public Object[][] getRank()
        {
            MySQL getResult = new MySQL();
            getResult.connectDataBase();
            rankList = getResult.getAllItem();
            getResult.closeDataBase();
            return rankList;
        }

        public boolean insertItem(String name, int score)
        {
            MySQL getResult = new MySQL();
            getResult.connectDataBase();
            isInsert = getResult.insertNewItem(name, score);
            getResult.closeDataBase();
            return isInsert;
        }

    }

    public static void main(String[] arg0)
    {
        Rank test = new Rank();
        test.show();
    }

}
