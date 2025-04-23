package com.xiaomi.customer.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestDatabaseConnection {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 替换为你的数据库驱动
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/logindemo?useSSL=false&serverTimezone=UTC",
                "root",
                "yuki"
            );
            System.out.println("数据库连接成功！");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}