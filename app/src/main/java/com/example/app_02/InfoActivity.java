package com.example.app_02;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.app_02.entity.UserInfo;
import com.example.app_02.utils.UserDao;
import com.example.app_02.utils.UserInfoDao;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText ed_studentid;
    private EditText ed_name;
    private EditText ed_phonenumber;
    private EditText ed_class;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ed_studentid = findViewById(R.id.ed_studentid);
        ed_name = findViewById(R.id.ed_name);
        ed_phonenumber = findViewById(R.id.ed_phonenumber);
        ed_class = findViewById(R.id.ed_class);
        findViewById(R.id.btn_sure).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // 获取输入框的值
        String studentid = ed_studentid.getText().toString();
        String name = ed_name.getText().toString();
        String phonenumber = ed_phonenumber.getText().toString();
        String className = ed_class.getText().toString();
        UserInfo userInfo = new UserInfo(studentid, name, phonenumber, className);
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserInfoDao userInfoDao = new UserInfoDao();
                userInfoDao.insertUserInfo(userInfo);

                Bundle bundle1 = getIntent().getExtras();
                if(bundle1 != null) {
                    String username = bundle1.getString("username", "");
                    UserDao userDao = new UserDao();
                    userDao.updateUserStudentid(userInfo, username);
                }
//                Toast.makeText(InfoActivity.this, "登记成功", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(InfoActivity.this, LoginActivity.class);

                startActivity(intent);
                finish();
            }
        }).start();

    }
}