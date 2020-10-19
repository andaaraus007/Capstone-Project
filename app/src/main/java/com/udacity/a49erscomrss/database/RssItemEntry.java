package com.udacity.a49erscomrss.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "rssItems", primaryKeys = {"guid"})
public class RssItemEntry {
    @NonNull
    private final String guid;
    private final String title;
    private final String description;
    private final String link;

    public RssItemEntry(@NonNull String guid, @NonNull String title, String description, String link) {
        this.guid = guid;
        this.title = title;
        this.description = description;
        this.link = link;
    }

    @NonNull
    public String getGuid() {
        return guid;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }
}
