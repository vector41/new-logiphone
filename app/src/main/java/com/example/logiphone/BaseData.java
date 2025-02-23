package com.example.logiphone;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.logiphone.model.CallLog;
import com.example.logiphone.model.Favorite;
import com.example.logiphone.model.Phone;
import com.example.logiphone.model.SmsLog;
import com.example.logiphone.model.User;
import com.example.logiphone.ui.FavoriteList;

import java.util.List;

public class BaseData {
    public List<User> userList;
    public List<SmsLog> smsLogList;
    public List<CallLog> callLogList;
    public List<Favorite> favoriteList;
    public List<Phone> phoneList;

    public int _id;
    public String _email;
    public int _type;
    public int _selectedUserId;
    public int _selectedCompanyId;

    public String _searchKeyword = "";
    public String _sort = "";
    public boolean _isSelectedNewFilter = false;
    public boolean _isSelectedOldFilter = false;

    public int selectedCompanyId;
    public String selectedCompanyName;
    private final String PREFS_NAME = "SESSION";
    private final String AUTH_ID = "_ID";
    private final String AUTH_EMAIL = "_EMAIL";

    private static BaseData instance;
    public static synchronized BaseData getInstance() {
        if (instance == null) {
            instance = new BaseData();
        }
        return instance;
    }

    private BaseData() {

    }

    public void setAuthData(Context context, int id, String email) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(AUTH_ID, id);
        editor.putString(AUTH_EMAIL, email);
        editor.apply();
    }

    public int getAuthUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        return sharedPreferences.getInt(AUTH_ID, 0);
    }

    public String getAuthUserEmail(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        return sharedPreferences.getString(AUTH_EMAIL, null);
    }

    public void clearAuthData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
