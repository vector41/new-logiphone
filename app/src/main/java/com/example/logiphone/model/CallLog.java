package com.example.logiphone.model;

public class CallLog {
    public int id;
    public int caller_id;
    public int receiver_id;
    public int state;
    public String created_at;
    public String updated_at;

    public int getId() {
        return id;
    }

    public int getCallerId() {
        return caller_id;
    }

    public int getReceiverId() {
        return receiver_id;
    }

    public int getState() {
        return state;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public String getUpdatedAt() {
        return updated_at;
    }
}
