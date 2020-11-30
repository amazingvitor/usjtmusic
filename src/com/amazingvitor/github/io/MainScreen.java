package com.amazingvitor.github.io;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

public class MainScreen {
    private JButton RegisterBtn;
    private JPanel mainPnl;
    private JButton loginBtn;
    private static JFrame jFrame;

    public static void main(String[] args) {
        jFrame = new JFrame("Main Screen");
        jFrame.setContentPane(new MainScreen().mainPnl);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(400, 250);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    public MainScreen() {
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jLogin = new JFrame("Login");
                jLogin.setContentPane(new Login(jLogin).getLoginPanel());
                jLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                jLogin.setSize(400, 250);
                jLogin.setLocationRelativeTo(null);
                jLogin.setVisible(true);
                jFrame.dispose();
            }
        });
        RegisterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jRegister = new JFrame("Registrar");
                jRegister.setContentPane(new Register().getRegisterPnl());
                jRegister.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                jRegister.setSize(400, 250);
                jRegister.setLocationRelativeTo(null);
                jRegister.setVisible(true);
                jFrame.dispose();
            }
        });
    }
}
