package com.example.logiphone.model;

import java.util.Objects;

public class Favorite {
    public int id;
    public int user_id;
    public int selected_id;
    public String first_name;
    public String second_name;
    public String first_name_kana;
    public String second_name_kana;
    public String tel1;
    public String tel2;
    public String tel3;
    public int gender;
    public int type;

    public Favorite(int id, int user_id, int selected_id, int type, String first_name, String second_name, String first_name_kana, String second_name_kana, int gender, String tel1, String tel2, String tel3) {
        this.id = id;
        this.user_id = user_id;
        this.selected_id = selected_id;
        this.first_name = first_name;
        this.first_name_kana = first_name_kana;
        this.second_name = second_name;
        this.second_name_kana = second_name_kana;
        this.gender = gender;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.tel3 = tel3;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return user_id;
    }

    public int getSelectedId() {
        return selected_id;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getSecondName() {
        return second_name;
    }

    public String getFirstNameKana() {
        return first_name_kana;
    }

    public String getSecondNameKana() {
        return second_name_kana;
    }

    public String getNickname() {
        if (!Objects.equals(getSecondName(), "")) {
            return getSecondName().substring(0, 1);
        } else if (!Objects.equals(getFirstName(), "")) {
            return getFirstName().substring(0, 1);
        }
        return "";
    }

    public String getFullName() {
        return getSecondName() + " " + getFirstName();
    }

    public String getTel1() {
        return tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public String getTel3() {
        return tel3;
    }

    public int getTelCount() {
        int count = 0;

        if (!Objects.equals(getTel1(), "") && !Objects.equals(getTel1(), null) && !Objects.equals(getTel1(), "--")) {
            count ++;
        }
        if (!Objects.equals(getTel2(), "") && !Objects.equals(getTel2(), null) && !Objects.equals(getTel2(), "--")) {
            count ++;
        }
        if (!Objects.equals(getTel3(), "") && !Objects.equals(getTel3(), null) && !Objects.equals(getTel3(), "--")) {
            count ++;
        }

        return count;
    }

    public int getGender() {
        return gender;
    }

    public int getShared() {
        return type;
    }
}
