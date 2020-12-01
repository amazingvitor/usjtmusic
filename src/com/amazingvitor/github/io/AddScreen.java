package com.amazingvitor.github.io;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

public class AddScreen {
    public JPanel getAddPnl() {
        return addPnl;
    }

    private JPanel addPnl;
    private JTextField nameTxt;
    private JTextField genreTxt;
    private JTextField artistTxt;
    private JLabel addLbl;
    private JLabel nameLbl;
    private JLabel artistLbl;
    private JButton saveBtn;
    private JLabel genreLbl;


    public AddScreen(Integer userId) {

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name = nameTxt.getText();
                String artist = artistTxt.getText();
                String genre = genreTxt.getText();

                ConnectionFactory myConn = new ConnectionFactory();
                try {
                    Statement statement = myConn.myConn.createStatement();
                    String sql = "CREATE TABLE IF NOT EXISTS`usjt`.`music` (\n" +
                            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                            "  `name` VARCHAR(200) NOT NULL,\n" +
                            "  `artist` VARCHAR(45) CHARACTER SET 'ascii' NULL,\n" +
                            "  `genre` VARCHAR(50) NULL,\n" +
                            "  `userId` INT NOT NULL,\n" +
                            "  PRIMARY KEY (`id`));";
                    try {
                        statement.executeUpdate(sql);
                        System.out.println("Created table success");
                        String insertUser = "INSERT INTO music (name, artist, genre, userId) VALUES ('"+name+"', '"+artist+"', '"+genre+"', '"+userId+"')";
                        try {
                            statement.executeUpdate(insertUser);
                            System.out.println("Music Created.");
                            JOptionPane.showMessageDialog(null, "Musica salva com sucesso.");
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
