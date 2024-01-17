package com.example.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Spinneradapter extends BaseAdapter {

    ArrayList<Cityname> citynames;
    Context context;
    Myclick myclick;

    public Spinneradapter(ArrayList<Cityname> citynames, Context context, Myclick myclick) {
        this.citynames = citynames;
        this.context = context;
        this.myclick = myclick;
    }

    @Override
    public int getCount() {
        return citynames.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.list_items, viewGroup, false);
        TextView tv = view1.findViewById(R.id.tv);
        tv.setText(citynames.get(i).getName());

        return view1;
    }

    public interface Myclick {
        public void getpos(int pos);
    }
}