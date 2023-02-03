package com.example.myapplication.television;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.room_database.TelevisionDatabase;
import com.example.myapplication.room_database.TelevisionModel.Television;
import com.example.myapplication.television.adapter.ParseAdapter;
import com.example.myapplication.television.adapter.ParseAdapterFavourites;

import java.util.ArrayList;
import java.util.List;

public class SelectTvCategory extends Activity {
    RecyclerView recyclerView;
    RecyclerView recyclerViewFav;
    ParseAdapter parseAdapter;
    ParseAdapterFavourites parseAdapterFav;
    List<Television> televisions;
    List<Television> favouriteTelevisions;
    LinearLayoutManager horizontalLayout;
    GridLayoutManager layoutManager;
    Thread thread;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_category);

        InitViews();
        RecyclerViewSetParams();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        InitViews();
        RecyclerViewSetParams();
    }

    private void RecyclerViewSetParams() {
        recyclerView.setHasFixedSize(true);
        recyclerViewFav.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewFav.setLayoutManager(horizontalLayout);

        parseAdapterFav = new ParseAdapterFavourites(favouriteTelevisions, SelectTvCategory.this);
        recyclerViewFav.setAdapter(parseAdapterFav);

        parseAdapter = new ParseAdapter(televisions, SelectTvCategory.this);
        recyclerView.setAdapter(parseAdapter);

    }

    private void InitViews() {
        recyclerViewFav = findViewById(R.id.recyclerViewFav);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new GridLayoutManager(this, checkScreenSizeAndMakeRV());
        horizontalLayout = new LinearLayoutManager(SelectTvCategory.this, LinearLayoutManager.HORIZONTAL, false);
        thread = new Thread(() -> {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            boolean isSeirSandukHost = sharedPreferences.getBoolean("seirSanduk", false);
            if (!isSeirSandukHost) {
                televisions = TelevisionDatabase.getInstance(getApplicationContext()).televisionDao().getAllBgGledaiTV();
                favouriteTelevisions = TelevisionDatabase.getInstance(getApplicationContext()).televisionDao().getAllFavouriteTelevisionsBgGledai();
            } else {
                televisions = TelevisionDatabase.getInstance(getApplicationContext()).televisionDao().getAllSeirSanduk();
                favouriteTelevisions = TelevisionDatabase.getInstance(getApplicationContext()).televisionDao().getAllFavouriteTelevisionsSeirSanduk();
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
    }

    private int checkScreenSizeAndMakeRV() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);

        if (screenInches < 6) {
            return 1;
        } else if (screenInches > 6 && screenInches < 10) {
            return 2;
        } else if (screenInches > 8 && screenInches < 20) {
            return 4;
        } else {
            return 6;
        }
    }
}