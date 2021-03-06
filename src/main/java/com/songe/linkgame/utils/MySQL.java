package com.songe.linkgame.utils;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MySQL {

    // JDBC 驱动名及数据库 URL
    static final String DB_URL = "jdbc:mysql://localhost:3306/linkgame";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "puzzle";
    static final String PASS = "puzzledatabase";

    private Connection conn = null;
    private Statement stmt = null;

    public boolean connectDataBase()
    {
        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");

            // 打开链接
            System.out.println("连接数据库...");
//            String PASSWORD = SHA(PASS, "SHA-256");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            return true;
        }
        catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
            return false;
        }
    }

    public Object[][] getAllItem()
    {
        Object[][] getResult;
        if (!connectDataBase())
        {
            getResult = null;
            return getResult;
        }
        try
        {
            stmt = conn.createStatement();
            int numberRow = 0;
            String count,sql;
            count = "select count(*) from rank";
            ResultSet countRs = stmt.executeQuery(count);
            while (countRs.next())
            {
                numberRow = countRs.getInt("count(*)");
            }
            sql = "SELECT id, name, score FROM rank ORDER BY -score";
            ResultSet rs = stmt.executeQuery(sql);
            getResult = new String[numberRow][3];
            int rank = 0;
            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
//                getResult[rank][0]= Integer.toString( rs.getInt("id"));
                getResult[rank][0] = rank+1;
                getResult[rank][1] = rs.getString("name");
                getResult[rank][2]= Integer.toString(rs.getInt("score"));
                rank += 1;
            }
            rs.close();
            return getResult;
        }catch (SQLException sqlError)
        {
            sqlError.printStackTrace();
            getResult = null;
            return getResult;
        }
    }

    public boolean insertNewItem(String name, int score)
    {
        if (!connectDataBase())
            return false;
        try
        {
            stmt = conn.createStatement();
            String sql;
            //这种SQL拼接的很危险啊。。。
            sql = String.format("INSERT INTO rank VALUES (0, %S, %S);","\'"+name+"\'","\'"+score+"\'");
            stmt.executeUpdate(sql);
            return true;
        }catch (SQLException sqlError)
        {
            sqlError.printStackTrace();
            return false;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean closeDataBase()
    {
        //最后一定要关闭数据库
        try
        {
            if(stmt!=null) stmt.close();
            if (conn!=null) conn.close();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    //留这个是因为可能要用SHA-256加密的密码。
    static private String SHA (final String strText, final String strType)
    {
        String strResult = null;
        // 是否是有效字符串
        if (strText != null && strText.length() > 0)
        {
            try
            {
                // SHA 加密开始
                // 创建加密对象 并傳入加密類型
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                // 传入要加密的字符串
                messageDigest.update(strText.getBytes());
                // 得到 byte 類型结果
                byte byteBuffer[] = messageDigest.digest();

                // 將 byte 轉換爲 string
                StringBuffer strHexString = new StringBuffer();
                // 遍歷 byte buffer
                for (int i = 0; i < byteBuffer.length; i++)
                {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1)
                    {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回結果
                strResult = strHexString.toString();
            }
            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }
        }

        return strResult;
    }

    //用来测试的函数。
    public static void main(String[] arg0)
    {
        Object[][] result;
        MySQL getResult = new MySQL();
        getResult.connectDataBase();
        result = getResult.getAllItem();
        System.out.println(Arrays.deepToString(result));
        getResult.closeDataBase();
    }

}