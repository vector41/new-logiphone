package com.example.logiphone.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.logiphone.model.Member;
import com.example.logiphone.ui.ProfileDetail;

import java.util.List;

public class MemberListAdapter extends ArrayAdapter<Member> {
    public MemberListAdapter(Context context, List<Member> memberList) {
        super(context, 0, memberList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.user_list_item, parent, false);
        }

        Member memberItem = getItem(position);
        if (memberItem != null) {
            TextView nickname = convertView.findViewById(R.id.user_nickname);
            TextView fullName = convertView.findViewById(R.id.user_name);
            LinearLayout avatarContainer = convertView.findViewById(R.id.user_avatar_container);

            nickname.setText(memberItem.getNickname());
            fullName.setText(memberItem.getFullName());
            avatarContainer.setBackgroundResource(memberItem.getGender() == 1 ? R.drawable.background_green : R.drawable.background_pink);

            convertView.findViewById(R.id.user_item_info).setOnClickListener(v -> {
                BaseData.getInstance()._selectedUserId = memberItem.getId();
                BaseData.getInstance()._type = memberItem.getType();

                Intent intent = new Intent(v.getContext(), ProfileDetail.class);
                v.getContext().startActivity(intent);
            });
        }

        return convertView;
    }
}
