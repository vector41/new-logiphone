package com.example.logiphone;

import com.example.logiphone.model.CallLog;
import com.example.logiphone.model.Favorite;
import com.example.logiphone.model.Phone;
import com.example.logiphone.model.SmsLog;
import com.example.logiphone.model.User;

import java.util.List;

public class BaseData {
    public List<User> userList;
    public List<SmsLog> smsLogList;
    public List<CallLog> callLogList;
    public List<Favorite> favoriteList;
    public List<Phone> phoneList;

    public String _token;
    public int _id;
    public String _email;
    public int _type;

    private static BaseData instance;
    public static synchronized BaseData getInstance() {
        if (instance == null) {
            instance = new BaseData();
        }

        return instance;
    }

    private BaseData() {

    }
}
