package com.example.kolin.fintechhomework9.presentation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.kolin.fintechhomework9.R;
import com.example.kolin.fintechhomework9.data.model.NewsPojo;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainRetainFragment.MainRetainFragmentListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private static final String KEY_LOADING_STATE = "loading_satate";
    private static final String KEY_ERROR_STATE = "error_state";


    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipe;
    private TextView textError;

    private NewsAdapterRV adapter;
    private MainPresenter currentPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate: ");

        adapter = new NewsAdapterRV();
        textError = findViewById(R.id.main_activity_error);

        recyclerView = findViewById(R.id.main_activity_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);

        swipe = findViewById(R.id.main_activity_swipe);
        swipe.setOnRefreshListener(() -> {
                currentPresenter.loadNews();
        });

        if (savedInstanceState != null) {
            textError.setVisibility(savedInstanceState.getBoolean(KEY_ERROR_STATE) ? View.VISIBLE : View.GONE);
            swipe.setRefreshing(savedInstanceState.getBoolean(KEY_LOADING_STATE));
        }

        Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(MainRetainFragment.TAG);

        if (fragmentByTag == null) {
            fragmentByTag = new MainRetainFragment();
            getSupportFragmentManager().beginTransaction().add(fragmentByTag, MainRetainFragment.TAG).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentPresenter = ((MainRetainFragment) getSupportFragmentManager().findFragmentByTag(MainRetainFragment.TAG)).getCurrentPresenter();
    }

    @Override
    public void onDataResult(List<NewsPojo> data) {
        recyclerView.setVisibility(View.VISIBLE);
        swipe.setRefreshing(false);
        textError.setVisibility(View.GONE);
        adapter.clear();
        adapter.addAll(data);
    }

    @Override
    public void onError() {
        recyclerView.setVisibility(View.INVISIBLE);
        swipe.setRefreshing(false);
        textError.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(KEY_LOADING_STATE, swipe.isRefreshing());
        outState.putBoolean(KEY_ERROR_STATE, textError.getVisibility() == View.VISIBLE);
    }
}
