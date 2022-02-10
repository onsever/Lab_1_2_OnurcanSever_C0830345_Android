package com.example.lab_1_2_onurcansever_c0830345_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.lab_1_2_onurcansever_c0830345_android.databinding.ActivityProductBinding;
import com.example.lab_1_2_onurcansever_c0830345_android.db.Product;
import com.example.lab_1_2_onurcansever_c0830345_android.model.ProductViewModel;

import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private ActivityProductBinding binding;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ProductAdapter productAdapter = new ProductAdapter(this);
        binding.productsRecyclerView.setAdapter(productAdapter);
        binding.productsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        productViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(ProductViewModel.class);

        productViewModel.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productAdapter.setData(products);
            }
        });
    }
}