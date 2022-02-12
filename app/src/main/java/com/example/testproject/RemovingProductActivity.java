package com.example.testproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.testproject.Entities.ProductEntity;

import java.util.ArrayList;

public class RemovingProductActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductsForRemovingAdapter productsAdapter;
    private ArrayList<ProductEntity> allProducts;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_removing_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DatabaseHelper dbHelper = new DatabaseHelper(getBaseContext());

        allProducts = dbHelper.getAllProducts();
        recyclerView = findViewById(R.id.removeProductsAllRecyclerView);
        layoutManager = new LinearLayoutManager(this);

        productsAdapter = new ProductsForRemovingAdapter(allProducts);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(productsAdapter);

        productsAdapter.setOnClickListenerProperty(new ProductsForRemovingAdapter.onClickListenerInterface() {
            @Override
            public void onDeleteButtonClick(int position) {
                ProductEntity productForRemoval = allProducts.get(position);
                dbHelper.removeProductCompletely(productForRemoval.getID());
                allProducts.remove(position);
                productsAdapter.notifyItemRemoved(position);
            }

            @Override
            public void onEditButtonClick(int positionOfElement) {
                ProductEntity productForEdit = allProducts.get(positionOfElement);
                Intent productEdit = new Intent(getApplicationContext(), EditProductActivity.class);
                productEdit.putExtra("editProductEntity", productForEdit);
                startActivity(productEdit);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.searcher_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.searchProductSearchView);
        SearchView searchView = (SearchView)menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String writtenText) {
                productsAdapter.getFilter().filter(writtenText);
                return false;
            }
        });
        return true;
    }
}
