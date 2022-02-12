package com.example.testproject;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testproject.Entities.ProductEntity;
import com.example.testproject.Entities.RecepyEntity;

import java.util.ArrayList;

public class AddProductsToRecepyActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductsAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ProductEntity> productsList = new ArrayList<ProductEntity>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products_to_recepy);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView recepyTitle = findViewById(R.id.recepyNameProductsTextView);
        ImageButton recepyComplete = findViewById(R.id.addProductsEndImageButton);

        Intent gotIntent = getIntent();
        RecepyEntity receivedRecepyEntity = (RecepyEntity)gotIntent.getSerializableExtra("RecepyEntitySent");
        recepyTitle.setText(receivedRecepyEntity.getRecepyName());

        DatabaseHelper dbHelper = new DatabaseHelper(getBaseContext());



        productsList = dbHelper.getAllProducts();


        recyclerView = findViewById(R.id.productsRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewAdapter = new ProductsAdapter(productsList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerViewAdapter.setOnClickListenerProperty(new ProductsAdapter.onClickListenerInterface() {
            @Override
            public void onItemClick(int position) {
                DatabaseHelper dbHelper = new DatabaseHelper(getBaseContext());
                dbHelper.addProduct(receivedRecepyEntity.getId(), productsList.get(position).getProductName());
                Toast addedProduct = Toast.makeText(getApplicationContext(), "Added product: "+productsList.get(position).getProductName(), Toast.LENGTH_LONG);
                addedProduct.show();
            }
        });

        recepyComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast successfullEnd = Toast.makeText(getApplicationContext(), "You have added recepy successfully! Now get in recepies.", Toast.LENGTH_LONG);
                successfullEnd.show();
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

                recyclerViewAdapter.getFilter().filter(writtenText);
                return false;
            }
        });
        return true;
    }
}
