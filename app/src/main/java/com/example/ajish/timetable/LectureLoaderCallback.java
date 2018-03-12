package com.example.ajish.timetable;

import com.example.ajish.timetable.db.Lectures;

import java.util.ArrayList;

/**
 * Created by ajish on 3/10/18.
 */

public interface LectureLoaderCallback {
    void onLectureLoaded(ArrayList<Lectures> lectures);
}