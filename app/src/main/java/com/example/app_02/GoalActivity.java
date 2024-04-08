package com.example.app_02;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.app_02.utils.ToastUtil;

import java.util.Calendar;

public class GoalActivity extends AppCompatActivity implements View.OnClickListener {
    private DatePicker dp_date;
    private EditText ed_goal;
    private SharedPreferences sharedPreferences;
    String goalDate = "";
    // 获取日历的一个实例，里面包含了当前的年月日
    Calendar calendar = Calendar.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        dp_date = findViewById(R.id.dp_date);
        ed_goal = findViewById(R.id.ed_goal);
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);

        dp_date.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                goalDate = year + "-" + (month + 1) + "-" + day;
            }
        });
//        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                goalDate = year + "-" + (month + 1) + "-" + dayOfMonth;
//            }
//        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


//        // 显示日期对话框
//        dialog.show();

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save) {
            String edGoal = ed_goal.getText().toString();
            if (edGoal.equals("")) {
                ToastUtil.show(this, "请输入你的本周目标");
            } else {
                sharedPreferences = getSharedPreferences("goal", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("goal", ed_goal.getText().toString());
                if (goalDate.equals("")) {
                    goalDate = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + " : ";
                }
                editor.putString("goalDate", goalDate);
                editor.apply();
                Intent intent = new Intent(this, FirstPageActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            Intent intent = new Intent(this, FirstPageActivity.class);
            startActivity(intent);
            finish();
        }
    }
}