package com.example.metroapp.DataBase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.metroapp.Infomation.SubwayBean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CRUD {
    private DBHelper dbHelper;

    public CRUD (Context context){dbHelper = new DBHelper(context);}

    @SuppressLint("Range")
    public ArrayList<SubwayBean> ArrayTransferStation(String station1, String station2){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<SubwayBean> resultArray = new ArrayList<>();
        int i=0;
        String sql =
                    "WITH RECURSIVE transfer(start_station, stop_station, stops, paths) AS (\n" +
                    "SELECT station_name, next_station, 1 stops, \n" +
                    "       line_name||station_name||'->'||line_name||next_station AS paths\n" +
                    "FROM bj_subway\n" +
                    "WHERE station_name = ?\n" +
                    "UNION ALL \n" +
                    "SELECT t.start_station, s.next_station, stops+1, paths||'->'||s.line_name||s.next_station\n" +
                    "FROM transfer t\n" +
                    "JOIN bj_subway s \n" +
                    "ON (t.stop_station = s.station_name AND instr(paths, s.next_station)=0)\n" +
                    ")\n" +
                    "SELECT *\n" +
                    "FROM transfer\n" +
                    "WHERE stop_station = ?;";

        Cursor cursor = db.rawQuery(sql,new String[]{station1,station2});

        if (cursor.moveToFirst()){
            do {
                i++;
                SubwayBean subwayBean = new SubwayBean();
                subwayBean._StartStation = cursor.getString(cursor.getColumnIndex("start_station"));
                subwayBean._EndStation = cursor.getString(cursor.getColumnIndex("stop_station"));
                subwayBean._Number = cursor.getString(cursor.getColumnIndex("stops"));
                subwayBean._Path = cursor.getString(cursor.getColumnIndex("paths"));
                resultArray.add(subwayBean);
            }while (cursor.moveToNext());
        }
        if(i==0)
        {
            return null;
        }
        cursor.close();
        db.close();
        return resultArray;
    }

    @SuppressLint("Range")
    public ArrayList<String> PositionStation(int position){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<String> ArrayOnly = new ArrayList<>();
        int number = 1;
        switch (position){
            case 0:number = 1;
            break;
            case 1:number = 2;
                break;
            case 2:number = 8;
                break;
            case 3:number = 4;
                break;
            case 4:number = 5;
                break;
            case 5:number = 6;
                break;
            case 6:number = 7;
                break;
            case 7:number = 9;
                break;
            case 8:number = 11;
                break;
            case 9:number = 10;
                break;
        }
        String the_line = number+"号"+"线";
        String sql = "Select * From bj_subway2 Where line_name=?";
        Cursor cursor = db.rawQuery(sql,new String[]{the_line});
        int i=0;
        if (cursor.moveToFirst()){
            do {
                    i++;
                    String a = cursor.getString(cursor.getColumnIndex("station_name"));
                    ArrayOnly.add(a);
            }while (cursor.moveToNext());
        }

        if(i==0)
        {
            return null;
        }
        cursor.close();
        db.close();
        return ArrayOnly;
    }
    @SuppressLint("Range")
    public ArrayList<String> getArrayLine(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<String> ArrayLine = new ArrayList<>();
        String sql ="Select * From bj_subway2 Where station_id In (Select Max(station_id) From bj_subway2 Group By line_name)";
        Cursor cursor = db.rawQuery(sql,null);
        int i=0;
        if (cursor.moveToFirst()){
            do {
                i++;
                String a = cursor.getString(cursor.getColumnIndex("line_name"));
                ArrayLine.add(a);
            }while (cursor.moveToNext());
        }
        if(i==0)
        {
            return null;
        }
        cursor.close();
        db.close();
        return ArrayLine;
    }
    @SuppressLint("Range")
    public String getLine(String station){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String Line = null;
        String sql = "Select * From bj_subway2 Where station_name=?";
        Cursor cursor = db.rawQuery(sql,new String[]{station});
        int i=0;
        if (cursor.moveToFirst()){
                i++;
                Line = cursor.getString(cursor.getColumnIndex("line_name"));
        }
        if(i==0)
        {
            return null;
        }
        cursor.close();
        db.close();
        return Line;

    }
//    @SuppressLint("Range")
//    public SubwayBean BestTransferStation(String station1, String station2){
//
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String sql =
//                "WITH RECURSIVE transfer(start_station, stop_station, stops, paths) AS (\n" +
//                        "SELECT station_name, next_station, 1 stops, \n" +
//                        "       line_name||station_name||'->'||line_name||next_station AS paths\n" +
//                        "FROM bj_subway\n" +
//                        "WHERE station_name = ?\n" +
//                        "UNION ALL \n" +
//                        "SELECT t.start_station, s.next_station, stops+1, paths||'->'||s.line_name||s.next_station\n" +
//                        "FROM transfer t\n" +
//                        "JOIN bj_subway s \n" +
//                        "ON (t.stop_station = s.station_name AND instr(paths, s.next_station)=0)\n" +
//                        ")\n" +
//                        "SELECT *\n" +
//                        "FROM transfer\n" +
//                        "WHERE stop_station = ?;";
//
//        Cursor cursor = db.rawQuery(sql,new String[]{station1,station2});
//        SubwayBean subwayBean = new SubwayBean();
//        if (cursor.moveToFirst()){
//            subwayBean._StartStation = cursor.getString(cursor.getColumnIndex("start_station"));
//            subwayBean._EndStation = cursor.getString(cursor.getColumnIndex("stop_station"));
//            subwayBean._Number = cursor.getString(cursor.getColumnIndex("stops"));
//            subwayBean._Path = cursor.getString(cursor.getColumnIndex("paths"));
//        }
//        cursor.close();
//        db.close();
//        return  subwayBean;
//    }

//    @SuppressLint("Range")
//    public ArrayList<String> ArrayOnlyStation(){
//
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        ArrayList<String> ArrayOnly = new ArrayList<>();
//        String sql ="Select * From bj_subway Where station_id In (Select Max(station_id) From bj_subway Group By station_name)";
//        Cursor cursor = db.rawQuery(sql,null);
//        int i=0;
//        if (cursor.moveToFirst()){
//            do {
//                i++;
//                String a = cursor.getString(cursor.getColumnIndex("station_name"));
//                ArrayOnly.add(a);
//            }while (cursor.moveToNext());
//        }
//        if(i==0)
//        {
//            return null;
//        }
//        cursor.close();
//        db.close();
//        return ArrayOnly;
//    }
}
