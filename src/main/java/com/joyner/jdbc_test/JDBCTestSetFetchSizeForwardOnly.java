package com.joyner.jdbc_test;

import com.joyner.jdbc_test.util.DBUtil;
import com.joyner.jdbc_test.util.ThreadUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 在PreparedStatement的resultSetType设置为ResultSet.TYPE_FORWARD_ONLY的情况下，测试SetFetchSize是否还可以一批一批的去读取数据，运行该程序<br>
 *     补充：设置ResultSet.TYPE_FORWARD_ONLY，可以节省内存，参考文章：https://blog.csdn.net/10km/article/details/50404694
 * 1.在部署oracle的机器上，抓TCP包，指定来源机器
 *  tcpdump -s0 -n -i eno1 'tcp port 1521 and src 10.10.15.45'
 * 2.运行该程序，观察数据包发送情况
 *
 * 结论：不可以，设置SetFetchSize无效果，每next就要去查询数据库，会每次都去请求数据库，
 */
public class JDBCTestSetFetchSizeForwardOnly {


    public static void main(String[] args) throws Exception {
        Connection con = DBUtil.getConnection();
        try {
            String sql = "select *  from test t where rownum < 1000000";
            //设置为TYPE_FORWARD_ONLY
            PreparedStatement stmt = con.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            //每次取20行记录
            int fetchSize = 5000;
            stmt.setFetchSize(fetchSize);
            int row_num = 0;
            ResultSet rs = stmt.executeQuery();
            //休息10s钟，观察抓包的情况。
            ThreadUtil.sleepSecond(10);
            System.out.println("休息结束");
            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("NAME");
                row_num++;
                System.out.println("row_num-->" + row_num + ",name:" + name);
                if (row_num >= fetchSize) {
                    row_num = 0;
                }
                ThreadUtil.sleepSecond(5);//休息5秒，方便查看抓包数据
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            con.close();
        }


    }

}
