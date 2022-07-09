package com.mirror.schedule_android.schedule;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ScheduleFragment extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("subjects");
    DatabaseReference myRef2 = database.getReference("mySubjects");

    List<Subject> allSubjects = new ArrayList<>();

    List<Subject> subjects = new ArrayList<>();
    public static List<MySubjects> mySubjects = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ScrollView scrollView;
    private ProgressBar progressBar;
    private RadioGroup radioGroup;
    private EditText searchValue;
    private Button searchButton;
    private TextView nullValue;
    private ImageView wastebaske, zoom;

    private TextView monday[] = new TextView[13];
    private TextView tuesday[] = new TextView[13];
    private TextView wednesday[] = new TextView[13];
    private TextView thursday[] = new TextView[13];
    private TextView friday[] = new TextView[13];

    public static final int REQUEST_CODE = 100;
    public static String studentID;

    View v;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.schedulefragment, container, false);

        Intent intent = getActivity().getIntent();
        studentID = intent.getStringExtra("studentID");

        /*
        testData
            public Subject(String key, String title, String professor,
                   String location, String rest, String schedule,
                   String latitude, String longitude)
                           String key = myRef.push().getKey();
        myRef.child(key).setValue(new Subject(key, "컴퓨터과학과코딩", "정승한",
                "전주:공과대학 1호관 158", "교양 3", "화 3-A,화 3-B,목 1-A,목 1-B,목 2-A,목 2-B",
                "35.84662802030483", "127.13266095072535"));
         */

        myRef2.child(studentID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    MySubjects subject = snapshot1.getValue(MySubjects.class);

                    String[] array = subject.getSchedules().split(",");
                    MySubjects mySubjects1 = new MySubjects(subject.getKey(), subject.getTitle(), array, subject.getSchedules(),
                            subject.getLocation(), subject.getProfessor(), subject.getRest(), subject.getLatitude(), subject.getLongitude());
                    mySubjects.add(mySubjects1);
                }
                addSchedule(mySubjects);

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        scrollView = (ScrollView) v.findViewById(R.id.scrollView);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        wastebaske = (ImageView) v.findViewById(R.id.deleteSubject);
        zoom = (ImageView) v.findViewById(R.id.zoomScreen);

        wastebaske.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mySubjects.size() == 0) {
                    Toast.makeText(getActivity(), "과목을 추가해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getActivity(), DeleteSubjectActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mySubjects.size() == 0) {
                    Toast.makeText(getActivity(), "과목을 추가해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getActivity(), ZoomSchedule.class);
                startActivity(intent);

            }
        });

        searchValue = (EditText) v.findViewById(R.id.searchValue);
        searchButton = (Button) v.findViewById(R.id.searchButton);
        nullValue = (TextView) v.findViewById(R.id.nullValue);
        // 검색 버튼 Click
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nullValue.setVisibility(View.GONE);
                int id = radioGroup.getCheckedRadioButtonId();
                String value = searchValue.getText().toString();

                if (id == R.id.all) {
                    searchValue.setText("");
                    setAdapter(allSubjects);
                } else if (id == R.id.subject) {

                    List<Subject> subject = new ArrayList<>();

                    for (int j = 0; j < allSubjects.size(); j++) {
                        if (value.equals(allSubjects.get(j).getTitle())) {
                            subject.add(allSubjects.get(j));
                        }
                    }

                    if (subject.size() == 0) {
                        nullValue.setText("과목명: '" + value + "'에 대한 결과가 존재하지 않습니다.");
                        nullValue.setVisibility(View.VISIBLE);
                    }
                    searchValue.setText("");
                    setAdapter(subject);

                } else if (id == R.id.professor) {
                    List<Subject> subject = new ArrayList<>();
                    for (int j = 0; j < allSubjects.size(); j++) {
                        if (value.equals(allSubjects.get(j).getProfessor())) {
                            subject.add(allSubjects.get(j));
                        }
                    }
                    if (subject.size() == 0) {
                        nullValue.setText("교수명: '" + value + "'에 대한 결과가 존재하지 않습니다.");
                        nullValue.setVisibility(View.VISIBLE);
                    }
                    searchValue.setText("");
                    setAdapter(subject);
                }
            }
        });
        radioGroup = (RadioGroup) v.findViewById(R.id.radioGroup);

        progressBar = (ProgressBar) v.findViewById(R.id.progress);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Subject subject = snapshot1.getValue(Subject.class);
                    Subject subject1 = new Subject(snapshot1.getKey(),
                            subject.getTitle(),
                            subject.getProfessor(),
                            subject.getLocation(),
                            subject.getRest(),
                            subject.getSchedule(),
                            subject.getLatitude(),
                            subject.getLongitude());
                    subjects.add(subject1);
                }
                allSubjects = subjects;
                setAdapter(allSubjects);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        init();

        return v;
    }

    private void init() {
        monday[0] = (TextView) v.findViewById(R.id.monday0);
        monday[1] = (TextView) v.findViewById(R.id.monday1);
        monday[2] = (TextView) v.findViewById(R.id.monday2);
        monday[3] = (TextView) v.findViewById(R.id.monday3);
        monday[4] = (TextView) v.findViewById(R.id.monday4);
        monday[5] = (TextView) v.findViewById(R.id.monday5);
        monday[6] = (TextView) v.findViewById(R.id.monday6);
        monday[7] = (TextView) v.findViewById(R.id.monday7);
        monday[8] = (TextView) v.findViewById(R.id.monday8);
        monday[9] = (TextView) v.findViewById(R.id.monday9);
        monday[10] = (TextView) v.findViewById(R.id.monday10);
        monday[11] = (TextView) v.findViewById(R.id.monday11);
        monday[12] = (TextView) v.findViewById(R.id.monday12);

        tuesday[0] = (TextView) v.findViewById(R.id.tuesday0);
        tuesday[1] = (TextView) v.findViewById(R.id.tuesday1);
        tuesday[2] = (TextView) v.findViewById(R.id.tuesday2);
        tuesday[3] = (TextView) v.findViewById(R.id.tuesday3);
        tuesday[4] = (TextView) v.findViewById(R.id.tuesday4);
        tuesday[5] = (TextView) v.findViewById(R.id.tuesday5);
        tuesday[6] = (TextView) v.findViewById(R.id.tuesday6);
        tuesday[7] = (TextView) v.findViewById(R.id.tuesday7);
        tuesday[8] = (TextView) v.findViewById(R.id.tuesday8);
        tuesday[9] = (TextView) v.findViewById(R.id.tuesday9);
        tuesday[10] = (TextView) v.findViewById(R.id.tuesday10);
        tuesday[11] = (TextView) v.findViewById(R.id.tuesday11);
        tuesday[12] = (TextView) v.findViewById(R.id.tuesday12);

        wednesday[0] = (TextView) v.findViewById(R.id.wednesday0);
        wednesday[1] = (TextView) v.findViewById(R.id.wednesday1);
        wednesday[2] = (TextView) v.findViewById(R.id.wednesday2);
        wednesday[3] = (TextView) v.findViewById(R.id.wednesday3);
        wednesday[4] = (TextView) v.findViewById(R.id.wednesday4);
        wednesday[5] = (TextView) v.findViewById(R.id.wednesday5);
        wednesday[6] = (TextView) v.findViewById(R.id.wednesday6);
        wednesday[7] = (TextView) v.findViewById(R.id.wednesday7);
        wednesday[8] = (TextView) v.findViewById(R.id.wednesday8);
        wednesday[9] = (TextView) v.findViewById(R.id.wednesday9);
        wednesday[10] = (TextView) v.findViewById(R.id.wednesday10);
        wednesday[11] = (TextView) v.findViewById(R.id.wednesday11);
        wednesday[12] = (TextView) v.findViewById(R.id.wednesday12);

        thursday[0] = (TextView) v.findViewById(R.id.thursday0);
        thursday[1] = (TextView) v.findViewById(R.id.thursday1);
        thursday[2] = (TextView) v.findViewById(R.id.thursday2);
        thursday[3] = (TextView) v.findViewById(R.id.thursday3);
        thursday[4] = (TextView) v.findViewById(R.id.thursday4);
        thursday[5] = (TextView) v.findViewById(R.id.thursday5);
        thursday[6] = (TextView) v.findViewById(R.id.thursday6);
        thursday[7] = (TextView) v.findViewById(R.id.thursday7);
        thursday[8] = (TextView) v.findViewById(R.id.thursday8);
        thursday[9] = (TextView) v.findViewById(R.id.thursday9);
        thursday[10] = (TextView) v.findViewById(R.id.thursday10);
        thursday[11] = (TextView) v.findViewById(R.id.thursday11);
        thursday[12] = (TextView) v.findViewById(R.id.thursday12);

        friday[0] = (TextView) v.findViewById(R.id.friday0);
        friday[1] = (TextView) v.findViewById(R.id.friday1);
        friday[2] = (TextView) v.findViewById(R.id.friday2);
        friday[3] = (TextView) v.findViewById(R.id.friday3);
        friday[4] = (TextView) v.findViewById(R.id.friday4);
        friday[5] = (TextView) v.findViewById(R.id.friday5);
        friday[6] = (TextView) v.findViewById(R.id.friday6);
        friday[7] = (TextView) v.findViewById(R.id.friday7);
        friday[8] = (TextView) v.findViewById(R.id.friday8);
        friday[9] = (TextView) v.findViewById(R.id.friday9);
        friday[10] = (TextView) v.findViewById(R.id.friday10);
        friday[11] = (TextView) v.findViewById(R.id.friday11);
        friday[12] = (TextView) v.findViewById(R.id.friday12);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
            for (int i = 0; i < mySubjects.size(); i++) {
                System.out.println(mySubjects.get(i).getTitle());
            }
            addSchedule(mySubjects);
            scrollToView(monday[0], scrollView, 0);
        }
    }


    private void setAdapter(List<Subject> list) {
        mAdapter = new SubjectAdapter(list, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object obj = v.getTag();
                if (obj != null) {
                    final int position = (int) obj;
                    String[] str = list.get(position).getSchedule().split(",");
                    MySubjects mySubject = new MySubjects(list.get(position).getKey(), list.get(position).getTitle(), str, list.get(position).getSchedule(), list.get(position).getLocation(),
                            list.get(position).getProfessor(), list.get(position).getRest(), list.get(position).getLatitude(), list.get(position).getLongitude());
                    boolean overlap = false;
                    int overlapIndex = 0;
                    for (int i = 0; i < mySubjects.size(); i++) {
                        HashSet<String> map = new HashSet<>();
                        for (String str1 : mySubjects.get(i).getSchedule()) {
                            map.add(str1);
                        }
                        for (String str2 : str) {
                            if (map.contains(str2)) {
                                overlap = true;
                                overlapIndex = i;
                                break;
                            }
                        }
                    }
                    if (overlap) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("중복");
                        builder.setMessage("'" + mySubjects.get(overlapIndex).getTitle() + "' 수업과 시간이 겹칩니다. 교체하시겠습니까?");
                        int finalOverlapIndex = overlapIndex;
                        builder.setPositiveButton("교체하기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mySubjects.set(finalOverlapIndex, mySubject);
                                addSchedule(mySubjects);
                                return;
                            }
                        });
                        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                        builder.show();
                    } else {
                        mySubjects.add(mySubject);
                        addSchedule(mySubjects);
                    }
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


    private void initSchedule() {
        for (int i = 0; i < monday.length; i++) {
            monday[i].setText("");
            tuesday[i].setText("");
            wednesday[i].setText("");
            thursday[i].setText("");
            friday[i].setText("");
        }
    }


    private void addSchedule(List<MySubjects> list) {
        initSchedule();
        // 여기서 데이터 베이스에 저장!
        myRef2.child(studentID).removeValue();
        for (int i = 0; i < list.size(); i++) {
            String[] str = list.get(i).getSchedule();
            MySubjects mySubjects1 = new MySubjects(list.get(i).getKey(), list.get(i).getTitle(), list.get(i).getSchedules(), list.get(i).getLocation(),
                    list.get(i).getProfessor(), list.get(i).getRest(), list.get(i).getLatitude(), list.get(i).getLongitude());
            myRef2.child(studentID).push().setValue(mySubjects1);
            for (int j = 0; j < str.length; j++) {

                String day = String.valueOf(str[j].charAt(0));
                int index = Integer.parseInt(String.valueOf(str[j].charAt(2))) - 1;

                if (j == 0) {
                    switch (day) {
                        case "월":
                            scrollToView(monday[index], scrollView, 0);
                            break;

                        case "화":
                            scrollToView(tuesday[index], scrollView, 0);
                            break;

                        case "수":
                            scrollToView(wednesday[index], scrollView, 0);
                            break;

                        case "목":
                            scrollToView(thursday[index], scrollView, 0);
                            break;

                        case "금":
                            scrollToView(friday[index], scrollView, 0);
                            break;

                        case "토":
                            scrollToView(monday[index], scrollView, 0);
                            break;
                    }

                }

                switch (day) {
                    case "월":
                        if (!(TextUtils.isEmpty(monday[index].getText()))) {
                        }
                        monday[index].setText(list.get(i).getTitle() + "\n" + list.get(i).getLocation());
                        break;

                    case "화":
                        tuesday[index].setText(list.get(i).getTitle() + "\n" + list.get(i).getLocation());
                        break;

                    case "수":
                        wednesday[index].setText(list.get(i).getTitle() + "\n" + list.get(i).getLocation());
                        break;

                    case "목":
                        thursday[index].setText(list.get(i).getTitle() + "\n" + list.get(i).getLocation());
                        break;

                    case "금":
                        friday[index].setText(list.get(i).getTitle() + "\n" + list.get(i).getLocation());
                        break;

                    case "토":
                        monday[index].setText(list.get(i).getTitle() + "\n" + list.get(i).getLocation());
                        break;

                }
            }


        }
    }

    private static void scrollToView(View view, final ScrollView scrollView, int count) {
        if (view != null && view != scrollView) {
            count += view.getTop();
            scrollToView((View) view.getParent(), scrollView, count);
        } else if (scrollView != null) {
            final int finalCount = count;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    scrollView.smoothScrollTo(0, finalCount);
                }
            }, 200);

        }
    }
}