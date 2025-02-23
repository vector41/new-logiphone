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
import com.example.logiphone.model.NewLogiCompany;
import com.example.logiphone.ui.CompanyDetail;
import com.example.logiphone.ui.ProfileDetail;

import java.util.List;

public class NewLogiCompanyAdapter extends ArrayAdapter<NewLogiCompany> {
    public NewLogiCompanyAdapter(Context context, List<NewLogiCompany> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_list_item, parent, false);
        }

        NewLogiCompany logiCompanyItem = getItem(position);
        if (logiCompanyItem != null) {
            TextView companyName = convertView.findViewById(R.id.company_name);
            TextView nickname = convertView.findViewById(R.id.company_nickname);

            nickname.setText(logiCompanyItem.getAvatarName());
            companyName.setText(logiCompanyItem.getBranchName());

            convertView.findViewById(R.id.company_item_info).setOnClickListener(v -> {
                BaseData.getInstance()._selectedCompanyId = logiCompanyItem.getId();
                BaseData.getInstance()._type = logiCompanyItem.getType();

                Intent intent = new Intent(v.getContext(), CompanyDetail.class);
                v.getContext().startActivity(intent);
            });
        }

        return convertView;
    }
}
