package com.mirror.schedule_android.chat;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mirror.schedule_android.R;
import com.mirror.schedule_android.schedule.DeleteSubjectAdapter;
import com.mirror.schedule_android.schedule.MySubjects;
import com.mirror.schedule_android.schedule.ScheduleFragment;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<String> users = new ArrayList<String>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("chattingRoom");
    String studentID = ScheduleFragment.studentID;
    List<MySubjects> mySubjects = ScheduleFragment.mySubjects;

    View v;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.chatfragment, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.chatFragment);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new DeleteSubjectAdapter(mySubjects, new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Object obj = v.getTag();
                if(obj != null){
                    final int position = (int) obj;
                    myRef.child(mySubjects.get(position).getKey()).child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            users.clear();
                            for(DataSnapshot snapshot1: snapshot.getChildren()) {
                                String value = (String) snapshot1.getValue();
                                System.out.println(value);
                                users.add(value);
                            }
                            if (!(users.contains(studentID))) {
                                myRef.child(mySubjects.get(position).getKey()).child("users").push().setValue(studentID);
                            }
                            Intent intent = new Intent(getActivity(), ChatScreen.class);
                            intent.putExtra("key", ScheduleFragment.mySubjects.get(position).getKey());
                            intent.putExtra("title", ScheduleFragment.mySubjects.get(position).getTitle());
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });


                }
            }
        });
        recyclerView.setAdapter(mAdapter);

        return v;
    }
}
