package com.example.logiphone.model;

import java.util.Objects;

public class FavoriteAdd {
    public int id;
    public String person_name_second;
    public String person_name_first;
    public String person_name_second_kana;
    public String person_name_first_kana;
    public int gender;
    public String source;
    public int type;

    public FavoriteAdd(int id, String person_name_second, String person_name_first, String person_name_second_kana, String person_name_first_kana, int gender, String source, int type) {
        this.id = id;
        this.person_name_second = person_name_second;
        this.person_name_first = person_name_first;
        this.person_name_second_kana = person_name_second_kana;
        this.person_name_first_kana = person_name_first_kana;
        this.gender = gender;
        this.source = source;
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

    public String getNickname() {
        if (!Objects.equals(getPersonNameSecond(), "")) {
            return getPersonNameSecond().substring(0, 1);
        } else if (!Objects.equals(getPersonNameFirst(), "")) {
            return getPersonNameFirst().substring(0, 1);
        }
        return "";
    }

    public String getFullName() {
        if (!Objects.equals(getPersonNameSecond(), "") && !Objects.equals(getPersonNameFirst(), "")) {
            return getPersonNameSecond() + " " + getPersonNameFirst();
        }
        return getPersonNameSecondKana() + " " + getPersonNameFirstKana();
    }

    public int getGender() {
        return gender;
    }

    public String getSource() {
        return source;
    }

    public int getType() {
        return type;
    }
}
