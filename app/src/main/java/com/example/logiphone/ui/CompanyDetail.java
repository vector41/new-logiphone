package com.example.logiphone.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class CompanyDetail extends AppCompatActivity {
    private Controller controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_detail);

        controller = new Controller();

        findViewById(R.id.btn_arrow_back).setOnClickListener(v -> {
            super.onBackPressed();
        });

        findViewById(R.id.btn_switch_member_list).setOnClickListener(v -> {
            Intent intent = new Intent(this, MemberList.class);
            startActivity(intent);
        });

        loadCompanyDetail();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void loadCompanyDetail() {
        new Thread(() -> {
            try {
                String res = controller.getCompanyDetail();
                Log.e("Response", res);
                try {
                    JSONObject resObj = new JSONObject(res);

                    String branchName = resObj.getString("branch_name");
                    String nickname = resObj.getString("nickname");
                    String zip = resObj.getString("zip");
                    String other = resObj.getString("other");
                    String tel = resObj.getString("tel");
                    String fax = resObj.getString("fax");

                    runOnUiThread(() -> {
                        TextView avatarNameField = findViewById(R.id.detail_avatarname);
                        TextView nickNameField = findViewById(R.id.detail_nickname);
                        TextView fullNameField = findViewById(R.id.detail_fullname);
                        TextView zipField = findViewById(R.id.detail_zip);
                        TextView otherField = findViewById(R.id.detail_other);
                        TextView telField = findViewById(R.id.detail_tel);
                        TextView faxField = findViewById(R.id.detail_fax);

                        avatarNameField.setText(generateNickName(branchName));
                        fullNameField.setText(generateFullName(branchName));
                        nickNameField.setText(generateNickname(nickname));
                        zipField.setText(generateZip(zip));
                        otherField.setText(generateZip(other));
                        telField.setText(generateZip(tel));
                        faxField.setText(generateZip(fax));
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private String generateNickName(String param) {
        if (!Objects.equals(param, "") && !Objects.equals(param, "null")) {
            return param.substring(0, 1);
        }
        return getString(R.string.content_no_information);
    }

    private String generateFullName(String param) {
        if (!Objects.equals(param, "") && !Objects.equals(param, "null")) {
            return param;
        }
        return getString(R.string.content_no_information);
    }

    private String generateNickname(String param) {
        if (!Objects.equals(param, "") && !Objects.equals(param, "null")) {
            return param;
        }
        return getString(R.string.content_no_information);
    }

    private String generateZip(String param) {
        if (!Objects.equals(param, "") && !Objects.equals(param, "null")) {
            return param;
        }
        return getString(R.string.content_no_information);
    }

    private String generateOther(String param) {
        if (!Objects.equals(param, "") && !Objects.equals(param, "null")) {
            return param;
        }
        return getString(R.string.content_no_information);
    }

    private String generateTel(String param) {
        if (!Objects.equals(param, "") && !Objects.equals(param, "null")) {
            return param;
        }
        return getString(R.string.content_no_information);
    }

    private String generateFax(String param) {
        if (!Objects.equals(param, "") && !Objects.equals(param, "null")) {
            return param;
        }
        return getString(R.string.content_no_information);
    }
}
