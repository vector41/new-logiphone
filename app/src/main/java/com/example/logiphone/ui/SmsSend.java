package com.example.logiphone.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.logiphone.BaseData;
import com.example.logiphone.Controller;
import com.example.logiphone.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

public class SmsSend extends AppCompatActivity {
    private Controller controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_send);

        controller = new Controller();

        findViewById(R.id.btn_arrow_back).setOnClickListener(v -> {
            super.onBackPressed();
        });

        findViewById(R.id.btn_send_sms).setOnClickListener(v -> {
            EditText contentField = (EditText) findViewById(R.id.sms_content_input);
            sendSmsContent(contentField.getText().toString());
        });

        findViewById(R.id.sms_content_input).setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    EditText contentField = (EditText) findViewById(R.id.sms_content_input);
                    sendSmsContent(contentField.getText().toString());
                }
                return false;
            }
        });

        loadProfileDetail();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void loadProfileDetail() {
        new Thread(() -> {
            try {
                String res = controller.getProfileDetail();
                Log.e("Response", res);
                try {
                    JSONObject resObj = new JSONObject(res);

                    JSONObject profileObj = resObj.getJSONObject("profile");
                    String firstName = profileObj.getString("person_name_first");
                    String secondName = profileObj.getString("person_name_second");
                    String nickname = profileObj.getString("nickname");
                    String gender = profileObj.getString("gender");

                    runOnUiThread(() -> {
                        LinearLayout avatarContainer = findViewById(R.id.sms_avatar_container);

                        TextView nickNameField = findViewById(R.id.sms_avatar_nickname);
                        TextView fullNameField = findViewById(R.id.sms_user_name);
                        avatarContainer.setBackgroundResource(generateGender(gender) == 1 ? R.drawable.background_green : R.drawable.background_pink);

                        nickNameField.setText(generateNickName(firstName, secondName, nickname));
                        fullNameField.setText(generateFullName(firstName, secondName));
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sendSmsContent(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    private String generateNickName(String param1, String param2, String param3) {
        if (!Objects.equals(param2, "")) {
            return param2.substring(0, 1);
        } else if (!Objects.equals(param1, "")) {
            return param1.substring(0, 1);
        } else {
            return param3.substring(0, 1);
        }
    }

    private String generateFullName(String param1, String param2) {
        return param2 + " " + param1;
    }

    private int generateGender(String param) {
        if (Objects.equals(param, "") || Objects.equals(param, "null")) {
            return 1;
        } else {
            return Integer.parseInt(param);
        }
    }

}
