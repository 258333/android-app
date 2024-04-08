package com.example.app_02.utils;

import com.example.app_02.R;
import com.example.app_02.database.MySQLConnection;
import com.example.app_02.entity.Record;
import com.example.app_02.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class RecordDao extends MySQLConnection {
    public int insertRecord(Record record) {
        int value = 0;
        connect();
        try {
            String sql = "insert into record(starttime, endtime, record,time) values(?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            //将数据插入数据库中
            preparedStatement.setString(1, record.getStartTime());
            preparedStatement.setString(2, record.getEndTime());
            preparedStatement.setString(3, record.getRecord());
            preparedStatement.setInt(4, record.getTime());
            value = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return value;
    }

    public ArrayList<Record> findRecord(int n) {
        connect();
        ArrayList<Record> arrayList = new ArrayList<>();
        try {
            //sql语句。我这里是根据我自己的users表的username字段来查询记录
            String sql = "select * from record ORDER BY id DESC LIMIT ?";
            //获取用于向数据库发送sql语句的preparedStatement
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,n);
            //根据账号进行查询
            //执行sql查询语句并返回结果集
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                //.next()表示指针先下一行，若有数据则返回true
                Record record = new Record(resultSet.getString("starttime"), resultSet.getString("endtime"), resultSet.getString("record"));
                arrayList.add(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return arrayList;//若传入成功返回账号，失败则为null
    }

    public int sumTime(int n) throws SQLException {
        connect();
        int time = 0;
        try {
            //sql语句。我这里是根据我自己的users表的username字段来查询记录
            String sql = "select SUM(TIME) from (select time from record ORDER BY id DESC LIMIT ?) AS last_five_rows_sum";
            //获取用于向数据库发送sql语句的preparedStatement
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,n);
            //根据账号进行查询
            //执行sql查询语句并返回结果集
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                time = resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return time;
    }
}
