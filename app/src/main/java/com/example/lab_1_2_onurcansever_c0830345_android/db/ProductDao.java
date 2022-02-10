package com.example.lab_1_2_onurcansever_c0830345_android.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProduct(Product product);

    @Query("DELETE FROM product_table")
    void deleteAllProducts();

    @Query("DELETE FROM product_table WHERE id = :id")
    void deleteProductById(int id);

    @Delete
    void deleteProduct(Product product);

    @Update
    void update(Product product);

    @Query("UPDATE product_table SET name = :name, description = :description, price = :price, latitude = :latitude, longitude = :longitude")
    int updateProduct(String name, String description, double price, double latitude, double longitude);

    @Query("SELECT * FROM product_table ORDER BY name")
    LiveData<List<Product>> getAllProducts();


}
