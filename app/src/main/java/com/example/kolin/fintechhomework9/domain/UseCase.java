package com.example.kolin.fintechhomework9.domain;

import io.reactivex.Observable;

/**
 * Created by kolin on 27.11.2017.
 */

public interface UseCase<T, P> {
    Observable<T> apply(P params);
}
