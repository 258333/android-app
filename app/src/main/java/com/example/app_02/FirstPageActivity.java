package com.example.app_02;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.example.app_02.entity.Record;
import com.example.app_02.utils.RecordDao;

import java.util.ArrayList;

public class FirstPageActivity extends AppCompatActivity implements View.OnClickListener {


    private String record;
    private TextView tvRecord;

    private SharedPreferences sharedPreferences;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        findViewById(R.id.btnGoal).setOnClickListener(this);
        findViewById(R.id.btnRecord).setOnClickListener(this);
        findViewById(R.id.btnAnalyse).setOnClickListener(this);

        TextView tvGoal = findViewById(R.id.tvGoal);
        tvRecord = findViewById(R.id.tvRecord);

        sharedPreferences = getSharedPreferences("goal", Context.MODE_PRIVATE);
        String goalDate = sharedPreferences.getString("goalDate", "");
        String goal = sharedPreferences.getString("goal", "");

        tvGoal.setText(goalDate + " : " + goal);

        sharedPreferences = getSharedPreferences("record", Context.MODE_PRIVATE);
        tvRecord.setText(sharedPreferences.getString("record", ""));
//
//        h = new Handler(){
//            @SuppressLint("HandlerLeak")
//            @Override
//            public void handleMessage(){
//                record = "";
//                ArrayList<Record> records = new ArrayList<>();
//                RecordDao recordDao = new RecordDao();
//                records = recordDao.findRecord();
//                for (int i = 0; i < records.size(); i++) {
//                    record += records.get(i).toString();
//                }
//                tvRecord.setText(record);
//            }
//        };


//        Handler handler = new Handler(Looper.getMainLooper());
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                record = "";
//                ArrayList<Record> records = new ArrayList<>();
//                RecordDao recordDao = new RecordDao();
//                records = recordDao.findRecord();
//                for (int i = 0; i < records.size(); i++) {
//                    record += records.get(i).toString();
//                }
//                tvRecord.setText(record);
//            }
//        });

//
//        FirstPageActivity.this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                record = "";
//                ArrayList<Record> records = new ArrayList<>();
//                RecordDao recordDao = new RecordDao();
//                records = recordDao.findRecord();
//                for (int i = 0; i < records.size(); i++) {
//                    record += records.get(i).toString();
//                }
//                tvRecord.setText(record);
//            }
//        });

    }

    protected void onResume() {

        super.onResume();


        sharedPreferences = getSharedPreferences("record", Context.MODE_PRIVATE);
        tvRecord.setText(sharedPreferences.getString("record", ""));

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                record = "";
//                ArrayList<Record> records = new ArrayList<>();
//                RecordDao recordDao = new RecordDao();
//                records = recordDao.findRecord();
//                for (int i = 0; i < records.size(); i++) {
//                    record += records.get(i).toString();
//                }
//                tvRecord.setText(record);
//            }
//        }).start();


        TextView tvAnalyse = findViewById(R.id.tvAnalyse);
        SharedPreferences sharedPreferences = getSharedPreferences("analyse", MODE_PRIVATE);
        tvAnalyse.setText(sharedPreferences.getString("analyse", ""));
    }

//    public void findAll() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                record = "";
//                ArrayList<Record> records = new ArrayList<>();
//                RecordDao recordDao = new RecordDao();
//                records = recordDao.findRecord();
//                for (int i = 0; i < records.size(); i++) {
//                    record += records.get(i).toString();
//                }
//            }
//        }).start();
//    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnGoal) {
            Intent intent = new Intent(this, GoalActivity.class);
            startActivity(intent);
        } else if (i == R.id.btnRecord) {
            Intent intent = new Intent(this, RecordActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, AnalyseActivity.class);
            startActivity(intent);
        }
    }
}