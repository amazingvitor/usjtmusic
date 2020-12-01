package com.amazingvitor.github.io;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class RecommendedScreen
{
    ConnectionFactory myConn = new ConnectionFactory();
    private Integer userId;
    private JPanel panel;

    public JPanel getPanel() {
        return panel;
    }

    public RecommendedScreen(Integer userId, JFrame jframe)
    {
        this.userId = userId;
        String favoriteGenre = getFavoriteGenre();
        Object[][] musicList = musicSelect(favoriteGenre);

    }

    public Map<String, Integer> getGenreMap(){
        String sql;

        Map map = new HashMap();
        Integer popCount = 0;
        Integer rockCount = 0;
        Integer countryCount = 0;
        try{
            sql = "select count(*) as count from usjit.music where genre = 'pop' where userId= ?";
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
            sql = "select count(*) as count from usjit.music where genre = 'rock' where userId= ?";
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
            sql = "select count(*) as count from usjit.music where genre = 'country' where userId= ?";
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

        try {
            sql = "SELECT * from usjt.music where userId not like ? and genre = ?";
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
            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return data;
    }
}