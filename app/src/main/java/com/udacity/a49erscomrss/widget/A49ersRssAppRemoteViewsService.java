package com.udacity.a49erscomrss.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class A49ersRssAppRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new A49ersRssAppRemoteViewFactory(this.getApplicationContext());
    }
}
