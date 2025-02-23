package com.example.logiphone;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WebSocketCommunication {
    public Context globalContext;
    private static final String BASE_URL = "ws://10.0.2.2:6001/";
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
                    try {
                        JSONObject obj = new JSONObject(text);
                        String event = obj.getString("event");

                        if (event.equals("GOT_USER_DETAIL")) {
                            JSONObject resultObj = obj.getJSONObject("data");
                            PhoneService service = PhoneService.getInstance();

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    service.displayIncomingDetail(resultObj);
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
