package com.udacity.a49erscomrss.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RssItemDao {

    @Query("SELECT * FROM rssItems")
    LiveData<List<RssItemEntry>> loadAllRssItems();

    @Query("SELECT * FROM rssItems")
    List<RssItemEntry> loadAllRssItemsForWidget();

    @Delete
    void deleteRssItem(RssItemEntry rssItemEntry);

    @Insert
    void insertRssItem(RssItemEntry rssItem);
}
