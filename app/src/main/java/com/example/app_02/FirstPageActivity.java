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
import android.widget.Button;
import android.widget.TextView;

import com.example.app_02.entity.Record;
import com.example.app_02.utils.RecordDao;

import java.sql.SQLException;
import java.util.ArrayList;

public class FirstPageActivity extends AppCompatActivity implements View.OnClickListener {


    //    private String record;
    private TextView tvRecord;
    TextView tvAnalyse;
    TextView tvGoal;
    private SharedPreferences sharedPreferences;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        findViewById(R.id.btnGoal).setOnClickListener(this);
        findViewById(R.id.btnRecord).setOnClickListener(this);
        findViewById(R.id.btnAnalyse).setOnClickListener(this);
        findViewById(R.id.btnClear).setOnClickListener(this);
        findViewById(R.id.btnDelete).setOnClickListener(this);


        tvGoal = findViewById(R.id.tvGoal);
        tvRecord = findViewById(R.id.tvRecord);
        tvAnalyse = findViewById(R.id.tvAnalyse);

        sharedPreferences = getSharedPreferences("goal", Context.MODE_PRIVATE);
        String goalDate = sharedPreferences.getString("goalDate", "");
        String goal = sharedPreferences.getString("goal", "");

        tvGoal.setText(goalDate + goal);

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


//        TextView tvAnalyse = findViewById(R.id.tvAnalyse);
//        SharedPreferences sharedPreferences = getSharedPreferences("analyse", MODE_PRIVATE);
//        tvAnalyse.setText(sharedPreferences.getString("analyse", ""));
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
        } else if (i == R.id.btnAnalyse) {
            new Thread(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    RecordDao recordDao = new RecordDao();
                    int time;
                    try {
                        time = recordDao.sumTime(sharedPreferences.getInt("n", 0));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    // 在新线程中更新UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (time == 0) {
                                tvAnalyse.setText("还没有学习记录");
                            } else if (time > 0 && time < 5) {
                                tvAnalyse.setText("6");
                            } else if (time < 10) {
                                tvAnalyse.setText("7");
                            } else if (time < 15) {
                                tvAnalyse.setText("8");
                            } else if (time < 20) {
                                tvAnalyse.setText("9");
                            } else {
                                tvAnalyse.setText("10");
                            }
                        }
                    });
                }
            }).start();

        } else if (i == R.id.btnClear) {
            tvRecord.setText("");
            sharedPreferences = getSharedPreferences("record", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器对象
            editor.clear();
            editor.apply();
        } else {
            tvGoal.setText("");
            sharedPreferences = getSharedPreferences("goal", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        }
    }
}