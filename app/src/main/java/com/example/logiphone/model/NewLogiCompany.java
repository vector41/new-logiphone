package com.example.logiphone.model;

import java.util.Objects;

public class NewLogiCompany {
    public int id;
    public String branch_name;
    public String nickname;
    public String zip;
    public String other;
    public String tel;
    public String fax;
    public int type;

    public NewLogiCompany(int id, String branch_name, String nickname, String zip, String other, String tel, String fax, int type) {
        this.id = id;
        this.branch_name = branch_name;
        this.zip = zip;
        this.other = other;
        this.tel = tel;
        this.fax = fax;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getBranchName() {
        return branch_name;
    }

    public String getAvatarName() {
        if (!Objects.equals(getBranchName(), "") || !Objects.equals(getBranchName(), "null")) {
            return getBranchName().substring(0, 1);
        }
        return "";
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

    public int getType() {
        return type;
    }
}
