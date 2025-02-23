package com.example.logiphone.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logiphone.BaseData;
import com.example.logiphone.R;
import com.example.logiphone.model.Company;

import java.util.List;

public class CompanyListAdapter extends BaseAdapter {
    private Context context;
    private List<Company> companyList;

    public CompanyListAdapter(Context context, List<Company> companyList) {
        this.context = context;
        this.companyList = companyList;
    }

    @Override
    public int getCount() {
        return companyList.size();
    }

    @Override
    public Object getItem(int position) {
        return companyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.spinner_item, null);

        TextView idField = convertView.findViewById(R.id.spinner_id);
        TextView titleField = convertView.findViewById(R.id.spinner_name);

        Company companyItem = companyList.get(position);

        int id = companyItem.getCompanyId();
        idField.setText(String.valueOf(id));
        titleField.setText(companyItem.getBranchName());

        convertView.findViewById(R.id.spinner_item).setOnClickListener(v -> {
            BaseData.getInstance().selectedCompanyId = companyItem.getCompanyId();
            BaseData.getInstance().selectedCompanyName = companyItem.getBranchName();
        });

        return convertView;
    }
}
