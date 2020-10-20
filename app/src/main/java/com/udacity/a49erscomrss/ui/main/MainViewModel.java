package com.udacity.a49erscomrss.ui.main;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.udacity.a49erscomrss.database.AppDatabase;
import com.udacity.a49erscomrss.database.RssItemEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private final LiveData<List<RssItemEntry>> mRssItems;

    public MainViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        mRssItems = database.rssItemDao().loadAllRssItems();
    }

    public LiveData<List<RssItemEntry>> getRssItems() {
        return mRssItems;
    }
}
