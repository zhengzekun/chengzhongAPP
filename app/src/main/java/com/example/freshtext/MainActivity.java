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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freshtext.Activity.CommonDialog;
import com.example.freshtext.Activity.UserActivity;
import com.example.freshtext.Adapter.BottomAdapter;
import com.example.freshtext.Adapter.GoodAdapter;
import com.example.freshtext.Adapter.MenuAdapter;
import com.example.freshtext.Adapter.CategoryAdapter;
import com.example.freshtext.Entity.Category;
import com.example.freshtext.Entity.Good;
import com.example.freshtext.Entity.ShoppingCart;
import com.example.freshtext.Entity.User;
import com.example.freshtext.Utility.HttpUtility;
import com.example.freshtext.Utility.JsonUtility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.FormBody;
import okhttp3.RequestBody;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static MainActivity instance = null;
    private static Boolean isExit = false;
    final HttpUtility httpUtility = new HttpUtility();

    private User user;
    private List<Category> categoryList = null;
    private List<Good> goodList = null;
    private List<Good> goodList_want = new ArrayList<>();
    private List<ShoppingCart> shoppingCartList = new ArrayList<>();
    private BottomAdapter bottomAdapter;
    private CategoryAdapter categoryAdapter;
    private MenuAdapter menuAdapter;
    private GoodAdapter goodAdapter;

    private RecyclerView menu_recycler;
    private RecyclerView up_recycler;
    private RecyclerView bottom_recycler;
    private RecyclerView main_recycler;

    private TextView userName;
    private ImageView user_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        //接收登录界面传回的user对象
        Intent re = getIntent();
        user = (User) re.getExtras().get("user");
        init();
        uploadAdapter();
    }

    /**
     * 初始化控件
     */
    public void init() {
        menu_recycler = (RecyclerView) findViewById(R.id.menu_recycler);
        up_recycler = (RecyclerView) findViewById(R.id.up_recycler);
        bottom_recycler = (RecyclerView)findViewById(R.id.bottom_recycler);
        main_recycler = (RecyclerView)findViewById(R.id.main_recycler);

        userName = (TextView)findViewById(R.id.user_name);
        user_image = (ImageView)findViewById(R.id.user_image);
        if (user != null) {
            userName.setText(user.getName());
            user_image.setImageResource(R.drawable.zzk);
        }
    }

    /**
     * 加载布局适配器
     */
    public void uploadAdapter() {
        //左侧菜单
        LinearLayoutManager linearLayoutManager_menu = new LinearLayoutManager(this);
        menu_recycler.setLayoutManager(linearLayoutManager_menu);
        menuAdapter = new MenuAdapter(goodList_want);
        menu_recycler.setAdapter(menuAdapter);
        //顶部
        LinearLayoutManager linearLayoutManager_up = new LinearLayoutManager(this);
        linearLayoutManager_up.setOrientation(RecyclerView.HORIZONTAL);
        up_recycler.setLayoutManager(linearLayoutManager_up);
        getCategory();
        categoryAdapter = new CategoryAdapter(categoryList);
        up_recycler.setAdapter(categoryAdapter);
        //底部
        LinearLayoutManager linearLayoutManager_bottom = new LinearLayoutManager(this);
        linearLayoutManager_bottom.setOrientation(RecyclerView.HORIZONTAL);
        bottom_recycler.setLayoutManager(linearLayoutManager_bottom);
        bottomAdapter = new BottomAdapter(shoppingCartList);
        bottom_recycler.setAdapter(bottomAdapter);
        //主界面
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        main_recycler.setLayoutManager(gridLayoutManager);
        getGood(1, false);
        goodAdapter = new GoodAdapter(goodList);
        main_recycler.setAdapter(goodAdapter);
    }

    /**
     * 按钮点击事件
     * @param view
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_image:
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(intent);
                break;
            case R.id.set:
                //TODO：转到设置界面
                Toast.makeText(this, "点击了设置", Toast.LENGTH_LONG).show();
                break;
            case R.id.order:
                //TODO：转到订单界面
                Toast.makeText(this, "点击了订单", Toast.LENGTH_LONG).show();
                break;
            case R.id.pay:
                //TODO：弹出框确认支付
                Toast.makeText(this, "点击了支付", Toast.LENGTH_LONG).show();
                break;
            case R.id.add_shopping_cart:
                //TODO：弹出框设置购物车名
                final CommonDialog dialog = new CommonDialog(MainActivity.this);
                dialog.setTitle("增加购物车")
                        .setTextView_1_message("购物车名：")
                        .setSingle(false).setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                        CommonDialog.instance.onClick(true, 1);
                    }

                    @Override
                    public void onNegtiveClick() {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "点击了取消", Toast.LENGTH_LONG).show();
                    }
                }).show();
                Toast.makeText(this, "增加购物车", Toast.LENGTH_LONG).show();
                break;
        }
    }

    /**
     * 获取所有类别
     * @return
     */
    public List<Category> getCategory() {
        RequestBody body = new FormBody.Builder()
                .add("enabled","1")
                .build();
        String res = httpUtility.post(body, "http://192.168.43.245:10031/category").toString();
        categoryList= (List<Category>) JsonUtility.getList(res,new TypeToken<ArrayList<Category>>() {}.getType());
        return categoryList;
    }

    /**
     * 获取该类别下的商品
     * @param category
     * @param change
     * @return
     */
    public List<Good> getGood(int category, boolean change) {
        RequestBody body = new FormBody.Builder()
                .add("enabled","1")
                .build();
        String res = httpUtility.post(body, "http://192.168.43.245:10031/good/" + category).toString();
        goodList= (List<Good>) JsonUtility.getList(res,new TypeToken<ArrayList<Good>>() {}.getType());
        if (change && res != null) {
            goodAdapter = new GoodAdapter(goodList);
            main_recycler.setAdapter(goodAdapter);
        }
        return goodList;
    }

    /**
     * 增加欲购买的商品至左边菜单
     * @param good
     */
    public void addGoodWant(Good good) {
        goodList_want.add(good);
        menuAdapter.notifyDataSetChanged();
    }

    /**
     * 新增购物车
     */
    public void saveShoppingCart(String name){
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setName(name);
        shoppingCart.setEnabled(true);
        RequestBody body = new FormBody.Builder()
                .add("name", shoppingCart.getName())
                .add("enabled", "1")
                .build();
        String res = httpUtility.post(body, "http://192.168.43.245:10031/shoppingcart/name").toString();
        ShoppingCart sc = (ShoppingCart)JsonUtility.fromJson(res, ShoppingCart.class);
        //判断该购物车是否已存在
        if (sc == null) {
            httpUtility.post(body, "http://192.168.43.245:10031/shoppingcart/save");
            shoppingCartList.add(shoppingCart);
            bottomAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(MainActivity.this, "已经存在该购物车", Toast.LENGTH_LONG).show();
        }
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
    /**
     * 双击退出函数
     *
     */
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

    /**
     * 以下代码仅做参考
     */
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
