package com.udacity.a49erscomrss.utils;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RetrofitApiClient {
    private static Retrofit retrofit = null;
    private static final String URL = "https://www.49ers.com";

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
