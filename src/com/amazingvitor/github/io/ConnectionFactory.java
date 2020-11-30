package com.amazingvitor.github.io;

import java.sql.*;

public class ConnectionFactory {
    String url = "jdbc:mysql://localhost:3306/usjt";
    String user = "root";
    String pass = "";

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    Integer userId;
    Connection myConn;
    {
        try {
            myConn = DriverManager.getConnection(url, user, pass);
//            Statement statement = myConn.createStatement();
//            String sql = "select * from usjt.contacts";
//            ResultSet rs = statement.executeQuery(sql);
//
//            while (rs.next()) {
//                System.out.println(rs.getInt("name"));
//            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
