package com.example.fabian.matchit.Adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fabian.matchit.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class lvAdapterPath extends BaseAdapter {


    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;

    HashMap<String, String> resultp =                    new HashMap<String, String>();

    public lvAdapterPath(Context context,
                         ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        TextView tvProjectName, tvCategory, tvPath;
        String[] colors;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_one_word, parent, false);

        resultp = data.get(position);
        tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
        tvPath = (TextView) itemView.findViewById(R.id.tvPath);
        tvPath.setText(resultp.get("path"));
        tvCategory.setText(resultp.get("category"));

        return itemView;
    }


}