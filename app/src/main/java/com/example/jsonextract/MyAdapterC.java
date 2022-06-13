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

public class MyAdapterC extends RecyclerView.Adapter<MyAdapterC.ViewHolder> {
    Context context;
    ArrayList<Ctg> img;
    public MyAdapterC(@NonNull Context context, ArrayList<Ctg> img) {
        this.img=img;
        this.context=context;
    }

    @NonNull
    @Override
    public MyAdapterC.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int i=position+1;
        holder.tvN.setText(img.get(position).getCategory());

    }

    @Override
    public int getItemCount() {
        return img.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvr,tvr2,tvN;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvN=itemView.findViewById(R.id.textView6);

        }
    }

}


