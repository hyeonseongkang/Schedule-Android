package com.mirror.schedule_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText studentId;
    private Button loginButton, memberButton;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("students");

    ArrayList<String> students = new ArrayList<String>();

    private RadioGroup radioGroup;

    DatabaseReference myRef2 = database.getReference("subjects");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        studentId = (EditText) findViewById(R.id.studentId);
        loginButton = (Button) findViewById(R.id.loginButton);
        memberButton = (Button) findViewById(R.id.memberButton);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String id = studentId.getText().toString();

                if (id.equals("") || id == null) {
                    Toast.makeText(LoginActivity.this, "학번을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                students.clear();

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1: snapshot.getChildren()) {
                            String value = (String) snapshot1.getValue();
                            students.add(value);

                        }

                        if (students.contains(id)) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("studentID", id);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(LoginActivity.this, "가입되지 않은 학번입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });


            }
        });

        memberButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String id = studentId.getText().toString();
                Toast.makeText(LoginActivity.this, id+"" , Toast.LENGTH_SHORT).show();
                if (id.equals("") || id == null) {
                    Toast.makeText(LoginActivity.this, "학번을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                students.clear();

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1: snapshot.getChildren()) {
                            String value = (String) snapshot1.getValue();
                            students.add(value);

                        }

                        if (students.contains(id)) {
                            Toast.makeText(LoginActivity.this, "이미 가입된 학번입니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            myRef.push().setValue(id);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }
        });
    }
}