package com.example.logiphone.adapter;

import com.example.logiphone.model.Member;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MemberListPaginate {
    @SerializedName("current_page")
    private int currentPage;

    @SerializedName("data")
    private List<Member> data;

    @SerializedName("next_page_url")
    private String nextPageUrl;

    public int getCurrentPage() {
        return currentPage;
    }

    public List<Member> getData() {
        return data;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }
}
