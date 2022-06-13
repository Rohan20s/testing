package com.example.jsonextract;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapterA extends RecyclerView.Adapter<MyAdapterA.ViewHolder> {
    Context context;
     ArrayList<Atc> img;
    public MyAdapterA(@NonNull Context context, ArrayList<Atc> img) {
        this.img=img;
        this.context=context;
    }

    @NonNull
    @Override
    public MyAdapterA.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_article,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int i=position+1;
        holder.tvN.setText(""+i);
        holder.tvr2.setText(img.get(position).getTitle());
        holder.tvr.setText(img.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return img.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvr,tvr2,tvN;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvN=itemView.findViewById(R.id.textView5);
            tvr2=itemView.findViewById(R.id.textView4);
            tvr=itemView.findViewById(R.id.textView3);
        }
    }

}

