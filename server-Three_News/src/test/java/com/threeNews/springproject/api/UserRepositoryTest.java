package com.threeNews.springproject.api;

import com.threeNews.springproject.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 * UserRepositoryTest
 * @author Jinxuan Liu
 * @author Jinhao Zhong
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://47.107.97.154:3306/three_news?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private static final String username = "root";
    private static final String password = "Zjh19971215";
    @Autowired
    private UserRepository userRepository;
    @Test
    /**
     * test findByNameAndPassword()
     */
    public void findByNameAndPasswordWithCorrect() {
        Connection conn = null;
        Statement stmt = null;
        List<User> resultlist = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, username, password);

            stmt = conn.createStatement();
            String sql;
            sql = "select * from user where user_name=\'11611129\' and user_password=\'11111111\'";
            ResultSet rs = stmt.executeQuery(sql);
            resultlist = new ArrayList<>();
            while (rs.next()){
                resultlist.add(new User(rs.getString("user_name"),rs.getString("user_password")));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException ignored){
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        List<User> testResult = userRepository.findByNameAndPassword("11611129","11111111");
        assertEquals(testResult.get(0).getName(),resultlist.get(0).getName());
    }
    /**
     * test findByNameAndPassword()
     */
    @Test
    public void findByNameAndPasswordWithInvalid() {
        Connection conn = null;
        Statement stmt = null;
        List<User> resultlist = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, username, password);

            stmt = conn.createStatement();
            String sql;
            sql = "select * from user where user_name=\'a11611129\' and user_password=\'111111111\'";
            ResultSet rs = stmt.executeQuery(sql);
            resultlist = new ArrayList<>();
            while (rs.next()){
                resultlist.add(new User(rs.getString("user_name"),rs.getString("user_password")));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException ignored){
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        List<User> testResult = userRepository.findByNameAndPassword("a11611129","111111111");
        assertEquals(testResult.isEmpty(),resultlist.isEmpty());
    }
    /**
     * test findByName() with correct input
     */
    @Test
    public void findByNameWithCorrect() {
        Connection conn = null;
        Statement stmt = null;
        List<User> resultlist = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, username, password);

            stmt = conn.createStatement();
            String sql;
            sql = "select * from user where user_name=\'11611129\'";
            ResultSet rs = stmt.executeQuery(sql);
            resultlist = new ArrayList<>();
            while (rs.next()){
                resultlist.add(new User(rs.getString("user_name"),rs.getString("user_password")));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException ignored){
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        List<User> testResult = userRepository.findByName("11611129");
        assertEquals(testResult.get(0).getName(),resultlist.get(0).getName());
    }

    /**
     * test findByName() with invalid input
     */
    @Test
    public void findByNameWithInValid() {
        Connection conn = null;
        Statement stmt = null;
        List<User> resultlist = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, username, password);

            stmt = conn.createStatement();
            String sql;
            sql = "select * from user where user_name=\'a11611129\'";
            ResultSet rs = stmt.executeQuery(sql);
            resultlist = new ArrayList<>();
            while (rs.next()){
                resultlist.add(new User(rs.getString("user_name"),rs.getString("user_password")));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException ignored){
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        List<User> testResult = userRepository.findByName("a11611129");
        assertEquals(testResult.isEmpty(),resultlist.isEmpty());
    }
}