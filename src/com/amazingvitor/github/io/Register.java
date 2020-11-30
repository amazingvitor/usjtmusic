package com.amazingvitor.github.io;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Register {
    private JTextField nomeTxt;
    private JPasswordField passTxt;
    private JTextField emailTxt;
    private JButton registrarButton;

    public JPanel getRegisterPnl() {
        return registerPnl;
    }

    private JPanel registerPnl;

    public Register() {
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name = nomeTxt.getText();
                String password = passTxt.getText();
                String email = emailTxt.getText();

                ConnectionFactory myConn = new ConnectionFactory();
                try {
                    Statement statement = myConn.myConn.createStatement();
                    String sql = "CREATE TABLE IF NOT EXISTS`usjt`.`users` (\n" +
                            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                            "  `name` VARCHAR(200) NOT NULL,\n" +
                            "  `password` VARCHAR(45) CHARACTER SET 'ascii' NULL,\n" +
                            "  `email` VARCHAR(50) NULL,\n" +
                            "  PRIMARY KEY (`id`));";
                    try {
                        statement.executeUpdate(sql);
                        System.out.println("Created table success");
                        String insertUser = "INSERT INTO users (name, password, email) VALUES ('"+name+"', '"+password+"', '"+email+"')";
                        try {
                            statement.executeUpdate(insertUser);
                            System.out.println("User Created.");
                        } catch (SQLException userError) {
                            System.out.println(userError);
                        };

                    } catch(SQLException error){
                        System.out.println(error);
                    };

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}
