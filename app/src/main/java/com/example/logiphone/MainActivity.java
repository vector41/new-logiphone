package com.example.logiphone;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.logiphone.ui.FavoriteList;
import com.example.logiphone.ui.Login;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String _email = BaseData.getInstance().getAuthUserEmail(MainActivity.this);
        Intent intent;

        if (Objects.equals(_email, null) || Objects.equals(_email, "") || Objects.equals(_email, "null")) {
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