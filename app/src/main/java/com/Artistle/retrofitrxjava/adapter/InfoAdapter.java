package com.Artistle.retrofitrxjava.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Artistle.retrofitrxjava.model.InfoModel;
import com.learn2crack.retrofitrxjava.R;

import java.util.ArrayList;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {
    private ArrayList<InfoModel> info;

    public InfoAdapter(ArrayList<InfoModel> info) {
        this.info = info;
    }

    @Override
    public InfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_model, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(InfoAdapter.ViewHolder holder, int position) {
        InfoModel infoModel = info.get(position);


        holder.title.setText(infoModel.getTitle());
        holder.body.setText(infoModel.getBody());
    }

    @Override
    public int getItemCount() {
        return info.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView body;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.info_title);
            body = (TextView)itemView.findViewById(R.id.info_body);
        }
    }
}
