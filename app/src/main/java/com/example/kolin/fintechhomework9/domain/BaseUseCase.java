package com.example.kolin.fintechhomework9.domain;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kolin on 27.11.2017.
 */

public abstract class BaseUseCase<T, P>  implements UseCase<T,P>{

    abstract Observable<T> createObservable(P params);

    @Override
    public Observable<T> apply(P params) {
        return createObservable(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

}
