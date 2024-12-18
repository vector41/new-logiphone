package com.example.logiphone.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.logiphone.R;

public class ProfileDetail extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_detail);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
