package com.example.logiphone.adapter;

import android.content.Context;

import com.example.logiphone.model.Favorite;

import java.util.List;

public class FavoriteListPaginate {

    private int currentPage;
    private List<Favorite> data;
    private String nextPageUrl;

    public int getCurrentPage() {
        return currentPage;
    }

    public List<Favorite> getFavoriteList() {
        return data;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }
}
