package com.example.deepblue.miniproject2_1451032;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by deepblue on 8/13/2016.
 */
public class ListMenu extends ArrayAdapter<Food> {
    Context context;
    ArrayList<Food> a;
    int resLayout;

    public ListMenu(Context context, int textViewResourceId, ArrayList<Food> a) {
        super(context, textViewResourceId, a);
        this.a = a;
        resLayout = textViewResourceId;
        this.context = context;
    }
/*
    public ListMenu(Context context) {
        super(context, 0);
        this.context = context;
    }
*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(resLayout, parent, false);

        }

        Food food = getItem(position);
        if (food != null) {
            ((TextView)view.findViewById(R.id.text_view_name)).setText(food.getName());
            ((TextView)view.findViewById(R.id.text_view_price)).setText(String.valueOf(food.getPrice())+"₫");
            ((TextView)view.findViewById(R.id.quantity)).setText("x"+String.valueOf(food.getQuantity()));
        }
        return view;
    }
}
