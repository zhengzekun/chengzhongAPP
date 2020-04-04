package com.example.freshtext.Utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.freshtext.R;

public class UserItemView extends RelativeLayout {
    private TextView data;

    public UserItemView(final Context context, AttributeSet attrs){
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_user, this);
        @SuppressLint("CustomViewStyleable") TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PersonaltemView);

        ImageView icon = findViewById(R.id.icon);
        ImageView more = findViewById(R.id.more);
        ImageView line = findViewById(R.id.line);
        TextView name = findViewById(R.id.name);
        data = findViewById(R.id.data);

        icon.setImageDrawable(typedArray.getDrawable(R.styleable.PersonaltemView_icon));
        name.setText(typedArray.getText(R.styleable.PersonaltemView_name));

        if (typedArray.getBoolean(R.styleable.PersonaltemView_show_more, false)){
            more.setVisibility(VISIBLE);
        }
        if (typedArray.getBoolean(R.styleable.PersonaltemView_show_line, false)){
            line.setVisibility(VISIBLE);
        }
        typedArray.recycle();
    }

    // 提供设置控件的描述数据
    public void setData(String data){
        this.data.setText(data);
    }
}
