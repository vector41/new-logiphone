package com.example.logiphone.alert;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.logiphone.R;

public class InfoDialog extends Dialog implements View.OnClickListener {
    private String content;

    public InfoDialog(Activity activity, String content) {
        super(activity);
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modal_info);

        findViewById(R.id.submit_ok).setOnClickListener(this);

        TextView modalContent = findViewById(R.id.modal_content);
        modalContent.setText(content);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit_ok) {
            dismiss();
        }
    }
}
