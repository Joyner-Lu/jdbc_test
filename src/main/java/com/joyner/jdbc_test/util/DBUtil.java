package com.joyner.jdbc_test.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

    public static Connection getConnection() throws Exception {
        //select count(*) from v$process; 连接
        Class.forName("oracle.jdbc.driver.OracleDriver");
        String url = "jdbc:oracle:thin:@10.10.15.76:1521:XE";
        String user = "sys AS SYSDBA";
        String password = "oracle";
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }
}
