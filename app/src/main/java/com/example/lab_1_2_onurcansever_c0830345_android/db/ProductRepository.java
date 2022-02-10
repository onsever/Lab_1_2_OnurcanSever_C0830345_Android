package com.example.lab_1_2_onurcansever_c0830345_android.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductRepository {
    private ProductDao productDao;
    private LiveData<List<Product>> allProducts;

    public ProductRepository(Application application) {
        ProductDatabase db = ProductDatabase.getInstance(application);
        productDao = db.productDao();
        allProducts = productDao.getAllProducts();
    }

    public LiveData<List<Product>> getAllProducts() { return allProducts; }

    public void insert(Product product) {
        ProductDatabase.databaseWriteExecutor.execute(() -> {
            productDao.insertProduct(product);
        });
    }

    public void delete(Product product) {
        ProductDatabase.databaseWriteExecutor.execute(() -> {
            productDao.deleteProduct(product);
        });
    }

    public void update(Product product) {
        ProductDatabase.databaseWriteExecutor.execute(() -> {
            productDao.update(product);
        });
    }
}
