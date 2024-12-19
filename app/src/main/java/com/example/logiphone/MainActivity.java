package com.example.logiphone;

import android.content.Intent;
import android.os.Bundle;

import com.example.logiphone.ui.FavoriteList;
import com.example.logiphone.ui.Login;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String _token = BaseData.getInstance()._token;
        Intent intent;

        if (Objects.equals(_token, null)) {
            intent = new Intent(this, Login.class);
        } else {
            intent = new Intent(this, FavoriteList.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }

        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}