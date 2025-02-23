package com.example.logiphone.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.logiphone.BaseData;
import com.example.logiphone.Controller;
import com.example.logiphone.R;
import com.example.logiphone.alert.SuccessDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Objects;

public class ProfileDetail extends AppCompatActivity {
    private Controller controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_detail);

        controller = new Controller();

        findViewById(R.id.btn_arrow_back).setOnClickListener(v -> {
            super.onBackPressed();
        });

        findViewById(R.id.btn_switch_edit).setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileCustom.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_switch_member_list).setOnClickListener(v -> {
            Intent intent = new Intent(this, MemberList.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_change_favorite).setOnClickListener(v -> {
            changeFavoriteState();
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

                    int favoriteState = resObj.getInt("favorite");
                    JSONObject profileObj = resObj.getJSONObject("profile");
                    String firstName = profileObj.getString("person_name_first");
                    String secondName = profileObj.getString("person_name_second");
                    String firstNameKana = profileObj.getString("person_name_first_kana");
                    String secondNameKana = profileObj.getString("person_name_second_kana");
                    String phone1 = profileObj.getString("tel1");
                    String phone2 = profileObj.getString("tel2");
                    String phone3 = profileObj.getString("tel3");
                    String email = profileObj.getString("email");
                    String position = profileObj.getString("position");
                    String roleScreen = profileObj.getString("role_screen");
                    String birthDate = profileObj.getString("birth_date");
                    String blood = profileObj.getString("blood");
                    String gender = profileObj.getString("gender");
                    String zipcode = profileObj.getString("zip");
                    String city = profileObj.getString("city");
                    String prefecture = profileObj.getString("prefecture");
                    String department = profileObj.getString("department");
                    JSONArray company = profileObj.getJSONArray("company_name");
                    JSONObject companyInfo = company.getJSONObject(0);
                    String companyName = companyInfo.getString("company_name");

                    runOnUiThread(() -> {
                        ImageView favoriteContainer = (ImageView) findViewById(R.id.btn_change_favorite);
                        ImageView editContainer = (ImageView) findViewById(R.id.btn_switch_edit);
                        if (favoriteState == 1) {
                            favoriteContainer.setBackgroundResource(R.drawable.ic_star);
                        } else {
                            favoriteContainer.setBackgroundResource(R.drawable.ic_star_outline);
                        }

                        if (BaseData.getInstance()._type == 1) {
                            editContainer.setVisibility(View.INVISIBLE);
                        }

                        LinearLayout avatarContainer = findViewById(R.id.detail_avatar_container);
                        LinearLayout phoneGroup1 = findViewById(R.id.detail_phone1_group);
                        LinearLayout phoneGroup2 = findViewById(R.id.detail_phone2_group);
                        LinearLayout phoneGroup3 = findViewById(R.id.detail_phone3_group);
                        TextView phone1Field = findViewById(R.id.detail_phone1);
                        TextView phone2Field = findViewById(R.id.detail_phone2);
                        TextView phone3Field = findViewById(R.id.detail_phone3);

                        TextView nickNameField = findViewById(R.id.detail_nickname);
                        TextView fullNameField = findViewById(R.id.detail_fullname);
                        TextView fullNameKanaField = findViewById(R.id.detail_fullname_kana);
                        TextView emailField = findViewById(R.id.detail_email);
                        TextView birthDateField = findViewById(R.id.detail_birth_date);
                        TextView genderField = findViewById(R.id.detail_gender);
                        TextView addressField = findViewById(R.id.detail_address);
                        TextView zipcodeField = findViewById(R.id.detail_zipcode);
                        TextView positionField = findViewById(R.id.detail_position);
                        TextView companyField = findViewById(R.id.detail_company);
                        TextView departmentField = findViewById(R.id.detail_department);
                        TextView bloodField = findViewById(R.id.detail_blood);
                        ImageView roleScreenField = findViewById(R.id.detail_role_screen);

                        if (generateGender(gender).equals(getString(R.string.content_male))) {
                            avatarContainer.setBackgroundResource(R.drawable.background_green);
                        } else {
                            avatarContainer.setBackgroundResource(R.drawable.background_pink);
                        }

                        if (validatePhone(phone1)) {
                            phone1Field.setText(phone1);
                            phoneGroup1.setVisibility(View.VISIBLE);
                        }
                        if (validatePhone(phone2)) {
                            phone2Field.setText(phone2);
                            phoneGroup2.setVisibility(View.VISIBLE);
                        }
                        if (validatePhone(phone3)) {
                            phone3Field.setText(phone3);
                            phoneGroup3.setVisibility(View.VISIBLE);
                        }

                        findViewById(R.id.btn_switch_sms_list).setOnClickListener(v -> {
                            String number = getCorrectNumber(phone1, phone2, phone3);

                            if (!Objects.equals(number, "N/A")) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("sms:+" + number));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                v.getContext().startActivity(intent);
                            }
                        });

                        nickNameField.setText(generateNickName(firstName, secondName));
                        fullNameField.setText(generateFullName(firstName, secondName));
                        fullNameKanaField.setText(generateFullNameKana(firstNameKana, secondNameKana));
                        emailField.setText(email);
                        birthDateField.setText(generateBirthDate(birthDate));
                        genderField.setText(generateGender(gender));
                        addressField.setText(generateAddress(prefecture, city));
                        zipcodeField.setText(generateZipcode(zipcode));
                        positionField.setText(generatePosition(position));
                        companyField.setText(generateCompany(companyName));
                        departmentField.setText(generateDepartment(department));
                        bloodField.setText(generateBlood(blood));
                        roleScreenField.setImageBitmap(getBitmapFromEncodedString(roleScreen));

                        findViewById(R.id.btn_call_phone1).setOnClickListener(v -> {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + phone1));
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        });

                        findViewById(R.id.btn_call_phone2).setOnClickListener(v -> {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + phone2));
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        });

                        findViewById(R.id.btn_call_phone3).setOnClickListener(v -> {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + phone3));
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        });
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private String getCorrectNumber(String phone1, String phone2, String phone3) {
        String number = "";
        if (!Objects.equals(phone1, "") && !Objects.equals(phone1, "null") && !Objects.equals(phone1, "--")) {
            number = phone1;
        } else if (!Objects.equals(phone2, "") && !Objects.equals(phone2, "null") && !Objects.equals(phone2, "--")) {
            number = phone2;
        } else if (!Objects.equals(phone3, "") && !Objects.equals(phone3, "null") && !Objects.equals(phone3, "--")) {
            number = phone3;
        } else {
            number = "N/A";
        }
        return number;
    }

    private void changeFavoriteState() {
        new Thread(() -> {
            try {
                String response = controller.changeFavoriteState();
                try {
                    JSONObject resObj = new JSONObject(response);
                    String message = resObj.getString("message");

                    if (message.equals("inserted")) {
                        runOnUiThread(() -> {
                            SuccessDialog successDialog = new SuccessDialog(this, getString(R.string.message_add_favorite_list));
                            successDialog.show();

                            ImageView favoriteContainer = findViewById(R.id.btn_change_favorite);
                            favoriteContainer.setBackgroundResource(R.drawable.ic_star);
                        });
                    } else {
                        runOnUiThread(() -> {
                            SuccessDialog successDialog = new SuccessDialog(this, getString(R.string.message_remove_favorite_list));
                            successDialog.show();

                            ImageView favoriteContainer = findViewById(R.id.btn_change_favorite);
                            favoriteContainer.setBackgroundResource(R.drawable.ic_star_outline);
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private String generateNickName(String param1, String param2) {
        if (!Objects.equals(param2, "")) {
            return param2.substring(0, 1);
        } else if (!Objects.equals(param1, "")) {
            return param1.substring(0, 1);
        }
        return "";
    }

    private String generateFullName(String param1, String param2) {
        return param2 + " " + param1;
    }

    private String generateFullNameKana(String param1, String param2) {
        if (Objects.equals(param1, "null") || Objects.equals(param2, "null")) {
            return "";
        }
        return param2 + " " + param1;
    }

    private String generateBirthDate(String param) {
        if (Objects.equals(param, "") || Objects.equals(param, "null")) {
            return getString(R.string.content_no_information);
        }
        return param;
    }

    private String generateGender(String param) {
        if (Objects.equals(param, "") || Objects.equals(param, "null")) {
            return getString(R.string.content_no_information);
        } else {
            return Integer.parseInt(param) == 1 ? getString(R.string.content_male) : getString(R.string.content_female);
        }
    }

    private String generateAddress(String param1, String param2) {
        if ((Objects.equals(param1, "") || Objects.equals(param1, "null")) && (Objects.equals(param2, "") || Objects.equals(param2, "null"))) {
            return getString(R.string.content_no_information);
        }
        return param2 + " " + param1;
    }

    private String generateZipcode(String param) {
        if (Objects.equals(param, "") || Objects.equals(param, "null")) {
            return getString(R.string.content_no_information);
        }
        return param;
    }

    private String generatePosition(String param) {
        if (Objects.equals(param, "") || Objects.equals(param, "null")) {
            return getString(R.string.content_no_information);
        }
        return param;
    }

    private String generateCompany(String param) {
        if (Objects.equals(param, "") || Objects.equals(param, "null")) {
            return getString(R.string.content_no_information);
        }
        return param;
    }

    private String generateDepartment(String param) {
        if (Objects.equals(param, "") || Objects.equals(param, "null")) {
            return getString(R.string.content_no_information);
        }
        return param;
    }

    private String generateBlood(String param) {
        if (Objects.equals(param, "") || Objects.equals(param, "null")) {
            return getString(R.string.content_no_information);
        }
        return param;
    }

    private boolean validatePhone(String param) {
        return !Objects.equals(param, "") && !Objects.equals(param, "null") && !Objects.equals(param, "--");
    }

    public static Bitmap getBitmapFromEncodedString(String encodedString) {
        try {
            byte[] decodedBytes = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }
}
