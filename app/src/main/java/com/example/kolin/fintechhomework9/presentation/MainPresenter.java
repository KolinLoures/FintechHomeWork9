package com.example.kolin.fintechhomework9.presentation;


import android.content.Context;
import android.util.Log;

import com.example.kolin.fintechhomework9.data.cache.FileCache;
import com.example.kolin.fintechhomework9.data.net.ApiManager;
import com.example.kolin.fintechhomework9.domain.GetNewsUseCase;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by kolin on 28.11.2017.
 */

public class MainPresenter extends BasePresenter<MainView> {

    public static final String TAG = MainPresenter.class.getSimpleName();

    private GetNewsUseCase getNews;
    private CompositeDisposable compositeDisposable;

    public MainPresenter(Context context) {
        this.getNews = new GetNewsUseCase(new FileCache(context), new ApiManager().getNewsApi());
        compositeDisposable = new CompositeDisposable();
    }

    public void loadNews() {

        Disposable subscribe = getNews
                .apply(new GetNewsUseCase.GetNewsParams(false))
                .subscribe(
                        newsPojos -> getView().showLoadedData(newsPojos), throwable -> getView().showError()
                );

        compositeDisposable.add(subscribe);
    }


    @Override
    void detachView() {
        compositeDisposable.dispose();
        super.detachView();
    }
}
