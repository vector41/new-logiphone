package com.example.logiphone.adapter;

import com.example.logiphone.model.OldLogiCompany;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.List;

public class OldLogiCompanyPaginate {
    @SerializedName("current_page")
    private int currentPage;

    @SerializedName("data")
    private List<OldLogiCompany> data;

    @SerializedName("next_page_url")
    private String nextPageUrl;

    public int getCurrentPage() {
        return currentPage;
    }

    public Collection<? extends OldLogiCompany> getData() {
        return data;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }
}
