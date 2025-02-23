package com.example.logiphone.adapter;

import com.example.logiphone.model.Company;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CompanyListPaginate {
    @SerializedName("current_page")
    private int currentPage;

    @SerializedName("data")
    private List<Company> data;

    @SerializedName("next_page_url")
    private String nextPageUrl;

    public int getCurrentPage() {
        return currentPage;
    }

    public List<Company> getData() {
        return data;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }
}
