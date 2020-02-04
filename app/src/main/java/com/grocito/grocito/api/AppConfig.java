package com.grocito.grocito.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Advosoft2 on 5/8/2017.
 */

public class AppConfig {
    public static String BASE_URL = WebUrls.BASE_URL;

    private static OkHttpClient getRequestHeader() {
        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5,TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .build();
        return client;
    }
//    static Gson gson = new_image GsonBuilder()
//            .setLenient()
//            .create();
    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .client(getRequestHeader())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
