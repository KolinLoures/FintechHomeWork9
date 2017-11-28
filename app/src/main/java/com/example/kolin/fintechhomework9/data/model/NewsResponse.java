package com.example.kolin.fintechhomework9.data.model;

import java.util.List;

/**
 * Created by kolin on 27.11.2017.
 */

public class NewsResponse {

    private String resultCode;
    private List<NewsPojo> payload;

    public NewsResponse() {
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public List<NewsPojo> getPayload() {
        return payload;
    }

    public void setPayload(List<NewsPojo> payload) {
        this.payload = payload;
    }
}
