package com.example.jsonextract;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Context context;
    ArrayList<Img> img;
    public MyAdapter(@NonNull Context context, ArrayList<Img> img) {
        this.img=img;
        this.context=context;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_image,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Picasso.get().load(img.get(position).getUrl()).resize(157,147).into(holder.imgv);
        holder.tvr.setText(img.get(position).title);
    }

    @Override
    public int getItemCount() {
        return img.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

       ImageView imgv;
       TextView tvr;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           imgv=itemView.findViewById(R.id.imageView);
            tvr=itemView.findViewById(R.id.textView2);
        }
    }

}
