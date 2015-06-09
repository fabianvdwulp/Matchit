package com.example.fabian.matchit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fabian.matchit.GlobalVariables;
import com.example.fabian.matchit.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class lvAdapterShoppingCart extends BaseAdapter {


    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;

    HashMap<String, String> resultp =       new HashMap<String, String>();

    public lvAdapterShoppingCart(Context context,
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

        TextView tvName, tvTotal, tvUnits;
        ImageView ivProductImage;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_shopping_cart_selected, parent, false);

        resultp = data.get(position);
        tvName = (TextView) itemView.findViewById(R.id.tvCategoryName);
        tvName.setText(resultp.get("Name"));

        tvTotal = (TextView) itemView.findViewById(R.id.tvTotal);
        tvTotal.setText(resultp.get("Currency") +" " + resultp.get("Value"));

        tvUnits = (TextView) itemView.findViewById(R.id.tvUnits);
        tvUnits.setText(resultp.get("Units"));

        ivProductImage = (ImageView) itemView.findViewById(R.id.ivProductImage);
        String PictureId = resultp.get("PictureId");
        Picasso.with(context).load(GlobalVariables.URL_PICTUREID +PictureId+ ".jpg").into(ivProductImage);

        return itemView;
    }


}