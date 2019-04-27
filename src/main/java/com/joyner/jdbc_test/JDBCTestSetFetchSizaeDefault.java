package com.joyner.jdbc_test;

import com.joyner.jdbc_test.util.DBUtil;
import com.joyner.jdbc_test.util.ThreadUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *  PreparedStatement object will by default be type TYPE_FORWARD_ONLY and have a concurrency level of CONCUR_READ_ONLY
 */
public class JDBCTestSetFetchSizaeDefault {


    public static void main(String[] args) throws Exception {
        Connection con = DBUtil.getConnection();
        try {
            String sql = "select *  from test t where rownum < 1000000";
            //设置为TYPE_FORWARD_ONLY
            PreparedStatement stmt = con.prepareStatement(sql);
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
