package com.example.lab_1_2_onurcansever_c0830345_android;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_1_2_onurcansever_c0830345_android.databinding.ProductRowBinding;
import com.example.lab_1_2_onurcansever_c0830345_android.db.Product;
import com.example.lab_1_2_onurcansever_c0830345_android.db.ProductDatabase;
import com.example.lab_1_2_onurcansever_c0830345_android.db.ProductRepository;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private ProductRowBinding binding;
    private List<Product> products;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public ProductAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
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
        binding.productPrice.setText(String.format(context.getResources().getString(R.string.product_price), products.get(position).getPrice()));
        binding.productLatitude.setText(String.format(context.getResources().getString(R.string.latitude), products.get(position).getLatitude()));
        binding.productLongitude.setText(String.format(context.getResources().getString(R.string.longitude), products.get(position).getLongitude()));

        binding.getRoot().setOnClickListener(v -> {
            onItemClickListener.onItemClicked(products.get(position));
        });
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

    public interface OnItemClickListener {
        void onItemClicked(Product product);
    }
}
