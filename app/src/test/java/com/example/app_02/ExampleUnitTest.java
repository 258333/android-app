package com.example.app_02;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.app_02.entity.Record;
import com.example.app_02.utils.RecordDao;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void test(){
        String record = "";
        ArrayList<Record> records = new ArrayList<>();
        RecordDao recordDao = new RecordDao();
        records = recordDao.findRecord();
        for (int i = 0; i < records.size(); i++) {
            record += records.get(i).toString();
        }
        System.out.println(record);
    }
}