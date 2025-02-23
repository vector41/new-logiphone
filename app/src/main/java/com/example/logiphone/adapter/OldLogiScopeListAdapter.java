package com.example.logiphone.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.logiphone.BaseData;
import com.example.logiphone.R;
import com.example.logiphone.model.OldLogiScope;
import com.example.logiphone.ui.ProfileDetail;

import java.util.List;
import java.util.Objects;

public class OldLogiScopeListAdapter extends ArrayAdapter<OldLogiScope> {
    public OldLogiScopeListAdapter(Context context, List<OldLogiScope> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.user_list_item, parent, false);
        }

        OldLogiScope oldLogiScopeItem = getItem(position);
        if (oldLogiScopeItem != null) {
            TextView username = convertView.findViewById(R.id.user_name);
            TextView nickname = convertView.findViewById(R.id.user_nickname);
            TextView telCount = convertView.findViewById(R.id.user_tel_count);
            LinearLayout avatar = convertView.findViewById(R.id.user_avatar_container);

            nickname.setText(oldLogiScopeItem.getNickname());
            username.setText(oldLogiScopeItem.getUsername());
            String countResult = String.valueOf(oldLogiScopeItem.getTelCount());
            avatar.setBackgroundResource(oldLogiScopeItem.getGender() == 1 ? R.drawable.background_green : R.drawable.background_pink);

            if (oldLogiScopeItem.getTelCount() <= 1) {
                convertView.findViewById(R.id.user_tel_badge).setVisibility(View.INVISIBLE);
            } else {
                convertView.findViewById(R.id.user_tel_badge).setVisibility(View.VISIBLE);
                telCount.setText(countResult);
            }

            convertView.findViewById(R.id.user_item_info).setOnClickListener(v -> {
                BaseData.getInstance()._selectedUserId = oldLogiScopeItem.getId();
                BaseData.getInstance()._type = oldLogiScopeItem.getShared();

                Intent intent = new Intent(v.getContext(), ProfileDetail.class);
                v.getContext().startActivity(intent);
            });

            convertView.findViewById(R.id.btn_user_call).setOnClickListener(v -> {
                String number = "";
                if (!Objects.equals(oldLogiScopeItem.getTel1(), "") && !Objects.equals(oldLogiScopeItem.getTel1(), "null") && !Objects.equals(oldLogiScopeItem.getTel1(), "--")) {
                    number = oldLogiScopeItem.getTel1();
                } else if (!Objects.equals(oldLogiScopeItem.getTel2(), "") && !Objects.equals(oldLogiScopeItem.getTel2(), "null") && !Objects.equals(oldLogiScopeItem.getTel2(), "--")) {
                    number = oldLogiScopeItem.getTel2();
                } else if (!Objects.equals(oldLogiScopeItem.getTel3(), "") && !Objects.equals(oldLogiScopeItem.getTel3(), "null") && !Objects.equals(oldLogiScopeItem.getTel3(), "--")) {
                    number = oldLogiScopeItem.getTel3();
                } else {
                    number = "N/A";
                }

                if (!Objects.equals(number, "N/A")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + number));
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                }
            });
        }

        return convertView;
    }
}
