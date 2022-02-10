package com.example.lab_1_2_onurcansever_c0830345_android.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Product.class}, version = 1)
public abstract class ProductDatabase extends RoomDatabase {

    public abstract ProductDao productDao();
    private static volatile ProductDatabase INSTANCE;
    private static final String DATABASE_NAME = "product_database";
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ProductDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (ProductDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ProductDatabase.class, DATABASE_NAME).addCallback(sRoomDatabaseCallback).build();
                }
            }
        }

        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                ProductDao productDao = INSTANCE.productDao();
                productDao.deleteAllProducts();

                Product product = new Product("MacBook Pro", "M1 Chip will power all your tasks!", 1499.99, 0, 0);
                productDao.insertProduct(product);
            });
        }
    };


}
