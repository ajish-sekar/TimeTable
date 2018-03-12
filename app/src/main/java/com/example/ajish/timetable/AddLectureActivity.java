package com.example.ajish.timetable;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ajish.timetable.db.DbHandler;
import com.example.ajish.timetable.db.Lectures;

public class AddLectureActivity extends AppCompatActivity {

    int startHour=0;
    int startMinute=0;
    int endHour=0;
    int endMinute=0;
    int flag = 0;
    DbHandler db;
    String day;
    TextView startTime, endTime;
    EditText subject;
    Button add, changeStartTime, changeEndTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lecture);
        startTime = (TextView) findViewById(R.id.add_start_time);
        endTime = (TextView)findViewById(R.id.add_end_time);
        add = (Button) findViewById(R.id.add_new_lecture);
        changeEndTime = (Button) findViewById(R.id.change_end_time);
        changeStartTime = (Button) findViewById(R.id.change_start_time);
        subject = (EditText) findViewById(R.id.add_subject);

        day = getIntent().getStringExtra("day");

        db = new DbHandler(this);
        changeStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 0;
                new TimePickerDialog(AddLectureActivity.this, timeSetListener, startHour, startMinute, false).show();
            }
        });

        changeEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 1;
                new TimePickerDialog(AddLectureActivity.this, timeSetListener, endHour, endMinute, false).show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(subject.getText().toString().equals("")){
                    Toast.makeText(AddLectureActivity.this, "Do not Leave the Subject Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                String sub = subject.getText().toString();
                String sTime = startTime.getText().toString();
                String eTime = endTime.getText().toString();
                Lectures lectures = new Lectures(day, sub, eTime, sTime);
                db.addLecture(lectures);
                finish();
            }
        });
    }

    private TimePickerDialog.OnTimeSetListener timeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                 if(flag == 0){
                     startHour = i;
                     startMinute = i1;
                     startTime.setText(startHour+":"+startMinute);
                 }else{
                     endHour = i;
                     endMinute = i1;
                     endTime.setText(endHour+":"+endMinute);
                 }
                }
            };
}
