package com.joyner.jdbc_test;

import com.joyner.jdbc_test.util.DBUtil;
import com.joyner.jdbc_test.util.JVMMemoryUtil;
import com.joyner.jdbc_test.util.ThreadUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 设置Java的对内存为-Xms512M -Xmx512M,PreparedStatement的resultSetType设置为TYPE_SCROLL_INSENSITIVE
 * 运行该程序，观察内存的变化。
 * 取10000000万条数据，每次取5000,
 * 实验结果：
 * （1）如果fetchSize设置过大，则会内存溢出。否则也不会，例如设置1万
 * （2）还有就是如果不设置setFetchSize的话，则查询非常的慢。
 *
 * 结论：没多少变化,和文章说的不一致：https://blog.csdn.net/10km/article/details/50404694
 */
public class ForwardOnlyMemoryTestInSensitive {

    public static void main(String[] args) throws Exception {
        Connection con = DBUtil.getConnection();
        try {
            String sql = "select *  from test t where rownum <= 1000000";
            //设置为TYPE_FORWARD_ONLY
            PreparedStatement stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //每次取20行记录
            int fetchSize = 5000;
            stmt.setFetchSize(fetchSize);
            int row_num = 0;
            ResultSet rs = stmt.executeQuery();
            ThreadUtil.sleepSecond(1);
            System.out.println("休息结束");
            int sum = 0;
            while (rs.next()) {
                sum ++;
                String id = rs.getString("ID");
                String name = rs.getString("NAME");
                row_num++;
                //System.out.println("row_num-->" + row_num + ",name:" + name);
                if (row_num >= fetchSize) {
                    JVMMemoryUtil.pritnMemoryInfo();
                    row_num = 0;
                }
            }
            System.out.println("总数：" + sum);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            con.close();
        }


    }


}
