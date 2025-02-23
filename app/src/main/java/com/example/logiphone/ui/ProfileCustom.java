package com.example.logiphone.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.logiphone.BaseData;
import com.example.logiphone.Controller;
import com.example.logiphone.R;
import com.example.logiphone.alert.SuccessDialog;
import com.example.logiphone.alert.WarningDialong;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class ProfileCustom extends AppCompatActivity {
    private Controller controller;
    private final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);

        controller = new Controller();

        findViewById(R.id.btn_arrow_back).setOnClickListener(v -> {
            super.onBackPressed();
        });

        findViewById(R.id.btn_image_capture).setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imageUri = getImageUri();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        findViewById(R.id.btn_submit_save).setOnClickListener(v -> {
            submitUpdateProfile();
        });

        loadProfileDetail();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ImageView roleScreenView = findViewById(R.id.profile_edit_role);
                roleScreenView.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadProfileDetail() {
        new Thread(() -> {
            try {
                String res = controller.getProfileDetail();
                try {
                    JSONObject resObj = new JSONObject(res);

                    JSONObject profileObj = resObj.getJSONObject("profile");
                    String firstName = profileObj.getString("person_name_first");
                    String secondName = profileObj.getString("person_name_second");
                    String firstNameKana = profileObj.getString("person_name_first_kana");
                    String secondNameKana = profileObj.getString("person_name_second_kana");
                    String nickname = profileObj.getString("nickname");
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
                        ImageView roleScreenField = findViewById(R.id.profile_edit_role);
                        EditText nickNameField = findViewById(R.id.profile_edit_nickname);
                        EditText firstNameField = findViewById(R.id.profile_edit_first_name);
                        EditText secondNameField = findViewById(R.id.profile_edit_second_name);
                        EditText firstNameKanaField = findViewById(R.id.profile_edit_first_name_kana);
                        EditText secondNameKanaField = findViewById(R.id.profile_edit_second_name_kana);
                        EditText emailField = findViewById(R.id.profile_edit_email);
                        EditText phone1Field = findViewById(R.id.profile_edit_phone_number1);
                        EditText phone2Field = findViewById(R.id.profile_edit_phone_number2);
                        EditText phone3Field = findViewById(R.id.profile_edit_phone_number3);
                        EditText birthDateField = findViewById(R.id.profile_edit_birth_date);
                        Switch genderField = findViewById(R.id.profile_edit_gender);
                        EditText cityField = findViewById(R.id.profile_edit_city);
                        EditText prefectureField = findViewById(R.id.profile_edit_prefecture);
                        EditText zipcodeField = findViewById(R.id.profile_edit_zipcode);
                        EditText positionField = findViewById(R.id.profile_edit_position);
                        EditText bloodField = findViewById(R.id.profile_edit_blood);
                        EditText companyField = findViewById(R.id.profile_edit_company);
                        EditText departmentField = findViewById(R.id.profile_edit_department);

                        nickNameField.setText(nickname);
                        firstNameField.setText(firstName);
                        secondNameField.setText(secondName);
                        firstNameKanaField.setText(firstNameKana);
                        secondNameKanaField.setText(secondNameKana);
                        emailField.setText(email);
                        phone1Field.setText(phone1);
                        phone2Field.setText(phone2);
                        phone3Field.setText(phone3);
                        birthDateField.setText(birthDate);
                        genderField.setChecked(generateGender(gender) != 1);
                        cityField.setText(city);
                        prefectureField.setText(prefecture);
                        zipcodeField.setText(zipcode);
                        positionField.setText(position);
                        bloodField.setText(blood);
                        companyField.setText(companyName);
                        departmentField.setText(department);
                        roleScreenField.setImageBitmap(getBitmapFromEncodedString(roleScreen));
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void submitUpdateProfile() {
        EditText emailField = (EditText) findViewById(R.id.profile_edit_email);
        EditText firstNameField = (EditText) findViewById(R.id.profile_edit_first_name);
        EditText secondNameField = (EditText) findViewById(R.id.profile_edit_second_name);
        EditText firstNameFieldKana = (EditText) findViewById(R.id.profile_edit_first_name_kana);
        EditText secondNameFieldKana = (EditText) findViewById(R.id.profile_edit_second_name_kana);
        EditText nickNameField = (EditText) findViewById(R.id.profile_edit_nickname);
        EditText phone1Field = (EditText) findViewById(R.id.profile_edit_phone_number1);
        EditText phone2Field = (EditText) findViewById(R.id.profile_edit_phone_number2);
        EditText phone3Field = (EditText) findViewById(R.id.profile_edit_phone_number3);
        EditText birthDateField = (EditText) findViewById(R.id.profile_edit_birth_date);
        Switch genderField = (Switch) findViewById(R.id.profile_edit_gender);
        EditText cityField = (EditText) findViewById(R.id.profile_edit_city);
        EditText prefectureField = (EditText) findViewById(R.id.profile_edit_prefecture);
        EditText zipcodeField = (EditText) findViewById(R.id.profile_edit_zipcode);
        EditText positionField = (EditText) findViewById(R.id.profile_edit_position);
        EditText bloodField = (EditText) findViewById(R.id.profile_edit_blood);
        EditText companyField = (EditText) findViewById(R.id.profile_edit_company);
        EditText departmentField = (EditText) findViewById(R.id.profile_edit_department);

        String email = emailField.getText().toString();
        String firstName = firstNameField.getText().toString();
        String secondName = secondNameField.getText().toString();
        String firstNameKana = firstNameFieldKana.getText().toString();
        String secondNameKana = secondNameFieldKana.getText().toString();
        String nickName = nickNameField.getText().toString();
        String phone1 = phone1Field.getText().toString();
        String phone2 = phone2Field.getText().toString();
        String phone3 = phone3Field.getText().toString();
        String birthDate = birthDateField.getText().toString();
        int gender = genderField.isChecked() ? 0 : 1;
        String city = cityField.getText().toString();
        String prefecture = prefectureField.getText().toString();
        String zipcode = zipcodeField.getText().toString();
        String position = positionField.getText().toString();
        String blood = bloodField.getText().toString();
        String company = companyField.getText().toString();
        String department = departmentField.getText().toString();
        String roleScreen = imageBitmap == null ? "" : getEncodedImage(imageBitmap);

        if (validateUpdateForm(email, secondName, company, department)) {
            new Thread(() -> {
                try {
                    JSONObject paramObj = new JSONObject();
                    paramObj.put("user_id", BaseData.getInstance()._selectedUserId);
                    paramObj.put("type", BaseData.getInstance()._type);
                    paramObj.put("person_name_first", firstName);
                    paramObj.put("person_name_second", secondName);
                    paramObj.put("person_name_first_kana", firstNameKana);
                    paramObj.put("person_name_second_kana", secondNameKana);
                    paramObj.put("nickname", nickName);
                    paramObj.put("email", email);
                    paramObj.put("role", position);
                    paramObj.put("tel1", phone1);
                    paramObj.put("tel2", phone2);
                    paramObj.put("tel3", phone3);
                    paramObj.put("birth_date", birthDate);
                    paramObj.put("gender", gender);
                    paramObj.put("prefecture", prefecture);
                    paramObj.put("city", city);
                    paramObj.put("blood", blood);
                    paramObj.put("zip", zipcode);
                    paramObj.put("company_name", company);
                    paramObj.put("department", department);
                    paramObj.put("role_screen", roleScreen);

                    Log.e("Param", "\"" + roleScreen + "\"");

                    String endpoint = "update-user";
                    String res = controller.updateUser(endpoint, paramObj.toString());
                    Log.e("Result", res);
                    JSONObject obj = new JSONObject(res);

                    String message = obj.getString("message");

                    runOnUiThread(() -> {
                        if (message.equals("success")) {
                            SuccessDialog successDialog = new SuccessDialog(this, getString(R.string.message_update_profile));
                            successDialog.show();
                        }
                    });
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            WarningDialong warningDialong = new WarningDialong(this, getString(R.string.message_require_update));
            warningDialong.show();
        }
    }

    private int generateGender(String param) {
        if (Objects.equals(param, "") || Objects.equals(param, "null")) {
            return 1;
        } else {
            return Integer.parseInt(param);
        }
    }

    private Uri getImageUri() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "captured_image_" + System.currentTimeMillis() + ".jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CapturedImages"); // Scoped Storage: Save under Pictures directory

        // Insert the metadata into MediaStore and get the URI
        return getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
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

    private String getEncodedImage(Bitmap bitmap) {
//        Bitmap scaledImage = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        scaledImage.compress(Bitmap.CompressFormat.WEBP, 50, stream);
//
//        byte[] array = stream.toByteArray();
//        return Base64.encodeToString(array, Base64.DEFAULT);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP, 50, stream);

        byte[] array = stream.toByteArray();
        return Base64.encodeToString(array, Base64.DEFAULT);
    }

    private boolean validateUpdateForm(String email, String name, String company, String department) {
        if (Objects.equals(email, "")) {
            return false;
        }
        if (Objects.equals(name, "")) {
            return false;
        }
        if (Objects.equals(company, "")) {
            return false;
        }
        if (Objects.equals(department, "")) {
            return false;
        }
        return true;
    }
}
