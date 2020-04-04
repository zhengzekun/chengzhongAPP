package com.example.freshtext.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.freshtext.Entity.User;
import com.example.freshtext.MainActivity;
import com.example.freshtext.R;
import com.example.freshtext.Utility.HttpUtility;
import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.RequestBody;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    final HttpUtility httpUtility = new HttpUtility();

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private User user;
    private Boolean login_finish = false; //登录线程标志位

    private Button logIn;
    private Button signIn;
    private EditText userName;
    private EditText userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        //SharedPreferences存储，存储文件为userInfo
        preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        //editor对象实现存储操作
        editor = preferences.edit();
        String userName = preferences.getString("userName", "");
        String password = preferences.getString("password", "");
        //已登录则跳转主界面
        if (userName != "" && password != "") {
            User user = new User(userName, password);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    //加载控件
    public void init() {
        logIn = (Button)findViewById(R.id.login);
        signIn = (Button)findViewById(R.id.signin);
        userName = (EditText)findViewById(R.id.user_name);
        userPassword = (EditText)findViewById(R.id.user_password);
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击登录
            case R.id.login:
                login();
                //登录完成
                while (login_finish) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                break;
        }
    }

    //登录
    public void login() {
        //开启线程访问服务器实现登陆
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                login_finish = false;
                RequestBody body = new FormBody.Builder()
                        .add("name",userName.getText().toString())
                        .add("password", userPassword.getText().toString())
                        .build();
                String res = httpUtility.post(body, "http://192.168.0.6:10031/login").toString();
                Gson gson = new Gson();
                user = gson.fromJson(res, User.class);
                //登陆成功则写入SharedPreferences
                if (user != null) {
                    editor.putString("userName", user.getName());
                    editor.putString("password", user.getPassword());
                    editor.commit();
                    login_finish = true;//标志该线程完成
                } else {
                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
                }
            }
        });
        thread.run();
    }

    private void get() {
        String res = httpUtility.get("http://192.168.0.63:10031/home").toString();
        Gson gson = new Gson();
        User user = gson.fromJson(res, User.class);
        Log.i("WY","打印GET响应的数据：" + user);
    }

    private void post() {
        RequestBody body = new FormBody.Builder()
                .add("name","zzk")
                .add("password", "123456")
                .build();
        String res = httpUtility.post(body, "http://192.168.0.63:10031/login").toString();
        Gson gson = new Gson();
        User user = gson.fromJson(res, User.class);
        Log.i("WY","打印POST响应的数据：" + res);
    }

    //页面跳转后销毁登录界面
    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }
}
