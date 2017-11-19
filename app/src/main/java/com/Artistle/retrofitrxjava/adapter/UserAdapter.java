package com.Artistle.retrofitrxjava.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Artistle.retrofitrxjava.Activity.InfoUserActivity;
import com.Artistle.retrofitrxjava.Data.DBHelper;
import com.Artistle.retrofitrxjava.R;
import com.Artistle.retrofitrxjava.model.UserModel;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private ArrayList<UserModel> userListModels;

    public UserAdapter(ArrayList<UserModel> androidList) {
        userListModels = androidList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_model, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserModel user = userListModels.get(position);

        holder.userName.setText(user.getName());
        holder.userMail.setText(user.getEmail());
        holder.userCompany.setText(user.getCompany().getName_company());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, InfoUserActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userListModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView userName;
        private TextView userCompany;
        private TextView userMail;
        public ViewHolder(View view) {
            super(view);

            userName = (TextView)view.findViewById(R.id.user_name);
            userCompany = (TextView)view.findViewById(R.id.user_company);
            userMail = (TextView)view.findViewById(R.id.user_email);
        }
    }
}
