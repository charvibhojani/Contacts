package com.example.contacts;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.Myh> {

    ArrayList<ShowItem> showItems;
    ArrayList<ShowItem> tempshowItems;
    Context context;
    Myclick myclick;

    public ShowAdapter(ArrayList<ShowItem> showItems, Context context, Myclick myclick) {

        this.showItems = showItems;
        this.context = context;
        this.myclick = myclick;
        tempshowItems = new ArrayList<>();
        tempshowItems.addAll(showItems);
    }

    @NonNull
    @Override
    public ShowAdapter.Myh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show, parent, false);

        return new Myh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAdapter.Myh holder, int position) {

        holder.name.setText(showItems.get(position).getName());

        holder.number.setText(showItems.get(position).getNumber());

        Glide.with(context).load(showItems.get(position).getPath()).into(holder.profile);

        holder.ly.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                myclick.getmypos(position, view);
            }
        });

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myclick.more(position, view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return showItems.size();
    }

    public class Myh extends RecyclerView.ViewHolder {
        TextView name, number;
        LinearLayout ly;
        ImageView more, profile;

        public Myh(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            ly = itemView.findViewById(R.id.ly);
            more = itemView.findViewById(R.id.more);
            profile = itemView.findViewById(R.id.profile);
        }
    }

    public interface Myclick {
        public void getmypos(int pos, View view);

        public void more(int pos, View view);
    }

    public void filter(String query) {
        query = query.toLowerCase(Locale.ROOT);
        showItems.clear();

        if (query.length() == 0) {
            showItems.addAll(tempshowItems);


        } else {

            for (ShowItem tempshowItem : tempshowItems) {


                if (tempshowItem.getNumber().toLowerCase(Locale.ROOT).contains(query)) {
                    showItems.add(tempshowItem);
                } else if (tempshowItem.getName().toLowerCase(Locale.ROOT).contains(query)) {
                    showItems.add(tempshowItem);
                }else if (tempshowItem.getAddress().toLowerCase(Locale.ROOT).contains(query)) {
                    showItems.add(tempshowItem);
                }
            }
        }

        notifyDataSetChanged();


    }
}
