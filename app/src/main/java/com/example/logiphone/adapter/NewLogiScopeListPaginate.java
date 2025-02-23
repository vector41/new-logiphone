package com.example.logiphone.adapter;

import com.example.logiphone.model.NewLogiScope;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewLogiScopeListPaginate {
    @SerializedName("current_page")
    private int currentPage;

    @SerializedName("data")
    private List<NewLogiScope> data;

    @SerializedName("next_page_url")
    private String nextPageUrl;

    public int getCurrentPage() {
        return currentPage;
    }

    public List<NewLogiScope> getData() {
        return data;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }
}
