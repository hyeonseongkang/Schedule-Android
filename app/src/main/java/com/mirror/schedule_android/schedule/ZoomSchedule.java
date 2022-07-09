package com.mirror.schedule_android.schedule;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mirror.schedule_android.R;

import java.util.List;

public class ZoomSchedule extends AppCompatActivity {

    private TextView monday[] = new TextView[13];
    private TextView tuesday[] = new TextView[13];
    private TextView wednesday[] = new TextView[13];
    private TextView thursday[] = new TextView[13];
    private TextView friday[] = new TextView[13];
    private ScrollView scrollView;
    private ImageView finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_schedule);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        init();
        addSchedule(ScheduleFragment.mySubjects);

        finish = (ImageView) findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void init() {
        monday[0] = (TextView) findViewById(R.id.monday0);
        monday[1] = (TextView) findViewById(R.id.monday1);
        monday[2] = (TextView) findViewById(R.id.monday2);
        monday[3] = (TextView) findViewById(R.id.monday3);
        monday[4] = (TextView) findViewById(R.id.monday4);
        monday[5] = (TextView) findViewById(R.id.monday5);
        monday[6] = (TextView) findViewById(R.id.monday6);
        monday[7] = (TextView) findViewById(R.id.monday7);
        monday[8] = (TextView) findViewById(R.id.monday8);
        monday[9] = (TextView) findViewById(R.id.monday9);
        monday[10] = (TextView) findViewById(R.id.monday10);
        monday[11] = (TextView) findViewById(R.id.monday11);
        monday[12] = (TextView) findViewById(R.id.monday12);

        tuesday[0] = (TextView) findViewById(R.id.tuesday0);
        tuesday[1] = (TextView) findViewById(R.id.tuesday1);
        tuesday[2] = (TextView) findViewById(R.id.tuesday2);
        tuesday[3] = (TextView) findViewById(R.id.tuesday3);
        tuesday[4] = (TextView) findViewById(R.id.tuesday4);
        tuesday[5] = (TextView) findViewById(R.id.tuesday5);
        tuesday[6] = (TextView) findViewById(R.id.tuesday6);
        tuesday[7] = (TextView) findViewById(R.id.tuesday7);
        tuesday[8] = (TextView) findViewById(R.id.tuesday8);
        tuesday[9] = (TextView) findViewById(R.id.tuesday9);
        tuesday[10] = (TextView) findViewById(R.id.tuesday10);
        tuesday[11] = (TextView) findViewById(R.id.tuesday11);
        tuesday[12] = (TextView) findViewById(R.id.tuesday12);

        wednesday[0] = (TextView) findViewById(R.id.wednesday0);
        wednesday[1] = (TextView) findViewById(R.id.wednesday1);
        wednesday[2] = (TextView) findViewById(R.id.wednesday2);
        wednesday[3] = (TextView) findViewById(R.id.wednesday3);
        wednesday[4] = (TextView) findViewById(R.id.wednesday4);
        wednesday[5] = (TextView) findViewById(R.id.wednesday5);
        wednesday[6] = (TextView) findViewById(R.id.wednesday6);
        wednesday[7] = (TextView) findViewById(R.id.wednesday7);
        wednesday[8] = (TextView) findViewById(R.id.wednesday8);
        wednesday[9] = (TextView) findViewById(R.id.wednesday9);
        wednesday[10] = (TextView) findViewById(R.id.wednesday10);
        wednesday[11] = (TextView) findViewById(R.id.wednesday11);
        wednesday[12] = (TextView) findViewById(R.id.wednesday12);

        thursday[0] = (TextView) findViewById(R.id.thursday0);
        thursday[1] = (TextView) findViewById(R.id.thursday1);
        thursday[2] = (TextView) findViewById(R.id.thursday2);
        thursday[3] = (TextView) findViewById(R.id.thursday3);
        thursday[4] = (TextView) findViewById(R.id.thursday4);
        thursday[5] = (TextView) findViewById(R.id.thursday5);
        thursday[6] = (TextView) findViewById(R.id.thursday6);
        thursday[7] = (TextView) findViewById(R.id.thursday7);
        thursday[8] = (TextView) findViewById(R.id.thursday8);
        thursday[9] = (TextView) findViewById(R.id.thursday9);
        thursday[10] = (TextView) findViewById(R.id.thursday10);
        thursday[11] = (TextView) findViewById(R.id.thursday11);
        thursday[12] = (TextView) findViewById(R.id.thursday12);

        friday[0] = (TextView) findViewById(R.id.friday0);
        friday[1] = (TextView) findViewById(R.id.friday1);
        friday[2] = (TextView) findViewById(R.id.friday2);
        friday[3] = (TextView) findViewById(R.id.friday3);
        friday[4] = (TextView) findViewById(R.id.friday4);
        friday[5] = (TextView) findViewById(R.id.friday5);
        friday[6] = (TextView) findViewById(R.id.friday6);
        friday[7] = (TextView) findViewById(R.id.friday7);
        friday[8] = (TextView) findViewById(R.id.friday8);
        friday[9] = (TextView) findViewById(R.id.friday9);
        friday[10] = (TextView) findViewById(R.id.friday10);
        friday[11] = (TextView) findViewById(R.id.friday11);
        friday[12] = (TextView) findViewById(R.id.friday12);
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
        for(int i = 0; i < list.size(); i++) {
            String[] str = list.get(i).getSchedule();

            for(int j = 0; j < str.length; j++) {

                String day = String.valueOf(str[j].charAt(0));
                int index = Integer.parseInt(String.valueOf(str[j].charAt(2))) - 1;

                if(j == 0) {
                    switch(day) {
                        case "월":
                            scrollToView(monday[index],scrollView, 0);
                            break;

                        case "화":
                            scrollToView(tuesday[index],scrollView, 0);
                            break;

                        case "수":
                            scrollToView(wednesday[index],scrollView, 0);
                            break;

                        case "목":
                            scrollToView(thursday[index],scrollView, 0);
                            break;

                        case "금":
                            scrollToView(friday[index],scrollView, 0);
                            break;

                        case "토":
                            scrollToView(monday[index],scrollView, 0);
                            break;
                    }

                }

                switch (day) {
                    case "월":
                        if(!(TextUtils.isEmpty(monday[index].getText()))) {
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
        if(view != null && view != scrollView) {
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