package com.example.lab_1_2_onurcansever_c0830345_android.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.lab_1_2_onurcansever_c0830345_android.db.Product;
import com.example.lab_1_2_onurcansever_c0830345_android.db.ProductDatabase;
import com.example.lab_1_2_onurcansever_c0830345_android.db.ProductRepository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private ProductRepository repository;
    private final LiveData<List<Product>> allProducts;

    public ProductViewModel(@NonNull Application application) {
        super(application);

        repository = new ProductRepository(application);
        allProducts = repository.getAllProducts();
    }

    public LiveData<List<Product>> getAllProducts() { return allProducts; }

    public void insert(Product product) {
        repository.insert(product);
    }

    public void delete(Product product) {
        repository.delete(product);
    }

    public void update(Product product) {
        repository.update(product);
    }

    public void updateProductById(int id, String name, String description, double price, double latitude, double longitude) {
        repository.updateProductById(id, name, description, price, latitude, longitude);
    }

    public LiveData<List<Product>> searchProduct(String name) {
        return repository.searchProduct(name);
    }
}
