package com.example.kolin.fintechhomework9.data.net;

import com.example.kolin.fintechhomework9.data.model.NewsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by kolin on 27.11.2017.
 */

public interface NewsApi {

    String BASE_URL = "https://api.tinkoff.ru/v1/";

    @GET("news")
    Observable<NewsResponse> getLatesNews();
}
