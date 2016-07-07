package com.kongnan.sidebar.Demo2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kongnan.sidebar.R;

import java.util.List;

/**
 * Created by lixian on 2016/7/4.
 */

public class MyAdapter extends BaseAdapter {
    private List<DataModel> list;
    private LayoutInflater inflater;

    public MyAdapter(Context context, List<DataModel> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.sideworks_main_namelist_item, parent, false);
        TextView indexView = (TextView) convertView.findViewById(R.id.find_nameitem_index);
        TextView nameView = (TextView) convertView.findViewById(R.id.find_nameitem_name);
        indexView.setText(String.valueOf(list.get(position).getIndexName()));
        nameView.setText(list.get(position).getName());
        if (position != 0 && list.get(position - 1).getIndexName() == (list.get(position).getIndexName())) {
            indexView.setVisibility(View.GONE);
        }
        return convertView;
    }
}