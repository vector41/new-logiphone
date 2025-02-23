package com.example.logiphone.adapter;

import com.example.logiphone.model.FavoriteAdd;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoriteAddListPaginate {
    @SerializedName("current_page")
    private int currentPage;

    @SerializedName("data")
    private List<FavoriteAdd> data;

    @SerializedName("next_page_url")
    private String nextPageUrl;

    public int getCurrentPage() {
        return currentPage;
    }

    public List<FavoriteAdd> getData() {
        return data;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }
}
