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
import com.example.logiphone.model.FavoriteAdd;
import com.example.logiphone.ui.ProfileDetail;

import java.util.List;

public class FavoriteAddListAdapter extends ArrayAdapter<FavoriteAdd> {
    public FavoriteAddListAdapter(Context context, List<FavoriteAdd> favoriteAddList) {
        super(context, 0, favoriteAddList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.favorite_add_item, parent, false);
        }

        FavoriteAdd favoriteAddItem = getItem(position);
        if (favoriteAddItem != null) {
            TextView nickname = convertView.findViewById(R.id.favorite_add_avatar_nickname);
            TextView fullName = convertView.findViewById(R.id.favorite_add_item_name);
            LinearLayout avatarContainer = convertView.findViewById(R.id.favorite_add_avatar_container);
            LinearLayout shared = convertView.findViewById(R.id.favorite_add_shared);

            nickname.setText(favoriteAddItem.getNickname());
            fullName.setText(favoriteAddItem.getFullName());
            avatarContainer.setBackgroundResource(favoriteAddItem.getGender() == 1 ? R.drawable.background_green : R.drawable.background_pink);
            shared.setVisibility(favoriteAddItem.getType() == 0 ? View.VISIBLE : View.INVISIBLE);

            convertView.findViewById(R.id.favorite_add_item_info).setOnClickListener(v -> {
                BaseData.getInstance()._selectedUserId = favoriteAddItem.getId();
                BaseData.getInstance()._type = favoriteAddItem.getType();

                Intent intent = new Intent(v.getContext(), ProfileDetail.class);
                v.getContext().startActivity(intent);
            });
        }

        return convertView;
    }
}
