package com.example.kolin.fintechhomework9.presentation;

import android.support.annotation.CallSuper;

import java.lang.ref.WeakReference;

/**
 * Created by kolin on 28.11.2017.
 */

public class BasePresenter<V> {

    private WeakReference<V> view;

    @CallSuper
    void attachView(V view){
        if (this.view != null)
            detachView();

        this.view = new WeakReference<>(view);
    }

    @CallSuper
    void detachView(){
        if (view != null) {
            view.clear();
            view = null;
        }
    }

    public boolean isViewAttach(){
        return view != null && view.get() != null;
    }

    public V getView (){
        return view != null ? view.get() : null;
    }
}

