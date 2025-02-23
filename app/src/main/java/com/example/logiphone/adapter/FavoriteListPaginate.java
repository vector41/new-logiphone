package com.example.logiphone.adapter;

import com.example.logiphone.model.Favorite;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoriteListPaginate {
    @SerializedName("current_page")
    private int currentPage;

    @SerializedName("data")
    private List<Favorite> data;

    @SerializedName("next_page_url")
    private String nextPageUrl;

    public int getCurrentPage() {
        return currentPage;
    }

    public List<Favorite> getData() {
        return data;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }
}
