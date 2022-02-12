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

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> implements Filterable {


    private List<ProductEntity> productEntityListAll;
    private List<ProductEntity> productEntityList;
    private onClickListenerInterface onClickListenerProperty;

    public interface onClickListenerInterface
    {
        void onItemClick(int position);
    }

    public void setOnClickListenerProperty(onClickListenerInterface onClickListenerProperty)
    {
        this.onClickListenerProperty = onClickListenerProperty;
    }



    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView productTextView;

        public ProductViewHolder(@NonNull View itemView, onClickListenerInterface onClickListenerInput) {
            super(itemView);
            productTextView = itemView.findViewById(R.id.productNameTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onClickListenerInput != null)
                    {
                        int positionOfElement = getAdapterPosition();
                        if(positionOfElement != RecyclerView.NO_POSITION)
                        {
                            onClickListenerInput.onItemClick(positionOfElement);
                        }
                    }
                }
            });
        }


    }



    public ProductsAdapter(List<ProductEntity> productEntityList)
    {
        this.productEntityListAll = new ArrayList<>(productEntityList);
        this.productEntityList = productEntityList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_card, viewGroup, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view, onClickListenerProperty);
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

    public Filter getFilter() {
        return productFilter;
    }

    private Filter productFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ProductEntity> productEntityListFilt = new ArrayList<>();
            if(charSequence == null || charSequence.length() == 0)
            {
                productEntityListFilt.addAll(productEntityListAll);
            }
            else
            {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(ProductEntity product : productEntityListAll)
                {
                    if(product.getProductName().toLowerCase().contains(filterPattern))
                    {
                        product.setIsInDb(true);
                        productEntityListFilt.add(product);
                    }
                }
                if(productEntityListFilt.isEmpty())
                {

                    ProductEntity newProduct = new ProductEntity(filterPattern);
                    newProduct.setIsInDb(false);

                        productEntityListFilt.add(newProduct);

                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = productEntityListFilt;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            productEntityList.clear();
            productEntityList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };
}
