package com.example.freshtext.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freshtext.R;
import com.example.freshtext.Utility.UserItemView;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_name:
                System.out.println("点击了用户");
                break;
            case R.id.item_phone:
                System.out.println("点击了手机号");
                break;
        }
    }
}
