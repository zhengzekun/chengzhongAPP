package com.example.freshtext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freshtext.Activity.UserActivity;
import com.example.freshtext.Adapter.BottomAdapter;
import com.example.freshtext.Adapter.GoodAdapter;
import com.example.freshtext.Adapter.MenuAdapter;
import com.example.freshtext.Adapter.UpAdapter;
import com.example.freshtext.Entity.User;
import com.example.freshtext.Utility.HttpUtility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.FormBody;
import okhttp3.RequestBody;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static Boolean isExit = false;
    final HttpUtility httpUtility = new HttpUtility();

    private User user;
    private List list;
    private BottomAdapter bottomAdapter;
    private UpAdapter upAdapter;
    private MenuAdapter menuAdapter;
    private GoodAdapter goodAdapter;

    private RecyclerView menu_recycler;
    private RecyclerView up_recycler;
    private RecyclerView bottom_recycler;
    private RecyclerView main_recycler;

    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //接收登录界面传回的user对象
        Intent re = getIntent();
        user = (User) re.getExtras().get("user");
        init();
        uploadAdapter();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //点击两下返回按钮退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }
    /** * 双击退出函数 */
    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true;
            // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer(); tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                    // 取消退出
                } }, 2000);
            // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
            System.exit(0);
        }
    }

    //加载控件
    public void init() {
        menu_recycler = (RecyclerView) findViewById(R.id.menu_recycler);
        up_recycler = (RecyclerView) findViewById(R.id.up_recycler);
        bottom_recycler = (RecyclerView)findViewById(R.id.bottom_recycler);
        main_recycler = (RecyclerView)findViewById(R.id.main_recycler);

        userName = (TextView)findViewById(R.id.user_name);
        if (user != null) {
            userName.setText(user.getName());
        }
    }

    //加载布局适配器
    public void uploadAdapter() {
        list = new ArrayList();
        for (int i = 0; i < 7; i++) {
            list.add("item" + i);
        }
        //左侧菜单
        LinearLayoutManager linearLayoutManager_menu = new LinearLayoutManager(this);
        menu_recycler.setLayoutManager(linearLayoutManager_menu);
        menuAdapter = new MenuAdapter(list);
        menu_recycler.setAdapter(menuAdapter);
        //顶部
        LinearLayoutManager linearLayoutManager_up = new LinearLayoutManager(this);
        linearLayoutManager_up.setOrientation(RecyclerView.HORIZONTAL);
        up_recycler.setLayoutManager(linearLayoutManager_up);
        upAdapter = new UpAdapter(list);
        up_recycler.setAdapter(upAdapter);
        //底部
        LinearLayoutManager linearLayoutManager_bottom = new LinearLayoutManager(this);
        linearLayoutManager_bottom.setOrientation(RecyclerView.HORIZONTAL);
        bottom_recycler.setLayoutManager(linearLayoutManager_bottom);
        bottomAdapter = new BottomAdapter(list);
        bottom_recycler.setAdapter(bottomAdapter);
        //主界面
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        main_recycler.setLayoutManager(gridLayoutManager);
        goodAdapter = new GoodAdapter(list);
        main_recycler.setAdapter(goodAdapter);
    }

    //按钮点击事件
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user:
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(intent);
                break;
            case R.id.text:
                list.add("1");
                menuAdapter.notifyDataSetChanged();
                break;
            case R.id.set:
                Toast.makeText(this, "点击了设置", Toast.LENGTH_LONG).show();
                break;
            case R.id.order:
                Toast.makeText(this, "点击了订单", Toast.LENGTH_LONG).show();
                break;
            case R.id.pay:
                Toast.makeText(this, "点击了支付", Toast.LENGTH_LONG).show();
                break;
            case R.id.good:
                list.add("增加的");
                menuAdapter.notifyDataSetChanged();
                break;
        }
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
}
