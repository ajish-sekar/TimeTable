package com.example.ajish.timetable.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by ajish on 3/11/18.
 */

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        //TODO: Complete this service
        return new ListRemoteViewFactory(this.getApplicationContext(), intent);
    }
}
