package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.prefs.Preferences;

public class SettingsActivity extends AppCompatActivity {
    CheckBox seirSanduk, bgGledai;
    Button save;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String url;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        seirSanduk = findViewById(R.id.seirSanuk);
        bgGledai = findViewById(R.id.bgGledai);
        save = findViewById(R.id.saveBtn);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean("seirSanduk", false)) {
            seirSanduk.setChecked(true);
        } else {
            bgGledai.setChecked(true);
        }

        seirSanduk.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                System.out.println(compoundButton.getText().toString());
                bgGledai.setChecked(false);
            } else {
                bgGledai.setChecked(true);
            }
            editor.putBoolean("seirSanduk", true);
            editor.apply();
        });

        bgGledai.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                System.out.println(compoundButton.getText().toString());
                seirSanduk.setChecked(false);
            } else {
                seirSanduk.setChecked(true);
            }
            editor.putBoolean("seirSanduk", false);
            editor.apply();
        });

        save.setOnClickListener(view -> {
            if (bgGledai.isChecked()) {
                url = bgGledai.getText().toString();
            } else {
                url = seirSanduk.getText().toString();
            }
            editor.putString("host", url);
            editor.apply();
            finish();
        });
        save.setOnFocusChangeListener((view, b) -> {
            if (b) {
                view.setBackgroundResource(R.drawable.gradient_src_hover);
            } else {
                view.setBackgroundResource(R.drawable.gradient_src);
            }
        });
    }
}
