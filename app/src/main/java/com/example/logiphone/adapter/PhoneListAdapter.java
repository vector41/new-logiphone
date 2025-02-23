package com.example.logiphone.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.logiphone.R;

import java.nio.channels.ClosedByInterruptException;
import java.util.List;

public class PhoneListAdapter extends BaseAdapter {
    private final Context context;
    private final int layoutId;
    private final List<String> phoneList;

    public PhoneListAdapter(Context context, int layoutId, List<String> phoneList) {
        this.context = context;
        this.layoutId = layoutId;
        this.phoneList = phoneList;
    }

    @Override
    public int getCount() {
        return phoneList.size();
    }

    @Override
    public Object getItem(int position) {
        return phoneList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View phoneItem;
        LayoutInflater layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        phoneItem = layout.inflate(layoutId, null);

        TextView numberField = (TextView) phoneItem.findViewById(R.id.phone_number);
        String phone = phoneList.get(position);
        numberField.setText(phone);

        return phoneItem;
    }
}
