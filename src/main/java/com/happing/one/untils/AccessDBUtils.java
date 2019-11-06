package com.happing.one.untils;

import java.sql.*;
public class AccessDBUtils {
    private static final String dbURL = "jdbc:ucanaccess://" +
            "D:\\MD201910014.accdb";
    /*
     * 加载驱动
     */
    static {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        } catch (ClassNotFoundException cnfex) {
            System.out.println("Problem in loading or registering MS Access JDBC driver");
            cnfex.printStackTrace();
        }
    }

    //建立连接
    public static Connection getConn() {
        try {
            return DriverManager.getConnection(dbURL);
        } catch (Exception e) {
            System.out.println("AccessDB connection fail");
            e.printStackTrace();
        }
        return null;
    }

    // 关闭资源
    public static void close(Connection con, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null)
                rs.close();// 这里出现异常了，rs关闭了吗？，如果没有怎么解决,ps , con也是一样的。
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (con != null)
                    try {
                        con.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(getConn());
        System.out.println("链接成功");
    }
}
