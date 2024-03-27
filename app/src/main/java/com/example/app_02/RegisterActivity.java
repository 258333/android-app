package com.example.app_02;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.*;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.app_02.entity.User;
import com.example.app_02.utils.UserDao;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Vibrator vibrator;
    private final long VIBRATION_DURATION = 100; // 震动持续时间100毫秒

    private Handler handler;
    private UserDao userDao;
    private SharedPreferences sharedPreferences;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        TextView textview_register = findViewById(R.id.textview_register);//从布局文件中获取textview_register文本视图
        Button button_return_login = findViewById(R.id.button_return_login);//获取跳转到登录页面功能的button按钮的实例
        Button button_register_yes = findViewById(R.id.button_register_yes);//获取确认注册button按钮实例
        EditText editText_username = findViewById(R.id.editText_username);//获取EditText实例
        EditText editText_password = findViewById(R.id.editText_password);//获取EditText实例
        EditText editText_password_define = findViewById(R.id.editText_password_define);//获取EditText实例

        //为每个button按钮控件注册点击监听器
        button_return_login.setOnClickListener(this);
        button_register_yes.setOnClickListener(this);


        handler = new Handler(getMainLooper());//获取主线程
        userDao = new UserDao();


        CheckBox checkbox_show_password = findViewById(R.id.checkbox_show_password);
        CheckBox checkbox_show_password_affirm = findViewById(R.id.checkbox_show_password_affirm);
        checkbox_show_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //采用HideReturnsTransformationMethod文本转换器，隐藏输入的字符。若CheckBox被选中，则显示注册密码。
                    editText_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    //设置光标在最右端
                    editText_password.setSelection(editText_password.getText().length());
                } else {
                    //采用PasswordTransformationMethod文本转换器，隐藏输入的字符。若CheckBox没选中，则隐藏注册密码。
                    editText_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //设置光标在最右端
                    editText_password.setSelection(editText_password.getText().length());
                }
                //统一每次显示或关闭时密码显示编辑的线
                editText_password.setSelection(editText_password.length());
            }
        });
        checkbox_show_password_affirm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //采用HideReturnsTransformationMethod文本转换器，隐藏输入的字符。若CheckBox被选中，则显示注册密码。
                    editText_password_define.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    //设置光标在最右端
                    editText_password_define.setSelection(editText_password_define.getText().length());
                } else {
                    //采用PasswordTransformationMethod文本转换器，隐藏输入的字符。若CheckBox没选中，则隐藏注册密码。
                    editText_password_define.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //设置光标在最右端
                    editText_password_define.setSelection(editText_password_define.getText().length());
                }
                //统一每次显示或关闭时密码显示编辑的线
                editText_password_define.setSelection(editText_password_define.length());
            }
        });


        //设计动态渐变
        int gradient_startColor = Color.rgb(148, 0, 211);//定义深紫色为渐变起点
        int gradient_endColor = Color.rgb(255, 0, 0);//定义深红色为渐变终点
        int[] color_start_end = {gradient_startColor, gradient_endColor};
        float[] position = {0f, 1f};
        //创建一个LinearGradient渐变对象应用于TextView的Paint对象，实现TextView中文字的渐变效果
        LinearGradient shader = new LinearGradient(0, 0, textview_register.getTextSize() * textview_register.getText().length(), textview_register.getTextSize(), color_start_end, position, Shader.TileMode.CLAMP);
        textview_register.getPaint().setShader(shader);
        //实例化对象，创建透明动画效果，从0.7f到1.0f渐变  0.0是完全透明，1.0完全不透明
        AlphaAnimation animation = new AlphaAnimation(0.7f, 1.0f);
        //设置动画持续时常  单位：毫秒
        animation.setDuration(300);
        //设置重复次数
        animation.setRepeatCount(Animation.INFINITE);
        //设置重复模式
        animation.setRepeatMode(Animation.REVERSE);
        //给TextView文本添加动画效果
        textview_register.startAnimation(animation);


        //点击button按钮缩小，松开恢复和点击震动
        button_return_login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //缩小按钮
                        ScaleAnimation shrinkAnimation = new ScaleAnimation(1.0f, 0.9f, 1.0f, 0.9f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        shrinkAnimation.setDuration(100);
                        shrinkAnimation.setFillAfter(true);
                        v.startAnimation(shrinkAnimation);
                        break;
                    case MotionEvent.ACTION_UP:
                        //恢复按钮
                        ScaleAnimation restoreAnimation = new ScaleAnimation(0.9f, 1.0f, 0.9f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        restoreAnimation.setDuration(100);
                        restoreAnimation.setFillAfter(true);
                        v.startAnimation(restoreAnimation);
                        break;
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 按下时开始震动
                        vibrator.vibrate(VibrationEffect.createOneShot(VIBRATION_DURATION, VibrationEffect.DEFAULT_AMPLITUDE));
                        break;
                    case MotionEvent.ACTION_UP:
                        // 松开时停止震动
                        vibrator.cancel();
                        break;
                }
                return false;
            }
        });


        //点击button按钮缩小，松开恢复和点击震动
        button_register_yes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //缩小按钮
                        ScaleAnimation shrinkAnimation = new ScaleAnimation(1.0f, 0.9f, 1.0f, 0.9f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        shrinkAnimation.setDuration(100);
                        shrinkAnimation.setFillAfter(true);
                        v.startAnimation(shrinkAnimation);
                        break;
                    case MotionEvent.ACTION_UP:
                        //恢复按钮
                        ScaleAnimation restoreAnimation = new ScaleAnimation(0.9f, 1.0f, 0.9f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        restoreAnimation.setDuration(100);
                        restoreAnimation.setFillAfter(true);
                        v.startAnimation(restoreAnimation);
                        break;
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 按下时开始震动
                        vibrator.vibrate(VibrationEffect.createOneShot(VIBRATION_DURATION, VibrationEffect.DEFAULT_AMPLITUDE));
                        break;
                    case MotionEvent.ACTION_UP:
                        // 松开时停止震动
                        vibrator.cancel();
                        break;
                }
                return false;
            }
        });


    }


    public void register() {
        EditText editText_username = findViewById(R.id.editText_username);//获取EditText实例
        EditText editText_password = findViewById(R.id.editText_password);//获取EditText实例
        EditText editText_password_define = findViewById(R.id.editText_password_define);//获取EditText实例
        final String username = editText_username.getText().toString().trim();//获取用户输入的用户名
        final String password = editText_password.getText().toString().trim();//获取用户输入的密码
        if (TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            //弹出提醒对话框，提醒用户用户名不能为空
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle("尊敬的用户");
            builder.setMessage("用户名不能为空，请输入！");
            builder.setPositiveButton("好的", null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            //设计AlertDialog提醒对话框大小
            WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
            layoutParams.width = 700;
            layoutParams.height = 565;
            alertDialog.getWindow().setAttributes(layoutParams);//设置AlertDialog的宽高
            editText_username.requestFocus();
        } else if (TextUtils.isEmpty(password) && !TextUtils.isEmpty(username)) {
            //弹出提醒对话框，提醒用户密码不能为空
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle("尊敬的用户");
            builder.setMessage("密码不能为空，请输入！");
            builder.setPositiveButton("好的", null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            //设计AlertDialog提醒对话框大小
            WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
            layoutParams.width = 700;
            layoutParams.height = 565;
            alertDialog.getWindow().setAttributes(layoutParams);//设置AlertDialog的宽高
            editText_password.requestFocus();
        } else if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle("尊敬的用户");
            builder.setMessage("请输入用户名和密码！");
            builder.setPositiveButton("好的", null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            //设计AlertDialog提醒对话框大小
            WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
            layoutParams.width = 700;
            layoutParams.height = 565;
            alertDialog.getWindow().setAttributes(layoutParams);//设置AlertDialog的宽高
            editText_username.requestFocus();
            editText_password.requestFocus();
        } else {
            final User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final int value = userDao.registerUser(user);

                    sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器对象
                    editor.putBoolean("ischeckName", true);
                    editor.putString("username", editText_username.getText().toString());//添加名为username的账号
                    editor.putBoolean("ischeckPassword", true);
                    editor.putString("password", editText_password.getText().toString());
                    editor.apply();

                    Intent intent = new Intent(RegisterActivity.this, InfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    intent.putExtras(bundle);
                    //跳转到意图对象指定的活动页面
                    startActivity(intent);
                    //关闭当前活动页面
                    finish();

                }
            }).start();
        }
    }


    //设计读取button按钮点击的功能函数onClick()
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_return_login) {
            //创建一个意图对象，准备跳转到指定的活动页面
            Intent intent = new Intent(this, LoginActivity.class);
            //跳转到意图对象指定的活动页面
            startActivity(intent);
        }
        if (v.getId() == R.id.button_register_yes) {
            EditText editText_username = findViewById(R.id.editText_username);//获取EditText实例
            EditText editText_password = findViewById(R.id.editText_password);//获取EditText实例
            EditText editText_password_define = findViewById(R.id.editText_password_define);//获取EditText实例
            final String username = editText_username.getText().toString().trim();//获取用户输入的用户名
            String password1 = editText_password.getText().toString();
            String password2 = editText_password_define.getText().toString();
            if (password1.equals(password2)) {
                //密码和确认密码相同
                //这里要以线程访问，否则会报错
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final User user_name = userDao.findUserName(username);
                        //这里使用Handler类中常用的一个方法，post(Runnable r),立即发送Runnable对象。这里使用已经创建的android.os.Handler对象
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (user_name != null) {
                                    //创建提醒对话框的建造器
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    //设计对话框标题图标
                                    builder.setIcon(R.mipmap.ic_launcher);
                                    //设置对话框标题文本
                                    builder.setTitle("尊敬的用户");
                                    //设置对话框内容文本
                                    builder.setMessage("您所输入的账号已存在，请重新输入！");
                                    //设置对话框的肯定按钮文本及其点击监听器
                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            editText_username.setText("");//清空editText_username内容
                                            editText_password.setText("");//清空editText_password内容
                                            editText_password_define.setText("");//清空editText_password_define内容
                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();//根据建造器构建提醒对话框对象
                                    alertDialog.show();//显示提醒对话框
                                    //设计AlertDialog提醒对话框大小
                                    WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
                                    layoutParams.width = 700;
                                    layoutParams.height = 565;
                                    alertDialog.getWindow().setAttributes(layoutParams);//设置AlertDialog的宽高
                                } else {
                                    register();
                                }

                            }
                        });

                    }
                }).start();
            } else {
                //不同
                //创建提醒对话框的建造器
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                //设计对话框标题图标
                builder.setIcon(R.mipmap.ic_launcher);
                //设置对话框标题文本
                builder.setTitle("尊敬的用户");
                //设置对话框内容文本
                builder.setMessage("密码和确认密码不同，请重新输入！");
                //设置对话框的肯定按钮文本及其点击监听器
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editText_password.setText("");//清空editText_password内容
                        editText_password_define.setText("");//清空editText_password_define内容
                    }
                });
                AlertDialog alertDialog = builder.create();//根据建造器构建提醒对话框对象
                alertDialog.show();//显示提醒对话框
                //设计AlertDialog提醒对话框大小
                WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
                layoutParams.width = 700;
                layoutParams.height = 565;
                alertDialog.getWindow().setAttributes(layoutParams);//设置AlertDialog的宽高
                return;
            }
        }
    }
}