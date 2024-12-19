package com.example.logiphone;

import java.io.IOException;

import okhttp3.Response;

public class Controller {
    public HttpCommunication communication = new HttpCommunication();

    public String login(String email, String password) throws IOException {
        String endpoint = "login?email=" + email + "&password=" + password;

        try (Response response = communication.client.newCall(communication.buildHttpRequest(endpoint)).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Exception: " + response);
            }
        }
    }

    public String getFavoriteList(int userId, int page) throws IOException {
        String endpoint = "get-favorite-list?user_id=" + userId + "&page=" + page;

        try (Response response = communication.client.newCall(communication.buildHttpRequest(endpoint)).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Exception: " + response);
            }
        }
    }
}
