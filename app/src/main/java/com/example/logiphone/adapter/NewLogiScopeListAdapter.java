package com.example.logiphone.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.logiphone.BaseData;
import com.example.logiphone.R;
import com.example.logiphone.model.NewLogiScope;
import com.example.logiphone.ui.ProfileDetail;

import java.util.List;
import java.util.Objects;

public class NewLogiScopeListAdapter extends ArrayAdapter<NewLogiScope> {
    public NewLogiScopeListAdapter(Context context, List<NewLogiScope> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_list_item, parent, false);
        }

        NewLogiScope newLogiScopeItem = getItem(position);
        if (newLogiScopeItem != null) {
            TextView username = convertView.findViewById(R.id.user_name);
            TextView nickname = convertView.findViewById(R.id.user_nickname);
            TextView telCount = convertView.findViewById(R.id.user_tel_count);
            LinearLayout avatar = convertView.findViewById(R.id.user_avatar_container);

            nickname.setText(newLogiScopeItem.getNickname());
            username.setText(newLogiScopeItem.getUsername());
            String countResult = String.valueOf(newLogiScopeItem.getTelCount());
            avatar.setBackgroundResource(newLogiScopeItem.getGender() == 0 ? R.drawable.background_pink : R.drawable.background_green);

            if (newLogiScopeItem.getTelCount() <= 1) {
                convertView.findViewById(R.id.user_tel_badge).setVisibility(View.INVISIBLE);
            } else {
                convertView.findViewById(R.id.user_tel_badge).setVisibility(View.VISIBLE);
                telCount.setText(countResult);
            }

            convertView.findViewById(R.id.user_item_info).setOnClickListener(v -> {
                BaseData.getInstance()._selectedUserId = newLogiScopeItem.getId();
                BaseData.getInstance()._type = newLogiScopeItem.getShared();

                Intent intent = new Intent(v.getContext(), ProfileDetail.class);
                v.getContext().startActivity(intent);
            });

            convertView.findViewById(R.id.btn_user_call).setOnClickListener(v -> {
                String number = "";
                if (!Objects.equals(newLogiScopeItem.getTel1(), "") && !Objects.equals(newLogiScopeItem.getTel1(), null) && !Objects.equals(newLogiScopeItem.getTel1(), "--")) {
                    number = newLogiScopeItem.getTel1();
                } else if (!Objects.equals(newLogiScopeItem.getTel2(), "") && !Objects.equals(newLogiScopeItem.getTel2(), null) && !Objects.equals(newLogiScopeItem.getTel2(), "--")) {
                    number = newLogiScopeItem.getTel2();
                } else if (!Objects.equals(newLogiScopeItem.getTel3(), "") && !Objects.equals(newLogiScopeItem.getTel3(), null) && !Objects.equals(newLogiScopeItem.getTel3(), "--")) {
                    number = newLogiScopeItem.getTel3();
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
