package com.example.app_02;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.app_02.entity.Record;
import com.example.app_02.utils.RecordDao;

import java.util.ArrayList;
import java.util.Calendar;

public class RecordActivity extends AppCompatActivity implements View.OnClickListener, TimePicker.OnTimeChangedListener {
    private TimePicker tp_time_start;
    private TimePicker tp_time_end;
    Calendar calendar = Calendar.getInstance();
    String startTime = null;
    String endTime = null;
    private int startHour;
    private int endHour;
    private int time;
    private EditText editTextLearningRecord;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        tp_time_start = findViewById(R.id.tp_time_start);
        tp_time_end = findViewById(R.id.tp_time_end);
        editTextLearningRecord = findViewById(R.id.editTextLearningRecord);

        findViewById(R.id.buttonSaveRecord).setOnClickListener(this);
        findViewById(R.id.butCancel).setOnClickListener(this);
        Calendar calendar = Calendar.getInstance();

//         构建一个时间对话框，该对话框已经集成了时间选择器。
//        TimePickerDialog dialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, this,
//                calendar.get(Calendar.HOUR_OF_DAY),
//                calendar.get(Calendar.MINUTE),
//                true); // true表示24小时制，false表示12小时制
//        dialog.show();

        tp_time_start.setOnTimeChangedListener(this);
        tp_time_end.setOnTimeChangedListener(this);
    }

    @Override
    public void onClick(View view) {
        if (R.id.buttonSaveRecord == view.getId()) {
            if (startTime == null) {
                startTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) +
                        " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
                startHour = calendar.get(Calendar.HOUR_OF_DAY);
            }
            if (endTime == null) {
                endTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) +
                        " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
                endHour = calendar.get(Calendar.HOUR_OF_DAY);
            }
            String record = editTextLearningRecord.getText().toString();

            time = endHour - startHour;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RecordDao recordDao = new RecordDao();
                    Record record1 = new Record(startTime, endTime, record, time);
                    recordDao.insertRecord(record1);

                    SharedPreferences sharedPreferences = getSharedPreferences("record", Context.MODE_PRIVATE);

                    String record = "";
                    ArrayList<Record> records = new ArrayList<>();
                    records = recordDao.findRecord(sharedPreferences.getInt("n", 0) + 1);
                    for (int i = 0; i < records.size(); i++) {
                        record += records.get(i).toString();
                    }

                    SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器对象
                    editor.putInt("n", sharedPreferences.getInt("n", 0) + 1);
                    editor.putString("record", record);

                    editor.apply();
                }
            }).start();
        }
        Intent intent = new Intent(this, FirstPageActivity.class);
        startActivity(intent);
    }


    @Override
    public void onTimeChanged(TimePicker view, int hour, int minute) {
        String tag = (String) view.getTag();
        if (tag.equals("start")) {
            startTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) +
                    "-" + calendar.get(Calendar.DAY_OF_MONTH) + " " + hour + ":" + minute;
            startHour = hour;
        } else {
            endTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) +
                    "-" + calendar.get(Calendar.DAY_OF_MONTH) + " " + hour + ":" + minute;
            endHour = hour;
        }
    }
}