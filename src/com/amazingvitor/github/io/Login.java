package com.amazingvitor.github.io;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login {
    private JButton loginBtn;
    ConnectionFactory myConn = new ConnectionFactory();

    public JPanel getLoginPanel() {
        return loginPanel;
    }

    private JPanel loginPanel;
    private JTextField emailTxt;
    private JPasswordField passTxt;

    Integer userId;
    String name;

    public boolean consultar(String login, char[] senha) {
        boolean autenticado = false;
        String sql;

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

    public Login(JFrame jFrame) {
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailTxtText = emailTxt.getText();
                char[] passTxtText = passTxt.getPassword();

                boolean resposta = consultar(emailTxtText, passTxtText);
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
            }
        });
    }
}
