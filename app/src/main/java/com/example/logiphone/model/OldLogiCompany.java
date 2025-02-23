package com.example.logiphone.model;

import java.util.Objects;

public class OldLogiCompany {
    public int id;
    public String company_name;
    public String kana;
    public String url;
    public int type;

    public OldLogiCompany(int id, String company_name, String kana, String url, int type) {
        this.id = id;
        this.company_name = company_name;
        this.kana = kana;
        this.url = url;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getCompanyName() {
        return company_name;
    }

    public String getCompanyNickname() {
        if (!Objects.equals(getCompanyName(), "")) {
            return getCompanyName().substring(0, 1);
        }
        return "";
    }

    public int getType() {
        return type;
    }
}
