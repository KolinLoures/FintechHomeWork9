package com.example.kolin.fintechhomework9.domain;

import android.util.Log;

import com.example.kolin.fintechhomework9.data.cache.ICache;
import com.example.kolin.fintechhomework9.data.model.NewsPojo;
import com.example.kolin.fintechhomework9.data.model.NewsResponse;
import com.example.kolin.fintechhomework9.data.net.NewsApi;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by kolin on 27.11.2017.
 */

public class GetNewsUseCase extends BaseUseCase<List<NewsPojo>, GetNewsUseCase.GetNewsParams> {

    private static final String TAG = GetNewsParams.class.getSimpleName();

    private ICache cache;
    private NewsApi api;

    public GetNewsUseCase(ICache cache, NewsApi api) {
        this.cache = cache;
        this.api = api;
    }

    @Override
    Observable<List<NewsPojo>> createObservable(GetNewsParams p) {
        Observable<List<NewsPojo>> dataObs = p.loadFromNetworkOnly ? this.loadFromNetworkOnly() : loadFromCacheAndNetwork();

        return dataObs.doOnError(throwable -> Log.e(TAG, "get news use case error", throwable));
    }

    private Observable<List<NewsPojo>> loadFromCacheAndNetwork() {
        return Observable
                .concat(
                        api.getLatesNews().map(NewsResponse::getPayload).doOnNext(cache::putToCache),
                        cache.getFromCache())
                .map(newsPojos -> {
                    Collections.sort(newsPojos, (o1, o2) -> Long.compare(o1.getPublicationDate(), o2.getPublicationDate()));
                    return newsPojos;
                });
    }

    private Observable<List<NewsPojo>> loadFromNetworkOnly() {
        return api
                .getLatesNews()
                .map(NewsResponse::getPayload)
                .doOnNext(cache::putToCache)
                .map(newsPojos -> {
                    Collections.sort(newsPojos, (o1, o2) -> ((Long) o2.getPublicationDate()).compareTo(o1.getPublicationDate()));
                    return newsPojos;
                });
    }


    public static class GetNewsParams {
        boolean loadFromNetworkOnly;

        public GetNewsParams(boolean loadFromNetworkOnly) {
            this.loadFromNetworkOnly = loadFromNetworkOnly;
        }
    }
}
