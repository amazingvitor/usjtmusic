package com.amazingvitor.github.io;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class RecommendedScreen
{
    ConnectionFactory myConn = new ConnectionFactory();
    private Integer userId;
    private JPanel panel;
    private JButton yesBtn;
    private JButton notBtn;
    private JLabel welcomeLbl;
    private JLabel musicLbl;
    private int counter;
    Object[][] musicList;

    String name;
    String artist;
    String genre;

    public JPanel getPanel() {
        return panel;
    }

    public RecommendedScreen(Integer userId, JFrame jframe)
    {
        counter = 0;
        this.userId = userId;
        String favoriteGenre = getFavoriteGenre();
        musicList = musicSelect(favoriteGenre);

        if (musicList[0][0] != null || musicList[0][1] != null || musicList[0][2] != null) {
            musicLbl.setText(musicList[0][0].toString() + " " + musicList[0][1].toString() + " " + musicList[0][2].toString());
        } else {
            JOptionPane.showMessageDialog(null, "Não existem recomendações para você no momento.");
            yesBtn.setEnabled(false);
            notBtn.setEnabled(false);
            return;
        }


//        musicLbl.setText(favoriteGenre);

        yesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] music = changeMusic();

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
                        System.out.println(name);
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

        notBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeMusic();
            }
        });
    }

    String[] changeMusic() {
        counter++;
        String[] properties = new String[3];
        if(musicList.length <= counter) {
            musicLbl.setText("Não existem recomendações no momento :(");
            yesBtn.setEnabled(false);
            notBtn.setEnabled(false);
        } else {
            if (this.musicList[counter][0] != null) {
                musicLbl.setText(musicList[counter][0].toString() + " " + musicList[counter][1].toString() + " " + musicList[counter][2].toString());

//                properties[0] = musicList[counter][0].toString();
//                properties[1] = musicList[counter][1].toString();
//                properties[2] = musicList[counter][2].toString();
//
//                this.name = musicList[counter][0].toString();
//                this.artist = musicList[counter][1].toString();
//                this.genre = musicList[counter][2].toString();
//                System.out.println(this.name + this.artist + this.genre);
//                System.out.println(properties[0] + properties[1] + properties[2]);

            } else {
                musicLbl.setText("Acabaram as recomendações :(");
                yesBtn.setEnabled(false);
                notBtn.setEnabled(false);
            }
        }
     return properties;
    }

    public Map<String, Integer> getGenreMap(){
        String sql;

        Map map = new HashMap();
        Integer popCount = 0;
        Integer rockCount = 0;
        Integer countryCount = 0;
        try{
            sql = "select count(*) as count from usjt.music where genre = 'pop' and userId= ?";
            PreparedStatement ps = myConn.myConn.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                popCount = rs.getInt("count");
            }
            ps.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        try{
            sql = "select count(*) as count from usjt.music where genre = 'rock' and userId= ?";
            PreparedStatement ps = myConn.myConn.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                rockCount = rs.getInt("count");
            }
            ps.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }

        try{
            sql = "select count(*) as count from usjt.music where genre = 'country' and userId= ?";
            PreparedStatement ps = myConn.myConn.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                countryCount = rs.getInt("count");
            }
            ps.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        map.put("pop", popCount);
        map.put("country", countryCount);
        map.put("rock", rockCount);
        return map;
    }

    private String getFavoriteGenre(){
        Map<String, Integer> map = getGenreMap();
        String favoriteGenre = null;
        int favoriteGenreCount = 0;
        for (String key: map.keySet()) {
            int count = map.get(key);
            
            if(count > favoriteGenreCount){
                favoriteGenreCount = count;
                favoriteGenre = key;
            }
        }

        return favoriteGenre;
    }

    public Object[][] musicSelect(String favoriteGenre) {
        String sql;

        //actual data for the table in a 2d array
        Object[][] data = new Object[100][3];
        System.out.println("musicSelect()");
        try {
            sql = "SELECT * from usjt.music where userId <> ? and genre = ?";
            PreparedStatement ps;
            ps = myConn.myConn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, favoriteGenre);
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

                this.name = name;
                this.artist = artist;
                this.genre = genre;
                System.out.println(name);
            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return data;
    }
}