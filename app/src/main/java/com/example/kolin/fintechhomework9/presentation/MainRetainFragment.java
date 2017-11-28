package com.example.kolin.fintechhomework9.presentation;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kolin.fintechhomework9.data.model.NewsPojo;

import java.util.List;

public class MainRetainFragment extends Fragment implements MainView {

    public static final String TAG = MainRetainFragment.class.getSimpleName();

    private MainPresenter presenter;

    private boolean errorState = false;
    private List<NewsPojo> data;

    private MainRetainFragmentListener listener;

    public interface MainRetainFragmentListener {

        void onDataResult(List<NewsPojo> data);

        void onError();

    }

    public MainRetainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        Log.i(TAG, "onCreate: ");

        presenter = new MainPresenter(getContext());
        presenter.attachView(this);
    }

    public MainPresenter getCurrentPresenter() {
        return presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter.loadNews();
    }

    @Override
    public void showLoadedData(List<NewsPojo> data) {
        if (listener != null) {
            listener.onDataResult(data);
        } else {
            errorState = false;
            this.data = data;
        }
    }

    @Override
    public void showError() {
        if (listener != null)
            listener.onError();
        else {
            this.data = null;
            errorState = true;
        }
    }

    @Override
    public void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MainRetainFragmentListener) {
            listener = (MainRetainFragmentListener) context;

            if (data != null) {
                listener.onDataResult(data);
                this.data = null;
            }

            if (errorState)
                listener.onError();

        } else
            throw new RuntimeException(context.toString() + " must implement MainRetainFragmentListener");
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }
}
