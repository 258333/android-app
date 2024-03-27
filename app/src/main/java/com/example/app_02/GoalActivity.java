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

import java.util.Calendar;

public class GoalActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {
    private DatePicker dp_date;
    private EditText ed_goal;
    private SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        dp_date=findViewById(R.id.dp_date);
        ed_goal=findViewById(R.id.ed_goal);
        findViewById(R.id.btn_save).setOnClickListener(this);
        // 获取日历的一个实例，里面包含了当前的年月日
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        // 显示日期对话框
        dialog.show();

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        sharedPreferences = getSharedPreferences("goal", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("goalDate", year + "-" + (month + 1) + "-" + dayOfMonth);
//        editor.putString("goal", ed_goal.getText().toString());
        editor.apply();
    }

    @Override
    public void onClick(View view) {
        sharedPreferences = getSharedPreferences("goal", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("goal", ed_goal.getText().toString());
        editor.apply();
        Intent intent = new Intent(this, FirstPageActivity.class);
        startActivity(intent);
        finish();
    }
}