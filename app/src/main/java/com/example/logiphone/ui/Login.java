package com.example.logiphone.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

        if (controller == null) {
            controller = new Controller();
        }

        if (!Objects.equals(BaseData.getInstance()._token, null)) {
            Intent intent = new Intent(this, FavoriteList.class);
            startActivity(intent);
        }

        findViewById(R.id.submit_login).setOnClickListener(v -> {
            EditText emailField = (EditText) findViewById(R.id.login_email);
            EditText passwordField = (EditText) findViewById(R.id.login_password);
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();

            if (validateLoginForm(email, password)) {
                new Thread(() -> {
                    try {
                        String res = controller.login(email, password);
                        JSONObject obj = new JSONObject(res);

                        String _token = obj.getString("token");
                        String _email = obj.getString("email");
                        int _id = Integer.parseInt(obj.getString("userId"));
                        if (_token != null) {
                            BaseData.getInstance()._token = _token;
                            BaseData.getInstance()._id = _id;
                            BaseData.getInstance()._email = _email;

                            Intent intent = new Intent(this, FavoriteList.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }).start();
            } else {
                WarningDialong warningDialong = new WarningDialong(this, getString(R.string.message_require_correct_login));
                warningDialong.show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
