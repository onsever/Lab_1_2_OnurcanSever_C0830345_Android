package com.example.lab_1_2_onurcansever_c0830345_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_1_2_onurcansever_c0830345_android.databinding.ProductRowBinding;
import com.example.lab_1_2_onurcansever_c0830345_android.db.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private ProductRowBinding binding;
    private List<Product> products;
    private Context context;

    public ProductAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ProductRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        binding.productName.setText(products.get(position).getName());
        binding.productDescription.setText(products.get(position).getDescription());
        binding.productPrice.setText(String.valueOf(products.get(position).getPrice()));
        binding.productLatitude.setText(String.valueOf(products.get(position).getLatitude()));
        binding.productLongitude.setText(String.valueOf(products.get(position).getLongitude()));
    }

    @Override
    public int getItemCount() {
        if (products == null || products.size() == 0) {
            return 0;
        }
        return products.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        public ProductViewHolder(@NonNull ProductRowBinding binding) {
            super(binding.getRoot());
        }
    }
}
