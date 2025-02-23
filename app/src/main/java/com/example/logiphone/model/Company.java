package com.example.logiphone.model;

public class Company {
    public int id;
    public String branch_name;
    public String nickname;
    public String zip;
    public String other;
    public String tel;
    public String fax;

    public Company(int id, String branch_name, String nickname, String zip, String other, String tel, String fax) {
        this.id = id;
        this.branch_name = branch_name;
        this.nickname = nickname;
        this.zip = zip;
        this.other = other;
        this.tel = tel;
        this.fax = fax;
    }

    public int getCompanyId() {
        return id;
    }

    public String getBranchName() {
        return branch_name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getZip() {
        return zip;
    }

    public String getOther() {
        return other;
    }

    public String getTel() {
        return tel;
    }

    public String getFax() {
        return fax;
    }
}
