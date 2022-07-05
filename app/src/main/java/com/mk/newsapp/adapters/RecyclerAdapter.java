package com.mk.newsapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mk.newsapp.R;
import com.mk.newsapp.models.ModelClass;
import com.mk.newsapp.webView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<ModelClass> modelClassList;

    public RecyclerAdapter(Context context, ArrayList<ModelClass> modelClassList) {
        this.context = context;
        this.modelClassList = modelClassList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_item_layout,null,false);
        return new ViewHolder(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context, webView.class);
                in.putExtra("url",modelClassList.get(position).getUrl());
                context.startActivity(in);
            }
        });

        holder.heading.setText(modelClassList.get(position).getTitle());
        Glide.with(context).load(modelClassList.get(position).getUrlToImage()).into(holder.mainImg);

    }

    @Override
    public int getItemCount() {
        return modelClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mainImg;
        TextView heading;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            heading = itemView.findViewById(R.id.heading);
            mainImg = itemView.findViewById(R.id.img);
            cardView = itemView.findViewById(R.id.cardview);

        }
    }
}
