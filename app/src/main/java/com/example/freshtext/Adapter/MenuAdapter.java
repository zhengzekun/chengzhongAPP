package com.example.freshtext.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.freshtext.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private List<String> list;
    static class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public MenuViewHolder(View view) {
            super(view);
            imageView = (ImageView)view.findViewById(R.id.image);
            textView = (TextView)view.findViewById(R.id.title);
        }
    }

    public MenuAdapter(List<String> list_2) {
        list = list_2;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        final MenuViewHolder holder = new MenuViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                String str = list.get(position);
                Toast.makeText(v.getContext(), "点击了" + str, Toast.LENGTH_LONG).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        String str = list.get(position);
        holder.imageView.setImageResource(R.mipmap.ic_launcher);
        holder.textView.setText(str);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
