package com.example.logiphone;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

import org.json.JSONObject;

public class PhoneService extends AccessibilityService {
    private AccessibilityEvent event = null;
    private static PhoneService instance;

    public static PhoneService getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        this.event = event;
    }

    @Override
    public void onInterrupt() { }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }

    public void displayIncomingDetail(JSONObject obj) {

    }
}
