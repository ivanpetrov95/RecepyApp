package com.example.testproject;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.testproject.Entities.ProductEntity;
import com.example.testproject.Entities.RecepyEntity;

import java.util.ArrayList;
import java.util.StringJoiner;

public class RecepyInfoActivity extends AppCompatActivity {

    private RecyclerView infoRecepyRecyclerView;
    private ProductsInfoAdapter productsAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepy_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView recepyTitle = findViewById(R.id.recepyInfoTitleTextView);
        TextView recepyDesc = findViewById(R.id.recepyInfoDescTextView);
        DatabaseHelper dbHelper = new DatabaseHelper(getBaseContext());

        Intent gotIntent = getIntent();
        RecepyEntity recepyEntity = (RecepyEntity)gotIntent.getSerializableExtra("resultEntity");
        recepyTitle.setText(recepyEntity.getRecepyName());


        recepyDesc.setText(recepyEntity.getRecepyDescription());
        ArrayList<ProductEntity> allProducts = dbHelper.getAllProductsOfRecepy(recepyEntity.getId());

        infoRecepyRecyclerView = findViewById(R.id.infoProductsInRecepyRecycleView);
        layoutManager = new LinearLayoutManager(this);


        productsAdapter = new ProductsInfoAdapter(allProducts);
        infoRecepyRecyclerView.setLayoutManager(layoutManager);
        infoRecepyRecyclerView.setAdapter(productsAdapter);

    }
}
