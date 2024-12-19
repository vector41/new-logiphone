package com.example.logiphone.model;

public class Company {
    public int id;
    public String company_name;
    public String company_name_kana;
    public String phone_number;
    public String fax_number;
    public String address;
    public String role;

    public int getId() {
        return id;
    }

    public String getCompanyName() {
        return company_name;
    }

    public String getCompanyNameKana() {
        return company_name_kana;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public String getFaxNumber() {
        return fax_number;
    }

    public String getAddress() {
        return address;
    }

    public String getRole() {
        return role;
    }
}
