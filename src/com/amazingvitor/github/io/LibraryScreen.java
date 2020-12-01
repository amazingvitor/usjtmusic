package com.amazingvitor.github.io;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibraryScreen extends JFrame
{
    ConnectionFactory myConn = new ConnectionFactory();
    private JPanel libraryPnl;
    private JButton otherMusicBtn;

    public LibraryScreen(Integer userId)
    {
        //headers for the table
        String[] columns = new String[] {
                "Nome", "Artista", "Genero"
        };
        //create table with data
        JTable table = new JTable(musicSelect(userId), columns);

        //add the table to the frame
        this.add(new JScrollPane(table));

        this.setTitle("Sua Biblioteca");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    public Object[][] musicSelect(Integer userId) {
        String sql;

        //actual data for the table in a 2d array
        Object[][] data = new Object[100][3];

        try {
            sql = "SELECT * from usjt.music where userId=?";
            PreparedStatement ps;
            ps = myConn.myConn.prepareStatement(sql);
            ps.setInt(1, userId);
            int counter = 0;

            ResultSet rs;
            rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String artist = rs.getString("artist");
                String genre = rs.getString("genre");
                data[counter][0] = name;
                data[counter][1] = artist;
                data[counter][2] = genre;
                counter++;
            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return data;
    }
}