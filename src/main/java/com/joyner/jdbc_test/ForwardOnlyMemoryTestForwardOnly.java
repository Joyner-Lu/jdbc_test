package com.joyner.jdbc_test;

import com.joyner.jdbc_test.util.DBUtil;
import com.joyner.jdbc_test.util.JVMMemoryUtil;
import com.joyner.jdbc_test.util.ThreadUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 设置Java的对内存为-Xms512M -Xmx512M,PreparedStatement的resultSetType设置为TYPE_FORWARD_ONLY
 * 运行该程序，观察内存的变化。
 * 取100万数据，每次取5000
 *
 * ResultSet.TYPE_FORWARD_ONLY使用setFetchSize无用，查询都是非常的满。(另参见：ForwardOnlyMemoryTestSensitive)
 *
 * 结论：内存基本无变化，和文章的结论一致：https://blog.csdn.net/10km/article/details/50404694
 */
public class ForwardOnlyMemoryTestForwardOnly {

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
            System.out.println("休息结束");
            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("NAME");
                row_num++;
                //System.out.println("row_num-->" + row_num + ",name:" + name);
                if (row_num >= fetchSize) {
                    JVMMemoryUtil.pritnMemoryInfo();
                    row_num = 0;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            con.close();
        }


    }


}
