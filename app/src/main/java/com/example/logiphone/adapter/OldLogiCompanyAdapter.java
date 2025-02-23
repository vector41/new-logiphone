package com.example.logiphone.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.logiphone.BaseData;
import com.example.logiphone.R;
import com.example.logiphone.model.OldLogiCompany;
import com.example.logiphone.ui.CompanyDetail;

import java.util.List;

public class OldLogiCompanyAdapter extends ArrayAdapter<OldLogiCompany> {
    public OldLogiCompanyAdapter(Context context, List<OldLogiCompany> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_list_item, parent, false);
        }

        OldLogiCompany logiCompanyItem = getItem(position);
        if (logiCompanyItem != null) {
            TextView companyName = convertView.findViewById(R.id.company_name);
            TextView nickname = convertView.findViewById(R.id.company_nickname);

            nickname.setText(logiCompanyItem.getCompanyNickname());
            companyName.setText(logiCompanyItem.getCompanyName());

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
