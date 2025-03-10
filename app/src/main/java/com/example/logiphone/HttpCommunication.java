package com.example.logiphone;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpCommunication {
//    private static final String BASE_URL = "http://10.0.2.2:8000/api/";
    private static final String BASE_URL = "https://logiphone.new-challenge.jp/api/";

    public OkHttpClient client;

    public HttpCommunication() {
        if (client == null) {
            client = new OkHttpClient();
        }
    }

    public Request buildHttpGetRequest(String endpoint) {
        return new Request.Builder()
                          .url(BASE_URL + endpoint)
                          .build();
    }

    public Request bulidHttpPostRequest(RequestBody body, String endpoint) {
        return new Request.Builder()
                          .url(BASE_URL + endpoint)
                          .post(body)
                          .addHeader("Content-Type", "application/json")
                          .build();
    }
}
