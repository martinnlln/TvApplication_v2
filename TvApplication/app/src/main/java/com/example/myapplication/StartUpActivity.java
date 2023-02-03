package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.television.SelectTvCategory;

public class StartUpActivity extends AppCompatActivity {
    Button tv, movies, youtube, settings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_up_activity);
        tv = findViewById(R.id.television);
        movies = findViewById(R.id.movies);
        youtube = findViewById(R.id.youtube);
        settings = findViewById(R.id.settings);


        onFocusChange(tv);
        onFocusChange(movies);
        onFocusChange(youtube);
        onFocusChange(settings);

        onClick(tv);
        onClick(movies);
        onClick(youtube);
        onClick(settings);
    }

    private void onFocusChange(View view) {
        view.setOnFocusChangeListener((view1, b) -> {
            if (b) {
                view1.setBackgroundResource(R.drawable.gradient_src_hover);
            } else {
                view1.setBackgroundResource(R.drawable.gradient_src);
            }
        });
    }

    public void onClick(View view) {
        view.setOnClickListener(view1 -> {
            if (view1.getId() == R.id.television) {
                startActivity(new Intent(getApplicationContext(), SelectTvCategory.class));
            } else if (view1.getId() == R.id.movies) {
                Toast.makeText(this, "Movies TODO", Toast.LENGTH_SHORT).show();
            } else if (view1.getId() == R.id.youtube) {
                Toast.makeText(this, "YOUTuBE TODO", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            }
        });
    }
}
