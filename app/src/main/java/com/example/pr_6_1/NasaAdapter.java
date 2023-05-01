package com.example.pr_6_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NasaAdapter extends RecyclerView.Adapter<NasaAdapter.NasaViewHolder> {

    private Context context;
    private ArrayList<Nasa> nasaList;

    public NasaAdapter(Context context, ArrayList<Nasa> nasaList) {
        this.context = context;
        this.nasaList = nasaList;
    }

    @NonNull
    @Override
    public NasaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new NasaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NasaViewHolder holder, int position) {
        Nasa nasa = nasaList.get(position);
        holder.tvTitle.setText(nasa.getTitulo());
        holder.tvHttp.setText(nasa.getHttp());
        holder.tvUrl.setText(nasa.getUrl());
    }

    @Override
    public int getItemCount() {
        return nasaList.size();
    }

    public class NasaViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvHttp, tvUrl;

        public NasaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvHttp = itemView.findViewById(R.id.tv_http);
            tvUrl = itemView.findViewById(R.id.tv_url);
        }
    }
}
