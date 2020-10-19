package com.udacity.a49erscomrss.widget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class A49ersRssAppRemoteViewsService extends RemoteViewsService {
    private static final String TAG = "[Widget]" + A49ersRssAppRemoteViewsService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "onGetViewFactory: ");
        return new A49ersRssAppRemoteViewFactory(this.getApplicationContext());
    }
}
