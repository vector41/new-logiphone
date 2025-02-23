package com.example.logiphone.model;

public class User {
    public int id;
    public String email;
    public String password;
    public int company_id;
    public int company_department_id;
    public String first_name;
    public String second_name;
    public String first_name_kana;
    public String second_name_kana;
    public String role;
    public String role_screen;
    public String birth_date;
    public String hire_date;
    public int gender;
    public String blood;
    public String zipcode;
    public String address;

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getCompanyId() {
        return company_id;
    }

    public int getCompanyDepartmentId() {
        return company_department_id;
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

    public String getRole() {
        return role;
    }

    public String getRoleScreen() {
        return role_screen;
    }

    public String getBirthDate() {
        return birth_date;
    }

    public String getHireDate() {
        return hire_date;
    }

    public int getGender() {
        return gender;
    }

    public String getBlood() {
        return blood;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getAddress() {
        return address;
    }
}
