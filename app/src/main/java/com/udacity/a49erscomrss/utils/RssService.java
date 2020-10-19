package com.udacity.a49erscomrss.utils;

import com.udacity.a49erscomrss.model.RssFeed;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RssService {
    @GET("/rss/news")
    Call<RssFeed> getNewsItems();

    @GET("/rss/galleries")
    Call<RssFeed> getPhotoItems();

    @GET("/rss/videos")
    Call<RssFeed> getVideoItems();

    @GET("/rss/audio")
    Call<RssFeed> getAudioItems();
}