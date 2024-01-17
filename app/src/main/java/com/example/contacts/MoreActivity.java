package com.example.contacts;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class MoreActivity extends Activity {

    ImageView share, favorites, edit;
    TextView tvshare, tvfavorites, tvedit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        share = findViewById(R.id.share);
        favorites = findViewById(R.id.favorites);
        edit = findViewById(R.id.edit);
        tvshare = findViewById(R.id.tvshare);
        tvfavorites = findViewById(R.id.tvfavorites);
        tvedit = findViewById(R.id.tvedit);

    }
}
