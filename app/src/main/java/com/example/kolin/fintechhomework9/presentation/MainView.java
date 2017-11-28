package com.example.kolin.fintechhomework9.presentation;

import com.example.kolin.fintechhomework9.data.model.NewsPojo;

import java.util.List;

/**
 * Created by kolin on 28.11.2017.
 */

public interface MainView {

    void showLoadedData(List<NewsPojo> data);

    void showError();

}
