package com.example.app_02.utils;

import com.example.app_02.database.MySQLConnection;
import com.example.app_02.entity.User;
import com.example.app_02.entity.UserInfo;

public class UserInfoDao extends MySQLConnection {
    public int insertUserInfo(UserInfo userInfo) {
        int value = 0;
        connect();
        try {
            String sql = "insert into userinfo(studentid, name, phonenumber, class) values(?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            //将数据插入数据库中
            preparedStatement.setString(1, userInfo.getStudentid());
            preparedStatement.setString(2, userInfo.getName());
            preparedStatement.setString(3, userInfo.getPhonenumber());
            preparedStatement.setString(4, userInfo.getClassname());
            value = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return value;
    }
}
