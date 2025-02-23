package com.example.logiphone.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
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
import com.example.logiphone.alert.ErrorDialog;
import com.example.logiphone.model.Favorite;
import com.example.logiphone.ui.FavoriteList;
import com.example.logiphone.ui.ProfileDetail;
import com.example.logiphone.ui.SmsSend;

import java.util.List;
import java.util.Objects;

public class FavoriteListAdapter extends ArrayAdapter<Favorite> {
    private int selectedPosition = -1;

    public FavoriteListAdapter(Context context, List<Favorite> favoriteList) {
        super(context, 0, favoriteList);
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.favorite_item, parent, false);
        }

        Favorite favoriteItem = getItem(position);
        if (favoriteItem != null) {
            TextView nickname = convertView.findViewById(R.id.favorite_avatar_nickname);
            TextView fullName = convertView.findViewById(R.id.favorite_item_name);
            TextView telCount = convertView.findViewById(R.id.favorite_tel_count);
            LinearLayout avatarContainer = convertView.findViewById(R.id.favorite_avatar_container);
            LinearLayout shared = convertView.findViewById(R.id.favorite_shared);

            LinearLayout detailGroup = convertView.findViewById(R.id.favorite_contact_details);
            View split = convertView.findViewById(R.id.favorite_spliter);
            LinearLayout favoriteItemContainer = convertView.findViewById(R.id.favorite_list_item);

            nickname.setText(favoriteItem.getNickname());
            fullName.setText(favoriteItem.getFullName());
            String countResult = String.valueOf(favoriteItem.getTelCount());

            if (favoriteItem.getTelCount() <= 1) {
                convertView.findViewById(R.id.favorite_tel_badge).setVisibility(View.INVISIBLE);
            } else {
                convertView.findViewById(R.id.favorite_tel_badge).setVisibility(View.VISIBLE);
                telCount.setText(countResult);
            }
            avatarContainer.setBackgroundResource(favoriteItem.getGender() == 1 ? R.drawable.background_green : R.drawable.background_pink);
            shared.setVisibility(favoriteItem.getShared() == 0 ? View.VISIBLE : View.INVISIBLE);

            if (position == selectedPosition) {
                detailGroup.setVisibility(View.VISIBLE);
                split.setVisibility(View.VISIBLE);
                favoriteItemContainer.setBackgroundResource(R.drawable.bg_favorite_active);
            } else {
                detailGroup.setVisibility(View.GONE);
                split.setVisibility(View.GONE);
                favoriteItemContainer.setBackground(null);
            }

            convertView.findViewById(R.id.favorite_item_info).setOnClickListener(v -> {
                selectedPosition = position;
                notifyDataSetChanged();
            });

            convertView.findViewById(R.id.btn_switch_sms).setOnClickListener(v -> {
                String number = "";
                if (!Objects.equals(favoriteItem.getTel1(), "") && !Objects.equals(favoriteItem.getTel1(), "null") && !Objects.equals(favoriteItem.getTel1(), "--")) {
                    number = favoriteItem.getTel1();
                } else if (!Objects.equals(favoriteItem.getTel2(), "") && !Objects.equals(favoriteItem.getTel2(), "null") && !Objects.equals(favoriteItem.getTel2(), "--")) {
                    number = favoriteItem.getTel2();
                } else if (!Objects.equals(favoriteItem.getTel3(), "") && !Objects.equals(favoriteItem.getTel3(), "null") && !Objects.equals(favoriteItem.getTel3(), "--")) {
                    number = favoriteItem.getTel3();
                } else {
                    number = "N/A";
                }

                if (!Objects.equals(number, "N/A")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("sms:+" + number));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    v.getContext().startActivity(intent);
                }
            });

            convertView.findViewById(R.id.btn_switch_detail).setOnClickListener(v -> {
                BaseData.getInstance()._selectedUserId = favoriteItem.getSelectedId();
                BaseData.getInstance()._type = favoriteItem.getShared();

                Intent intent = new Intent(v.getContext(), ProfileDetail.class);
                v.getContext().startActivity(intent);
            });

            convertView.findViewById(R.id.btn_favorite_call).setOnClickListener(v -> {
                String number = "";
                if (!Objects.equals(favoriteItem.getTel1(), "") && !Objects.equals(favoriteItem.getTel1(), "null") && !Objects.equals(favoriteItem.getTel1(), "--")) {
                    number = favoriteItem.getTel1();
                } else if (!Objects.equals(favoriteItem.getTel2(), "") && !Objects.equals(favoriteItem.getTel2(), "null") && !Objects.equals(favoriteItem.getTel2(), "--")) {
                    number = favoriteItem.getTel2();
                } else if (!Objects.equals(favoriteItem.getTel3(), "") && !Objects.equals(favoriteItem.getTel3(), "null") && !Objects.equals(favoriteItem.getTel3(), "--")) {
                    number = favoriteItem.getTel3();
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
