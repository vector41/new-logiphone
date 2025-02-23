package com.example.logiphone.adapter;

import com.example.logiphone.model.OldLogiScope;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OldLogiScopeListPaginate {
    @SerializedName("current_page")
    private int currentPage;

    @SerializedName("data")
    private List<OldLogiScope> data;

    @SerializedName("next_page_url")
    private String nextPageUrl;

    public int getCurrentPage() {
        return currentPage;
    }

    public List<OldLogiScope> getData() {
        return data;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }
}
