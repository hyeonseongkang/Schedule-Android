package com.mirror.schedule_android.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mirror.schedule_android.R;
import com.mirror.schedule_android.schedule.ScheduleFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatScreen extends AppCompatActivity {

    private static final String TAG = "ChatScreen";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("chattingRoom");
    String studentID = ScheduleFragment.studentID;
    String key;

    private AppCompatImageButton sendButton;
    private EditText message;
    private TextView users, title;

    List<ChatData> chatDataList = new ArrayList<>();
    RecyclerView recyclerView;
    ChatAdapter chatAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        title = (TextView) findViewById(R.id.title);
        title.setText(intent.getStringExtra("title"));

        sendButton = (AppCompatImageButton) findViewById(R.id.sendButton);
        users = (TextView) findViewById(R.id.users);
        message = (EditText) findViewById(R.id.message);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendMessage = message.getText().toString();
                if (sendMessage.equals("")) {
                    return;
                }
                SimpleDateFormat format1 = new SimpleDateFormat ( "HH:mm");
                Calendar time = Calendar.getInstance();
                String format_time1 = format1.format(time.getTime());
                myRef.child(key).child("chat").push().setValue(new ChatData(studentID, sendMessage, format_time1));
                message.setText("");
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ChatScreen.this);
        recyclerView.setLayoutManager(layoutManager);
        chatAdapter = new ChatAdapter(chatDataList, studentID);
        recyclerView.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onStart() {
        super.onStart();
        myRef.child(key).child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userList = "";
                for(DataSnapshot snapshot1: snapshot.getChildren()) {
                    String value = (String) snapshot1.getValue();
                    userList += "'"+value+"' ";
                }
                users.setText(userList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        chatDataList.clear();
        myRef.child(key).child("chat").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d(TAG, "onChildAdded:" + snapshot.getKey());
                ChatData chatData = snapshot.getValue(ChatData.class);
                chatDataList.add(chatData);
                recyclerView.scrollToPosition(chatDataList.size() - 1);
                chatAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}