package com.example.logiphone.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.logiphone.BaseData;
import com.example.logiphone.Controller;
import com.example.logiphone.R;
import com.example.logiphone.alert.SuccessDialog;
import com.example.logiphone.alert.WarningDialong;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

public class Login extends AppCompatActivity {
    Controller controller = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        controller = new Controller();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_PHONE_NUMBERS,
                    Manifest.permission.READ_CALL_LOG
            }, 100);
        }

        findViewById(R.id.submit_login).setOnClickListener(v -> {
            submitLogin();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void submitLogin() {
        EditText emailField = (EditText) findViewById(R.id.login_email);
        EditText passwordField = (EditText) findViewById(R.id.login_password);
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        if (validateLoginForm(email, password)) {
            new Thread(() -> {
                try {
                    String res = controller.login(email, password);
                    JSONObject obj = new JSONObject(res);

                    int _id = Integer.parseInt(obj.getString("userId"));
                    String _email = obj.getString("email");
                    BaseData.getInstance()._id = _id;
                    BaseData.getInstance()._email = _email;
                    BaseData.getInstance().setAuthData(Login.this, _id, _email);

                    Intent intent = new Intent(this, FavoriteList.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            WarningDialong warningDialong = new WarningDialong(this, getString(R.string.message_require_correct_login));
            warningDialong.show();
        }
    }

    private boolean validateLoginForm(String email, String password) {
        if (Objects.equals(email, "")) {
            return false;
        }
        if (Objects.equals(password, "")) {
            return false;
        }
        return true;
    }
}
