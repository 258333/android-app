package com.example.app_02.utils;

import com.example.app_02.database.MySQLConnection;
import com.example.app_02.entity.User;
import com.example.app_02.entity.UserInfo;

public class UserDao extends MySQLConnection {
    public User findUserName(String username) {
        connect();
        User user = null;
        try {
            //sql语句。我这里是根据我自己的users表的username字段来查询记录
            String sql = "select * from user where username=?";
            //获取用于向数据库发送sql语句的preparedStatement
            preparedStatement = connection.prepareStatement(sql);
            //根据账号进行查询
            preparedStatement.setString(1, username);
            //执行sql查询语句并返回结果集
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                //.next()表示指针先下一行，若有数据则返回true
                user = new User();
                user.setUsername(resultSet.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return user;//若传入成功返回账号，失败则为null
    }

    public User findUser(String username, String password) {
        connect();
        User user = null;
        try {
            //sql语句。我这里是根据我自己的users表的username和password字段来查询记录
            String sql = "select * from user where username=? and password=?";
            //获取用于向数据库发送sql语句的preparedStatement
            preparedStatement = connection.prepareStatement(sql);
            //根据账号和密码进行查询
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return user;//若传入成功返回账号和密码，失败则为null
    }


    public int registerUser(User user) {
        int value = 0;
        connect();
        try {
            String sql = "insert into user(username,password) values(?,?)";
            preparedStatement = connection.prepareStatement(sql);
            //将数据插入数据库中
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            value = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return value;
    }

    public int updateUserStudentid(UserInfo userInfo, String username){
        int value = 0;
        connect();
        try {
            String sql = "update user set studentid=? where username=?";
            preparedStatement = connection.prepareStatement(sql);
            //将数据插入数据库中
            preparedStatement.setString(1, userInfo.getStudentid());
            preparedStatement.setString(2, username);
            value = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return value;
    }
}
