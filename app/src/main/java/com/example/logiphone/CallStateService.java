package com.example.logiphone;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class CallStateService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, @NonNull Intent intent) {
        WebSocketCommunication websocket = new WebSocketCommunication(context);

        if (intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (state != null && state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                // Get the incoming call number
                String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.d("IncomingCall", "Incoming number: " + incomingNumber);
                JSONObject obj = new JSONObject();
                try {
                    obj.put("event", "GET_USER_DETAIL");
                    obj.put("data", incomingNumber);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                websocket.getGlobalWebSocket().send(obj.toString());
            }
        }
    }
}
