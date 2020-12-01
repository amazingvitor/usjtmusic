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

    Integer userId;
    String name;

    public JPanel getRegisterPnl() {
        return registerPnl;
    }

    private JPanel registerPnl;

    public Register(JFrame jFrame) {
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name = nomeTxt.getText();
                String password = passTxt.getText();
                char[] charPass = passTxt.getPassword();
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

                            boolean resposta = consultar(email, charPass);
                            if (resposta == true) {
                                System.out.println("logged");
                                JFrame jMusic = new JFrame("Music Screen");
                                jMusic.setContentPane(new MusicScreen(userId, name).getMusicPnl());
                                jMusic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                jMusic.setSize(400, 250);
                                jMusic.setLocationRelativeTo(null);
                                jMusic.setVisible(true);
                                jFrame.dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, "Usuário e(ou) senha incorretos!");
                            }

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

    public boolean consultar(String login, char[] senha) {
        boolean autenticado = false;
        String sql;
        ConnectionFactory myConn = new ConnectionFactory();

        try {
            sql = "SELECT id, name, email, password FROM users WHERE email=? and password=?";
            PreparedStatement ps;
            ps = myConn.myConn.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, String.valueOf(senha));

            ResultSet rs;
            rs = ps.executeQuery();

            if (rs.next()) {
                this.userId = rs.getInt("id");
                this.name = rs.getString("name");
                String user = rs.getString("email");
                String password = rs.getString("password");
                autenticado = true;
            } else {
                ps.close();
                return autenticado;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return autenticado;
    }
}
