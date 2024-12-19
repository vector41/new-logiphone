package com.example.logiphone.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.logiphone.BaseData;
import com.example.logiphone.R;
import com.example.logiphone.model.Favorite;
import com.example.logiphone.ui.ProfileDetail;
import com.example.logiphone.ui.SmsSend;

import java.util.List;

public class FavoriteListAdapter extends ArrayAdapter<Favorite> {
    private Context context;

    public FavoriteListAdapter(Context context, List<Favorite> favoriteList) {
        super(context, 0, favoriteList);
        this.context = context;
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.favorite_item, parent, false);
        }

        Favorite favoriteItem = getItem(position);
        if (favoriteItem != null) {
            BaseData.getInstance()._id = favoriteItem.getId();
            BaseData.getInstance()._type = favoriteItem.getShared();

            TextView nickname = convertView.findViewById(R.id.favorite_avatar_nickname);
            TextView fullname = convertView.findViewById(R.id.favorite_item_name);
            LinearLayout avatarContainer = convertView.findViewById(R.id.favorite_avatar_container);
            LinearLayout shared = convertView.findViewById(R.id.favorite_shared);

            LinearLayout detailGroup = convertView.findViewById(R.id.favorite_contact_details);
            LinearLayout spliter = convertView.findViewById(R.id.favorite_spliter);
            LinearLayout favoriteItemContainer = convertView.findViewById(R.id.favorite_list_item);

            nickname.setText(favoriteItem.getUsername().substring(0));
            fullname.setText(favoriteItem.getUsername());
            if (favoriteItem.getGender() == 1) {
                avatarContainer.setBackgroundResource(R.drawable.background_green);
            } else {
                avatarContainer.setBackgroundResource(R.drawable.background_pink);
            }

            if (favoriteItem.getShared() == 1) {
                shared.setVisibility(View.VISIBLE);
            }

            convertView.findViewById(R.id.favorite_item_info).setOnClickListener(v -> {
                if (detailGroup.getVisibility() == View.VISIBLE) {
                    detailGroup.setVisibility(View.GONE);
                    spliter.setVisibility(View.GONE);
                    favoriteItemContainer.setBackground(null);
                } else {
                    detailGroup.setVisibility(View.VISIBLE);
                    spliter.setVisibility(View.VISIBLE);
                    favoriteItemContainer.setBackgroundResource(R.drawable.bg_favorite_active);
                }
            });

            convertView.findViewById(R.id.btn_switch_sms).setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), SmsSend.class);
                v.getContext().startActivity(intent);
            });

            convertView.findViewById(R.id.btn_switch_detail).setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), ProfileDetail.class);
                v.getContext().startActivity(intent);
            });
        }

        return convertView;
    }
}
