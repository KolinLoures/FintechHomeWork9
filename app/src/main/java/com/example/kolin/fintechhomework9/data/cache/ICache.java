package com.example.kolin.fintechhomework9.data.cache;

import com.example.kolin.fintechhomework9.data.model.NewsPojo;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by kolin on 27.11.2017.
 */

public interface ICache {

    void putToCache(List<NewsPojo> newsPojos);

    Observable<List<NewsPojo>> getFromCache();

    boolean deleteCacheFile();

}
