package com.example.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.Myh> {

    ArrayList<Contact> contacts;
    ArrayList<Contact> tempcontacts;
    Context context;
    Myclick myclick;

    public ContactAdapter(ArrayList<Contact> contacts, Context context, Myclick myclick) {
        this.contacts = contacts;
        this.context = context;
        this.myclick = myclick;

        tempcontacts = new ArrayList<>();
        tempcontacts.addAll(contacts);
    }

    @NonNull
    @Override
    public ContactAdapter.Myh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_show, parent, false);
        return new Myh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.Myh holder, int position) {
//        holder.Name.SetText(contacts.get(position).getName());

        holder.more.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                myclick.getpos(position, view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class Myh extends RecyclerView.ViewHolder {
        ImageView more, iv;
        TextView etname;
        LinearLayout ll;

        public Myh(@NonNull View itemView) {
            super(itemView);

            etname = itemView.findViewById(R.id.etname);
//            more = itemView.findViewById(R.id.more);
            iv = itemView.findViewById(R.id.iv);
            ll = itemView.findViewById(R.id.ll);
        }
    }

    public interface Myclick {
        public void getpos(int pos, View view);
    }

}
