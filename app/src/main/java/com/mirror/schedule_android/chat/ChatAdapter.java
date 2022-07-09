package com.mirror.schedule_android.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mirror.schedule_android.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder>{

    private List<ChatData> dataList;
    private String user;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView userName, date, message;
        public MyViewHolder(View v) {
            super(v);
            userName = (TextView) v.findViewById(R.id.userName);
            message = (TextView) v.findViewById(R.id.message);
            date = (TextView) v.findViewById(R.id.time);

        }
    }

    public ChatAdapter(List<ChatData> getData, String user1) {
        dataList = getData;
        user = user1;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if (viewType == 1) {
            v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.chatadapter, parent, false);
        } else {
            v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.chatadaptertwo, parent, false);
        }


        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        if(dataList.get(position).getUser().equals(user)) {
            return 1;
        } else {
            return 2;
        }
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.date.setText(dataList.get(position).getTime());
        holder.message.setText(dataList.get(position).getMessage());
        holder.userName.setText(dataList.get(position).getUser());
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

}
