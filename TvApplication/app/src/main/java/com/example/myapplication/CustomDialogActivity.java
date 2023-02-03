package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.room_database.TelevisionDatabase;
import com.example.myapplication.room_database.TelevisionModel.Television;

public class CustomDialogActivity extends AppCompatActivity {
    Thread thread;
    String tv_name;
    Television television;
    Button noBtn;
    Button yesBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);
        this.setFinishOnTouchOutside(false);
        noBtn = (Button) findViewById(R.id.no_btn);
        yesBtn = (Button) findViewById(R.id.yes_btn);

        tv_name = getIntent().getStringExtra("tv_name");

        thread = new Thread(() -> {
            try {
                television = TelevisionDatabase.getInstance(getApplicationContext()).televisionDao().getTelevisionByName(tv_name);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (television.isIs_favourite_tv()) {
            Toast.makeText(getApplicationContext(), "This television is already in favourites", Toast.LENGTH_LONG).show();
            finish();
        }

        noBtn.setOnClickListener(v -> {
            finish();
        });


        yesBtn.setOnClickListener(view1 -> {
            thread = new Thread(new Runnable() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void run() {
                    television.setIs_favourite_tv(true);
                    TelevisionDatabase.getInstance(getApplicationContext()).televisionDao().update(television);
                    finish();
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
    }
}
