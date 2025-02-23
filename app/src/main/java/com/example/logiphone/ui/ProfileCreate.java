package com.example.logiphone.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.logiphone.BaseData;
import com.example.logiphone.Controller;
import com.example.logiphone.R;
import com.example.logiphone.alert.SuccessDialog;
import com.example.logiphone.alert.WarningDialong;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.zip.GZIPOutputStream;

public class ProfileCreate extends AppCompatActivity {
    private Controller controller;
    private final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_create);

        controller = new Controller();

        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }

        findViewById(R.id.btn_cancel_create).setOnClickListener(v -> {
            super.onBackPressed();
        });

        findViewById(R.id.btn_image_capture).setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imageUri = getImageUri();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        findViewById(R.id.btn_save_profile).setOnClickListener(v -> {
            submitCreateProfile();
        });
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

                ImageView roleScreenView = findViewById(R.id.profile_create_screen);
                roleScreenView.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void submitCreateProfile() {
        EditText firstNameField = (EditText) findViewById(R.id.profile_create_first_name);
        EditText secondNameField = (EditText) findViewById(R.id.profile_create_second_name);
        EditText firstNameFieldKana = (EditText) findViewById(R.id.profile_create_first_name_kana);
        EditText secondNameFieldKana = (EditText) findViewById(R.id.profile_create_second_name_kana);
        EditText nickNameField = (EditText) findViewById(R.id.profile_create_nickname);
        EditText phone1Field = (EditText) findViewById(R.id.profile_create_phone_number1);
        EditText phone2Field = (EditText) findViewById(R.id.profile_create_phone_number2);
        EditText phone3Field = (EditText) findViewById(R.id.profile_create_phone_number3);
//        EditText birthDateField = (EditText) findViewById(R.id.profile_create_birth_date);
        DatePicker birthDateField = findViewById(R.id.profile_create_birth_date);
        Switch genderField = (Switch) findViewById(R.id.profile_create_gender);
        EditText cityField = (EditText) findViewById(R.id.profile_create_city);
        EditText prefectureField = (EditText) findViewById(R.id.profile_create_prefecture);
        EditText zipcodeField = (EditText) findViewById(R.id.profile_create_zipcode);
        EditText positionField = (EditText) findViewById(R.id.profile_create_position);
        EditText bloodField = (EditText) findViewById(R.id.profile_create_blood);

        String firstName = firstNameField.getText().toString();
        String secondName = secondNameField.getText().toString();
        String firstNameKana = firstNameFieldKana.getText().toString();
        String secondNameKana = secondNameFieldKana.getText().toString();
        String nickName = nickNameField.getText().toString();
        String phone1 = phone1Field.getText().toString();
        String phone2 = phone2Field.getText().toString();
        String phone3 = phone3Field.getText().toString();
        int year = birthDateField.getYear();
        int month = birthDateField.getMonth() + 1;
        int day = birthDateField.getDayOfMonth();
        String birthDate = year + "-" + month + "-" + day;
        int gender = genderField.isChecked() ? 0 : 1;
        String city = cityField.getText().toString();
        String prefecture = prefectureField.getText().toString();
        String zipcode = zipcodeField.getText().toString();
        String position = positionField.getText().toString();
        String blood = bloodField.getText().toString();
        String roleScreen = imageBitmap == null ? "" : getEncodedImage(imageBitmap);

        if (validateCreateForm(secondName)) {
            new Thread(() -> {
                try {
                    JSONObject paramObj = new JSONObject();
                    paramObj.put("user_id", BaseData.getInstance()._id);
                    paramObj.put("person_name_first", firstName);
                    paramObj.put("person_name_second", secondName);
                    paramObj.put("person_name_first_kana", firstNameKana);
                    paramObj.put("person_name_second_kana", secondNameKana);
                    paramObj.put("nickname", nickName);
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
                    paramObj.put("role_screen", roleScreen);

                    Log.e("Param", paramObj.toString());
//
//                    try {
//                        FileOutputStream fos = openFileOutput("test.txt", Context.MODE_APPEND);
//                        OutputStreamWriter osw = new OutputStreamWriter(fos);
//                        osw.write(paramObj.toString());
//                        osw.flush();
//                        osw.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                    String endpoint = "add-employee";
                    String res = controller.addNewUser(endpoint, paramObj.toString());
                    JSONObject obj = new JSONObject(res);

                    String message = obj.getString("message");

                    runOnUiThread(() -> {
                        if (message.equals("success")) {
                            SuccessDialog successDialog = new SuccessDialog(this, getString(R.string.message_add_profile));
                            successDialog.show();
                        }
                    });
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            WarningDialong warningDialong = new WarningDialong(this, getString(R.string.message_require_add));
            warningDialong.show();
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

    private boolean validateCreateForm(String name) {
        return !Objects.equals(name, "");
    }

    private String getEncodedImage(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP, 50, stream);

        byte[] array = stream.toByteArray();
        return Base64.encodeToString(array, Base64.DEFAULT);
    }

    private String gzip(String data) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gzipOutputStream.write(data.getBytes(StandardCharsets.UTF_8));
        gzipOutputStream.close();
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
    }
}
