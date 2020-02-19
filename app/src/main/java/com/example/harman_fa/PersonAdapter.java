package com.example.harman_fa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PersonAdapter extends BaseAdapter {

    Context context;
    List<PersonModel> modelList;

    public PersonAdapter(Context context, List<PersonModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.adapter_person_row, parent, false);

        TextView tvName, tvPhone, tvAddress;

        tvName = convertView.findViewById(R.id.tv_name);
        tvPhone = convertView.findViewById(R.id.tv_phone);
        tvAddress = convertView.findViewById(R.id.tv_address);

        tvName.setText(modelList.get(position).getfName() + " " + modelList.get(position).getlName());
        tvPhone.setText(modelList.get(position).getPhone());
        tvAddress.setText(modelList.get(position).getAddress());

        return convertView;
    }
}
