package com.example.freshtext.Activity;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.freshtext.MainActivity;
import com.example.freshtext.R;

public class CommonDialog extends Dialog {
    public static CommonDialog instance = null;

    private TextView titleTv ; //标题
    private TextView textView_1 ; //第一个填空的标题
    private EditText editText_1; //第一个填空的内容
    private Button negtiveBn ,positiveBn; //取消和确认按钮
    private View columnLineView ; //分割线

    public CommonDialog(@NonNull Context context) {
        super(context, R.style.CustomDialog);
    }

    /**
     * 都是内容数据
     */
    private String textView_1_message, editText_1_message;
    private String title;
    private String positive,negtive ;
    private int imageResId = -1 ;

    /**
     * 底部是否只有一个按钮
     */
    private boolean isSingle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common);
        instance = this;
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面数据
        refreshView();
        //初始化界面控件的事件
        initEvent();
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        positiveBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( onClickBottomListener!= null) {
                    onClickBottomListener.onPositiveClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        negtiveBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( onClickBottomListener!= null) {
                    onClickBottomListener.onNegtiveClick();
                }
            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void refreshView() {
        //如果用户自定了title和message
        if (!TextUtils.isEmpty(title)) {
            titleTv.setText(title);
            titleTv.setVisibility(View.VISIBLE);
        }else {
            titleTv.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(textView_1_message)) {
            textView_1.setText(textView_1_message);
            textView_1.setVisibility(View.VISIBLE);
        } else {
            textView_1.setVisibility(View.GONE);
            editText_1.setVisibility(View.GONE);
        }
        //如果设置按钮的文字
        if (!TextUtils.isEmpty(positive)) {
            positiveBn.setText(positive);
        }else {
            positiveBn.setText("确定");
        }
        if (!TextUtils.isEmpty(negtive)) {
            negtiveBn.setText(negtive);
        }else {
            negtiveBn.setText("取消");
        }

        /**
         * 只显示一个按钮的时候隐藏取消按钮，回掉只执行确定的事件
         */
        if (isSingle){
            columnLineView.setVisibility(View.GONE);
            negtiveBn.setVisibility(View.GONE);
        }else {
            negtiveBn.setVisibility(View.VISIBLE);
            columnLineView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void show() {
        super.show();
        refreshView();
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        negtiveBn = (Button) findViewById(R.id.negtive);
        positiveBn = (Button) findViewById(R.id.positive);
        titleTv = (TextView) findViewById(R.id.title);
        textView_1 = (TextView) findViewById(R.id.text_1);
        editText_1 = (EditText) findViewById(R.id.editText_1);
        columnLineView = findViewById(R.id.column_line);
    }

    /**
     * 设置确定取消按钮的回调
     */
    public OnClickBottomListener onClickBottomListener;
    public CommonDialog setOnClickBottomListener(OnClickBottomListener onClickBottomListener) {
        this.onClickBottomListener = onClickBottomListener;
        return this;
    }
    public interface OnClickBottomListener{
        /**
         * 点击确定按钮事件
         */
        public void onPositiveClick();
        /**
         * 点击取消按钮事件
         */
        public void onNegtiveClick();
    }

    /**
     * 主活动点击按钮的回调
     * @param check 区分确认和取消按钮
     * @param activityNo 区分是哪个功能模块的回调
     */
    public void onClick(boolean check, int activityNo) {
        if (check) {
            switch (activityNo) {
                //添加购物车模块
                case 1:
                    System.out.println(editText_1.getText().toString());
                    MainActivity.instance.saveShoppingCart(editText_1.getText().toString());
                    break;
            }
        } else {
            editText_1_message = "";
        }
    }

    public String getTextView_1_message() {
        return textView_1_message;
    }

    public CommonDialog setTextView_1_message(String textView_1_message) {
        this.textView_1_message = textView_1_message;
        return this ;
    }

    public String getTitle() {
        return title;
    }

    public CommonDialog setTitle(String title) {
        this.title = title;
        return this ;
    }

    public String getEditText_1_message() {
        return editText_1_message;
    }

    public CommonDialog setEditText_1_message(String editText_1_message) {
        this.editText_1_message = editText_1_message;
        return this;
    }

    public String getPositive() {
        return positive;
    }

    public CommonDialog setPositive(String positive) {
        this.positive = positive;
        return this ;
    }

    public String getNegtive() {
        return negtive;
    }

    public CommonDialog setNegtive(String negtive) {
        this.negtive = negtive;
        return this ;
    }

    public int getImageResId() {
        return imageResId;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public CommonDialog setSingle(boolean single) {
        isSingle = single;
        return this ;
    }

    public CommonDialog setImageResId(int imageResId) {
        this.imageResId = imageResId;
        return this ;
    }
}
