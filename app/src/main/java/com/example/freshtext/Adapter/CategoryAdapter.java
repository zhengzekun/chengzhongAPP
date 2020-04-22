package com.example.freshtext.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freshtext.Entity.Category;
import com.example.freshtext.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.UpViewHolder> {
    private List<Category> categoryList;
    static class UpViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public UpViewHolder(View view) {
            super(view);
            textView = (TextView)view.findViewById(R.id.up_text);
        }
    }

    public CategoryAdapter(List<Category> categories) {
        categoryList = categories;
    }

    @NonNull
    @Override
    public UpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_up,parent,false);
        final UpViewHolder holder = new UpViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Category category = categoryList.get(position);
                Toast.makeText(v.getContext(), "点击了" + category.getName(), Toast.LENGTH_LONG).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UpViewHolder holder, int position) {
        String name = categoryList.get(position).getName();
        holder.textView.setText(name);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
