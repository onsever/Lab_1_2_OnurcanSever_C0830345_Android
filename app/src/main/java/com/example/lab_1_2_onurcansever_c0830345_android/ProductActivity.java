package com.example.lab_1_2_onurcansever_c0830345_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import com.example.lab_1_2_onurcansever_c0830345_android.databinding.ActivityProductBinding;
import com.example.lab_1_2_onurcansever_c0830345_android.db.Product;
import com.example.lab_1_2_onurcansever_c0830345_android.model.ProductViewModel;

import java.util.List;

public class ProductActivity extends AppCompatActivity implements ProductAdapter.OnItemClickListener, SearchView.OnQueryTextListener {

    private ActivityProductBinding binding;
    private ProductViewModel productViewModel;
    private ProductAdapter productAdapter;
    private double chosenLongitude = 0;
    private double chosenLatitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            this.chosenLongitude = extras.getDouble("savedLongitude");
            this.chosenLatitude = extras.getDouble("savedLatitude");
        }

        System.out.println("Latitude: " + chosenLatitude);
        System.out.println("Longitude: " + chosenLongitude);

        productAdapter = new ProductAdapter(this, this);
        binding.productsRecyclerView.setAdapter(productAdapter);
        binding.productsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        productViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(ProductViewModel.class);

        productViewModel.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productAdapter.setData(products);
            }
        });

        binding.addProductButton.setOnClickListener(v -> {
            addProduct();
        });

        binding.searchDatabaseView.setOnQueryTextListener(this);

    }

    private void addProduct() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductActivity.this);
        LayoutInflater layoutInflater = LayoutInflater.from(ProductActivity.this);
        View view = layoutInflater.inflate(R.layout.add_new_product, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        EditText productName = view.findViewById(R.id.et_name);
        EditText productDescription = view.findViewById(R.id.et_description);
        EditText productPrice = view.findViewById(R.id.et_price);
        Button addProductButton = view.findViewById(R.id.btn_add_product);

        addProductButton.setOnClickListener(v1 -> {
            String name = productName.getText().toString().trim();
            String description = productDescription.getText().toString().trim();
            String price = productPrice.getText().toString().trim();

            if (name.isEmpty()) {
                productName.setError("name field is empty");
                productName.requestFocus();
                return;
            }

            if (description.isEmpty()) {
                productDescription.setError("description field is empty");
                productDescription.requestFocus();
                return;
            }

            if (price.isEmpty()) {
                productPrice.setError("price field is empty");
                productPrice.requestFocus();
                return;
            }

            Product product = new Product(name, description, Double.parseDouble(price), chosenLatitude, chosenLongitude);
            productViewModel.insert(product);

            alertDialog.dismiss();
        });
    }

    private void updateProduct(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductActivity.this);
        LayoutInflater layoutInflater = LayoutInflater.from(ProductActivity.this);
        View view = layoutInflater.inflate(R.layout.update_product, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        EditText productName = view.findViewById(R.id.row_name);
        EditText productDescription = view.findViewById(R.id.row_desc);
        EditText productPrice = view.findViewById(R.id.row_price);
        Button updateProductButton = view.findViewById(R.id.btn_update_product);
        Button deleteProductButton = view.findViewById(R.id.btn_delete_product);

        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productPrice.setText(String.valueOf(product.getPrice()));

        updateProductButton.setOnClickListener(v -> {

            String name = productName.getText().toString().trim();
            String description = productDescription.getText().toString().trim();
            String price = productPrice.getText().toString().trim();

            if (name.isEmpty()) {
                productName.setError("name field is empty");
                productName.requestFocus();
                return;
            }

            if (description.isEmpty()) {
                productDescription.setError("description field is empty");
                productDescription.requestFocus();
                return;
            }

            if (price.isEmpty()) {
                productPrice.setError("price field is empty");
                productPrice.requestFocus();
                return;
            }

            productViewModel.updateProductById(product.getId(), name, description, Double.parseDouble(price), chosenLatitude, chosenLongitude);

            alertDialog.dismiss();

        });

        deleteProductButton.setOnClickListener(v -> {
            productViewModel.delete(product);
            alertDialog.dismiss();
        });

    }

    @Override
    public void onItemClicked(Product product) {
        System.out.println("Product name" + product.getName());
        updateProduct(product);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query != null) {
            searchDatabase(query);
        }

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void searchDatabase(String query) {

        productViewModel.searchProduct(query).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productAdapter.setData(products);
            }
        });
    }
}