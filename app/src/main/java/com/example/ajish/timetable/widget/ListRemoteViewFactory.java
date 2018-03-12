package com.example.ajish.timetable.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.ajish.timetable.R;
import com.example.ajish.timetable.db.DbHandler;
import com.example.ajish.timetable.db.Lectures;

import java.util.ArrayList;

/**
 * Created by ajish on 3/11/18.
 */

public class ListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<Lectures> lecturesArrayList = new ArrayList<>();
    private Context context = null;
    private int appWidgetId;
    private String day;
    private DbHandler db;

    ListRemoteViewFactory(Context context, Intent intent){
        this.context = context;
        db = new DbHandler(context);
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
        day = intent.getStringExtra("day");
        populateList();
    }

    private void populateList(){
        lecturesArrayList = new ArrayList<>(db.getDayLectures(day));
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return lecturesArrayList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.single_widget_item);
        Lectures lectures = lecturesArrayList.get(i);
        remoteView.setTextViewText(R.id.subject_widget, lectures.getSubject());
        remoteView.setTextViewText(R.id.time_widget, lectures.getStartTime()+" - "+lectures.getEndTime());
        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
