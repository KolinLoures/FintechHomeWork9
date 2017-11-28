package com.example.kolin.fintechhomework9.data.net;

import com.example.kolin.fintechhomework9.data.NewsJsonDeserializer;
import com.example.kolin.fintechhomework9.data.model.NewsPojo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kolin on 27.11.2017.
 */

public class ApiManager {

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private NewsApi newsApi;

    public ApiManager() {
    }

    private OkHttpClient getOkHttpClient(){
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    private Gson getGson(){
        return new GsonBuilder()
                .registerTypeAdapter(NewsPojo.class, new NewsJsonDeserializer())
                .create();
    }

    private Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(NewsApi.BASE_URL)
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build();
    }

    public NewsApi getNewsApi(){
        if (newsApi != null)
            return newsApi;
        else
            return getRetrofit().create(NewsApi.class);
    }
}
