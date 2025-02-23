package com.example.logiphone.model;

import android.util.Log;

import java.util.Objects;

public class NewLogiScope {
    public int id;
    public String person_name_second;
    public String person_name_first;
    public String person_name_second_kana;
    public String person_name_first_kana;
    public String tel1;
    public String tel2;
    public String tel3;
    public int gender;
    public int type;

    public NewLogiScope(int id, String person_name_second, String person_name_first, String person_name_second_kana, String person_name_first_kana, String tel1, String tel2, String tel3, int gender, int type, String person_name, String person_name_kana) {
        this.id = id;
        this.person_name_second = person_name_second;
        this.person_name_first = person_name_first;
        this.person_name_second_kana = person_name_second_kana;
        this.person_name_first_kana = person_name_first_kana;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.tel3 = tel3;
        this.gender = gender;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getPersonNameSecond() {
        return person_name_second;
    }

    public String getPersonNameFirst() {
        return person_name_first;
    }

    public String getPersonNameSecondKana() {
        return person_name_second_kana;
    }

    public String getPersonNameFirstKana() {
        return person_name_first_kana;
    }

    public String getUsername() {
        return getPersonNameSecond() + " " + getPersonNameFirst();
    }

    public String getNickname() {
        if (!Objects.equals(getPersonNameSecond(), "")) {
            return getPersonNameSecond().substring(0, 1);
        } else if (!Objects.equals(getPersonNameFirst(), "")) {
            return getPersonNameFirst().substring(0, 1);
        }
        return "";
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
