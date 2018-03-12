package com.example.ajish.timetable;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new DayPageAdapter(getSupportFragmentManager()));
        fab = (FloatingActionButton) findViewById(R.id.add_lecture);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = viewPager.getCurrentItem();
                Intent intent = new Intent(MainActivity.this, AddLectureActivity.class);
                intent.putExtra("day", getDay(pos));
                startActivity(intent);
            }
        });
    }

    public String getDay(int pos){
        String day = "";
        switch (pos){
            case 0:
                day = "sunday";
                break;
            case 1:
                day = "monday";
                break;
            case 2:
                day = "tuesday";
                break;
            case 3:
                day = "wednesday";
                break;
            case 4:
                day = "thursday";
                break;
            case 5:
                day = "friday";
                break;
            case 6:
                day = "saturday";
                break;
        }

        return day;
    }
}
