package com.example.testproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.testproject.Entities.ProductEntity;

import java.util.ArrayList;
import java.util.List;

public class ProductsInfoAdapter extends RecyclerView.Adapter<ProductsInfoAdapter.ProductViewHolder> {


    private List<ProductEntity> productEntityList;



    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView productTextView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productTextView = itemView.findViewById(R.id.infoProductsTextView);
        }


    }



    public ProductsInfoAdapter(List<ProductEntity> productEntityList)
    {
        this.productEntityList = productEntityList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.info_product_card, viewGroup, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder recepyViewHolder, int position) {
        ProductEntity currentProductEntity = productEntityList.get(position);
        recepyViewHolder.productTextView.setText(currentProductEntity.getProductName());
    }

    @Override
    public int getItemCount() {
        return productEntityList.size();
    }

}
