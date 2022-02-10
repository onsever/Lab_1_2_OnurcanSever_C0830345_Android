package com.example.lab_1_2_onurcansever_c0830345_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.lab_1_2_onurcansever_c0830345_android.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.showProductsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProductActivity.class);
            startActivity(intent);
        });
    }
}