package com.mirror.schedule_android.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mirror.schedule_android.R;

import java.util.List;

class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.MyViewHolder> {
    private List<Subject> itemList;
    static public View.OnClickListener onClick;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView professor;
        TextView location;
        TextView rest;
        TextView schedule;

        View rootView;


        public MyViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            //professor = (TextView) v.findViewById(R.id.professor);
            location = (TextView) v.findViewById(R.id.location);
            rest = (TextView) v.findViewById(R.id.rest);
            schedule = (TextView) v.findViewById(R.id.schedule);

            rootView = v;
            rootView.setClickable(true);
            rootView.setEnabled(true);
            rootView.setOnClickListener(onClick);

        }
    }

    public SubjectAdapter(List<Subject> myDataset, View.OnClickListener listener) {
        itemList = myDataset;
        onClick = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subjectadapter, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.rootView.setTag(position);

        holder.title.setText(itemList.get(position).getTitle() + " - " + itemList.get(position).getProfessor());
        //holder.professor.setText(" - " + itemList.get(position).getProfessor());
        holder.location.setText(itemList.get(position).getLocation());
        holder.rest.setText(itemList.get(position).getRest());
        holder.schedule.setText(itemList.get(position).getSchedule());



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }
}
