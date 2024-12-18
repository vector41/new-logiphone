package com.example.logiphone;

import android.content.Intent;
import android.os.Bundle;

import com.example.logiphone.ui.CallHistory;
import com.example.logiphone.ui.FavoriteAddGroup;
import com.example.logiphone.ui.FavoriteList;
import com.example.logiphone.ui.LogiPhoneList;
import com.example.logiphone.ui.LogiScopeList;
import com.example.logiphone.ui.Login;
import com.example.logiphone.ui.ProfileCustom;
import com.example.logiphone.ui.SmsSend;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.logiphone.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}