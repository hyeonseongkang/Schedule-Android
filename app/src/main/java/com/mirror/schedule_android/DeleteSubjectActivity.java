package com.mirror.schedule_android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DeleteSubjectActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ImageView finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_subject);
        recyclerView = (RecyclerView) findViewById(R.id.deleteSubject);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(DeleteSubjectActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        finish = (ImageView) findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        mAdapter = new DeleteSubjectAdapter(ScheduleFragment.mySubjects, new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Object obj = v.getTag();
                if(obj != null){
                    final int position = (int) obj;
                    AlertDialog.Builder builder = new AlertDialog.Builder(DeleteSubjectActivity.this);
                    builder.setTitle("삭제");
                    builder.setMessage("'" + ScheduleFragment.mySubjects.get(position).getTitle() +"' 과목을 삭제하시겠습니까?");
                    builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ScheduleFragment.mySubjects.remove(position);
                            mAdapter.notifyItemRemoved(position);
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();

                }
            }
        });
        recyclerView.setAdapter(mAdapter);

    }
}