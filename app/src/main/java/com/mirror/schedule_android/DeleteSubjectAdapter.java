package com.mirror.schedule_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class DeleteSubjectAdapter extends RecyclerView.Adapter<DeleteSubjectAdapter.MyViewHolder> {
    private List<MySubjects> itemList;
    static public View.OnClickListener onClick;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView location;
        TextView rest;
        TextView schedule;
        View rootView;


        public MyViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            location = (TextView) v.findViewById(R.id.location);
            rest = (TextView) v.findViewById(R.id.rest);
            schedule = (TextView) v.findViewById(R.id.schedule);

            rootView = v;
            rootView.setClickable(true);
            rootView.setEnabled(true);
            rootView.setOnClickListener(onClick);

        }
    }

    public DeleteSubjectAdapter(List<MySubjects> myDataset, View.OnClickListener listener) {
        itemList = myDataset;
        onClick = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deletesubjectadapter, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.rootView.setTag(position);

        holder.title.setText(itemList.get(position).getTitle() + " - " + itemList.get(position).getProfessor());
        holder.rest.setText(itemList.get(position).getRest());
        holder.location.setText(itemList.get(position).getLocation());
        holder.schedule.setText(itemList.get(position).getSchedules());



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }
}
