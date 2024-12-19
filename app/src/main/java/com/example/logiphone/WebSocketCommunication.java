package com.example.logiphone;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WebSocketCommunication {
    public Context globalContext;
    private static final String BASE_URL = "ws://192.168.145.33:6001/";
//    private static final String BASE_URL = "ws://logiphone.new-challenge.jp:6001/";
    private static final String endpoint = "app/3aea7acffe7a50de88de?protocol=7&client=js&version=4.3.1&flash=false";

    public OkHttpClient client = null;
    public WebSocket globalWebSocket = null;

    public WebSocketCommunication(Context context) {
        if (client == null) {
            client = new OkHttpClient();
        }

        this.globalContext = context;
    }

    private Request buildSocketRequest() {
        return new Request.Builder().url(BASE_URL + endpoint).build();
    }

    public WebSocket getGlobalWebSocket() {
        HandlerThread handlerThread = new HandlerThread("WebSocketHandlerThread");
        handlerThread.start();

        Handler handler = new Handler(Looper.getMainLooper());

        if (globalWebSocket == null) {
            globalWebSocket = client.newWebSocket(buildSocketRequest(), new WebSocketListener() {
                @Override
                public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                    super.onOpen(webSocket, response);
                }

                @Override
                public void onMessage(@NonNull WebSocket webSocket, @NonNull ByteString bytes) {
                    super.onMessage(webSocket, bytes);
                }

                @Override
                public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
                    super.onMessage(webSocket, text);
                }

                @Override
                public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
                    super.onFailure(webSocket, t, response);
                }

                @Override
                public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                    super.onClosing(webSocket, code, reason);
                }

                @Override
                public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                    super.onClosed(webSocket, code, reason);
                }
            });
        }

        return globalWebSocket;
    }
}
