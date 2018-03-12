package com.example.ajish.timetable.db;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by ajish on 2/28/18.
 */

public class Lectures implements Parcelable {

    private int id;
    private String day;
    private String subject;
    private String startTime;
    private String endTime;

    public Lectures(){}


    public Lectures(String day, String subject, String endTime, String startTime){
        this.day = day;
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Lectures(int id, String day, String subject, String endTime, String startTime){
        this.id = id;
        this.day = day;
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public String getDay() {
        return day;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeString(getDay());
        parcel.writeString(getSubject());
        parcel.writeString(getStartTime());
        parcel.writeString(getEndTime());
    }

    public static final Creator<Lectures> CREATOR = new Creator<Lectures>() {
        @Override
        public Lectures createFromParcel(Parcel in) {
            return new Lectures(in);
        }

        @Override
        public Lectures[] newArray(int size) {
            return new Lectures[size];
        }
    };

    protected Lectures(Parcel in){
        id = in.readInt();
        day = in.readString();
        subject = in.readString();
        startTime = in.readString();
        endTime = in.readString();
    }
}
