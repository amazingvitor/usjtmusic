package com.amazingvitor.github.io;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MusicScreen {
    private JButton libraryBtn;

    public JPanel getMusicPnl() {
        return musicPnl;
    }

    private JPanel musicPnl;
    private JButton addBtn;
    private JButton exitBtn;
    private JLabel welcomeLbl;
    private JButton otherMusicBtn;

    public MusicScreen(Integer userId, String name) {

        welcomeLbl.setText("Bem vindo: " + name);

        libraryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LibraryScreen(userId);
            }
        });

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jFrame = new JFrame("Adicionar uma nova Musica");
                jFrame.setContentPane(new AddScreen(userId).getAddPnl());
                jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrame.setSize(400, 250);
                jFrame.setLocationRelativeTo(null);
                jFrame.setVisible(true);
            }
        });

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        otherMusicBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jFrame = new JFrame("Recommendations");
                jFrame.setContentPane(new RecommendedScreen(userId, jFrame).getPanel());
                jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrame.setSize(400, 250);
                jFrame.setLocationRelativeTo(null);
                jFrame.setVisible(true);
            }
        });
    }
}
