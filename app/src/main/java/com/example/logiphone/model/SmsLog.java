package com.example.logiphone.model;

public class SmsLog {
    public int id;
    public int sender_id;
    public int receiver_id;
    public String content;
    public String created_at;
    public String updated_at;

    public int getId() {
        return id;
    }

    public int getSenderId() {
        return sender_id;
    }

    public int getReceiverId() {
        return receiver_id;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public String getUpdatedAt() {
        return updated_at;
    }
}
